package com.ibreakingpoint.connect;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import com.ibreakingpoint.liveagent.connect.domain.AccessTokenRepository;
import com.ibreakingpoint.liveagent.connect.service.WechatService;
import com.mongodb.MongoClient;
import static org.assertj.core.api.Assertions.*;

import java.util.Map;
public class WechatServiceTest {
	
	private WechatService wechatService;
	@Before
	public void setup() throws Exception{
		 MongoClient client = new MongoClient("127.0.0.1");
		 MongoOperations mongoTemplate =  new MongoTemplate(client, "test");
		 AccessTokenRepository repository =  new MongoRepositoryFactory(mongoTemplate).getRepository(AccessTokenRepository.class);
		 wechatService = new WechatService(repository);
		 wechatService.setApp_id("wx2534c18f9f0b8a79");
		 wechatService.setApp_secret("9f9af662058d48542a83552fb6adde03");
	}
	
	@Test
	public void testGetAccessToken() {
		String accessToken = wechatService.getAccessToken();
		assertThat(accessToken).isNotNull();
	}
	@Test
	public void testRefreshToken() {
		String accessToken = wechatService.refreshToken();
		assertThat(accessToken).isNotNull();
	}
	@Test
	public void testGetAccessTokenFromDatabase() {
		String accessToken = wechatService.getAccessTokenFromDatabase();
		assertThat(accessToken).isNotNull();
	}
	@Test
	public void testGetWechatUserInfo() {
		Map<String, Object> userInfo = wechatService.getWechatUserInfo("onw0Tt_fbdqQNw52EmOf4FDpP_64");
		assertThat(userInfo.get("nickname")).isEqualTo("凌");
	}
}