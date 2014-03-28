package com.mycompany.ppms.service.external;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/mycompany/ppms/service/external/test-tokeeptracks-service-context.xml")
public class ToKeepTracksServiceTest {

	@Autowired
	private ToKeepTracksService toKeepTracksService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void setUp() throws Exception {
		this.mockServer = MockRestServiceServer.createServer(restTemplate);
		this.resourceBundle = PropertyResourceBundle.getBundle("ToKeepTracksServiceJsons");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSearchKeyword() throws ToKeepTracksServiceException {
		String keyword = "Nice Shoes";
		String responseBody = this.resourceBundle.getString("testSearchKeyword");

		this.mockServer.expect(requestTo(toKeepTracksService.getUrlOfSearchKeyword()))
		.andExpect(method(HttpMethod.POST))
		.andExpect(jsonPath("$.api_key").value("mykey"))
		.andExpect(jsonPath("$.api_secret").value("mysecret"))
		.andExpect(jsonPath("$.keyword").value(keyword))
		.andRespond(withSuccess(responseBody , MediaType.APPLICATION_JSON));
		
		String result = toKeepTracksService.searchKeyword(keyword);
		
		assertEquals(responseBody, result);			
	}

	@Test
	public void testSearchKeywordBlankKeyword() throws ToKeepTracksServiceException {
		String keyword = "";
		String responseBody = this.resourceBundle.getString("testSearchKeywordBlankKeyword");

		this.mockServer.expect(requestTo(toKeepTracksService.getUrlOfSearchKeyword()))
		.andExpect(method(HttpMethod.POST))
		.andExpect(jsonPath("$.api_key").value("mykey"))
		.andExpect(jsonPath("$.api_secret").value("mysecret"))
		.andExpect(jsonPath("$.keyword").value(keyword))
		.andRespond(withSuccess(responseBody , MediaType.APPLICATION_JSON));
		
		String result = toKeepTracksService.searchKeyword(keyword);
		
		assertEquals(responseBody, result);
	}
	
	@Test
	public void testSearchKeywordServerError() {
		String keyword = "";
		
		this.mockServer.expect(requestTo(toKeepTracksService.getUrlOfSearchKeyword()))
		.andExpect(method(HttpMethod.POST))
		.andExpect(jsonPath("$.api_key").value("mykey"))
		.andExpect(jsonPath("$.api_secret").value("mysecret"))
		.andExpect(jsonPath("$.keyword").value(keyword))
		.andRespond(withServerError());
		
		try {
			String result = toKeepTracksService.searchKeyword(keyword);
		} catch (ToKeepTracksServiceException e) {
			assertEquals("server error", e.getMessage());
		}
	}
}
