/* 
 * TokenServiceImpl.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mulodo.miniblog.dao.TokenDAO;
import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.service.TokenService;

/**
 * The token service impl
 * 
 * @author UayLU
 * 
 */
@Service
public class TokenServiceImpl extends GenericServiceImpl<Token> implements TokenService{

	private TokenDAO tokenDAO;
	
	 /**
	 *  setTokenDAO use to set datasource from applicationcontent.xml
	 *	
	 *	@param	tokenDAO : tokenDAO from datasource
	 *
	 *	@return void
	 */
	@Autowired
	@Qualifier("tokenDAO")
	public void setTokenDAO (TokenDAO tokenDAO){
		this.tokenDAO = tokenDAO;
	}
	
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
	@Override
	public Boolean deleteByAccessKey(String access_key) throws ServiceException {
		try{
			if (access_key!= null){
				return this.tokenDAO.deleteByAccessKey(access_key);
			}else{
				return false;
			}
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

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
	@Override
	public Token findByAccessKey(String access_key) throws ServiceException  {
		try{
			if (access_key!= null){
				Token token = this.tokenDAO.findByAccessKey(access_key);
				if(token != null){
					return token;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

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
	@Override
	public Boolean deleteByUser(User user) throws ServiceException {
		try{
			if (user!= null){
				return this.tokenDAO.deleteByUser(user);
			}else{
				return false;
			}
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

}
