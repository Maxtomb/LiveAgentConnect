package com.ibreakingpoint.liveagent.connect.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ibreakingpoint.liveagent.connect.service.WechatService;
/**
 * 该类用于自动刷新微信的access_token到数据库里
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
@Component
@EnableScheduling
public class ScheduledTasks{
	
	private static final Logger logger  = LoggerFactory.getLogger(ScheduledTasks.class); 
	//使用wechat服务来获取access_token
	@Autowired
	private WechatService wService;
	
	/**
	 * 这个方法Spring Schedule机制会自动调用 每间隔 一段时间就会刷新一下 访问微信的accesstoken
	 * 因为 AccessToken 是两个小时到期所以 建议 刷新时间不要大于两个小时
	 */
    @Scheduled(fixedDelayString="${access_token_refresh_interval.setup}") //cron = "* 0/1 *  * * * "
    public void refreshToken(){
       logger.info("The new Job has been setup");
       String token = wService.refreshToken();
       logger.info("["+token+"] Access Token Refreshed..");
    }

   
    
}
