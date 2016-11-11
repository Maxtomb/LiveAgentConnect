package com.ibreakingpoint.liveagent.connect.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

/**
 * 这个 model 用来组装 chat session 的
 * @author JerryLing
 * @time Nov 2, 2016
 * @email toiklaun@gmail.com
 */
@Component
public class ChatSessionEntity {
	@Id
	public String id;
	private String openId;
	private String sessionId;
	private String sessionKey;
	private String affinityToken;
	private boolean sessionCreated;
	public boolean isSessionCreated() {
		return sessionCreated;
	}
	public void setSessionCreated(boolean sessionCreated) {
		this.sessionCreated = sessionCreated;
	}
	public String getAffinityToken() {
		return affinityToken;
	}
	public void setAffinityToken(String affinityToken) {
		this.affinityToken = affinityToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public ChatSessionEntity(String openId, String sessionId, String sessionKey, String affinityToken) {
		super();
		this.openId = openId;
		this.sessionId = sessionId;
		this.sessionKey = sessionKey;
		this.affinityToken = affinityToken;
	}
	public ChatSessionEntity(){}
	
}
