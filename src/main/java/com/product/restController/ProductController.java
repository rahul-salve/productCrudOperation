package com.product.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.product.entity.Product;
import com.product.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;

	// rest api for save new product in database
	// uri=http://localhost:8082/product
	@PostMapping("/product")
	public ResponseEntity<String> createProduct(@RequestBody Product product) {
		String status = productService.save(product);
		return new ResponseEntity<String>(status, HttpStatus.CREATED);
	}

	// rest api for save update existing product in database
	// uri=http://localhost:8082/product/2
	@PutMapping("/product/{productId}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable String productId) {
		Product products = productService.update(product, productId);
		return new ResponseEntity<Product>(products, HttpStatus.OK);
	}

	// rest api for get particular product
	// uri=http://localhost:8082/product/3
	@GetMapping("/product/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable String productId) {
		Product product = productService.getById(productId);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	// rest api for get all product
	// uri=http://localhost:8082/product
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> allProduct = productService.getAllProduct();
		return new ResponseEntity<>(allProduct, HttpStatus.OK);
	}

	// rest api for delete particular product
	// uri=http://localhost:8082/product/2
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
		String status = productService.deleteById(productId);
		return new ResponseEntity<>(status, HttpStatus.OK);

	}
}
