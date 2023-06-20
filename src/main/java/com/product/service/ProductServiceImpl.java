package com.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.entity.Product;
import com.product.exception.ProductNotFoundException;
import com.product.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {
  
	@Autowired
	private ProductRepo productRepo;
	@Override
	public String save(Product product) {
		productRepo.save(product);
		return "Product Data Inserted...";
	}

	@Override
	public Product update(Product product ,String productId) {
		Optional<Product> optionalProduct = productRepo.findById(productId);
            if (optionalProduct.isPresent()) {
		    Product existingProduct = optionalProduct.get();
		    existingProduct.setProductName(product.getProductName());
		    existingProduct.setProductDescription(product.getProductDescription());
		    existingProduct.setPrice(product.getPrice());
		    // Update other properties as needed

		   product= productRepo.save(existingProduct);
		   return product;
		} else { 	 	
			throw new ProductNotFoundException();
		}
      }

	@Override
	public Product getById(String productId) {
		Optional<Product> findbyId = productRepo.findById(productId);
		if (findbyId.isPresent()) {
			return findbyId.get();
		}
		throw new ProductNotFoundException();
	}

	@Override
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

	@Override
	public String deleteById(String productId) {
		if (productRepo.existsById(productId)) {
			productRepo.deleteById(productId);
			return "Product deleted...";
		} else
			throw new ProductNotFoundException();
	}

}
