package com.zhtkj.jt808.vo;

import io.netty.channel.Channel;

public class PackageData {

	protected MsgHead msgHead;
	protected MsgBody msgBody;
	protected Channel channel;


	public MsgHead getMsgHead() {
		return msgHead;
	}
	
	public void setMsgHead(MsgHead msgHead) {
		this.msgHead = msgHead;
	}

	public MsgBody getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(MsgBody msgBody) {
		this.msgBody = msgBody;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public static class MsgHead {

	    protected int headId;
	    protected int headSerial;
	    protected int bodyLength;
	    protected int encryptType;
	    protected boolean hasSubPack;
	    protected String terminalPhone;
		
		public int getHeadId() {
			return headId;
		}
		
		public void setHeadId(int headId) {
			this.headId = headId;
		}
		
		public int getHeadSerial() {
			return headSerial;
		}
		
		public void setHeadSerial(int headSerial) {
			this.headSerial = headSerial;
		}
		
		public int getBodyLength() {
			return bodyLength;
		}
		
		public void setBodyLength(int bodyLength) {
			this.bodyLength = bodyLength;
		}
		
		public int getEncryptType() {
			return encryptType;
		}
		
		public void setEncryptType(int encryptType) {
			this.encryptType = encryptType;
		}
		
		public boolean isHasSubPack() {
			return hasSubPack;
		}
		
		public void setHasSubPack(boolean hasSubPack) {
			this.hasSubPack = hasSubPack;
		}
		
		public String getTerminalPhone() {
			return terminalPhone;
		}
		
		public void setTerminalPhone(String terminalPhone) {
			this.terminalPhone = terminalPhone;
		}

	}

	public static class MsgBody {

		private int bodyId;
		private int bodySerial;
		private int result;
		private byte[] bodyBytes;
		
		public int getBodyId() {
			return bodyId;
		}
		
		public void setBodyId(int bodyId) {
			this.bodyId = bodyId;
		}
		
		public int getBodySerial() {
			return bodySerial;
		}
		
		public void setBodySerial(int bodySerial) {
			this.bodySerial = bodySerial;
		}
		
		public int getResult() {
			return result;
		}
		
		public void setResult(int result) {
			this.result = result;
		}
		
		public byte[] getBodyBytes() {
			return bodyBytes;
		}
		
		public void setBodyBytes(byte[] bodyBytes) {
			this.bodyBytes = bodyBytes;
		}
		
	}
	
}
