package com.ibreakingpoint.liveagent.connect.domain.entity;

import org.springframework.data.annotation.Id;
/**
 * 
 * @author JerryLing
 * @time Nov 11, 2016
 * @email toiklaun@gmail.com
 */
public class AccessTokenEntity{
	@Id
	private String id;
	private String access_token;
	private String createdDate;
	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String recordDate) {
		this.createdDate = recordDate;
	}
	
	
}
