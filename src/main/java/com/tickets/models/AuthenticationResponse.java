package com.tickets.models;

/** 
 * <h1>This model class is used to manage the response after authentication</h1> 
 * Jwt token will be passed to the client using this model class
 * This is used to implement the spring security with JPA
 * This is used during the authentication process using spring security 
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-04-04
 */
public class AuthenticationResponse {
	
	private String jwt;
	
	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	
	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
