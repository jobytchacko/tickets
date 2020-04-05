package com.tickets.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/** 
 * <h1>This entity class to implement the spring security</h1> 
 * The user table holds the details of the user to be logged in
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-04-03 
 */
@Entity
@Table(name = "users")
public class User {
	
	@GeneratedValue
	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "roles")
	private String roles;
	@Column(name = "active")
	private boolean active;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}

