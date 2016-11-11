package com.ibreakingpoint.liveagent.connect.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 这个model是用来mock 微信传进来的请求的。 微信传进来的xml会被直接mock成 MessageModel对象
 * @author JerryLing
 * @time Nov 2, 2016
 * @email toiklaun@gmail.com
 */
@Component
@XmlRootElement(name = "xml")
public class WechatComeInMessageResponseModel {
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String msgType;
	private String Content;
	private String msgId;
	private String PicUrl;
	private String MediaId;

	@XmlElement(name = "ToUserName" ,required = true)
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	@XmlElement(name = "FromUserName" ,required = true)
	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	@XmlElement(name = "CreateTime",required = true)
	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	@XmlElement(name = "MsgType" ,required = true)
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	@XmlElement(name = "PicUrl" ,required = false)
	public String getPicUrl() {
		return PicUrl;
	}
	
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	
	@XmlElement(name = "MediaId" ,required = false)
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	@XmlElement(name = "Content" ,required = false)
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@XmlElement(name = "MsgId",required = true)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
