package com.ibreakingpoint.liveagent.connect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 用来mock sessionId API 的返回结果
 * @author JerryLing
 * @time Nov 2, 2016
 * @email toiklaun@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveAgentSessionIdResponseModel{
	private String id;
	private String key;
	private String affinityToken;
	private String clientPollTimeout;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getAffinityToken() {
		return affinityToken;
	}
	public void setAffinityToken(String affinityToken) {
		this.affinityToken = affinityToken;
	}
	public String getClientPollTimeout() {
		return clientPollTimeout;
	}
	public void setClientPollTimeout(String clientPollTimeout) {
		this.clientPollTimeout = clientPollTimeout;
	}
	
}