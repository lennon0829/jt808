package com.zhtkj.jt808.mapper;

import org.apache.ibatis.annotations.Param;

import com.zhtkj.jt808.vo.req.LocationMsg.LocationInfo;

public interface CarHistoryMapper {

	int insertCarHistory(@Param(value = "month") String month, 
			@Param(value = "locationInfo") LocationInfo locationInfo);
}
