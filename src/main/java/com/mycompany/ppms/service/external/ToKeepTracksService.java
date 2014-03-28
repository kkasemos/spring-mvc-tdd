package com.mycompany.ppms.service.external;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestOperations;

public class ToKeepTracksService {
	private String urlOfSearchKeyword;
	RestOperations restTemplate;

	public void setUrlOfSearchKeyword(String urlOfSearchKeyword) {
		this.urlOfSearchKeyword = urlOfSearchKeyword;
	}

	public String getUrlOfSearchKeyword() {
		return urlOfSearchKeyword;
	}
	
	public RestOperations getRestTemplate() {
		return restTemplate;
	}
	
	public void setRestTemplate(RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String searchKeyword(String keyword) throws ToKeepTracksServiceException {
		
		String jsonRequest = String.format("{\"api_key\":\"mykey\",\"api_secret\":\"mysecret\",\"keyword\":\"%s\"}",keyword);
		
		String jsonResponse = null;
		
		try {
			jsonResponse = restTemplate.postForObject(urlOfSearchKeyword, jsonRequest, String.class);
		} catch(HttpServerErrorException e) {
			throw new ToKeepTracksServiceException("server error");
		}
		return jsonResponse;
	}
}
