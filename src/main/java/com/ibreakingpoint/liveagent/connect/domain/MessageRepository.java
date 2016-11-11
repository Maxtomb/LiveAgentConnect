package com.ibreakingpoint.liveagent.connect.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibreakingpoint.liveagent.connect.model.WechatComeInMessageResponseModel;
/**
 * 
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
public interface MessageRepository extends MongoRepository<WechatComeInMessageResponseModel, String> {

	public List<WechatComeInMessageResponseModel> findByFromUserName(String openId);

	
	
}
