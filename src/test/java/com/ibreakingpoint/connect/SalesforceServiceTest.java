package com.ibreakingpoint.connect;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.ibreakingpoint.liveagent.connect.service.SalesforceService;
import static org.assertj.core.api.Assertions.*;
public class SalesforceServiceTest {
	
	private SalesforceService service;
	@Before
	public void setup(){
		service = new SalesforceService();
		service.setLoginUri("https://login.salesforce.com/services/oauth2/token");
		service.setClientId("3MVG9ZL0ppGP5UrCT8IzV.WQzwskkYbJb5n3XMomO3xmTTi7Cd4G.Z9ygqF6Hw.Dk7ClGT72VDDNEQum1cMiH");
		service.setClientSecret("4639183164111459840");
		service.setUsername("devlife@max.com");
		service.setPassword("0xiaoheihVZTLOi5HcYScDl3ZpAgO4mQc");
		service.setsObjectName("Account");
		service.setVersion("v37.0");
	}
	@Test
	public void TestGetAccessToken(){
		Map<String,Object> respMap = service.getAccessTokenAndEndpoint();
		System.out.println(respMap);
		assertThat(respMap.get("access_token")).isNotNull();
	}
	
	@Test
	public void TestGetSObject(){
		Map<String,Object> respMap = service.getSObject();
		System.out.println(respMap);
	}
	
	
}
