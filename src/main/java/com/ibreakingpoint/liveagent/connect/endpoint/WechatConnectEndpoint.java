package com.ibreakingpoint.liveagent.connect.endpoint;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibreakingpoint.liveagent.connect.domain.AccessTokenRepository;
import com.ibreakingpoint.liveagent.connect.domain.ChatSessionRepository;
import com.ibreakingpoint.liveagent.connect.service.Connect;
import com.ibreakingpoint.liveagent.connect.service.LiveAgentService;
import com.ibreakingpoint.liveagent.connect.service.WechatService;
import com.ibreakingpoint.liveagent.connect.util.SignUtil;
/**
 * 
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
@RestController
@RequestMapping("/sfdc")
public class WechatConnectEndpoint {
	private Logger logger  = LoggerFactory.getLogger(WechatConnectEndpoint.class);  
	
	@Autowired
	private Connect connect;
	/**
	 * 这个方法是微信要求提供的 返回echostr 字符串微信公众平台会认为是开发者身份
	 * 具体细节请参考微信公众好开发文档
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/msg" ,method=RequestMethod.GET)
	public String authenticate(@RequestParam String signature,
							   @RequestParam String timestamp,
							   @RequestParam String nonce,
							   @RequestParam String echostr
								) throws UnsupportedEncodingException {
		String key ="404 Bad Request";
		//微信身份校验
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			key = echostr;
		}
		logger.info(echostr);
		return key;
		
	}
	/**
	 * msgData里面包含了所有的 微信推过来的数据
	 * 是整个程序的入口
	 * @param msgData
	 * @return
	 */
	@RequestMapping(value="/msg" ,method=RequestMethod.POST, produces="application/json")
	public Object getTextMsg(@RequestBody String msgData) {
		logger.info("--------Begin----------");
		logger.info(msgData);
		try {
			logger.info("You are using Connect["+ connect.toString()+"]");
			connect.doContent(msgData);
			logger.info("--------End----------");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value="/test" ,method=RequestMethod.GET, produces="application/json")
	public Object test(@RequestBody String msgData) {
		logger.info("Test");
		
		return "Hello Test";
	}
}
