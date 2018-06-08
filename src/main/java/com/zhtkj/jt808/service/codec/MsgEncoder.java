package com.zhtkj.jt808.service.codec;

import java.io.UnsupportedEncodingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zhtkj.jt808.common.JT808Const;
import com.zhtkj.jt808.entity.DataAction;
import com.zhtkj.jt808.entity.DataParam;
import com.zhtkj.jt808.util.ArrayUtil;
import com.zhtkj.jt808.util.DigitUtil;
import com.zhtkj.jt808.vo.PackageData;
import com.zhtkj.jt808.vo.req.EventMsg;
import com.zhtkj.jt808.vo.req.LocationMsg;
import com.zhtkj.jt808.vo.resp.RespMsgBody;

@Component
@Scope("prototype")
public class MsgEncoder {

	//编码整个消息包
	public byte[] encode4Msg(int typeId, String terminalPhone, byte[] bodybs) {
        //消息头
        byte[] headbs = new byte[13];
        headbs[0] = (byte) JT808Const.MSG_DELIMITER;
        //标识Id
        byte[] typeIdbs = DigitUtil.shortTo2Byte((short) typeId);
        headbs[1] = typeIdbs[0];
        headbs[2] = typeIdbs[1];
        //消息体属性
        //先获取消息体长度，并转成2个字节16位格式
        //808规范中用10bit表示长度，所以最大不能超过1024
        byte[] bodylengthbs = new byte[2];
        bodylengthbs = DigitUtil.shortTo2Byte((short) bodybs.length);
        //808规范中用10bit表示长度，所以只取低10bit，0~9
        String bodylengthstr = "" +
                //第一个字节最后2bit
                + (byte) ((bodylengthbs[0] >> 1) & 0x1) + (byte) ((bodylengthbs[0] >> 0) & 0x1)
                //第二个字节8bit
                + (byte) ((bodylengthbs[1] >> 7) & 0x1) + (byte) ((bodylengthbs[1] >> 6) & 0x1)
                + (byte) ((bodylengthbs[1] >> 5) & 0x1) + (byte) ((bodylengthbs[1] >> 4) & 0x1)
                + (byte) ((bodylengthbs[1] >> 3) & 0x1) + (byte) ((bodylengthbs[1] >> 2) & 0x1)
                + (byte) ((bodylengthbs[1] >> 1) & 0x1) + (byte) ((bodylengthbs[1] >> 0) & 0x1);
        //加密方式为不加密，第10、11、12三位都为0，表示消息体不加密
        String encrypt = "000";
        //分包，无分包，第13bit为0
        String subpackage = "0";
        //保留，第14，15bit为0
        String retain = "00";
        //生成消息体属性
        byte[] bodyattrbs = new byte[2];
        //消息体高8位
        bodyattrbs[0] = DigitUtil.binaryStrToByte(retain + subpackage + encrypt + bodylengthstr.substring(0, 2));
        //消息体低8位
        bodyattrbs[1] = DigitUtil.binaryStrToByte(bodylengthstr.substring(2, 10));
        headbs[3] = bodyattrbs[0];
        headbs[4] = bodyattrbs[1];
        //手机号码
        byte[] phonebs = DigitUtil.strToBcd(terminalPhone);
        headbs[5] = phonebs[0];
        headbs[6] = phonebs[1];
        headbs[7] = phonebs[2];
        headbs[8] = phonebs[3];
        headbs[9] = phonebs[4];
        headbs[10] = phonebs[5];
        //消息流水号
        headbs[11] = 0x00;
        headbs[12] = 0x00;
        
        //消息尾
        byte[] tailbs = new byte[] {0x00, (byte) JT808Const.MSG_DELIMITER};
        
        //整个消息
        byte[] msgbs = ArrayUtil.concatAll(headbs, bodybs, tailbs);
        //设置校验码
        msgbs[msgbs.length - 2] = (byte) DigitUtil.get808PackCheckCode(msgbs);
        return msgbs;
	}
	
	//编码通用的应答消息体(主要是指令、参数下发时的应答)
	public byte[] encode4RespBody(int msgType, int result) {
        byte[] src = new byte[2];
        src = DigitUtil.shortTo2Byte((short) msgType);
        byte[] target = new byte[3];
        target[0] = src[0];
        target[1] = src[1];
        target[2] = (byte) result;
        return target;
	}
	
	//生成登录响应包
	public byte[] encode4LoginResp(PackageData packageData, RespMsgBody respMsgBody) {
		byte[] bodybs = this.encode4RespBody(JT808Const.TASK_BODY_ID_LOGIN, respMsgBody.getReplyResult());
		byte[] msgbs = this.encode4Msg(JT808Const.TASK_HEAD_ID, packageData.getMsgHeader().getTerminalPhone(), bodybs);
		return msgbs;
	}
	
