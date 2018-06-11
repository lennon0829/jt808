package com.zhtkj.jt808.scheduler;

import java.util.Iterator;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhtkj.jt808.mapper.CarRuntimeMapper;
import com.zhtkj.jt808.server.SessionManager;
import com.zhtkj.jt808.vo.Session;

import io.netty.channel.Channel;

@Component
public class TerminalScheduler {

	@Autowired
	private CarRuntimeMapper carRuntimeMapper;
	
	//移除没有持续上报数据的终端session, 并更改车辆实时表的车辆状态为离线状态
	@Scheduled(cron = "0 6/6 * * * ? ")
	public void removeIdleSession() {
		Iterator<Entry<String, Session>> iterator = 
				SessionManager.getInstance().getSessionMap().entrySet().iterator();
		DateTime now = DateTime.now();
		while (iterator.hasNext()) {
			Session session = iterator.next().getValue();
			DateTime lastCommunicateTime = session.getLastCommunicateTime();
			if (lastCommunicateTime == null || lastCommunicateTime.plusMinutes(6).isBefore(now)) {
				Channel channel = session.getChannel();
				if (channel.isOpen()) {
					channel.close();
				}
				iterator.remove();
			}
		}
		String idleTime = DateTime.now().minusMinutes(6).toString("yyyy-MM-dd HH:mm:ss");
		carRuntimeMapper.setCarOfflineState(idleTime);
	}
	
}
