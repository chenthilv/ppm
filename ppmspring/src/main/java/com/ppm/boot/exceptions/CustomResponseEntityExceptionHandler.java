package com.ppm.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ppm.boot.response.ProjectIdExceptionResponse;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<Object> handleException(ProjectIdException exception, WebRequest webRequest){
		System.out.println("exception : "+ exception.getMessage());
		ProjectIdExceptionResponse projectIdExceptionResponse = new ProjectIdExceptionResponse(exception.getMessage());
		return new ResponseEntity(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception, WebRequest webRequest){
		System.out.println("exception : "+ exception.getMessage());
		UsernameAlreadyExistsException exceptionResponse = new UsernameAlreadyExistsException(exception.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
