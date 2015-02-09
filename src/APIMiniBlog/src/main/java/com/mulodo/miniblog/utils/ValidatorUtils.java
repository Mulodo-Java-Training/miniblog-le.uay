/* 
 * ValidatorUtils.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mulodo.miniblog.contraints.Constraints;

/**
 * The ValidatorUtils use to validate data
 * 
 * @author UayLU
 * 
 */
public final class ValidatorUtils {
	
	public static Boolean isNotNullNotEmptyNotWhiteSpace(String string){
		return string != null && !string.isEmpty() && !string.trim().isEmpty(); 
	}
	
	public static Boolean isValidLength(String string, int min, int max){
		if(min > 0 && string.length() < min){
			return false;
		}else if(max > 0 && string.length() > max){
			return false;
		}
		return true;
	}

	public static Boolean compare(String password, String repassword) {
		return password.equals(repassword);
	}
	
	public static Boolean isPositiveInteger (String string){

		try{
			int i = Integer.parseInt(string);
			if(i > 0){
				return true;
			}else{
				return false;
			}
			
		}catch(NumberFormatException ex){
			return false;
		}
	}
	
	public static Boolean isStatusInteger (String string){
		try{
			int i = Integer.parseInt(string);
			if(i == 0 || i == 1){
				return true;
			}else{
				return false;
			}
			
		}catch(NumberFormatException ex){
			return false;
		}
	}
	
	public static Boolean isValidEmail(String email){
		if(email == null){
			return false;
		}
		Pattern pattern = Pattern.compile(Constraints.EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
