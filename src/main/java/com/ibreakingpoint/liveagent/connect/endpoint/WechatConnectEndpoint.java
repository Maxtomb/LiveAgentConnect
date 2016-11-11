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
	
	@RequestMapping(value="/msg" ,method=RequestMethod.GET)
	public String authenticate(@RequestParam String signature,
							   @RequestParam String timestamp,
							   @RequestParam String nonce,
							   @RequestParam String echostr
								) throws UnsupportedEncodingException {
		String key ="404 Bad Request";
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			key = echostr;
		}
		logger.info(echostr);
		return key;
		
	}
	
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
	public String test() {
		logger.info("--------Begin Test----------");
		try {
//			String te = LiveAgentService.doChasitorInit();
//			logger.info(te);
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info(e.getStackTrace().toString());
		}
		return null;
	}

	
	
}
