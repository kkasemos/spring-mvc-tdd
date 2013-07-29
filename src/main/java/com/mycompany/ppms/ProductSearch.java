package com.mycompany.ppms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ProductSearch {
	
	final Logger logger = LoggerFactory.getLogger(ProductSearch.class);
	
	final static List<String> products = new ArrayList<String>();
	static {
		products.add("{\"name\": \"Very Nice Shoes\", \"description\":\"Very nice shoes made in Thailand.\"}");
		products.add("{\"name\": \"Cool Shoes\", \"description\":\"Cool shoes made in Japan.\"}");
	}
	
	@RequestMapping(value = "/product/search", method = RequestMethod.GET)
	public void productSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = StringUtils.stripToEmpty(request.getParameter("q"));
		String text = String.format("Could not find any product matches '%s'", keyword);
		
		logger.info("productSearch called with '" + StringUtils.stripToEmpty(keyword) + "'");
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		/* search the products */
		List<String> matchedProducts = new ArrayList<String>();
		for(String product: products) {
			if(product.contains(keyword)) {
				matchedProducts.add(product);
			}
		}
		
		/* generate the response */
		StringBuffer jsonBuffer = new StringBuffer();
		if(!matchedProducts.isEmpty()) {
			jsonBuffer.append("{\"status\":\"found\", \"products\": [");
			
			for(String product: matchedProducts) {
				jsonBuffer.append(product + ",");
			}
			jsonBuffer.append("]}");
		} else {
			jsonBuffer.append("{\"status\":\"not found\", \"text\":\"" + text + "\"}");
		}
		
		response.getWriter().write(jsonBuffer.toString());
	}
}
