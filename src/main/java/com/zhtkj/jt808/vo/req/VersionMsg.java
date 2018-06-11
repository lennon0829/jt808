package com.zhtkj.jt808.vo.req;

import com.zhtkj.jt808.vo.PackageData;

public class VersionMsg extends PackageData {

	/**
	 * @Fields versionInfo : 终端版本信息
	 */
	private VersionInfo versionInfo;
	
	public VersionMsg() {
		
	}
	
	public VersionMsg(PackageData packageData) {
		this();
		this.channel = packageData.getChannel();
		this.msgHead = packageData.getMsgHead();
		this.msgBody = packageData.getMsgBody();
	}
	
	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

	public static class VersionInfo {
		
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
