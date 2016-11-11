package com.ibreakingpoint.liveagent.connect.model;

import java.util.Map;
/**
 * 
 * @author JerryLing
 * @time Nov 11, 2016
 * @email toiklaun@gmail.com
 */
public class WechatComeOutMessageRequestModel {
	String touser;
	String msgtype;
	Map<String,Object> text;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public Map<String, Object> getText() {
		return text;
	}
	public void setText(Map<String, Object> text) {
		this.text = text;
	}
	
}
