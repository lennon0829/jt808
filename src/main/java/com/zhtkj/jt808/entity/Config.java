package com.zhtkj.jt808.entity;

import java.util.Date;

public class Config {

	private String mac;
	private String carNumber;
	private String devPhone;
	private String ecuType;
	private String carType;
	private String version;
	private String versionSys;
	private Integer updateTag;
	private Integer updateCfgTag;
	private Date reportTime;
	
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
	
	public String getVersionSys() {
		return versionSys;
	}
	
	public void setVersionSys(String versionSys) {
		this.versionSys = versionSys;
	}
	
	public Integer getUpdateTag() {
		return updateTag;
	}
	
	public void setUpdateTag(Integer updateTag) {
		this.updateTag = updateTag;
	}
	
	public Integer getUpdateCfgTag() {
		return updateCfgTag;
	}
	
	public void setUpdateCfgTag(Integer updateCfgTag) {
		this.updateCfgTag = updateCfgTag;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
}
