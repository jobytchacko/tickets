package com.tickets.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tickets.filters.JwtRequestFilter;

/** 
 * <h1>This is a security configurer class to manage the security related events</h1> 
 * Database username/password authentication is implemented
 * http security configuration helps for authorization to different roles
 * This configuration class is a part of spring security implemenattion
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-04-04
 */
@EnableWebSecurity
public class SecuritySecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{		
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		
	}
	
	@Override
	@Bean
	public AuthenticationManager  authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//csrf disabled
		// authenticate url is permitted for all users/requests
		// agent and admin url is for the users with role admin
		// all other url is accesable to most of the users - listed in hasanyrole
		// all requests shoule be authenticated 
		
		http.csrf().disable().authorizeRequests()
			.antMatchers("/authenticate").permitAll()
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/agent").hasRole("ADMIN")
			.antMatchers("*/admin").hasRole("ADMIN")
			.antMatchers("*/agent").hasRole("ADMIN")
			.antMatchers("/**").hasAnyRole("USER","ADMIN")
			.anyRequest().authenticated()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
			.invalidateHttpSession(true);
		
		// calls the jwtRequest filer before UsernamePasswordAuthenticationFilter
		// UsernamePasswordAuthenticationFilter execute all methods in all requests even though the request go through this filter
		// UsernamePasswordAuthenticationFilter specifically for initial Authentication request only
		 	
		http.addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); 
	}

}
