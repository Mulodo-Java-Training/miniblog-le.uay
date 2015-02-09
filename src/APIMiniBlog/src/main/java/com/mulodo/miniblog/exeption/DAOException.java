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
public class DAOException extends Exception {
	
	/**
	 *  constructor of DAOException
	 *	
	 * 
	 *	
	 */
	public DAOException() {
		super();
	}

	/**
	 *  constructor of DAOException
	 *	
	 *  @param message : message for exception
	 *
	 *	
	 */
	public DAOException(String message) {
		super(message);
	}
}
