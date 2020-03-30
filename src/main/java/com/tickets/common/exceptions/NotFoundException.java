package com.tickets.common.exceptions;

public class NotFoundException extends RuntimeException {
	
private static final long serialVersionUID = 1L;

private String customMessage;
	
	public NotFoundException(String message){
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
