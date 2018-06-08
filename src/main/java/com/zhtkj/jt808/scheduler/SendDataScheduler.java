package com.zhtkj.jt808.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhtkj.jt808.service.ServerMsgProcessService;

@Component
public class SendDataScheduler {

	@Autowired
	private ServerMsgProcessService serverMsgProcessService;
	
	@Scheduled(cron = "10/15 * * * * ?")
	public void sendActionData(){
		try {
			serverMsgProcessService.processSendActionMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "12/15 * * * * ?")
	public void sendParamData(){
		try {
			serverMsgProcessService.processSendParamMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
