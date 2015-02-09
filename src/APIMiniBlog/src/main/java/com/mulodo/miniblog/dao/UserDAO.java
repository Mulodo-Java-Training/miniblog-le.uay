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

import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.model.User;

/**
 * The interface of user dao
 * 
 * @author UayLU
 * 
 */
public interface UserDAO extends GenericDAO<User>{
	
	/**
	 *  is_user_exits use to check exist user in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public Boolean isUserExits(String username) throws DAOException;
	
	/**
	 *  is_email_exits use to check exist email in database
	 *	
	 *	@param	email : email for check
	 *  @param  user  : check email but not check email for this user
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public Boolean isEmailExits(String email, User user) throws DAOException;
	
	/**
	 *  is_valid_login check valid login
	 *	
	 *	@param	username : username for login
	 *  @param  password : password for login
	 *
	 *	@return User
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public User isValidLogin(String username, String password) throws DAOException;
	
	/**
	 *  find_by_name use for find user in database
	 *	
	 *	@param	name : name of username, firstname, lastname
	 *
	 *	@return List<User>
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public List<User> findByName(String name) throws DAOException;
}
