package com.zhtkj.jt808.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhtkj.jt808.entity.Config;
import com.zhtkj.jt808.vo.req.TerminalMsg.TerminalInfo;

public interface ConfigMapper {

	int insertConfig(@Param(value = "terminalInfo") TerminalInfo terminalInfo);
	
	int updateConfig(@Param(value = "terminalInfo") TerminalInfo terminalInfo);
	
	List<Config> selectConfigByKey(@Param(value = "mac") String mac);
	
}
