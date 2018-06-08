package com.zhtkj.jt808.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.zhtkj.jt808.common.JT808Const;
import com.zhtkj.jt808.entity.CarRuntime;
import com.zhtkj.jt808.entity.DataAction;
import com.zhtkj.jt808.entity.DataParam;
import com.zhtkj.jt808.mapper.gateway.CarRuntimeMapper;
import com.zhtkj.jt808.mapper.gateway.DataActionMapper;
import com.zhtkj.jt808.mapper.gateway.DataParamMapper;
import com.zhtkj.jt808.service.codec.MsgEncoder;

import io.netty.channel.Channel;

@Service
public class ServerMsgProcessService extends BaseMsgProcessService {

    @Autowired
    private TaskExecutor taskExecutor;
    
	@Autowired
    private MsgEncoder msgEncoder;
    
    @Autowired
	private CarRuntimeMapper carRuntimeMapper;
	
	@Autowired
	private DataActionMapper dataActionMapper;
	
	@Autowired
    private DataParamMapper dataParamMapper;
    
    //处理要发送给终端的指令
    public void processSendActionMsg() throws Exception {
    	taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
            	List<DataAction> actions = dataActionMapper.findSendActionData();
            	for (DataAction action: actions) {
            		try {
        				int actionType = action.getActionType();
        				byte[] bodybs = null;
        				if (actionType == 1) {
        					bodybs = msgEncoder.encode4SendActionBody(JT808Const.ACTION_BODY_ID_LOCKCAR, action);
        				} else if (actionType == 2) {
        					bodybs = msgEncoder.encode4SendActionBody(JT808Const.ACTION_BODY_ID_LIMITSPEED, action);
        				} else if (actionType == 3) {
        					bodybs = msgEncoder.encode4SendActionBody(JT808Const.ACTION_BODY_ID_LIMITUP, action);
        				} else if (actionType == 6) {
        					bodybs = msgEncoder.encode4SendActionBody(JT808Const.ACTION_BODY_ID_CONTROL, action);
        				} else if (actionType == 7) {
        					bodybs = msgEncoder.encode4SendActionBody(JT808Const.ACTION_BODY_ID_LOCKCARCOMPANY, action);
        				}
        				//抓拍指令
        				if (actionType == 4) {
        					bodybs = msgEncoder.encode4CatchImgActionBody(JT808Const.ACTION_BODY_ID_CATCHIMG, action);
        				}
        				//密码指令
        				if (actionType == 5) {
        					List<CarRuntime> carRuntimes = carRuntimeMapper.findCarPassword(action.getPhoneNumber());
        					if (carRuntimes.size() > 0 && carRuntimes.get(0).getCarPassport() != null) {
        						bodybs = msgEncoder.encode4PasswordActionBody(
        								JT808Const.ACTION_BODY_ID_PASSWORD, action, carRuntimes.get(0).getCarPassport());
        					} else {
        						continue;
        					}
        				}
        				byte[] msgbs = msgEncoder.encode4Msg(JT808Const.ACTION_HEAD_ID, action.getPhoneNumber(), bodybs);
        				Channel channel = sessionManager.getChannelByKey(action.getPhoneNumber());
        				if (channel != null && channel.isActive()) {
        					send2Terminal(channel, msgbs);
        					dataActionMapper.updateActionReceiveResult(action.getActionId(), 1);
        				} else {
        					dataActionMapper.updateActionReceiveResult(action.getActionId(), -1);
        				}
        			} catch (Exception e) {
        				dataActionMapper.updateActionReceiveResult(action.getActionId(), -1);
        				e.printStackTrace();
        			}
            	}
            }
        });
    }
    
    //处理要发送给终端的参数
    public void processSendParamMsg() throws Exception {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
            	List<DataParam> params = dataParamMapper.findSendParamData();
            	for (DataParam param: params) {
            		try {
        				int paramType = param.getParamType();
        				byte[] bodybs = null;
        				if (paramType == 1) {
        					bodybs = msgEncoder.encode4SendFenceParamBody(JT808Const.PARAM_BODY_ID_LINE, param);
        				} else if (paramType == 2) {
        					bodybs = msgEncoder.encode4SendFenceParamBody(JT808Const.PARAM_BODY_ID_GONG, param);
        				} else if (paramType == 3) {
        					bodybs = msgEncoder.encode4SendFenceParamBody(JT808Const.PARAM_BODY_ID_XIAO, param);
        				} else if (paramType == 4) {
        					bodybs = msgEncoder.encode4SendFenceParamBody(JT808Const.PARAM_BODY_ID_LIMSPCIRCLE, param);
        				} else if (paramType == 5) {
        					bodybs = msgEncoder.encode4SendFenceParamBody(JT808Const.PARAM_BODY_ID_PARKING, param);
        				} else if (paramType == 6) {
        					bodybs = msgEncoder.encode4SendFenceParamBody(JT808Const.PARAM_BODY_ID_BAN, param);
        				}
        				if (paramType == 7) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_WORKPASSPORT, param);
        				} else if (paramType == 8) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_INFO, param);
        				} else if (paramType == 9) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_FINGER, param);
        				} else if (paramType == 10) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_REDLIGHT, param);
        				} else if (paramType == 11) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_DEVICECONFIG, param);
        				} else if (paramType == 12) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_LOCKCAREXT, param);
        				} else if (paramType == 13) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_CONTROLSWITCH, param);
        				} else if (paramType == 14) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_THRESHOLDVALUE, param);
        				} else if (paramType == 15) {
        					bodybs = msgEncoder.encode4SendDirectParamBody(JT808Const.PARAM_BODY_ID_NOTIFY, param);
        				}
        				byte[] msgbs = msgEncoder.encode4Msg(JT808Const.PARAM_HEAD_ID, param.getPhoneNumber(), bodybs);
        				Channel channel = sessionManager.getChannelByKey(param.getPhoneNumber());
        				if (channel != null && channel.isActive()) {
        					send2Terminal(channel, msgbs);
        					dataParamMapper.updateParamReceiveResult(param.getParamId(), 1);
        				} else {
        					dataParamMapper.updateParamReceiveResult(param.getParamId(), -1);
        				}
        			} catch (Exception e) {
        				dataParamMapper.updateParamReceiveResult(param.getParamId(), -1);
        				e.printStackTrace();
        			}
            	}
            }
        });
    }
}
