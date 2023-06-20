package com.product.service;

import java.util.List;



import com.product.entity.Product;

public interface ProductService {
	public String save(Product product);

	public Product update(Product product, String productId);

	public Product getById(String productId);

	public List<Product> getAllProduct();

	public String deleteById(String productId);


	
	

	

}
