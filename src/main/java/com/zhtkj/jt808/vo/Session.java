package com.zhtkj.jt808.vo;

import org.joda.time.DateTime;

import io.netty.channel.Channel;

public class Session {

	private String id;
	private Channel channel;
	private DateTime lastCommunicateTime;
	
	public Session(String terminalPhone, Channel channel) {
		this.id = channel.id().asLongText();
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", channel=" + channel + "]";
	}

	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	
	public DateTime getLastCommunicateTime() {
		return lastCommunicateTime;
	}
	
	public void setLastCommunicateTime(DateTime lastCommunicateTime) {
		this.lastCommunicateTime = lastCommunicateTime;
	}

}