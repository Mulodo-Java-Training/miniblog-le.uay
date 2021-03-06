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

import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.User;

/**
 * The interface of user service
 * 
 * @author UayLU
 */
public interface UserService extends GenericService<User>
{

    /**
     * is_user_exits use to check exist user in database
     *
     * @param username
     *            : string username use to check
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean isUserExits(String username) throws HandlerException;

    /**
     * is_email_exits use to check exist email in database
     *
     * @param email
     *            : email for check
     * @param user
     *            : check email but not check email for this user
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean isEmailExits(String email, User user) throws HandlerException;

    /**
     * is_valid_login check valid login
     *
     * @param username
     *            : username for login
     * @param password
     *            : password for login
     * @return User
     * @exception HandlerException
     */
    public User isValidLogin(String username, String password) throws HandlerException;

    /**
     * find_by_name use for find user in database
     *
     * @param name
     *            : name of username, firstname, lastname
     * @return List<User>
     * @exception HandlerException
     */
    public List<User> findByName(String name) throws HandlerException;

    /**
     * deleteByUsername use to check delete user by username in database
     *
     * @param username
     *            : string username use to check
     * @return Boolean
     * @exception HandlerException
     */
    public Boolean deleteByUsername(String username) throws HandlerException;

    /**
     * findByUsername use to check find user by username in database
     *
     * @param username
     *            : string username use to find
     * @return User
     * @exception HandlerException
     */
    public User findByUsername(String username) throws HandlerException;
}
