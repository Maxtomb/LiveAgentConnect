package com.ibreakingpoint.liveagent.connect.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ibreakingpoint.liveagent.connect.domain.ChatSessionRepository;
import com.ibreakingpoint.liveagent.connect.domain.entity.ChatSessionEntity;

import net.sf.json.JSONObject;

/**
 * MessageThread 将会调用liveagent的api 循环抓取消息，并将有效的消息推送到 微信端
 * 
 * @author JerryLing
 * @time Nov 2, 2016
 * @email toiklaun@gmail.com
 */
public class MessageThread extends Thread {
	private static final Logger logger  = LoggerFactory.getLogger(MessageThread.class);  
	
	private WechatService wechatService;
	private LiveAgentService liveAgentService;
	private ChatSessionEntity session;
	private boolean flag = true;
	public MessageThread(LiveAgentService liveAgentService, WechatService wechatService,
			ChatSessionEntity session) {
		this.liveAgentService = liveAgentService;
		this.wechatService = wechatService;
		this.session = session;
	}
	
	
	@Override
	public void run() {
		logger.info(Thread.currentThread().getName()+" is runing");
		while(flag){
			String resp = liveAgentService.message(session);
			logger.info("[Get Message From LiveAgent]"+ resp);
			try {
				JSONObject json = JSONObject.fromObject(resp);
				Map map = (Map) json;
				if (map.size() > 0) {
					List<Map<String, Object>> messageMapList = (List<Map<String, Object>>) map.get("messages");
					for (Map<String, Object> messageMap : messageMapList) {
						if ("ChatMessage".equals(messageMap.get("type"))) {
							logger.info("[ChatMessage]");
							Map<String, Object> mdMap = (Map<String, Object>) messageMap.get("message");
							String text = mdMap.get("text").toString();
							String name = mdMap.get("name").toString();
							String agentId = mdMap.get("agentId").toString();
							String openId = session.getOpenId();
							String res = wechatService.sendMessageToOpenId(openId, text, "text");
							logger.info("[ThreadManager] Thread Count:"+ThreadManagerService.getThreadCount());
							logger.info("[ChatMessage] to ["+openId+"] content["+text+"] res:"+res);
						}
						if ("AgentDisconnect".equals(messageMap.get("type"))) {
							flag = false;
							logger.info("[AgentDisconnect]");
							ThreadManagerService.removeThread(session.getOpenId());
							logger.info("[ThreadManager] Thread Removed , Thread Count:"+ThreadManagerService.getThreadCount());
							session.setSessionCreated(false);
							liveAgentService.deleteSession(session);
							logger.info("[Session Removed]");
							Thread.currentThread().interrupt();
							
						}
						if ("ChatEnded".equals(messageMap.get("type"))) {
							flag = false;
							session.setSessionCreated(false);
							logger.info("[ChatEnded]");
							ThreadManagerService.removeThread(session.getOpenId());
							logger.info("[ThreadManager] Thread Removed , Thread Count:"+ThreadManagerService.getThreadCount());
							liveAgentService.deleteSession(session);
							logger.info("[Session Removed]");
							Thread.currentThread().interrupt();
						}
					}
				}
				if(flag){
					Thread.sleep(3000);
				}
			} catch (Exception e) {
				flag = false;
				session.setSessionCreated(false);
				liveAgentService.deleteSession(session);
				ThreadManagerService.removeThread(session.getOpenId());
				logger.info("[Exception]");
				logger.info("[ThreadManager] Thread Removed , Thread Count:"+ThreadManagerService.getThreadCount());
				logger.info("[Session Removed]");
				e.printStackTrace();
				Thread.currentThread().interrupt();
				
			}
			
		}
	}

}
