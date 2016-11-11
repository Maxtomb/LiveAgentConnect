package com.ibreakingpoint.liveagent.connect.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibreakingpoint.liveagent.connect.domain.entity.ChatSessionEntity;
import com.ibreakingpoint.liveagent.connect.exception.LiveAgentException;
import com.ibreakingpoint.liveagent.connect.model.LiveAgentSessionIdResponseModel;
import com.ibreakingpoint.liveagent.connect.model.WechatComeInMessageResponseModel;
import com.ibreakingpoint.liveagent.connect.util.XMLConverter;
/**
 * Connect类 里面写了这个程序的主要业务逻辑
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
@Service
public class Connect {
	private static Logger logger  = LoggerFactory.getLogger(Connect.class);  
	
	private LiveAgentService liveAgentService;
	private WechatService wechatService;

	@Autowired
	public Connect(LiveAgentService liveAgentService,WechatService wechatService){
		this.liveAgentService = liveAgentService;
		this.wechatService =  wechatService;
	}
	/**
	 * 1.从WechatComeInMessageResponseModel模型中获取微信用户的openid 
	 * 2.然后查询数据库看是否存在
	 * 3.存在 就返回ChatSessionEntity模型
	 * 4.不存在 就创建新的ChatSessionEntity模型 并调用 LiveAgent API ChasitorInit
	 * 5.如果ChasitorInit返回ok 就将ChatSessionEntity 的 isSessionCreated 状态更新成true
	 * 6.将ChatSessionEntity 存入数据库
	 * 
	 * @param xmlMsg
	 * @return
	 * @throws Exception
	 */
	public ChatSessionEntity getSession(WechatComeInMessageResponseModel m) throws Exception  {
		ChatSessionEntity session = null;
		if(m.getFromUserName()!=null && m.getFromUserName()!=""){
			session = liveAgentService.searchIfSessionExist(m.getFromUserName());
		}
		if(session==null){
			ChatSessionEntity chatSession = new ChatSessionEntity();
			LiveAgentSessionIdResponseModel resp = this.liveAgentService.createLiveAgentSession();
			String sessionId = resp.getId();
			String sessionKey = resp.getKey();
			String affinityToken = resp.getAffinityToken();
			chatSession.setOpenId(m.getFromUserName());
			chatSession.setSessionId(sessionId);
			chatSession.setSessionKey(sessionKey);
			chatSession.setAffinityToken(affinityToken);
			Map<String,Object> userRespMap = wechatService.getWechatUserInfo(m.getFromUserName());
			String nickName = userRespMap.get("nickname")!=null?userRespMap.get("nickname").toString():"匿名用户";
			String from = " From:["+userRespMap.get("province")+","+userRespMap.get("city")+"]";
			String resps = liveAgentService.doChasitorInit(chatSession,nickName+from);
			if("OK".equals(resps)){
				chatSession.setSessionCreated(true);
			}else{
				throw new LiveAgentException("ChasitorInit Not Success, Find More ["+resps+"]");
			}
			session  = liveAgentService.saveSession(chatSession);
		}
		logger.info("openid: "+session.getOpenId());
		
		return session;
	}
	
	/**
	 * 1.调用getSession()来获取 ChatSessionEntity 模型
	 * 2.判断ChatSessionEntity的isSessionCreated状态是否为true
	 * 3.如果为true 则调用LiveAgent API message接口 将消息发送到LiveAgent
	 * 4.创建一个新的线程我去轮询从LiveAgent获取消息，然后把线程交给ThreadManagerService管理
	 * @param xmlMsg
	 * @throws Exception
	 */
	public void doContent(String xmlMsg) throws Exception{
		WechatComeInMessageResponseModel m = (WechatComeInMessageResponseModel) XMLConverter.convertXmlStringToModel(com.ibreakingpoint.liveagent.connect.model.WechatComeInMessageResponseModel.class, xmlMsg);
		logger.info("messagemodel: "+m.getToUserName());
		logger.info("messagemodel: "+m.getFromUserName());
		logger.info("messagemodel: "+m.getContent());
		ChatSessionEntity session = getSession(m);
		logger.info("session created: "+ session.isSessionCreated());
		
		if(session.isSessionCreated()){
			//调用chatmessage api 发送数据到 live agent
			liveAgentService.chatMessage(m.getContent(), session);
		
			//这里开启线程从liveagent那边抓数据 默认时间3秒
			MessageThread t = ThreadManagerService.getThread(session.getOpenId());
			if(t == null){
				t = new MessageThread(liveAgentService,wechatService,session);;
				ThreadManagerService.setThread(session.getOpenId(), t);
			}
		}
	}
	
}


