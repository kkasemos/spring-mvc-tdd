package com.mycompany.ppms.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycompany.ppms.model.Product;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-service-context.xml")
public class ProductServiceTest {


    @Autowired
    private ProductService productService;	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testFindByNameContainsNiceShoesFoundOne() {
		String name = "Very Nice Shoes";
		String description = "Very Nice Shoes made in Thailand";
		List<Product> matchedProducts = productService.findByNameContains(name);
		
		assertTrue(matchedProducts.size() == 1);
		assertEquals(name, matchedProducts.get(0).getName());
		assertEquals(description, matchedProducts.get(0).getDescription());
	}
}
