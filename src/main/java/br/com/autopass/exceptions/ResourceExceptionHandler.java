package br.com.autopass.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<CustomErrorMessage> userNotFound(UserNotFoundException e, HttpServletRequest request) {
		CustomErrorMessage err = new CustomErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(SaleNotFoundException.class)
	public ResponseEntity<CustomErrorMessage> saleNotFound(SaleNotFoundException e, HttpServletRequest request) {
		CustomErrorMessage err = new CustomErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
