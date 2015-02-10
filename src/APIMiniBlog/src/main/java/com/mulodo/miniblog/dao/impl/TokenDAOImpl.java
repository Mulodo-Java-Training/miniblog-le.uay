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
import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;

/**
 * The token dao impl
 * 
 * @author UayLU
 * 
 */
@Repository("tokenDAO")
public class TokenDAOImpl extends GenericDAOImpl<Token> implements TokenDAO{

	private static final Logger logger = LoggerFactory.getLogger(TokenDAOImpl.class);
	
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
	@Override
	public Boolean deleteByAccessKey(String access_key) throws DAOException {
		try{ 
 	        session = this.sessionFactory.openSession();
 	        tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Token.class);
			if(access_key != null){
	        	criteria.add(Restrictions.eq("access_key",access_key));
			}
			
			if(criteria.list().isEmpty()){
				return false;
			}else{
				Token token = (Token) criteria.list().get(0);
				session.delete(token);
	 	        tx.commit();
	 	        logger.info("Token deleted successfully, token details="+token);
	 	        return true;
			}
     	}catch(HibernateException ex){
     		logger.info("Hibernate exception, Details="+ex.getMessage());
     		if(tx != null)	tx.rollback();
     		ex.printStackTrace();
     		throw new DAOException(ex.getMessage());
     	}finally {
     		session.close();
     	}
	}

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
	@Override
	public Token findByAccessKey(String access_key) throws DAOException {
		try{ 
 	        session = this.sessionFactory.openSession();
 	        tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Token.class);
			if(access_key != null){
	        	criteria.add(Restrictions.eq("access_key",access_key));
			}
			
			if(criteria.list().isEmpty()){
				return null;
			}else{
				Token token = (Token) criteria.list().get(0);
				logger.info("Token searching successfully, token details="+token);
				return token;
			}
     	}catch(HibernateException ex){
     		logger.info("Hibernate exception, Details="+ex.getMessage());
     		if(tx != null)	tx.rollback();
     		ex.printStackTrace();
     		throw new DAOException(ex.getMessage());
     	}finally {
     		session.close();
     	}
	}

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
	@Override
	public Boolean deleteByUser(User user) throws DAOException {
		
		try{ 
 	        session = this.sessionFactory.openSession();
 	        tx = session.beginTransaction();
 	        Query query = session.createQuery("delete Token where user_id = :user_id");
 	        query.setParameter("user_id", user.getId());
			query.executeUpdate();
			tx.commit();
 	        return true;
     	}catch(HibernateException ex){
     		logger.info("Hibernate exception, Details="+ex.getMessage());
     		if(tx != null)	tx.rollback();
     		ex.printStackTrace();
     		throw new DAOException(ex.getMessage());
     	}finally {
     		session.close();
     	}
	}

}
