package com.zhtkj.jt808.util;

import java.util.Hashtable;

import org.joda.time.DateTime;

import com.zhtkj.jt808.vo.req.EventMsg.EventInfo;

/** 
* @ClassName: CarEventUtil 
* @Description: 每种类型的事件只能在相隔一定的时间后才能上报
*/
public class CarEventUtil {

	private static Hashtable <String, Hashtable<Object, Object>> carEventMap = new Hashtable <String, Hashtable <Object, Object>>();
	
	//判断是否需要写入车辆事件数据到数据库
	public static boolean isPersistent(EventInfo eventInfo) {
		String carNumber = eventInfo.getLocationInfo().getCarNumber();
		int eventType = eventInfo.getEventType();
		if (carEventMap.get(carNumber) == null) {
			Hashtable<Object,Object> innerMap = new Hashtable <Object, Object>();
			innerMap.put(eventType, new DateTime());
			carEventMap.put(carNumber, innerMap);
			return true;
		} else {
			Hashtable<Object, Object> innerMap = carEventMap.get(carNumber);
			if(!innerMap.containsKey(eventType)) {
				innerMap.put(eventType, new DateTime());
				return true;
			}else{
				DateTime dateTime = (DateTime) innerMap.get(eventType);
				int intervalSec = getIntervalSec(eventType);
				if (intervalSec == 0 || dateTime.plusSeconds(intervalSec).isBeforeNow()) {
					innerMap.put(eventType, new DateTime());
					return true;
				} else {
					return false;
				}
			}
		}
	}
	
	private static int getIntervalSec(int eventType) {
		switch(eventType){
		case 1: //进入路线
			return 180;
		case 2: //离开路线（越界）
			return 180;
		case 3: //进入工地
			return 180;
		case 4: //离开工地
			return 180;
		case 5: //进入消纳场
			return 180;
		case 6: //离开消纳场
			return 180;
		case 7: //进入限速圈
			return 180;
		case 8: //离开限速圈
			return 180;
		case 9: //进入停车场
			return 180;
		case 10: //离开停车场
			return 180;
		case 11: //进入禁区
			return 180;
		case 12: //离开禁区
			return 180;
		case 13: //举斗
			return 180;
		case 14: //闯红灯区
			return 180;
			
		case 30: //开箱重车
			return 600;
		case 31: //GPS无信号
			return 600;
		case 32: //重车越界
			return 600;
		case 33: //重车闯禁
			return 600;
		case 34: //举斗状态监视器失联
			return 600;
		case 35: //开关箱状态监视器失联
			return 600;
		case 36: //空重车状态监视器失联
			return 600;
		case 37: //ECU失联
			return 600;
		case 38: //网关失联
			return 600;
			
		case 130: //开箱重车恢复
			return 180;
		case 131: //GPS无信号恢复
			return 180;
		case 132: //重车越界恢复
			return 180;
		case 133: //重车闯禁恢复
			return 180;
		case 134: //举斗状态监视器失联
			return 600;
		case 135: //开关箱状态监视器失联
			return 600;
		case 136: //空重车状态监视器失联
			return 600;
		case 137: //ECU失联恢复
			return 180;
		case 138: //网关失联恢复
			return 180;
			
		case 60: //非法举斗
			return 120;
		case 61: //ECU作弊
			return 600;
		case 62: //举升作弊
			return 600;
		case 63: //开关箱作弊
			return 600;
		case 64: //空重车作弊
			return 600;
		case 65: //重车核准证无效
			return 600;
		default :
			return 600;
		}
	}
	
}	