package com.product.exceptionHandler;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.product.exception.ProductNotFoundException;

@RestControllerAdvice 	
public class ProductExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorType> handleProductNotFound(ProductNotFoundException ex){
		return new ResponseEntity<ErrorType>(new ErrorType("Product Not Found..","404","NOT FOUND"), HttpStatus.NOT_FOUND);
		
	}

}
