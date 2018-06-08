package com.zhtkj.jt808.vo.req;

import com.zhtkj.jt808.vo.PackageData;

public class TerminalMsg extends PackageData {

	/**
	 * @Fields terminalInfo : 终端信息
	 */
	private TerminalInfo terminalInfo;
	
	public TerminalMsg() {
		
	}
	
	public TerminalMsg(PackageData packageData) {
		this();
		this.channel = packageData.getChannel();
		this.msgHeader = packageData.getMsgHeader();
		this.msgBody = packageData.getMsgBody();
	}
	
	public TerminalInfo getTerminalInfo() {
		return terminalInfo;
	}

	public void setTerminalInfo(TerminalInfo terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public static class TerminalInfo {
		
		private String mac;
		private String carNumber;
		private String devPhone;
		private String ecuType;
		private String carType;
		private String version;
		
		public String getMac() {
			return mac;
		}
		
		public void setMac(String mac) {
			this.mac = mac;
		}
		
		public String getCarNumber() {
			return carNumber;
		}
		
		public void setCarNumber(String carNumber) {
			this.carNumber = carNumber;
		}
		
		public String getDevPhone() {
			return devPhone;
		}
		
		public void setDevPhone(String devPhone) {
			this.devPhone = devPhone;
		}
		
		public String getEcuType() {
			return ecuType;
		}
		
		public void setEcuType(String ecuType) {
			this.ecuType = ecuType;
		}
		
		public String getCarType() {
			return carType;
		}
		
		public void setCarType(String carType) {
			this.carType = carType;
		}
		
		public String getVersion() {
			return version;
		}
		
		public void setVersion(String version) {
			this.version = version;
		}
	}
}
