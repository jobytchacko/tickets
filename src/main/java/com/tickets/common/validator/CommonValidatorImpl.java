package com.tickets.common.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class CommonValidatorImpl implements CommonValidator {
	
	public static final Pattern emailRegPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
	
	@Override
	public CommonValidator notNull(String parameter, String parameterName) {
		if (null == parameter) {
			throw new IllegalArgumentException("Parameter " + parameterName + " cannot be null");
		}
		return this;
	}

	@Override
	public CommonValidator notEmpty(String parameter, String parameterName) {
		notNull(parameter, parameterName);
		if (parameter.trim().isEmpty()) {
			throw new IllegalArgumentException("Parameter " + parameterName + " cannot be empty");
		}
		return this;
	}

	@Override
	public CommonValidator isValidEmail(String email, String parameterName) {
		notEmpty(email, parameterName);
		System.out.println("Email to check"+email);
		if(!emailRegPattern.matcher(email).matches()) {
			throw new IllegalArgumentException("Parameter " + parameterName + " is not in correct format");
		}
		return this;
	}
	
	@Override
	public CommonValidator isValidID(long id, String parameterName) {
		if(Long.valueOf(id) == null || Long.valueOf(id)<1) {
			throw new IllegalArgumentException("Parameter " + parameterName + " is not valid");
		}
		return this;
	}

	
}
