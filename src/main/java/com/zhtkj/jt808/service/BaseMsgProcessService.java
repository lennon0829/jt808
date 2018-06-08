package com.zhtkj.jt808.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhtkj.jt808.common.JT808Const;
import com.zhtkj.jt808.server.SessionManager;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class BaseMsgProcessService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SessionManager sessionManager;
    
	public BaseMsgProcessService() {
		this.sessionManager = SessionManager.getInstance();
	}

	public void send2Terminal(Channel channel, byte[] array) throws InterruptedException {
		//反转义数据包
        List<Byte> list = new ArrayList<Byte>();
        list.add((byte)JT808Const.MSG_DELIMITER);
        for (int i = 1; i < array.length - 1; i++){
            if (array[i] == (byte)JT808Const.MSG_DELIMITER) {
            	list.add((byte)0x7d);
            	list.add((byte)0x02);
            } else if (array[i] == (byte)0x7d) {
            	list.add((byte)0x7d);
            	list.add((byte)0x01);
            } else {
            	list.add(array[i]);
            }
        }
        list.add((byte)JT808Const.MSG_DELIMITER);
        byte[] bs  = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
        	bs[i] = list.get(i);
        }
        //发送给终端
		ChannelFuture future = channel.writeAndFlush(Unpooled.copiedBuffer(bs)).sync();
		if (!future.isSuccess()) {
			logger.error("发送数据出错:{}", future.cause());
		}
	}

}
