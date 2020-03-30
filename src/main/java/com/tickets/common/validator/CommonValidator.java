package com.tickets.common.validator;

public interface CommonValidator {
	
	public CommonValidator notNull(String parameter, String parameterName);
	
	public CommonValidator notEmpty(String parameter, String parameterName);
	
	public CommonValidator isValidEmail(String parameter, String parameterName);
	
	public CommonValidator isValidID(long parameter, String parameterName);

	
}
