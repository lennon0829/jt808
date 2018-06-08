package com.zhtkj.jt808.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.zhtkj.jt808.common.JT808Const;
import com.zhtkj.jt808.mapper.gateway.CarEventMapper;
import com.zhtkj.jt808.mapper.gateway.CarHistoryMapper;
import com.zhtkj.jt808.mapper.gateway.CarRuntimeMapper;
import com.zhtkj.jt808.mapper.gateway.DataActionMapper;
import com.zhtkj.jt808.mapper.gateway.DataParamMapper;
import com.zhtkj.jt808.service.codec.MsgEncoder;
import com.zhtkj.jt808.vo.PackageData;
import com.zhtkj.jt808.vo.PackageData.MsgBody;
import com.zhtkj.jt808.vo.Session;
import com.zhtkj.jt808.vo.req.EventMsg;
import com.zhtkj.jt808.vo.req.EventMsg.EventInfo;
import com.zhtkj.jt808.vo.req.LocationMsg;
import com.zhtkj.jt808.vo.req.LocationMsg.LocationInfo;
import com.zhtkj.jt808.vo.resp.RespMsgBody;

@Service
@Scope("prototype")
public class TerminalMsgProcessService extends BaseMsgProcessService {

	@Autowired
    private MsgEncoder msgEncoder;

	@Autowired
    private CarRuntimeMapper carRuntimeMapper;
    
	@Autowired
    private CarHistoryMapper carHistoryMapper;
	
	@Autowired
    private CarEventMapper carEventMapper;
    
	@Autowired
    private DataActionMapper dataActionMapper;
    
	@Autowired
    private DataParamMapper dataParamMapper;
    
    //处理终端登录业务
    public void processLoginMsg(PackageData packageData) throws Exception {
        byte[] bs = this.msgEncoder.encode4LoginResp(packageData, new RespMsgBody((byte) 1));
        String terminalPhone = packageData.getMsgHeader().getTerminalPhone();
        Session session = new Session(terminalPhone, packageData.getChannel());
        sessionManager.addSession(terminalPhone, session);
        carRuntimeMapper.setCarOnlineState(terminalPhone);
        super.send2Terminal(packageData.getChannel(), bs);
    }

    //处理基本位置信息业务
    public void processLocationMsg(LocationMsg msg) throws Exception {
    	LocationInfo locationInfo = msg.getLocationInfo();
    	if (carRuntimeMapper.updateCarRuntime(locationInfo) == 0) {
    		carRuntimeMapper.insertCarRuntime(locationInfo);
    	}
    	carHistoryMapper.insertCarHistory(DateTime.now().toString("M"), locationInfo);
    	Session session = sessionManager.findSessionByKey(msg.getMsgHeader().getTerminalPhone());
    	if (session != null) {
    		session.setLastCommunicateTime(DateTime.now());
    	}
        byte[] bs = this.msgEncoder.encode4LocationResp(msg, new RespMsgBody((byte) 1));
        super.send2Terminal(msg.getChannel(), bs);
    }
    
    //处理事件业务
    public void processEventMsg(EventMsg msg) throws Exception {
    	EventInfo eventInfo = msg.getEventInfo();
    	carEventMapper.insertCarEvent(DateTime.now().toString("M"), eventInfo, eventInfo.getLocationInfo());
    }
    
    //处理自检信息业务
    public void processSelfCheckMsg(PackageData packageData) {
    	carRuntimeMapper.setCarOnlineState(packageData.getMsgHeader().getTerminalPhone());
    }
    
    //处理指令业务，这里是处理终端返回的指令执行响应,不是下发指令
    public void processActionMsg(PackageData packageData) {
    	dataActionMapper.updateActionDealResult(packageData.getMsgBody());
    }
    
    //处理抓拍业务
    public void processCatchImgMsg(PackageData packageData) throws Exception {
    	MsgBody msgBody = packageData.getMsgBody();
    	byte[] msgBodyBytes = msgBody.getMsgBodyBytes();
    	long serialId = msgBody.getSerialId();
    	//如果流水号很大则是事件抓拍，否则就是指令抓拍
    	if (serialId > 888888) {
    		//这里好像没什么用吧
    	} else {
    		//保存图片到服务器
			File file = new File(JT808Const.IMAGE_SAVE_PATH + serialId + ".jpg");
		    try (FileOutputStream out = new FileOutputStream(file)) {
				out.write(msgBodyBytes, 6, msgBodyBytes.length - 6); //减去标示符2位，唯一ID4位，共6位。
				out.flush();
		    } catch (IOException e) {
		        throw new RuntimeException(e.getMessage(), e);
		    }
		    msgBody.setResult(1);
		    //更新抓拍指令执行结果
		    this.processActionMsg(packageData);
    	}
    }
    
    //处理参数业务，这里是处理终端返回的参数执行响应
    public void processParamMsg(PackageData packageData) {
    	dataParamMapper.updateParamResult(packageData.getMsgBody());
    }
}
