package com.ibreakingpoint.liveagent.connect.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ibreakingpoint.liveagent.connect.domain.ChatSessionRepository;
import com.ibreakingpoint.liveagent.connect.domain.entity.ChatSessionEntity;
import com.ibreakingpoint.liveagent.connect.model.LiveAgentSessionIdResponseModel;
import net.sf.json.JSONObject;

/**
 * 提供Live Agent 相关服务
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
@Service
public class LiveAgentService {
	private static Logger logger  = LoggerFactory.getLogger(LiveAgentService.class);  
	
	private LiveAgentSessionIdResponseModel sessionResp;
	@Value("${live_agent.endpoint}")
	private String host;
	@Value("${live_agent.deployment_id}")
	private String deploymentId;
	@Value("${live_agent.org_id}")
	private String orgId;
	@Value("${live_agent.button_id}")
	private String buttonId;
	@Value("${live_agent.nickname.prefix:微信用户:}")
	private String prefix;
	
	@Autowired
	private ChatSessionRepository chatSessionRepository;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * 调用LiveAgent API SessionId 去初始化Session
	 * @return
	 * @throws Exception
	 */
	public  LiveAgentSessionIdResponseModel createLiveAgentSession() {
		String uri = "https://"+host+"/chat/rest/System/SessionId";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-LIVEAGENT-API-VERSION","37");
        headers.set("X-LIVEAGENT-AFFINITY", "null");
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        HttpEntity<LiveAgentSessionIdResponseModel> response = restTemplate.exchange(uri,HttpMethod.GET,entity,LiveAgentSessionIdResponseModel.class);
        sessionResp = response.getBody();
        return sessionResp;
	}
	/**
	 * 调用 LiveAgent API ChasitorInit 去初始化Session
	 * @throws Exception 
	 */
	public  String doChasitorInit(ChatSessionEntity session,String nickName) throws Exception{
//		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://"+host+"/chat/rest/Chasitor/ChasitorInit";
		//Throw UnsupportedEncodingException
		String newPrefix= new String(prefix.getBytes("ISO-8859-1"),"utf-8");
		String fullName = newPrefix+nickName;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");  
        headers.set("X-LIVEAGENT-API-VERSION","37");
        headers.set("X-LIVEAGENT-SESSION-KEY",session.getSessionKey());
        headers.set("X-LIVEAGENT-AFFINITY",session.getAffinityToken());
        headers.set("X-LIVEAGENT-SEQUENCE","1");
        Map<String,Object> requestJson = new LinkedHashMap<String,Object>();
        requestJson.put("organizationId", orgId);
        requestJson.put("deploymentId", deploymentId);
        requestJson.put("buttonId", buttonId);
        requestJson.put("sessionId", session.getSessionId());
        requestJson.put("trackingId","");
        requestJson.put("userAgent", "");
        requestJson.put("language", "en-US");
        requestJson.put("screenResolution", "2560x1440");
        requestJson.put("visitorName", fullName);
        requestJson.put("prechatDetails", new ArrayList<Object>());
        requestJson.put("prechatEntities", new ArrayList<Object>());
        requestJson.put("receiveQueueUpdates", true);
        requestJson.put("isPost", true);
        JSONObject jsonMap = JSONObject.fromObject(requestJson);
        HttpEntity<String> entity = new HttpEntity<String>(jsonMap.toString(), headers);
        ResponseEntity<String> resp = restTemplate.exchange(uri,HttpMethod.POST,entity,String.class);
        return resp.getBody();
	}
	/**
	 * 调用 LiveAgent API ChatMessage 将消息发送给LiveAgent
	 * @param message
	 * @throws Exception
	 */
	public void chatMessage(String message,ChatSessionEntity session) throws Exception{
		String uri = "https://"+host+"/chat/rest/Chasitor/ChatMessage";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");  
        headers.set("X-LIVEAGENT-API-VERSION","37");
        headers.set("X-LIVEAGENT-SESSION-KEY",session.getSessionKey());
        headers.set("X-LIVEAGENT-AFFINITY",session.getAffinityToken());
		Map<String,Object> requestJson = new LinkedHashMap<String,Object>();
		requestJson.put("text", message);
		JSONObject jsonMap = JSONObject.fromObject(requestJson);
		logger.info("jsonMap:"+jsonMap.toString());
		HttpEntity<String> entity = new HttpEntity<String>(jsonMap.toString(), headers);
		restTemplate.exchange(uri,HttpMethod.POST,entity,String.class);
	}
	/**
	 * 调用 LiveAgent API Message 从LiveAgent获取消息
	 * @param session
	 * @return
	 */
	public String message(ChatSessionEntity session){
		String uri = "https://"+host+"/chat/rest/System/Messages";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); 
        headers.set("X-LIVEAGENT-API-VERSION","37");
        headers.set("X-LIVEAGENT-SESSION-KEY",session.getSessionKey());
        headers.set("X-LIVEAGENT-AFFINITY",session.getAffinityToken());
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> resp = restTemplate.exchange(uri,HttpMethod.GET,entity,String.class);
        String msgModel = resp.getBody();
        return msgModel;
	}
	
	/**
	 * 数据库查询 该openid 是否存在 对应的session
	 * @param openId
	 * @return
	 */
	public ChatSessionEntity searchIfSessionExist(String openId){
		ChatSessionEntity session = this.chatSessionRepository.findByOpenIdAndSessionCreated(openId,true);
		return session;
	}
	
	/**
	 * 数据库插入chatsession 模型
	 * @param session
	 * @return
	 */
	public ChatSessionEntity saveSession(ChatSessionEntity session){
		 return this.chatSessionRepository.save(session);
	}
	
	/**
	 * 数据库删除指定的 chatsession纪录
	 * @param session
	 */
	public void deleteSession(ChatSessionEntity session){
		 this.chatSessionRepository.delete(session);
	}
}	
