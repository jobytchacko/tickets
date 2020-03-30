package com.tickets.common.exceptions;

import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** 
 * <h1>Global exception controller to manage the exceptions in gloabl level</h1> 
 * Contains all the custom and java existing exceptions
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-03-20 
 */

@ControllerAdvice
public class GlobalExceptionsController {
	
	/**
	 * Logback supports ERROR, WARN, INFO, DEBUG, or TRACE as logging level. 
	 * By default, logging level is set to INFO. 
	 * It means that code>DEBUG and TRACE messages are not visible.
	 */
    private static final Logger LOGGER=LoggerFactory.getLogger(GlobalExceptionsController.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		LOGGER.info("Exception at Global Exception"+exception.getMessage());
	    return new ResponseEntity<>("Error in Processing request", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = InvalidFieldsException.class)
	public ResponseEntity<Object> invalidFieldsException(InvalidFieldsException exception) {
		LOGGER.info("GlobalExceptionsController....."+exception.wrongFields);
	    return new ResponseEntity<>("Invalid Fields :"+(exception.wrongFields != null && !exception.wrongFields.isEmpty() ? StringUtils.join(exception.wrongFields) : null), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<Object> NullPointerException(NullPointerException exception) {
		System.out.println("Null Pointer Exception at GlobalException");
		LOGGER.info("There is a null pointer exception while process the request");
	    return new ResponseEntity<>("Error in Processing request", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = InternalServerErrorException.class)
	public ResponseEntity<Object> internalServerErrorException(InternalServerErrorException exception) {
		LOGGER.info("Something Went wrong while processing your request"+exception.getCustomMessage());
	    return new ResponseEntity<>("Error in Processing request", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = NotAcceptableException.class)
	public ResponseEntity<Object> notAcceptableException(NotAcceptableException exception) {
		LOGGER.info("There are not accepatable content in your request"+exception.getCustomMessage());
	    return new ResponseEntity<>("Error in Processing request", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException exception) {
		LOGGER.info("There are not accepatable content in your request"+exception.getMessage());
	    return new ResponseEntity<>("Error in Processing request", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = NumberFormatException.class)
	public ResponseEntity<Object> numberFormatException(NumberFormatException exception) {
		LOGGER.info("There are not accepatable content in your request"+exception.getMessage());
	    return new ResponseEntity<>("Error in Processing request", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
		LOGGER.info("There are not accepatable content in your request"+exception.getMessage());
	    return new ResponseEntity<>("Error in Processing request", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<Object> notFoundException(NotFoundException exception) {
		LOGGER.info("Requested resource is not available"+exception.getMessage());
	    return new ResponseEntity<>(exception.getCustomMessage()!=""?exception.getCustomMessage():"Requested Resource is not available", HttpStatus.NOT_FOUND);
	}
	
	
	
}
