package com.mycompany.ppms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.mycompany.ppms.model.Product;

@Service
public class ProductService {
	
	@PersistenceContext
	EntityManager entityManager;

	public List<Product> findByNameContains(String name) {
		
		 TypedQuery<Product> namedQuery = entityManager.createNamedQuery("Product.findByNameContains", Product.class);
		 namedQuery.setParameter("name", "%" + name + "%");
		 
		 List<Product> products	= namedQuery.getResultList();
		
		return products;
	}

}
