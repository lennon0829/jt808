package com.zhtkj.jt808.mapper.gateway;

import org.apache.ibatis.annotations.Param;

import com.zhtkj.jt808.vo.req.EventMsg.EventInfo;
import com.zhtkj.jt808.vo.req.LocationMsg.LocationInfo;

public interface CarEventMapper {

	int insertCarEvent(@Param(value = "month") String month,
			@Param(value = "eventInfo") EventInfo eventInfo,
			@Param(value = "locationInfo") LocationInfo locationInfo);
	
}
