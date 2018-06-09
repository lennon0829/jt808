package com.zhtkj.jt808.server;

import com.zhtkj.jt808.common.JT808Const;
import com.zhtkj.jt808.service.TerminalMsgProcessService;
import com.zhtkj.jt808.service.codec.MsgDecoder;
import com.zhtkj.jt808.vo.PackageData;
import com.zhtkj.jt808.vo.PackageData.MsgBody;
import com.zhtkj.jt808.vo.req.EventMsg;
import com.zhtkj.jt808.vo.req.LocationMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private MsgDecoder msgDecoder;
    private TerminalMsgProcessService msgProcessService;
    
    public ServerHandler() {
        this.msgDecoder = ServerApplication.appCtx.getBean(MsgDecoder.class);
        this.msgProcessService = ServerApplication.appCtx.getBean(TerminalMsgProcessService.class);
    }
    
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
		try {
			ByteBuf buf = (ByteBuf) msg;
			if (buf.readableBytes() <= 0) {
				return;
			}
			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);
			// 字节数据转换为针对于808消息结构的业务对象
			PackageData pkg = this.msgDecoder.bytes2PackageData(bs);
			// 引用channel,以便回送数据给终端
			pkg.setChannel(ctx.channel());
			this.processPackageData(pkg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SessionManager.getInstance().removeSessionByChannelId(ctx.channel().id().asLongText());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
	}
	
	//业务处理逻辑
	private void processPackageData(PackageData packageData) throws Exception {
		MsgBody body = packageData.getMsgBody();
		//任务类业务处理，这里是接收终端主动上报的信息，包括登录、上报的位置信息、上报的事件等等
		if (body.getType() == JT808Const.TASK_BODY_ID_LOGIN) {
			this.msgProcessService.processLoginMsg(packageData);
		} else if (body.getType() == JT808Const.TASK_BODY_ID_GPS) {
			LocationMsg msg = this.msgDecoder.toLocationMsg(packageData);
			this.msgProcessService.processLocationMsg(msg);
		} else if (body.getType() == JT808Const.TASK_BODY_ID_EVENT) {
			EventMsg msg = this.msgDecoder.toEventMsg(packageData);
			this.msgProcessService.processEventMsg(msg);
		} else if (body.getType() == JT808Const.TASK_BODY_ID_SELFCHECK) {
			this.msgProcessService.processSelfCheckMsg(packageData);
		}
		
		//命令类业务处理，在这里是接收下发命令的响应，不是下发命令
		if (body.getType() == JT808Const.ACTION_BODY_ID_LOCKCAR ||
			body.getType() == JT808Const.ACTION_BODY_ID_LIMITSPEED ||
			body.getType() == JT808Const.ACTION_BODY_ID_LIMITUP ||
			body.getType() == JT808Const.ACTION_BODY_ID_PASSWORD ||
			body.getType() == JT808Const.ACTION_BODY_ID_CONTROL ||
			body.getType() == JT808Const.ACTION_BODY_ID_LOCKCARCOMPANY) {
			this.msgProcessService.processActionMsg(packageData);
		} else if (body.getType() == JT808Const.ACTION_BODY_ID_CATCHIMG) {
			this.msgProcessService.processCatchImgMsg(packageData);
		}
		
		//参数类业务处理，在这里是接收下发参数的响应，不是下发参数
		if (body.getType() == JT808Const.PARAM_BODY_ID_LINE ||
			body.getType() == JT808Const.PARAM_BODY_ID_GONG ||
			body.getType() == JT808Const.PARAM_BODY_ID_XIAO ||
			body.getType() == JT808Const.PARAM_BODY_ID_LIMSPCIRCLE ||
			body.getType() == JT808Const.PARAM_BODY_ID_PARKING ||
			body.getType() == JT808Const.PARAM_BODY_ID_BAN ||
			body.getType() == JT808Const.PARAM_BODY_ID_WORKPASSPORT ||
			body.getType() == JT808Const.PARAM_BODY_ID_INFO ||
			body.getType() == JT808Const.PARAM_BODY_ID_FINGER ||
			body.getType() == JT808Const.PARAM_BODY_ID_REDLIGHT ||
			body.getType() == JT808Const.PARAM_BODY_ID_DEVICECONFIG ||
			body.getType() == JT808Const.PARAM_BODY_ID_LOCKCAREXT ||
			body.getType() == JT808Const.PARAM_BODY_ID_NOTIFY ||
			body.getType() == JT808Const.PARAM_BODY_ID_CONTROLSWITCH ||
			body.getType() == JT808Const.PARAM_BODY_ID_THRESHOLDVALUE) {
			this.msgProcessService.processParamMsg(packageData);
		}
	}
}
