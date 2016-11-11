package com.ibreakingpoint.connect;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import com.ibreakingpoint.liveagent.connect.domain.ChatSessionRepository;
import com.ibreakingpoint.liveagent.connect.domain.entity.ChatSessionEntity;
import com.ibreakingpoint.liveagent.connect.model.LiveAgentSessionIdResponseModel;
import com.ibreakingpoint.liveagent.connect.model.WechatComeInMessageResponseModel;
import com.ibreakingpoint.liveagent.connect.service.LiveAgentService;
import com.ibreakingpoint.liveagent.connect.service.WechatService;
import com.ibreakingpoint.liveagent.connect.util.XMLConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest  {
	
	@Autowired
	private WechatService service;
	@Autowired
	private LiveAgentService liveAgentService;
	@Autowired
	private ChatSessionRepository chatSessionRepository;
    @Before
    public void setUp() throws Exception{
    	
    }
    
//    @Test
//    public void test() throws Exception {
//    	chatSessionRepository.save(new ChatSessionModel("test1", "didi", "30","a"));
//    	chatSessionRepository.save(new ChatSessionModel("test2", "mama", "40","b"));
//    	chatSessionRepository.save(new ChatSessionModel("test3", "kaka", "50","c"));
//    	List<ChatSessionModel> modelList = chatSessionRepository.findAll();
//    	assert modelList.size() == 3;
//    	chatSessionRepository.deleteAll();
//    }
//
//    @Test
//    public void find() throws Exception {
//    	chatSessionRepository.save(new ChatSessionModel("aaa", "bbb", "30","a"));
//    	ChatSessionModel model = chatSessionRepository.findByOpenId("aaa");
//    	assert model.getSessionId().equals("bbb");
//    	chatSessionRepository.deleteAll();
//    }
//
//    @Test
//    public void testSessionId()throws Exception{
//    	SessionIdResponse resp = liveAgentService.createLiveAgentSession();
//    	System.out.println("ID:"+resp.getId());
//    	System.out.println("KEY:"+resp.getKey());
//    	System.out.println("token:"+resp.getAffinityToken());
//    	assert resp!=null;
//    }
//
//    @Test
//    public void testChastorInit()throws Exception{
//    	SessionIdResponse resp = liveAgentService.createLiveAgentSession();
//    	ChatSessionModel chatSession = new ChatSessionModel();
//		String sessionId = resp.getId();
//		String sessionKey = resp.getKey();
//		String affinityToken = resp.getAffinityToken();
//		chatSession.setOpenId("onw0Tt_fbdqQNw52EmOf4FDpP_64");
//		chatSession.setSessionId(sessionId);
//		chatSession.setSessionKey(sessionKey);
//		chatSession.setAffinityToken(affinityToken);
//    	liveAgentService.doChasitorInit(chatSession);
//    }
    
    @Test
    public void testChatMessage() throws Exception{
    	String xmlMsg = "<xml><ToUserName>xiaoheiaa</ToUserName><FromUserName>onw0Tt1k8q_oDZLOtMMo9JqSNlyI</FromUserName><CreateTime>1348831860</CreateTime><MsgType>text</MsgType><Content>hello world</Content><MsgId>1234567890123456</MsgId></xml>";
    	WechatComeInMessageResponseModel m = (WechatComeInMessageResponseModel) XMLConverter.convertXmlStringToModel(com.ibreakingpoint.liveagent.connect.model.WechatComeInMessageResponseModel.class, xmlMsg);
    	ChatSessionEntity chatSession = new ChatSessionEntity();
		LiveAgentSessionIdResponseModel resp = this.liveAgentService.createLiveAgentSession();
		String sessionId = resp.getId();
		String sessionKey = resp.getKey();
		String affinityToken = resp.getAffinityToken();
		chatSession.setOpenId(m.getFromUserName());
		chatSession.setSessionId(sessionId);
		chatSession.setSessionKey(sessionKey);
		chatSession.setAffinityToken(affinityToken); 
		String resps  = liveAgentService.doChasitorInit(chatSession,"xiaohei");
		if(resps.equals("OK")){
			liveAgentService.chatMessage("test", chatSession);
		}
//		liveAgentService.chatMessage("test", session);
    	
    }
    
    
    
    /**
     * 这个测试需要accesstoken 一般这个 accesstoken是从数据库里面拿所以运行这个的前提是
     * 建立好与mongodb 的连接
     */
    @Test
    public void testSendMsgToWechat(){
    	
    	//service.sendMessageToOpenId(accessToken, toUser, messageContent, messageType)
    }

}


