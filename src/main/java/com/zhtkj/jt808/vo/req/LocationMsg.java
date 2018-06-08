package com.zhtkj.jt808.vo.req;

import com.zhtkj.jt808.vo.PackageData;

/**
 * ClassName: LocationMsg 
 * @Description: 基本位置信息消息
 */
 
public class LocationMsg extends PackageData {

	/**
	 * @Fields locationInfo : 基本位置信息
	 */
	private LocationInfo locationInfo;
	
	public LocationMsg() {
		
	}

	public LocationMsg(PackageData packageData) {
		this();
		this.channel = packageData.getChannel();
		this.msgHeader = packageData.getMsgHeader();
		this.msgBody = packageData.getMsgBody();
	}
	
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}

	public static class LocationInfo {
		
		private String carNumber; //车牌号码
		
		private String devPhone; //终端sim
		
		private String remoteAddress; //终端ip地址
		
		private String carState; //车辆状态
		
		private float gpsPosX; //经度
		
		private float gpsPosY; //纬度
		
		private float gpsHeight; //高程
		
		private float gpsSpeed; //速度
		
		private float gpsDirect; //方向
		
		private String sendDatetime; //时间(设备时间)
		
		private String driverId; //司机ID
		
		private String workPassport; //核准证
		
		private int boxClose; //车厢状态，1：关闭；2：打开
		
		private int boxUp; //举升状态，1：平放；2：举升；3：完全举升
		
		private int boxEmpty; //空重状态，1：空车；2：重车
		
		private int carWeigui; //违规情况，1：违规；0：未违规

		
		public String getDevPhone() {
			return devPhone;
		}

		public void setDevPhone(String devPhone) {
			this.devPhone = devPhone;
		}

		public String getRemoteAddress() {
			return remoteAddress;
		}

		public void setRemoteAddress(String remoteAddress) {
			this.remoteAddress = remoteAddress;
		}

		public String getCarState() {
			return carState;
		}
		
		public void setCarState(String carState) {
			this.carState = carState;
		}

		public float getGpsPosX() {
			return gpsPosX;
		}

		public void setGpsPosX(float gpsPosX) {
			this.gpsPosX = gpsPosX;
		}

		
		public float getGpsPosY() {
			return gpsPosY;
		}

		
		public void setGpsPosY(float gpsPosY) {
			this.gpsPosY = gpsPosY;
		}

		
		public float getGpsHeight() {
			return gpsHeight;
		}

		
		public void setGpsHeight(float gpsHeight) {
			this.gpsHeight = gpsHeight;
		}

		
		public float getGpsSpeed() {
			return gpsSpeed;
		}

		
		public void setGpsSpeed(float gpsSpeed) {
			this.gpsSpeed = gpsSpeed;
		}

		
		public float getGpsDirect() {
			return gpsDirect;
		}

		
		public void setGpsDirect(float gpsDirect) {
			this.gpsDirect = gpsDirect;
		}

		
		public String getSendDatetime() {
			return sendDatetime;
		}

		
		public void setSendDatetime(String sendDatetime) {
			this.sendDatetime = sendDatetime;
		}

		
		public String getCarNumber() {
			return carNumber;
		}

		
		public void setCarNumber(String carNumber) {
			this.carNumber = carNumber;
		}

		
		public String getDriverId() {
			return driverId;
		}
		
		public void setDriverId(String driverId) {
			this.driverId = driverId;
		}
		
		public String getWorkPassport() {
			return workPassport;
		}
		
		public void setWorkPassport(String workPassport) {
			this.workPassport = workPassport;
		}

		public int getBoxClose() {
			return boxClose;
		}

		
		public void setBoxClose(int boxClose) {
			this.boxClose = boxClose;
		}

		
		public int getBoxUp() {
			return boxUp;
		}

		
		public void setBoxUp(int boxUp) {
			this.boxUp = boxUp;
		}

		
		public int getBoxEmpty() {
			return boxEmpty;
		}

		
		public void setBoxEmpty(int boxEmpty) {
			this.boxEmpty = boxEmpty;
		}

		
		public int getCarWeigui() {
			return carWeigui;
		}

		
		public void setCarWeigui(int carWeigui) {
			this.carWeigui = carWeigui;
		}

	}

}
