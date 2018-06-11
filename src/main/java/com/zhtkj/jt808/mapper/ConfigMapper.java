package com.zhtkj.jt808.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhtkj.jt808.entity.Config;
import com.zhtkj.jt808.vo.req.VersionMsg.VersionInfo;

public interface ConfigMapper {

	int insertConfig(@Param(value = "configInfo") VersionInfo configInfo);
	
	int updateConfig(@Param(value = "configInfo") VersionInfo configInfo);
	
	List<Config> selectConfigByKey(@Param(value = "mac") String mac);
	
}
