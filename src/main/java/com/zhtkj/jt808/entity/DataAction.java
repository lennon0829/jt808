package com.zhtkj.jt808.entity;

import java.util.Date;

public class DataAction {

	private int actionId;

	private int actionType;

	private int actionValue;

	private String carPass;

	private byte dealResult;

	private Date dealTime;

	private String imgPath;

	private String phoneNumber;

	private byte receiveResult;

	private int resendCount;

	private String sendPerson;

	private String sendRemark;

	private Date storeTime;

	
	public int getActionId() {
		return actionId;
	}

	
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	
	public int getActionType() {
		return actionType;
	}

	
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	
	public int getActionValue() {
		return actionValue;
	}

	
	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	}

	
	public String getCarPass() {
		return carPass;
	}

	
	public void setCarPass(String carPass) {
		this.carPass = carPass;
	}

	
	public byte getDealResult() {
		return dealResult;
	}

	
	public void setDealResult(byte dealResult) {
		this.dealResult = dealResult;
	}

	
	public Date getDealTime() {
		return dealTime;
	}

	
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	
	public String getImgPath() {
		return imgPath;
	}

	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	public byte getReceiveResult() {
		return receiveResult;
	}

	
	public void setReceiveResult(byte receiveResult) {
		this.receiveResult = receiveResult;
	}

	
	public int getResendCount() {
		return resendCount;
	}

	
	public void setResendCount(int resendCount) {
		this.resendCount = resendCount;
	}

	
	public String getSendPerson() {
		return sendPerson;
	}

	
	public void setSendPerson(String sendPerson) {
		this.sendPerson = sendPerson;
	}

	
	public String getSendRemark() {
		return sendRemark;
	}

	
	public void setSendRemark(String sendRemark) {
		this.sendRemark = sendRemark;
	}

	
	public Date getStoreTime() {
		return storeTime;
	}

	
	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}
	
}
