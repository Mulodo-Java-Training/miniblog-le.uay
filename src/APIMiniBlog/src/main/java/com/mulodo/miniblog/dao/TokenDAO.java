/* 
 * TokenDAO.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.dao;

import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;

/**
 * The interface of token dao
 * 
 * @author UayLU
 * 
 */
public interface TokenDAO extends GenericDAO<Token> {
	
	/**
	 *  delete_by_access_key use to delete token exist in database
	 *	
	 *	@param	access_key : access_key in token table
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public Boolean deleteByAccessKey(String access_key) throws DAOException;
	
	/**
	 *  find_by_access_key use to find token exist in database
	 *	
	 *	@param	access_key : access_key in token table
	 *
	 *	@return Token
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public Token findByAccessKey(String access_key) throws DAOException;
	
	/**
	 *  delete_by_user use to delete all token exist with specific user in database
	 *	
	 *	@param	user : user that owner access_key in token table
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public Boolean deleteByUser(User user) throws DAOException;
}
