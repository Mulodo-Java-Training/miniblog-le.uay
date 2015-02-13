/* 
 * UserServiceImpl.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.service.impl;


import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.dao.UserDAO;
import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.EncrypUtils;

/**
 * The user service impl
 * 
 * @author UayLU
 * 
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService{
	
	private UserDAO userDAO;
	
	 /**
	 *  setUserDAO use to set datasource from applicationcontent.xml
	 *	
	 *	@param	ud : UserDAO from datasource
	 *
	 *	@return void
	 */
	@Autowired
	@Qualifier("userDAO")
	public void setUserDAO(UserDAO ud){
		this.userDAO = ud;
	}

	/**
	 *  is_user_exits use to check exist user in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	@Transactional
	public Boolean isUserExits(String username) throws ServiceException {
		try{
			username = username.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
			return this.userDAO.isUserExits(username);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
	 *  is_email_exits use to check exist email in database
	 *	
	 *	@param	email : email for check
	 *  @param  user  : check email but not check email for this user
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	@Transactional
	public Boolean isEmailExits(String email, User user) throws ServiceException {
		try{
			email = email.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
			return this.userDAO.isEmailExits(email, user);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
	 *  is_valid_login check valid login
	 *	
	 *	@param	username : username for login
	 *  @param  password : password for login
	 *
	 *	@return User
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	@Transactional
	public User isValidLogin(String username, String password)
			throws ServiceException {
		try{
			username = username.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
			password = password.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
			password = EncrypUtils.encrypData(password);
			return this.userDAO.isValidLogin(username,password);
		}catch(NoSuchAlgorithmException ex){
			throw new ServiceException(ex.getMessage());
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
	 *  find_by_name use for find user in database
	 *	
	 *	@param	name : name of username, firstname, lastname
	 *
	 *	@return List<User>
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	public List<User> findByName(String name) throws ServiceException {
		try{
			return this.userDAO.findByName(name);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	
	/**
	 *  deleteByUsername use to check delete user by username in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	public Boolean deleteByUsername(String username) throws ServiceException {
		try{
			return this.userDAO.deleteByUsername(username);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
	 *  searchByUsername use to check delete user by username in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return User
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	public User findByUsername(String username) throws ServiceException {
		try{
			return this.userDAO.findByUsername(username);
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	
}
