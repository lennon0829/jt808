package com.zhtkj.jt808.mapper.gateway;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhtkj.jt808.entity.CarRuntime;
import com.zhtkj.jt808.vo.req.LocationMsg.LocationInfo;

public interface CarRuntimeMapper {

	int insertCarRuntime(@Param(value = "locationInfo") LocationInfo locationInfo);
	
	int updateCarRuntime(@Param(value = "locationInfo") LocationInfo locationInfo);
	
	int setCarOnlineState(@Param(value = "devPhone") String devPhone);
	
	int setCarOfflineState(@Param(value = "idleTime") String idleTime);
	
	List<CarRuntime> findCarPassword(@Param(value = "terminalPhone") String terminalPhone);
}
