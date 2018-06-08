package com.zhtkj.jt808.vo.resp;

/**
 * ClassName: RespMsgBody 
 * @Description: 通用响应
 */
 
public class RespMsgBody {

	//响应结果
	protected byte replyResult;

	public RespMsgBody() {
		
	}
	
	public RespMsgBody(byte replyResult) {
		this.replyResult = replyResult;
	}
	
	public byte getReplyResult() {
		return replyResult;
	}

	
	public void setReplyResult(byte replyResult) {
		this.replyResult = replyResult;
	}

}
