package com.zhtkj.jt808.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhtkj.jt808.service.ServerMsgProcessService;

@Component
public class SendDataScheduler {

	private static final Logger logger = LoggerFactory.getLogger(SendDataScheduler.class);
	
	@Autowired
	private ServerMsgProcessService serverMsgProcessService;
	
	@Scheduled(cron = "10/15 * * * * ?")
	public void sendActionData() {
		try {
			serverMsgProcessService.processSendActionMsg();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "12/15 * * * * ?")
	public void sendParamData() {
		try {
			serverMsgProcessService.processSendParamMsg();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
