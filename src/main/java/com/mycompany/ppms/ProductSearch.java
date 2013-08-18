package com.mycompany.ppms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.ppms.model.Product;
import com.mycompany.ppms.service.ProductService;

@Controller
public class ProductSearch {
	
	@Autowired
	ProductService productService;
	
	final Logger logger = LoggerFactory.getLogger(ProductSearch.class);
	
	@RequestMapping(value = "/product/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> productSearch(@RequestParam("q") String q) throws IOException {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		String keyword = StringUtils.stripToEmpty(q);
		String text = String.format("Could not find any product matches '%s'", keyword);
		
		logger.info("productSearch called with '" + StringUtils.stripToEmpty(keyword) + "'");
		
		/* search the products */
		List<Product> matchedProducts = productService.findByNameContains(keyword);
		
		/* generate the response */
		if(!matchedProducts.isEmpty()) {
			jsonMap.put("status", "found");
			jsonMap.put("products", matchedProducts);
		} else {
			jsonMap.put("status", "not found");
			jsonMap.put("text", text);
		}
		
		return jsonMap;
	}
}
