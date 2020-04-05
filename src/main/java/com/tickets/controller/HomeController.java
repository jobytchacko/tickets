package com.tickets.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/** 
 * This is HomeController class. It doesn't have any specific purpose
 * Used to map the root / of the URL
 * Mostly used for testing different type of access
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-04-04 
 */
@RestController
@CrossOrigin(origins = "*")
public class HomeController {
	 
	
	/** 
	  * This is the root mapping
	  * @return string This returns "Hello World" as response 
	  */
	@RequestMapping("/")
	public String home() {
		return "(<h1>Welcome</h1>)";
	}
	
	 /** 
	  * This is for user mapping
	  * @return string This returns "Hello World" as response 
	  */
	@RequestMapping("/user")
	public String user() {
		return "(<h1>Welcome User</h1>)";
	}
	
	/** 
	  * This is for admin mapping
	  * @return string This returns "Hello World" as response 
	  */
	@RequestMapping("/admin")
	public String admin() {
		return "(<h1>Welcome Admin</h1>)";
	}
	
	 /** 
	  * This is for user mapping
	  * @return string This returns "Hello World" as response 
	  */
	@RequestMapping("/agent")
	public String agent() {
		return "(<h1>Welcome Agent</h1>)";
	}
	
	 /** 
	  * This is for login mapping
	  * @return string This returns "Hello World" as response 
	  */
	@RequestMapping("/login")
	public String login() {
		return "Logged out successfully";
	}


}
