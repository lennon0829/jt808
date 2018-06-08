package com.zhtkj.jt808.mapper.gateway;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhtkj.jt808.entity.DataParam;
import com.zhtkj.jt808.vo.PackageData.MsgBody;

public interface DataParamMapper {

	int updateParamResult(@Param(value = "msgBody") MsgBody msgBody);
	
	int updateParamReceiveResult(@Param(value = "paramId") Integer paramId,
            @Param(value = "receiveResult") Integer receiveResult);
	
	List<DataParam> findSendParamData();
}
