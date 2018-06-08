package com.zhtkj.jt808.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.zhtkj.jt808.vo.Session;

import io.netty.channel.Channel;

public class SessionManager {

	private static volatile SessionManager instance = null;

	//key:终端手机号, value:session
	private static Map<String, Session> sessionMap;

	private SessionManager() {
		sessionMap = new ConcurrentHashMap<>();
	}
	
	public static SessionManager getInstance() {
		if (instance == null) {
			synchronized (SessionManager.class) {
				if (instance == null) {
					instance = new SessionManager();
				}
			}
		}
		return instance;
	}

	public Map<String, Session> getSessionMap() {
		return sessionMap;
	}
	
	public synchronized boolean containsKey(String terminalPhone) {
		return sessionMap.containsKey(terminalPhone);
	}
	
	public synchronized Channel getChannelByKey(String terminalPhone) {
		if (sessionMap.get(terminalPhone) == null) {
			return null;
		} else {
			return sessionMap.get(terminalPhone).getChannel();
		}
	}
	
	public synchronized Session addSession(String terminalPhone, Session session) {
		return sessionMap.put(terminalPhone, session);
	}
	
	public synchronized Session findSessionByKey(String terminalPhone) {
		return sessionMap.get(terminalPhone);
	}

	public synchronized void removeSessionByKey(String terminalPhone) {
		sessionMap.remove(terminalPhone);
	}

	public synchronized void removeSessionByChannelId(String channelId) {
		Iterator<Entry<String, Session>> iterator = 
				SessionManager.getInstance().getSessionMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next().getValue().getChannel();
			if (channel.id().asLongText().equals(channelId)) {
				if (channel.isOpen()) {
					channel.close();
				}
				iterator.remove();
			}
		}
	}
}