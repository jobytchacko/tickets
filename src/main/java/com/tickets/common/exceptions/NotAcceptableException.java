package com.tickets.common.exceptions;

public class NotAcceptableException extends RuntimeException {
	
private static final long serialVersionUID = 1L;
	
	private String customMessage;
	
	public NotAcceptableException(String message){
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
