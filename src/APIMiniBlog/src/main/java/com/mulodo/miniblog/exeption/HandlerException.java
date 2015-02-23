/* 
 * DAOException.java 
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
 * The dao exception 
 * 
 * @author UayLU
 * 
 */
@SuppressWarnings("serial")
public class HandlerException extends Exception {
	
	/**
	 *  constructor of DAOException
	 *	
	 * 
	 *	
	 */
	public HandlerException() {
		super();
	}

	/**
	 *  constructor of DAOException
	 *	
	 *  @param message : message for exception
	 *
	 *	
	 */
	public HandlerException(String message) {
		super(message);
	}
}
