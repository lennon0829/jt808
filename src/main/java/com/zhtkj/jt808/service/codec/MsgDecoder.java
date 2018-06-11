package com.zhtkj.jt808.service.codec;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zhtkj.jt808.common.JT808Const;
import com.zhtkj.jt808.util.DigitUtil;
import com.zhtkj.jt808.vo.PackageData;
import com.zhtkj.jt808.vo.PackageData.MsgBody;
import com.zhtkj.jt808.vo.PackageData.MsgHeader;
import com.zhtkj.jt808.vo.req.EventMsg;
import com.zhtkj.jt808.vo.req.EventMsg.EventInfo;
import com.zhtkj.jt808.vo.req.LocationMsg;
import com.zhtkj.jt808.vo.req.LocationMsg.LocationInfo;
import com.zhtkj.jt808.vo.req.VersionMsg;
import com.zhtkj.jt808.vo.req.VersionMsg.VersionInfo;

@Component
@Scope("prototype")
public class MsgDecoder {

	public PackageData bytes2PackageData(byte[] bs) {
		//先把数据包反转义一下
		List<Byte> listbs = new ArrayList<Byte>();
		for (int i = 1; i < bs.length - 1; i++) {
            //如果当前位是0x7d，判断后一位是否是0x02或0x01，如果是，则反转义
            if ((bs[i] == (byte)0x7d) && (bs[i + 1] == (byte) 0x02)) {
            	listbs.add((byte) JT808Const.MSG_DELIMITER);
                i++;
            } else if ((bs[i] == (byte) 0x7d) && (bs[i + 1] == (byte) 0x01)) {
            	listbs.add((byte) 0x7d);
                i++;
            } else {
            	listbs.add(bs[i]);
            }
		}
		byte[] newbs = new byte[listbs.size()];
        for (int i = 0; i < listbs.size(); i++) {
        	newbs[i] = listbs.get(i);
        }
		//开始转成业务消息对象
		PackageData pkg = new PackageData();
		MsgHeader msgHeader = this.parseMsgHeaderFromBytes(newbs);
		pkg.setMsgHeader(msgHeader);
		byte[] bodybs = DigitUtil.sliceBytes(newbs, 11, 11 + msgHeader.getMsgBodyLength() - 1);
		MsgBody msgBody = this.parseMsgBodyFromBytes(bodybs);
		pkg.setMsgBody(msgBody);
		return pkg;
	}
	