	//生成登录响应包
	public byte[] encode4LocationResp(LocationMsg msg, RespMsgBody respMsgBody) {
		byte[] bodybs = this.encode4RespBody(JT808Const.TASK_BODY_ID_GPS, respMsgBody.getReplyResult());
		byte[] msgbs = this.encode4Msg(JT808Const.TASK_HEAD_ID, msg.getMsgHeader().getTerminalPhone(), bodybs);
		return msgbs;
	}
	
	//生成事件响应包
	public byte[] encode4EventResp(EventMsg msg, RespMsgBody respMsgBody) {
		byte[] bodybs = this.encode4RespBody(JT808Const.TASK_BODY_ID_EVENT, respMsgBody.getReplyResult());
		byte[] msgbs = this.encode4Msg(JT808Const.TASK_HEAD_ID, msg.getMsgHeader().getTerminalPhone(), bodybs);
		return msgbs;
	}
	
	//编码通用的要下发的指令消息体
	public byte[] encode4SendActionBody(int msgType, DataAction action) {
        byte[] msgTypebs = new byte[2];
        msgTypebs = DigitUtil.shortTo2Byte((short) msgType);
        byte[] serialbs = new byte[4];
        serialbs = DigitUtil.intTo4Byte(action.getActionId());
        byte[] valuebs = new byte[] {(byte)action.getActionValue()};
		return ArrayUtil.concatAll(msgTypebs, serialbs, valuebs);
	}
	
	//编码抓拍指令消息体
	public byte[] encode4ImageActionBody(int msgType, DataAction action) {
        byte[] msgTypebs = new byte[2];
        msgTypebs = DigitUtil.shortTo2Byte((short) msgType);
        byte[] serialbs = new byte[4];
        serialbs = DigitUtil.intTo4Byte(action.getActionId());
        byte[] valuebs = new byte[] {(byte)2,(byte)2,(byte)1,(byte)1,(byte)1,(byte)0,(byte)0};
		return ArrayUtil.concatAll(msgTypebs, serialbs, valuebs);
	}

	//编码密码指令消息体
	public byte[] encode4PasswordActionBody(int msgType, DataAction action, String carPassword) {
        byte[] msgTypebs = new byte[2];
        msgTypebs = DigitUtil.shortTo2Byte((short) msgType);
        byte[] serialbs = new byte[4];
        serialbs = DigitUtil.intTo4Byte(action.getActionId());
		return ArrayUtil.concatAll(msgTypebs, serialbs, carPassword.getBytes());
	}
	
	//编码参数消息体（直接取值）
	public byte[] encode4SendDirectParamBody(int msgType, DataParam param) throws UnsupportedEncodingException {
        byte[] msgTypebs = new byte[2];
        msgTypebs = DigitUtil.shortTo2Byte((short) msgType);
        byte[] serialbs = new byte[4];
        serialbs = DigitUtil.intTo4Byte(param.getParamId());
		return ArrayUtil.concatAll(msgTypebs, serialbs, 
				new byte[] {param.getParamDeal()}, 
				new byte[] {param.getLimitValue()},
				param.getTypeValue().getBytes("GBK"));
	}
	
	//编码参数消息体（围栏类型）
	public byte[] encode4SendFenceParamBody(int msgType, DataParam param) throws UnsupportedEncodingException {
        byte[] msgTypebs = new byte[2];
        msgTypebs = DigitUtil.shortTo2Byte((short) msgType);
        byte[] serialbs = new byte[4];
        serialbs = DigitUtil.intTo4Byte(param.getParamId());
        //将gps坐标点转成byte[]
        String[] points = param.getTypeValue().split(";");
        byte[] pointbs = new byte[points.length * 8];
        for (int i = 0; i < points.length; i++) {
            String[] point = points[i].split(",");
            float x = Float.parseFloat(point[0]);
            float y = Float.parseFloat(point[1]);
            x = x * 1000000 * 9 / 25;
            y = y * 1000000 * 9 / 25;
            int ix = (int)x;
            int iy = (int)y;
            byte[] bsx = new byte[4];
            byte[] bsy = new byte[4];
            bsx = DigitUtil.int32To4Byte(ix);
            bsy = DigitUtil.int32To4Byte(iy);
            pointbs[i * 8 + 0] = bsx[0];
            pointbs[i * 8 + 1] = bsx[1];
            pointbs[i * 8 + 2] = bsx[2];
            pointbs[i * 8 + 3] = bsx[3];
            pointbs[i * 8 + 4] = bsy[0];
            pointbs[i * 8 + 5] = bsy[1];
            pointbs[i * 8 + 6] = bsy[2];
            pointbs[i * 8 + 7] = bsy[3];
        }
		return ArrayUtil.concatAll(msgTypebs, serialbs, 
				new byte[] {param.getParamDeal()}, 
				new byte[] {param.getLimitValue()},
				pointbs);
	}
}
