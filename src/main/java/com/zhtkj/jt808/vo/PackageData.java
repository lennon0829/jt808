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

	    protected int msgHeadId;
	    
	    protected int msgSerial;
	    
	    protected int msgBodyLength;
	    
	    protected int encryptType;
	    
	    protected boolean hasSubPack;
	    
	    protected String terminalPhone;

		public int getMsgHeadId() {
			return msgHeadId;
		}

		public void setMsgHeadId(int msgHeadId) {
			this.msgHeadId = msgHeadId;
		}

		public int getMsgBodyLength() {
			return msgBodyLength;
		}
		
		public int getMsgSerial() {
			return msgSerial;
		}

		public void setMsgSerial(int msgSerial) {
			this.msgSerial = msgSerial;
		}

		public void setMsgBodyLength(int msgBodyLength) {
			this.msgBodyLength = msgBodyLength;
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
		
		private int serialId;
		
		private int result;
		
		private byte[] msgBodyBytes;
		
		public int getBodyId() {
			return bodyId;
		}

		public void setBodyId(int bodyId) {
			this.bodyId = bodyId;
		}

		public int getSerialId() {
			return serialId;
		}

		public void setSerialId(int serialId) {
			this.serialId = serialId;
		}

		public int getResult() {
			return result;
		}

		public void setResult(int result) {
			this.result = result;
		}

		
		public byte[] getMsgBodyBytes() {
			return msgBodyBytes;
		}

		
		public void setMsgBodyBytes(byte[] msgBodyBytes) {
			this.msgBodyBytes = msgBodyBytes;
		}
		
	}
	
}
