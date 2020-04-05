package com.tickets.common.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/** 
 * CommonValidatorImpl is an implementation class of CommonValidator
 * This class will have the common methods that will be useful to validate in the entire application
 * The main return will be CommonValidator itself to make service consumer to access other properties of CommonValidator
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-03-27
 */

@Service
public class CommonValidatorImpl implements CommonValidator {
	
	public static final Pattern emailRegPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
	
	/** 
	 * This method verifies whether the particular parameter is null or not
	 * If it is null, it will throw exception, otherwise this class itself
	 */
	@Override
	public CommonValidator notNull(String parameter, String parameterName) {
		if (null == parameter) {
			throw new IllegalArgumentException("Parameter " + parameterName + " cannot be null");
		}
		return this;
	}

	/** 
	 * This method verifies whether the particular parameter is empty or not
	 * If it is empty, it will throw exception, otherwise this class itself
	 */
	@Override
	public CommonValidator notEmpty(String parameter, String parameterName) {
		notNull(parameter, parameterName);
		if (parameter.trim().isEmpty()) {
			throw new IllegalArgumentException("Parameter " + parameterName + " cannot be empty");
		}
		return this;
	}

	/** 
	 * This method verifies whether the email ID is in correct format or not
	 * It will check whether particular string in empty or not in the first step
	 * If the email is valid, it will return this class itself. If not, throws the exception.
	 */
	@Override
	public CommonValidator isValidEmail(String email, String parameterName) {
		notEmpty(email, parameterName);
		System.out.println("Email to check"+email);
		if(!emailRegPattern.matcher(email).matches()) {
			throw new IllegalArgumentException("Parameter " + parameterName + " is not in correct format");
		}
		return this;
	}
	
	/** 
	 * This method verifies whether the long id is valid - Specialy to check the entity unique ID
	 * It will check whether particular string in empty or not in the first step
	 * If the email is valid, it will return this class itself. If not, throws the exception.
	 */
	@Override
	public CommonValidator isValidID(long id, String parameterName) {
		if(Long.valueOf(id) == null || Long.valueOf(id)<1) {
			throw new IllegalArgumentException("Parameter " + parameterName + " is not valid");
		}
		return this;
	}

	
}
