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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.dao.UserDAO;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.EncrypUtils;

/**
 * The user service impl
 * 
 * @author UayLU
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService
{

    private UserDAO userDAO;

    /**
     * setUserDAO use to set datasource from applicationcontent.xml
     *
     * @param ud
     *            : UserDAO from datasource
     * @return void
     */
    @Autowired
    @Qualifier("userDAO")
    public void setUserDAO(UserDAO ud)
    {
        this.userDAO = ud;
    }

    /**
     * is_user_exits use to check exist user in database
     *
     * @param username
     *            : string username use to check
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    @Transactional
    public Boolean isUserExits(String username) throws HandlerException
    {
        username = username.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
        return this.userDAO.isUserExits(username);
    }

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
    @Override
    @Transactional
    public Boolean isEmailExits(String email, User user) throws HandlerException
    {
        email = email.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
        return this.userDAO.isEmailExits(email, user);
    }

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
    @Override
    @Transactional
    public User isValidLogin(String username, String password) throws HandlerException
    {
        username = username.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
        password = password.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
        password = EncrypUtils.encrypData(password);
        return this.userDAO.isValidLogin(username, password);
    }

    /**
     * find_by_name use for find user in database
     *
     * @param name
     *            : name of username, firstname, lastname
     * @return List<User>
     * @exception HandlerException
     */
    @Override
    public List<User> findByName(String name) throws HandlerException
    {
        return this.userDAO.findByName(name);
    }

    /**
     * deleteByUsername use to check delete user by username in database
     *
     * @param username
     *            : string username use to check
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    public Boolean deleteByUsername(String username) throws HandlerException
    {
        return this.userDAO.deleteByUsername(username);
    }

    /**
     * searchByUsername use to check delete user by username in database
     *
     * @param username
     *            : string username use to check
     * @return User
     * @exception HandlerException
     */
    @Override
    public User findByUsername(String username) throws HandlerException
    {

        return this.userDAO.findByUsername(username);

    }

}
