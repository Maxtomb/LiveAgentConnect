package com.ibreakingpoint.liveagent.connect.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibreakingpoint.liveagent.connect.domain.entity.AccessTokenEntity;
/**
 * 
 * @author JerryLing
 * @time Nov 8, 2016
 * @email toiklaun@gmail.com
 */
public interface AccessTokenRepository extends  MongoRepository<AccessTokenEntity, String>{

}
