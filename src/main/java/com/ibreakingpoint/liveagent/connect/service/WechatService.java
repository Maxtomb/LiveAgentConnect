package com.ibreakingpoint.liveagent.connect.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ibreakingpoint.liveagent.connect.domain.AccessTokenRepository;
import com.ibreakingpoint.liveagent.connect.domain.entity.AccessTokenEntity;
import com.ibreakingpoint.liveagent.connect.model.WechatComeOutMessageRequestModel;

import net.sf.json.JSONObject;
/**
 * 
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
@Service
public class WechatService {
	private final Logger logger = LoggerFactory.getLogger(WechatService.class);
	private RestTemplate restTemplate;
	@Value("${wechat.app_id}")
	private String app_id;
	@Value("${wechat.app_secret}")
	private String app_secret;
	
	private AccessTokenRepository accessTokenRepository;
	
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getApp_secret() {
		return app_secret;
	}
	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}
	
	@Autowired
	public WechatService(AccessTokenRepository accessTokenRepository){
		this.accessTokenRepository = accessTokenRepository;
		restTemplate = new RestTemplate();
	}
	/**
	 * 
	 * @return
	 */
	public String getAccessToken(){
		String uri = "https://api.weixin.qq.com/cgi-bin/token?grant_type={grant_type}&appid={appid}&secret={secret}";
	    HttpHeaders headers = new HttpHeaders();
	    Map<String,Object> queryMap = new HashMap<String,Object>();
	    queryMap.put("grant_type", "client_credential");
	    queryMap.put("appid", app_id);
	    queryMap.put("secret", app_secret);
	    HttpEntity<String> entity = new HttpEntity<String>("", headers);
	    ResponseEntity<String> resp = restTemplate.exchange(uri,HttpMethod.GET,entity,String.class,queryMap);
	    JSONObject jsonMap = JSONObject.fromObject(resp.getBody());
	    return jsonMap.getString("access_token");
	}
	
	
	public String getAccessTokenFromDatabase(){
		List<AccessTokenEntity> accessTokenModelList = this.accessTokenRepository.findAll();
		AccessTokenEntity tokenModel = accessTokenModelList.get(0);
		String accessToken = tokenModel.getAccess_token();
		logger.info("AccessToken: "+ accessToken);
		return accessToken;
	}
	/**
	 * 
	 * @param accessToken
	 * @param toUser
	 * @param messageContent
	 * @param messageType
	 * @return
	 */
	public String sendMessageToOpenId(String toUser,String messageContent,String messageType){
		String accessToken = getAccessTokenFromDatabase();
		logger.info("AccessToken:["+accessToken+"]");
		String uri = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={ACCESS_TOKEN}";
	    HttpHeaders headers = new HttpHeaders();
	    WechatComeOutMessageRequestModel message = new WechatComeOutMessageRequestModel();
	    Map<String,Object> contentMap = new HashMap<String,Object>();
	    contentMap.put("content", messageContent);
	    message.setText(contentMap);
	    message.setTouser(toUser);
	    message.setMsgtype(messageType);
	    JSONObject jsonMap = JSONObject.fromObject(message);
	    Map<String,String> paramMap = new HashMap<String,String>();
	    paramMap.put("ACCESS_TOKEN", accessToken);
	    HttpEntity<String> entity = new HttpEntity<String>(jsonMap.toString(), headers);
	    HttpEntity<String> resp = restTemplate.exchange(uri,HttpMethod.POST,entity,String.class,paramMap);
	    return resp.getBody();
	
	}
	
	/**
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public Map<String,Object> getWechatUserInfo(String openId){
		String accessToken = getAccessTokenFromDatabase();
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={ACCESS_TOKEN}&openid={OPENID}";
	    HttpHeaders headers = new HttpHeaders();
	    Map<String,String> paramMap = new HashMap<String,String>();
	    paramMap.put("ACCESS_TOKEN", accessToken);
	    paramMap.put("OPENID",openId);
	    HttpEntity<String> entity = new HttpEntity<String>("", headers);
	    HttpEntity<Map> resp = restTemplate.exchange(uri,HttpMethod.GET,entity,Map.class,paramMap);
	    return resp.getBody();
	}
	
}
