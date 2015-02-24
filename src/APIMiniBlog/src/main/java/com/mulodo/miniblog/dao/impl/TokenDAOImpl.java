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

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.TokenDAO;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;

/**
 * The token dao impl
 * 
 * @author UayLU
 */
@Repository("tokenDAO")
public class TokenDAOImpl extends GenericDAOImpl<Token> implements TokenDAO
{

    private static final Logger logger = LoggerFactory.getLogger(TokenDAOImpl.class);

    /**
     * delete_by_access_key use to delete token exist in database
     *
     * @param access_key
     *            : access_key in token table
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    public Boolean deleteByAccessKey(String access_key) throws HandlerException
    {
        try {
            session = this.sessionFactory.openSession();
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Token.class);

            criteria.add(Restrictions.eq("access_key", access_key));
            if (criteria.list().isEmpty()) {
                return false;
            } else {
                Token token = (Token) criteria.list().get(0);
                session.delete(token);
                tx.commit();
                logger.info("Token deleted successfully, token details=" + token);
                return true;
            }
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * find_by_access_key use to find token exist in database
     *
     * @param access_key
     *            : access_key in token table
     * @return Token
     * @exception HandlerException
     */
    @Override
    public Token findByAccessKey(String access_key) throws HandlerException
    {
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(Token.class);

            criteria.add(Restrictions.eq("access_key", access_key));
            Token token = null;
            if (!criteria.list().isEmpty()) {
                token = (Token) criteria.list().get(0);
                logger.info("Token searching successfully, token details=" + token);
            }
            return token;
        } catch (HibernateException ex) {
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * delete_by_user use to delete all token exist with specific user in
     * database
     *
     * @param user
     *            : user that owner access_key in token table
     * @return Boolean
     * @exception HandlerException
     */
    @Override
    public Boolean deleteByUser(User user) throws HandlerException
    {

        try {
            session = this.sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("delete Token where user_id = :user_id");
            query.setParameter("user_id", user.getId());
            query.executeUpdate();
            tx.commit();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HandlerException(ex.getMessage());
        } finally {
            session.close();
        }
    }

}
