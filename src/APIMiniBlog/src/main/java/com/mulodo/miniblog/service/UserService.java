/* 
 * UserService.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.model.User;

/**
 * The interface of user service
 * 
 * @author UayLU
 * 
 */
public interface UserService extends GenericService<User> {
	
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
	public Boolean isUserExits(String username) throws ServiceException;
	
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
	public Boolean isEmailExits(String email, User user) throws ServiceException;
	
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
	public User isValidLogin(String username, String password) throws ServiceException;
	
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
	public List<User> findByName(String name) throws ServiceException;
	
	/**
	 *  deleteByUsername use to check delete user by username in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public Boolean deleteByUsername(String username) throws ServiceException;
}
