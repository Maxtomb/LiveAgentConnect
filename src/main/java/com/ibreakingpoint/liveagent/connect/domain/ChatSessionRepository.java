package com.ibreakingpoint.liveagent.connect.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibreakingpoint.liveagent.connect.domain.entity.ChatSessionEntity;
/**
 * 
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
public interface ChatSessionRepository extends  MongoRepository<ChatSessionEntity, String>{
	public ChatSessionEntity findByOpenId(String openId);
	public ChatSessionEntity findByOpenIdAndSessionCreated(String openId,Boolean sessionCreated);
}
