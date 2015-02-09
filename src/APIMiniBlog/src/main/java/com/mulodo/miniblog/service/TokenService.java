/* 
 * TokenService.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.service;

import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;

/**
 * The interface of token service
 * 
 * @author UayLU
 * 
 */
public interface TokenService extends GenericService<Token>{
	
	/**
	 *  delete_by_access_key use to call delete by access key function from tokendao
	 *	
	 *	@param	access_key : access_key in token table
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	public Boolean deleteByAccessKey(String access_key) throws ServiceException ;
	
	/**
	 *  find_by_access_key use to call find by access key function from tokendao
	 *	
	 *	@param	access_key : access_key in token table
	 *
	 *	@return Token
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	public Token findByAccessKey(String access_key) throws ServiceException ;
	
	/**
	 *  delete_by_user use to call delete by user function from tokendao
	 *	
	 *	@param	user : user that owner access_key in token table
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	public Boolean deleteByUser(User user) throws ServiceException;
}
