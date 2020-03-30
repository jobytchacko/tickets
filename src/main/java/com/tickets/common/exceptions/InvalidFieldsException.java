package com.tickets.common.exceptions;

import java.util.ArrayList;

public class InvalidFieldsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	   
	public ArrayList<String> wrongFields;
	
	public InvalidFieldsException(ArrayList<String> wrongFields){
		this.wrongFields = wrongFields;
	}   

}
