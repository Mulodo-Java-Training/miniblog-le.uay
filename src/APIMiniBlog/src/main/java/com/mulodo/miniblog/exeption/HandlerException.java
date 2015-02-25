/* 
 * HandlerException.java 
 *  
 * 1.0
 * 
 * 2015/02/23
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.exeption;

/**
 * The handler exception
 * 
 * @author UayLU
 */
@SuppressWarnings("serial")
public class HandlerException extends Exception
{

    /**
     * constructor of HandlerException
     */
    public HandlerException() {
        super();
    }

    /**
     * constructor of HandlerException
     *
     * @param message
     *            : message for exception
     */
    public HandlerException(String message) {
        super(message);
    }
}
