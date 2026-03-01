package com.skilltrack.customerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> handleClientErrors(HttpClientErrorException ex){
		return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
	}
}
