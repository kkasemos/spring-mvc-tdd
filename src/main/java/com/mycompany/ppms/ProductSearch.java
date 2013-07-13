package com.mycompany.ppms;

import java.io.IOException;

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

	@RequestMapping(value = "/product/search", method = RequestMethod.GET)
	public void productSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("q");
		logger.info("productSearch called with '" + StringUtils.stripToEmpty(keyword) + "'");
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write("{\"name\":\"Very Nice Shoes\"}");
	}
}
