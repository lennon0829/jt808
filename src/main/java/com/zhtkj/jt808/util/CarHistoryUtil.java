package com.zhtkj.jt808.util;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.joda.time.DateTime;

import com.zhtkj.jt808.vo.req.LocationMsg.LocationInfo;

/** 
* @ClassName: CarHistoryFilter 
* @Description: 过滤历史数据，主要是过滤白天的历史数据 
*/
public class CarHistoryUtil {

	private static Map<String, CarState> carState = new Hashtable <String, CarState>();
	
	//gps坐标连续相同次数，超过这个数目车辆数据就需要写入历史表
	private static Long GPS_SAME_TOTAL = 10L;
	
	// 判断是否需要写入车辆历史数据到数据库
	public static boolean isPersistent(LocationInfo locationInfo) {
		CarState lastCarState = carState.get(locationInfo.getCarNumber());
		CarState newCarState = new CarState();
		newCarState.setLongitude(locationInfo.getGpsPosX() + "");
		newCarState.setLatitude(locationInfo.getGpsPosY() + "");
		newCarState.setBoxUp(locationInfo.getBoxUp() + "");
		newCarState.setBoxEmpty(locationInfo.getBoxEmpty() + "");
		boolean result  = true;
		if (lastCarState==null) {
			newCarState.setGpsSameTotal(0L);
			newCarState.setUpdateTime(new Date());
			result =  true;
		} else if (newCarState.getLongitude().equals(lastCarState.getLongitude())
				&&newCarState.getLatitude().equals(lastCarState.getLatitude())
				&&newCarState.getBoxUp().equals(lastCarState.getBoxUp())
				&&newCarState.getBoxEmpty().equals(lastCarState.getBoxEmpty())) {
			newCarState.setGpsSameTotal(lastCarState.getGpsSameTotal() + 1);
			
			if (new DateTime(lastCarState.getUpdateTime()).plusMinutes(15).isBeforeNow()) {
				newCarState.setUpdateTime(new Date());
				result = true;
			} else if (newCarState.getGpsSameTotal() >= GPS_SAME_TOTAL) {
				newCarState.setUpdateTime(lastCarState.getUpdateTime());
				result = false;
			} else {
				newCarState.setUpdateTime(new Date());
				result = true;
			}
		} else {
			newCarState.setGpsSameTotal(0L);
			newCarState.setUpdateTime(new Date());
			result =  true;
		}
		carState.put(locationInfo.getCarNumber(), newCarState);
		return result;
	}
	
}

class CarState {
	
	public String longitude;
	
	public String latitude;
	
	public String boxUp;
	
	public String boxEmpty;
	
	public Long gpsSameTotal;
	
	public Date updateTime;
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getBoxUp() {
		return boxUp;
	}

	public void setBoxUp(String boxUp) {
		this.boxUp = boxUp;
	}

	public String getBoxEmpty() {
		return boxEmpty;
	}

	public void setBoxEmpty(String boxEmpty) {
		this.boxEmpty = boxEmpty;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getGpsSameTotal() {
		return gpsSameTotal;
	}

	public void setGpsSameTotal(Long gpsSameTotal) {
		this.gpsSameTotal = gpsSameTotal;
	}
	
}
