package com.tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tickets.models.AuthenticationRequest;
import com.tickets.models.AuthenticationResponse;
import com.tickets.utils.JwtUtil;

/**
 * <h1>Controller to authenticate the user</h1> This is part of the spring
 * security implementation It will create jwt token and send back as response
 * once it is authenticated.
 * 
 * @author Joby Chacko
 * @version 1.0
 * @since 2020-04-04
 */
@RestController
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	/**
	 * This is the root mapping
	 * 
	 * @return string This returns "Hello World" as response
	 * @throws Exception
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username & password");
		}

		UserDetails ticketUserDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(ticketUserDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	/**
	 * This is the root mapping
	 * 
	 * @return string This returns "Hello World" as response
	 * @throws Exception
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> test() throws Exception {
		return ResponseEntity.ok(new AuthenticationResponse("Test"));
	}
}
