/* 
 * ServiceException.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.exeption;

/**
 * The service exception 
 * 
 * @author UayLU
 * 
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception{
	
	/**
	 *  constructor of ServiceException
	 *	
	 */
	public ServiceException() {
		super();
	}

	/**
	 *  constructor of ServiceException
	 *	
	 *  @param message : message for exception
	 *	
	 */
	public ServiceException(String message) {
		super(message);
	}
}
