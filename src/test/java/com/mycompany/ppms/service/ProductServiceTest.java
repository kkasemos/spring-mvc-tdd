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
	public void testFindByNameContainsCoolShoesFoundOne() {
		String name = "Cool Shoes";
		String description = "Cool Shoes made in Japan";
		List<Product> matchedProducts = productService.findByNameContains(name);
		
		assertEquals(1, matchedProducts.size());
		assertEquals(name, matchedProducts.get(0).getName());
		assertEquals(description, matchedProducts.get(0).getDescription());
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
	
	@Test
	public void testFindByNameContainsShoesFoundTwo() {
		String keyword = "Shoes";
		String name1 = "Very Nice Shoes";
		String description1 = "Very Nice Shoes made in Thailand";
		String name2 = "Cool Shoes";
		String description2 = "Cool Shoes made in Japan";
		List<Product> matchedProducts = productService.findByNameContains(keyword);
		
		assertEquals(2, matchedProducts.size());
		
		assertEquals(name1, matchedProducts.get(0).getName());
		assertEquals(description1, matchedProducts.get(0).getDescription());

		assertEquals(name2, matchedProducts.get(1).getName());
		assertEquals(description2, matchedProducts.get(1).getDescription());
	}
}
