package com.tickets.common.exceptions;

/** 
 * <h1>Custom exception built to throw the Internal Server Exceptions manually</h1> 
 * The custom message holds the specific message passed whenever exception is thrown
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-03-25 
 */

public class InternalServerErrorException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String customMessage;
	
	public InternalServerErrorException(String message){
		super(message);
		this.customMessage = message;
	}
	
	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}
}
