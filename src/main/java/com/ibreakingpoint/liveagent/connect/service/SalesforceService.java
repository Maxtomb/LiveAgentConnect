package com.ibreakingpoint.liveagent.connect.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class SalesforceService {
	private RestTemplate restTemplate;
	
	@Value("${salesforce.clientid}")
	private String clientId;
	
	@Value("${salesforce.clientsecret}")
	private String clientSecret;
	
	@Value("${salesforce.username}")
	private String username;
	
	@Value("${salesforce.password}")
	private String password;
	
	@Value("${salesforce.restapi.version:v37.0}")
	private String version;
	
	@Value("${salesforce.sobject.name}")
	private String sObjectName;
	
	private String loginUri;
	
	public String getLoginUri() {
		return loginUri;
	}

	public void setLoginUri(String loginUri) {
		this.loginUri = loginUri;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getsObjectName() {
		return sObjectName;
	}

	public void setsObjectName(String sObjectName) {
		this.sObjectName = sObjectName;
	}

	public SalesforceService(){
		restTemplate = new RestTemplate();
	}
	
	public Map<String,Object> getAccessTokenAndEndpoint(){
		String url = loginUri+"?grant_type={grant_type}&client_id={client_id}&client_secret={client_secret}&username={username}&password={password}";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    Map<String,Object> queryMap = new HashMap<String,Object>();
	    queryMap.put("grant_type", "password");
	    queryMap.put("client_id", clientId);
	    queryMap.put("client_secret", clientSecret);
	    queryMap.put("username", username);
	    queryMap.put("password", password);
	    HttpEntity<String> entity = new HttpEntity<String>("", headers);
	    ResponseEntity<Map> resp = restTemplate.exchange(url,HttpMethod.POST,entity,Map.class,queryMap);
	    return resp.getBody();
	}
	/**
	 * 向指定的sobject里面插入纪录，用来标示该微信用户与agent的历史纪录 方便agent 为其创建相应的帐户，联系人或者case
	 */
	public Map<String,Object> getSObject(){
		Map<String,Object> respMap = getAccessTokenAndEndpoint();
		String instanceUri = respMap.get("instance_url").toString();
		String resourceUri = "/services/data/"+version+"/sobjects/";
		String url = instanceUri + resourceUri + sObjectName;
		String accessToken = respMap.get("access_token").toString();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer "+accessToken);
	    Map<String,Object> queryMap = new HashMap<String,Object>();
	    HttpEntity<String> entity = new HttpEntity<String>("", headers);
	    ResponseEntity<Map> resp = restTemplate.exchange(url,HttpMethod.GET,entity,Map.class,queryMap);
	    return resp.getBody();
	}
}
