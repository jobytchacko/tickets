package com.tickets.common.exceptions;

import java.util.ArrayList;

/** 
 * <h1>Custom exception built to throw the exception whenever there is an invalid fields values</h1> 
 * wrongFields has the collection of the fields that is invalid
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-03-25 
 */

public class InvalidFieldsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	   
	public ArrayList<String> wrongFields;
	
	public InvalidFieldsException(ArrayList<String> wrongFields){
		this.wrongFields = wrongFields;
	}   

}
