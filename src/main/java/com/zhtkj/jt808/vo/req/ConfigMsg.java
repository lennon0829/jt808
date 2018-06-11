package com.zhtkj.jt808.vo.req;

import com.zhtkj.jt808.vo.PackageData;

/**
 * ClassName: ConfigMsg 
 * @Description: 终端配置更新消息
 */
public class ConfigMsg extends PackageData {

	/**
	 * @Fields configInfo : 终端配置更新
	 */
	private ConfigInfo configInfo;
	
	public ConfigMsg() {
		
	}
	
	public ConfigMsg(PackageData packageData) {
		this();
		this.channel = packageData.getChannel();
		this.msgHead = packageData.getMsgHead();
		this.msgBody = packageData.getMsgBody();
	}
	
	public ConfigInfo getConfigInfo() {
		return configInfo;
	}

	public void setConfigInfo(ConfigInfo configInfo) {
		this.configInfo = configInfo;
	}

	public static class ConfigInfo {
		
		private String mac;
		
		public String getMac() {
			return mac;
		}
		
		public void setMac(String mac) {
			this.mac = mac;
		}
		
	}
}
