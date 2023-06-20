package com.product.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.entity.Product;
import com.product.repo.ProductRepo;
import com.product.restController.ProductController;
import com.product.service.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;;

	@MockBean
	private ProductService productService;

	@Test
	public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception {
          Product product=null;
		// given - precondition or setup
		 product = product.builder().productName("Laptop").productDescription("I7 Generation").price(55000)
				.build();
		given(productService.save(any(Product.class))).willAnswer((invocation) -> invocation.getArgument(0));

		// when - action or behavior that we are going test
		ResultActions response = mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product)));

		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.productName", is(product.getProductName())))
				.andExpect(jsonPath("$.productDescription", is(product.getProductDescription())))
				.andExpect(jsonPath("$.price", is(product.getPrice())));

	}

	// JUnit test for Get All product REST API
	@Test
	public void givenListOfProduct_whenGetAllProduct_thenReturnProductList() throws Exception {
		// given - precondition or setup
		List<Product> listOfProduct = new ArrayList<>();
		listOfProduct
				.add(Product.builder().productName("Laptop").productDescription("I7 Generation").price(55000).build());
		listOfProduct
				.add(Product.builder().productName("Mobile").productDescription("5G Support").price(10000).build());
		given(productService.getAllProduct()).willReturn(listOfProduct);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/product"));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfProduct.size())));

	}

	// positive scenario - valid product id
	// JUnit test for GET product by id REST API
	@Test
	public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception {
		// given - precondition or setup
		String productId = "1";
		Product product = Product.builder().productName("Laptop").productDescription("I7 Generation").price(55000)
				.build();
		given(productService.getById(productId)).willReturn(product);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/employees/{id}", productId));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.productName", is(product.getProductName())))
				.andExpect(jsonPath("$.productDescription", is(product.getProductDescription())))
				.andExpect(jsonPath("$.price", is(product.getPrice())));

	}

	// negative scenario - valid product id
	// JUnit test for GET product by id REST API
	@Test
	public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception {
		// given - precondition or setup
		String productId = "1";
		Product product = Product.builder().productName("Laptop").productDescription("I7 Generation").price(55000)
				.build();
		given(productService.getById(productId)).willReturn(product);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/product/{id}", productId));

		// then - verify the output
		response.andExpect(status().isNotFound()).andDo(print());

	}

	// JUnit test for update product REST API - positive scenario
	@Test
	public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdateProductObject() throws Exception {
		// given - precondition or setup
		String productId = "1";
		Product product = Product.builder().productName("Laptop").productDescription("I7 Generation").price(55000)
				.build();

		Product updatedProduct = Product.builder().productName("Mobile").productDescription("5G Supported").price(45000)
				.build();
		given(productService.getById(productId)).willReturn(updatedProduct);
		given(productService.update(any(Product.class), productId)).willAnswer((invocation) -> invocation.getArgument(0));

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(put("/product/{id}", productId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedProduct)));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.productName", is(updatedProduct.getProductName())))
				.andExpect(jsonPath("$.productDescription", is(updatedProduct.getProductDescription())))
				.andExpect(jsonPath("$.price", is(updatedProduct.getPrice())));
	}

	// JUnit test for update product REST API - negative scenario
	@Test
	public void givenUpdatedProduct_whenUpdateProduct_thenReturn404() throws Exception {
		// given - precondition or setup
		String productId = "1";
		Product product = Product.builder().productName("Laptop").productDescription("I7 Generation").price(55000)
				.build();

		Product updateProduct = Product.builder().productName("Mobile").productDescription("5G Supported").price(45000)
				.build();
		given(productService.getById(productId)).willReturn(updateProduct);
		given(productService.update(any(Product.class), productId)).willAnswer((invocation) -> invocation.getArgument(0));

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(put("/product/{id}", productId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateProduct)));

		// then - verify the output
		response.andExpect(status().isNotFound()).andDo(print());
	}

// JUnit test for delete product REST API
	@Test
	public void givenProductId_whenDeleteProduct_thenReturn200() throws Exception {
		// given - precondition or setup
		String productId = "1";
		((ProductController) willDoNothing().given(productService)).deleteProduct(productId);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(delete("/product/{id}", productId));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print());
	}
}
