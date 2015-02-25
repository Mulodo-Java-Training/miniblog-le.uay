/* 
 * GenericDAOImpl.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.dao.UserDAO;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.User;

/**
 * The user dao impl
 * 
 * @author UayLU
 */
@Repository("userDAO")
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO
{

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    /**
     * isUserExits use to check exist user in database
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
        try {

            session = this.sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(User.class);

            if (username != null) {
                criteria.add(Restrictions.eq("username", username));
            }

            if (criteria.list().isEmpty()) {
                return false;
            }
            User user = (User) criteria.list().get(0);
            logger.info("Check user successfully,  details=" + user.getId());
            return user.getId() != 0 && user.getUsername().equals(username);

        } catch (HibernateException ex) {

            throw new HandlerException(ex.getMessage());
        }
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

        try {

            session = this.sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(User.class);

            if (email != null) {
                criteria.add(Restrictions.eq("email", email));
            }

            if (criteria.list().isEmpty()) {
                return false;
            }
            User userDB = (User) criteria.list().get(0);

            return !(user != null && user.getId() == userDB.getId());

        } catch (HibernateException ex) {

            throw new HandlerException(ex.getMessage());
        }
    }

    /**
     * isValidLogin check valid login
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
        try {
            User user = null;

            session = this.sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(User.class);

            criteria.add(Restrictions.eq("username", username));
            criteria.add(Restrictions.eq("password", password));

            if (!criteria.list().isEmpty()) {
                user = (User) criteria.list().get(0);
                logger.info("Person loaded successfully, Person details=" + user);
            }
            return user;
        } catch (HibernateException ex) {

            throw new HandlerException(ex.getMessage());
        }
    }

    /**
     * find_by_name use for find user in database
     *
     * @param name
     *            : name of username, firstname, lastname
     * @return List<User>
     * @exception HandlerException
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<User> findByName(String name) throws HandlerException
    {
        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(User.class);

            criteria.add(
                    Restrictions.disjunction().add(Restrictions.like("username", "%" + name + "%"))
                            .add(Restrictions.like("firstname", "%" + name + "%"))
                            .add(Restrictions.like("lastname", "%" + name + "%")))
                    .add(Restrictions.eq("status", Constraints.USER_ACTIVE))
                    .setProjection(
                            Projections.projectionList().add(Projections.property("id"), "id")
                                    .add(Projections.property("username"), "username")
                                    .add(Projections.property("firstname"), "firstname")
                                    .add(Projections.property("lastname"), "lastname")
                                    .add(Projections.property("status"), "status")
                                    .add(Projections.property("email"), "email"))
                    .setResultTransformer(Transformers.aliasToBean(User.class));

            List<User> usersList = new ArrayList<User>();
            if (!criteria.list().isEmpty()) {
                usersList = criteria.list();
                for (User us : usersList) {
                    logger.info("Person List::" + us);
                }
            }
            return usersList;
        } catch (HibernateException ex) {

            throw new HandlerException(ex.getMessage());
        }
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
    @Transactional
    public Boolean deleteByUsername(String username) throws HandlerException
    {
        try {
            session = this.sessionFactory.getCurrentSession();

            Criteria criteria = session.createCriteria(User.class);

            criteria.add(Restrictions.eq("username", username));
            if (criteria.list().isEmpty()) {
                return false;
            } else {
                User user = (User) criteria.list().get(0);
                session.delete(user);

                logger.info("User deleted successfully, user details=" + user);
                return true;
            }

        } catch (HibernateException ex) {

            throw new HandlerException(ex.getMessage());
        }
    }

    /**
     * findByUsername use to check search user by username in database
     *
     * @param username
     *            : string username use to search
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    @Transactional
    public User findByUsername(String username) throws HandlerException
    {
        User user = null;

        try {
            session = this.sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("username", username));
            if (!criteria.list().isEmpty()) {
                user = (User) criteria.list().get(0);
                logger.info("Find user successfully, user details=" + user);
            }
            return user;
        } catch (HibernateException ex) {

            logger.info("Hibernate exception, Details=" + ex.getMessage());
            throw new HandlerException(ex.getMessage());
        }
    }
}