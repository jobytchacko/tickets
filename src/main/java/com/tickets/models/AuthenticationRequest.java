package com.tickets.models;

/** 
 * <h1>This model is used to hold the username and password</h1> 
 * It holds the username and password from the request
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-04-04
 */
public class AuthenticationRequest {
	
	private String username;
	private String password;
	
	
	public AuthenticationRequest() {
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}
