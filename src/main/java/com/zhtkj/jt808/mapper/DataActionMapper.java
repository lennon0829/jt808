package com.zhtkj.jt808.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhtkj.jt808.entity.DataAction;
import com.zhtkj.jt808.vo.PackageData.MsgBody;

public interface DataActionMapper {

	int updateActionDealResult(@Param(value = "msgBody") MsgBody msgBody);
	
	int updateActionReceiveResult(@Param(value = "actionId") Integer actionId,
			                      @Param(value = "receiveResult") Integer receiveResult);
	
	List<DataAction> findSendActionData();
}
