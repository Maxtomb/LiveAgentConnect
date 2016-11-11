package com.ibreakingpoint.connect;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import org.springframework.http.HttpHeaders;

import com.ibreakingpoint.liveagent.connect.domain.entity.ChatSessionEntity;
import com.ibreakingpoint.liveagent.connect.model.LiveAgentSessionIdResponseModel;
import com.ibreakingpoint.liveagent.connect.model.WechatComeInMessageResponseModel;
import com.ibreakingpoint.liveagent.connect.service.LiveAgentService;
import com.ibreakingpoint.liveagent.connect.service.MessageThread;
import com.ibreakingpoint.liveagent.connect.service.WechatService;
import com.ibreakingpoint.liveagent.connect.util.XMLConverter;

import net.sf.json.JSONObject;

public class MultiThreadTest {
//	public static void main(String args[]){
//		try{
//	    //onw0Tt1k8q_oDZLOtMMo9JqSNlyI
//	    //onw0Tt_fbdqQNw52EmOf4FDpP_64
//		String xmlMsg = "<xml><ToUserName>xiaoheiaa</ToUserName><FromUserName>onw0Tt1k8q_oDZLOtMMo9JqSNlyI</FromUserName><CreateTime>1348831860</CreateTime><MsgType>text</MsgType><Content>hello world</Content><MsgId>1234567890123456</MsgId></xml>";
//    	MessageModel m = (MessageModel) XMLConverter.convertXmlStringToModel(com.ibreakingpoint.connect.model.MessageModel.class, xmlMsg);
//		
//    	LiveAgentService liveAgentService = new LiveAgentService();
//    	WechatService wechatService = new WechatService();
//    	wechatService.setApp_id("wx2534c18f9f0b8a79");
//    	wechatService.setApp_secret("9f9af662058d48542a83552fb6adde03");
//    	String accessToken = wechatService.getAccessToken();
//		ChatSessionModel chatSession = new ChatSessionModel();
//		SessionIdResponse resp = liveAgentService.createLiveAgentSession();
//		String sessionId = resp.getId();
//		String sessionKey = resp.getKey();
//		String affinityToken = resp.getAffinityToken();
//		chatSession.setOpenId(m.getFromUserName());
//		chatSession.setSessionId(sessionId);
//		chatSession.setSessionKey(sessionKey);
//		chatSession.setAffinityToken(affinityToken);
//		System.out.println("openid is "+chatSession.getOpenId());
//		HttpHeaders header = liveAgentService.doChasitorInit(chatSession);
////		CountDownLatch latch = new CountDownLatch(1);
////		MessageThread t1 = new MessageThread(accessToken,liveAgentService,wechatService,chatSession,latch);
////		t1.start();
////		latch.await();
//		java.util.Timer timer = new java.util.Timer();  
//		MessageTimerTask t1 = new MessageTimerTask(accessToken,liveAgentService,wechatService,chatSession,timer);
//		timer.schedule(t1, 1000, 2000);
//		System.out.println("所有线程执行完毕");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//    	
//	}
}
