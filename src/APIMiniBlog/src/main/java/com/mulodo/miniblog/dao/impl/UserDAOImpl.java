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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.dao.UserDAO;
import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.model.User;

/**
 * The user dao impl
 * 
 * @author UayLU
 * 
 */
@Repository("userDAO")
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO{

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	
	/**
	 *  findAll use to find all user exist in database
	 *	
	 *	@param	tableName : table need for find
	 *
	 *	@return List<T>
	 *	
	 *	
	 *  @exception  DAOException
	 */
	@SuppressWarnings("unchecked")
	@Override
	 public List<User> findAll(String tableName) throws DAOException{
		try{			
	        session = this.sessionFactory.openSession();
	        tx = session.beginTransaction();
	        Criteria criteria = session.createCriteria(User.class)
	        		.setProjection(Projections.projectionList()
	        			      .add(Projections.property("id"), "id")
	        			      .add(Projections.property("username"), "username")
	        			      .add(Projections.property("firstname"), "firstname")
	        			      .add(Projections.property("lastname"), "lastname")
	        			      .add(Projections.property("created_at"), "created_at")
	        			      .add(Projections.property("modified_at"), "modified_at")
	        			      .add(Projections.property("email"), "email")
	        			      .add(Projections.property("status"), "status"))
	        			    .setResultTransformer(Transformers.aliasToBean(User.class));
	        logger.info("Get list user loaded successfully, user details");
	        tx.commit();
	        if(!criteria.list().isEmpty()){
	        	return criteria.list();
	        }else{
	        	return null;
	        }
    	}catch(ObjectNotFoundException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}catch(HibernateException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}	
	}
	
	/**
	 *  isUserExits use to check exist user in database
	 *	
	 *	@param	username : string username use to check
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  DAOException
	 */
	@Override
	public Boolean isUserExits(String username) throws DAOException {
		try{
			
	        session = this.sessionFactory.openSession();
	        tx = session.beginTransaction();
	        Criteria criteria = session.createCriteria(User.class);
	        
	        if(username != null){
	        	criteria.add(Restrictions.eq("username",username));
	        }
	        
	        if(criteria.list().isEmpty()){
	        	return false;
	        }
	        User user = (User) criteria.list().get(0);
	        logger.info("Check user successfully,  details="+user.getId());
	        tx.commit();
	        if(user.getId() != 0 && user.getUsername().equals(username)){
	        	return true;
	        }else{
	        	return false;
	        }
    	}catch(ObjectNotFoundException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}catch(HibernateException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
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
	 *  @exception  DAOException
	 */
	@Override
	public Boolean isEmailExits(String email, User user) throws DAOException {
		
		try{
			
	        session = this.sessionFactory.openSession();
	        tx = session.beginTransaction();
	        Criteria criteria = session.createCriteria(User.class);
	        
	        if(email != null){
	        	criteria.add(Restrictions.eq("email",email));
	        }
	        
	        if(criteria.list().isEmpty()){
	        	return false;
	        }
	        User userDB = (User) criteria.list().get(0);


	        tx.commit();
	        if(userDB.getId() != 0 && userDB.getEmail().equals(email)){
	        	logger.info("Email check successfully, email details="+userDB.getEmail());
	        	if(user != null && user.getId() == userDB.getId()){
	        		return false;
	        	}else{
	        		return true;
	        	}
	        }else{
	        	return false;
	        }
	        
    	}catch(ObjectNotFoundException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}catch(HibernateException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}
	}

	/**
	 *  isValidLogin check valid login
	 *	
	 *	@param	username : username for login
	 *  @param  password : password for login
	 *
	 *	@return User
	 *	
	 *	
	 *  @exception  DAOException
	 */
	@Override
	public User isValidLogin(String username, String password) throws DAOException{
		try{
			
	        session = this.sessionFactory.openSession();
	        tx = session.beginTransaction();
	        Criteria criteria = session.createCriteria(User.class);
	        
	        if(username != null && password != null){
	        	criteria.add(Restrictions.eq("username",username));
	        	criteria.add(Restrictions.eq("password",password));
	        }
	        
	        
	        if(criteria.list().isEmpty()){
	        	return null;
	        }
	        User user = (User) criteria.list().get(0);
	        logger.info("Person loaded successfully, Person details="+user);
	        tx.commit();
	        if(user.getUsername().equals(username) && user.getPassword().equals(password)){
	        	return user;
	        }else{
	        	return null;
	        }
		}catch(ObjectNotFoundException ex){
			ex.printStackTrace();
			throw new DAOException(ex.getMessage());
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new DAOException(ex.getMessage());
		}finally {
			   session.close();
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
	 *  @exception  DAOException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByName(String name) throws DAOException {
		try{
	        session = this.sessionFactory.openSession();
	        
	        Criteria criteria = session.createCriteria(User.class);
	        if(name != null){
	        	criteria.add(Restrictions.disjunction()
		        			.add(Restrictions.like("username", "%"+name+"%"))
		        			.add(Restrictions.like("firstname","%"+name+"%"))
		        			.add(Restrictions.like("lastname","%"+name+"%")))
		        		.add(Restrictions.eq("status",Constraints.USER_ACTIVE))
	        			.setProjection(Projections.projectionList()
	        			      .add(Projections.property("id"), "id")
	        			      .add(Projections.property("username"), "username")
	        			      .add(Projections.property("firstname"), "firstname")
	        			      .add(Projections.property("lastname"), "lastname")
	        			      .add(Projections.property("status"), "status")
	        			      .add(Projections.property("email"), "email"))
	        			.setResultTransformer(Transformers.aliasToBean(User.class));
	        }
	        

	        
//	        criteria.setFirstResult((pageNum - 1)* Constraints.LIMIT_ROW);
//	        criteria.setMaxResults(Constraints.LIMIT_ROW);
	        
	        if(!criteria.list().isEmpty()){
		        List<User> usersList = criteria.list();
		        for(User us : usersList){
		            logger.info("Person List::"+us);
		        }
		        return usersList;
	        }else{
	        	return null;
	        }
    	}catch(HibernateException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
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
	 *  @exception  DAOException
	 */
	@Override
	public Boolean deleteByUsername(String username) throws DAOException {
		try{ 
 	        session = this.sessionFactory.openSession();
 	        tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);
			if(username != null){
	        	criteria.add(Restrictions.eq("username",username));
	        	if(criteria.list().isEmpty()){
					return false;
				}else{
					User user = (User) criteria.list().get(0);
					session.delete(user);
		 	        tx.commit();
		 	        logger.info("User deleted successfully, user details="+user);
		 	        return true;
				}
			}
			return false;
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