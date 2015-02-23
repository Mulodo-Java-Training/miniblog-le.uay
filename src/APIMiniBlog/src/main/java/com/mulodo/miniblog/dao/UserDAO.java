/* 
 * UserDAO.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.User;

/**
 * The interface of user dao
 * 
 * @author UayLU
 * 
 */
public interface UserDAO extends GenericDAO<User>{
	
	/**
	 *  isUserExits use to check exist user in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  HandlerException
	 */
	public Boolean isUserExits(String username) throws HandlerException;
	
	/**
	 *  isEmailExits use to check exist email in database
	 *	
	 *	@param	email : email for check
	 *  @param  user  : check email but not check email for this user
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  HandlerException
	 */
	public Boolean isEmailExits(String email, User user) throws HandlerException;
	
	/**
	 *  isValidLogin check valid login
	 *	
	 *	@param	username : username for login
	 *  @param  password : password for login
	 *
	 *	@return User
	 *	
	 *	
	 *  @exception  HandlerException
	 */
	public User isValidLogin(String username, String password) throws HandlerException;
	
	/**
	 *  findByName use for find user in database
	 *	
	 *	@param	name : name of username, firstname, lastname
	 *
	 *	@return List<User>
	 *	
	 *	
	 *  @exception  HandlerException
	 */
	public List<User> findByName(String name) throws HandlerException;
	

	/**
	 *  deleteByUsername use to check delete user by username in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  HandlerException
	 */
	public Boolean deleteByUsername(String username) throws HandlerException;

	
	/**
	 *  findByUsername use to check find user by username in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  HandlerException
	 */
	public User findByUsername(String username) throws HandlerException;
}