	//解码消息头
	private MsgHeader parseMsgHeaderFromBytes(byte[] data) {
		MsgHeader msgHeader = new MsgHeader();
		msgHeader.setMsgId(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 0, 1)));
    	boolean hasSubPack = ((byte) ((data[2] >> 5) & 0x1) == 1) ? true : false;
    	msgHeader.setHasSubPack(hasSubPack);
    	int encryptType = ((byte) ((data[2] >> 2) & 0x1)) == 1 ? 1 : 0;
    	msgHeader.setEncryptType(encryptType);
    	String bodyLen = DigitUtil.byteToBinaryStr(data[1], 1, 0) + DigitUtil.byteToBinaryStr(data[2], 7, 0);
    	msgHeader.setMsgBodyLength(Integer.parseInt(bodyLen, 2));;
    	msgHeader.setTerminalPhone(new String(DigitUtil.bcdToStr(DigitUtil.sliceBytes(data, 3, 8))));
    	return msgHeader;
	}
	
	//解码消息体
	private MsgBody parseMsgBodyFromBytes(byte[] data) {
		MsgBody msgBody = new MsgBody();
		msgBody.setType(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 0, 1)));
		msgBody.setSerialId(DigitUtil.byte4ToInt(DigitUtil.sliceBytes(data, 2, 5)));
		msgBody.setResult(data[6]);
		msgBody.setMsgBodyBytes(data);
    	return msgBody;
	}
	
	//解码基本位置包
	public LocationMsg toLocationMsg(PackageData packageData) throws UnsupportedEncodingException {
		LocationMsg locationMsg = new LocationMsg(packageData);
		LocationInfo locationInfo = new LocationInfo();
		byte[] msgBodyBytes = locationMsg.getMsgBody().getMsgBodyBytes();
		//设置终端手机号
		locationInfo.setDevPhone(locationMsg.getMsgHeader().getTerminalPhone());
		//设置终端地址
		locationInfo.setRemoteAddress(locationMsg.getChannel().remoteAddress().toString());
		//处理状态
		locationInfo.setCarState(DigitUtil.byteToBinaryStr(msgBodyBytes[2]) + DigitUtil.byteToBinaryStr(msgBodyBytes[3]));
        //处理经度
        float gpsPosX = DigitUtil.byte4ToInt(msgBodyBytes, 4);
        locationInfo.setGpsPosX(gpsPosX*25/9/1000000);
        //处理纬度
        float gpsPosY = DigitUtil.byte4ToInt(msgBodyBytes, 8);
        locationInfo.setGpsPosY(gpsPosY*25/9/1000000);
        //处理高程
        float gpsHeight = DigitUtil.byte2ToInt(new byte[] {msgBodyBytes[12], msgBodyBytes[13]});
        locationInfo.setGpsHeight(gpsHeight);
        //处理速度
        float gpsSpeed = DigitUtil.byte2ToInt(new byte[] {msgBodyBytes[14], msgBodyBytes[15]});
        locationInfo.setGpsSpeed(gpsSpeed);
        //处理方向
        float gpsDirect = DigitUtil.byte2ToInt(new byte[] {msgBodyBytes[16], msgBodyBytes[17]});
        locationInfo.setGpsDirect(gpsDirect/100);
        //处理设备发送时间
        String year = DigitUtil.bcdToStr(msgBodyBytes[18]);
        String month = DigitUtil.bcdToStr(msgBodyBytes[19]);
        String day = DigitUtil.bcdToStr(msgBodyBytes[20]);
        String hour = DigitUtil.bcdToStr(msgBodyBytes[21]);
        String minute = DigitUtil.bcdToStr(msgBodyBytes[22]);
        String second = DigitUtil.bcdToStr(msgBodyBytes[23]);
        String sendDatetime = "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        locationInfo.setSendDatetime(sendDatetime);
        //处理车牌号码
        String carNumber = new String(DigitUtil.sliceBytes(msgBodyBytes, 24, 31), "GBK");
		locationInfo.setCarNumber(carNumber);
        //处理司机ID
        locationInfo.setDriverId(new String(DigitUtil.sliceBytes(msgBodyBytes, 32, 41)));
        //处理核准证ID
        locationInfo.setWorkPassport(new String(DigitUtil.sliceBytes(msgBodyBytes, 42, 51)));
        //处理车厢状态
        locationInfo.setBoxClose(DigitUtil.byteToInt(msgBodyBytes[52]));
        //处理举升状态
        locationInfo.setBoxUp(DigitUtil.byteToInt(msgBodyBytes[53]));
        //处理空重状态
        locationInfo.setBoxEmpty(DigitUtil.byteToInt(msgBodyBytes[54]));
        //处理违规情况
        locationInfo.setCarWeigui(DigitUtil.byteToInt(msgBodyBytes[55]));
        locationMsg.setLocationInfo(locationInfo);
		return locationMsg;
	}
	
	//解码事件包
	public EventMsg toEventMsg(PackageData packageData) throws UnsupportedEncodingException {
		EventMsg eventMsg = new EventMsg(packageData);
		EventInfo eventInfo = new EventInfo();
		LocationInfo locationInfo = new LocationInfo();
		byte[] msgBodyBytes = eventMsg.getMsgBody().getMsgBodyBytes();
        //处理事件流水号
        long eventSerialId = DigitUtil.byte4ToInt(DigitUtil.sliceBytes(msgBodyBytes, 2, 5), 0);
        eventInfo.setEventSerialId(eventSerialId);
        //处理事件类型
        eventInfo.setEventType(DigitUtil.byteToInt(msgBodyBytes[6]));
        
        //开始处理位置信息
        byte[] locationbs = DigitUtil.sliceBytes(msgBodyBytes, 3, msgBodyBytes.length - 1);
		//设置终端sim
		locationInfo.setDevPhone(eventMsg.getMsgHeader().getTerminalPhone());
		//设置终端地址
		locationInfo.setRemoteAddress(eventMsg.getChannel().remoteAddress().toString());
		//处理状态
		locationInfo.setCarState(DigitUtil.byteToBinaryStr(locationbs[2]) + DigitUtil.byteToBinaryStr(locationbs[3]));
        //处理经度
        float gpsPosX = DigitUtil.byte4ToInt(locationbs, 4);
        locationInfo.setGpsPosX(gpsPosX*25/9/1000000);
        //处理纬度
        float gpsPosY = DigitUtil.byte4ToInt(locationbs, 8);
        locationInfo.setGpsPosY(gpsPosY*25/9/1000000);
        //处理高程
        float gpsHeight = DigitUtil.byte2ToInt(new byte[] {locationbs[12], locationbs[13]});
        locationInfo.setGpsHeight(gpsHeight);
        //处理速度
        float gpsSpeed = DigitUtil.byte2ToInt(new byte[] {locationbs[14], locationbs[15]});
        locationInfo.setGpsSpeed(gpsSpeed);
        //处理方向
        float gpsDirect = DigitUtil.byte2ToInt(new byte[] {locationbs[16], locationbs[17]});
        locationInfo.setGpsDirect(gpsDirect/100);
        //处理设备发送时间
        String year = DigitUtil.bcdToStr(locationbs[18]);
        String month = DigitUtil.bcdToStr(locationbs[19]);
        String day = DigitUtil.bcdToStr(locationbs[20]);
        String hour = DigitUtil.bcdToStr(locationbs[21]);
        String minute = DigitUtil.bcdToStr(locationbs[22]);
        String second = DigitUtil.bcdToStr(locationbs[23]);
        String sendDatetime = "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        locationInfo.setSendDatetime(sendDatetime);//此时间在显示完毕更新
        //处理车牌号码
        String carNumber = new String(DigitUtil.sliceBytes(locationbs, 24, 31), "GBK");
		locationInfo.setCarNumber(carNumber);
        //处理司机ID
        locationInfo.setDriverId(new String(DigitUtil.sliceBytes(locationbs, 32, 41)));
        //处理核准证ID
        locationInfo.setWorkPassport(new String(DigitUtil.sliceBytes(locationbs, 42, 51)));
        //处理车厢状态
        locationInfo.setBoxClose(DigitUtil.byteToInt(locationbs[52]));
        //处理举升状态
        locationInfo.setBoxUp(DigitUtil.byteToInt(locationbs[53]));
        //处理空重状态
        locationInfo.setBoxEmpty(DigitUtil.byteToInt(locationbs[54]));
        //处理违规情况
        locationInfo.setCarWeigui(DigitUtil.byteToInt(locationbs[55]));
        eventInfo.setLocationInfo(locationInfo);
        eventMsg.setEventInfo(eventInfo);
		return eventMsg;
	}
	
	//解码终端版本信息
	public VersionMsg toVersionMsg(PackageData packageData) throws UnsupportedEncodingException {
		VersionMsg versionMsg = new VersionMsg(packageData);
		VersionInfo versionInfo = new VersionInfo();
		byte[] bodybs = packageData.getMsgBody().getMsgBodyBytes();
    	Integer ecuType = (int) bodybs[8];
    	Integer carType = (int) bodybs[9];
    	byte[] carinfobs = new byte[bodybs.length - 10];
    	for (int i = 0; i < bodybs.length - 10; i++) {
    		carinfobs[i] = bodybs[i + 10];
    	}
    	String[] carInfo = new String(carinfobs, "utf8").split(",");
    	versionInfo.setMac(carInfo[0]);
    	versionInfo.setCarNumber(carInfo[1]);
    	versionInfo.setDevPhone(packageData.getMsgHeader().getTerminalPhone());
    	versionInfo.setVersion(carInfo[2]);
    	versionInfo.setEcuType(ecuType.toString());
    	versionInfo.setCarType(carType.toString());
    	versionMsg.setVersionInfo(versionInfo);
    	return versionMsg;
	}
}
