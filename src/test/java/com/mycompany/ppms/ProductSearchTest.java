package com.mycompany.ppms;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("classpath:com/mycompany/ppms/service/test-service-context.xml"),
	@ContextConfiguration("test-servlet-context.xml"),
})
public class ProductSearchTest {

    @Autowired
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSearchProductByNameFound() throws Exception {
    	String keyword = "Very Nice Shoes";
    	
        this.mockMvc.perform(get("/product/search")
        		.param("q", keyword)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$.status").value("found"))
        		.andExpect(jsonPath("$.products[0].name").value("Very Nice Shoes"));
    }
    
	@Test
	public void testSearchProductByNameNotFound() throws Exception {
		String keyword = "Soft Shoes";
		String text = String.format("Could not find any product matches '%s'", keyword);

		this.mockMvc.perform(get("/product/search")
				.param("q", keyword)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("not found"))
				.andExpect(jsonPath("$.text").value(text));
	}
	
	@Test
	public void testSearchProductByNameShoesFoundTwo() throws Exception {
		String keyword = "Shoes";
		
		this.mockMvc.perform(get("/product/search")
			.param("q", keyword)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value("found"))
			.andExpect(jsonPath("$.products[0].name").value("Very Nice Shoes"))
			.andExpect(jsonPath("$.products[1].name").value("Cool Shoes"));
	}
	
	@Test
	public void testSearchProductByNameCoolShoesFoundOne() throws Exception {
		String keyword = "Cool Shoes";
		
		this.mockMvc.perform(get("/product/search")
			.param("q", keyword)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value("found"))
			.andExpect(jsonPath("$.products[1]").doesNotExist())
			.andExpect(jsonPath("$.products[0].name").value("Cool Shoes"));
	}
}
