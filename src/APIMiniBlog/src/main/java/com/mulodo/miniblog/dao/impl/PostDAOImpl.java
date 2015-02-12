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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.dao.PostDAO;
import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.User;

/**
 * The post dao impl
 * 
 * @author UayLU
 * 
 */
@Repository("postDAO")
public class PostDAOImpl extends GenericDAOImpl<Post> implements PostDAO
{

	private static final Logger logger = LoggerFactory.getLogger(PostDAOImpl.class);
	
	/**
	 *  get_all_post use to get all post from database
	 *	
	 *	@param	author_id : user id that owner post
	 *  @param  isForUser : result of check current user owner or not
	 *  @param  pageNume: the number of page you want to get
	 *
	 *	@return List<Post>
	 *	
	 *	
	 *  @exception  DAOException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAllPost(int pageNum, int author_id, String description, Boolean isForUser, Boolean isOwnerUser) throws DAOException {
		List<Post> listPost = null;
		try{
	        session = this.sessionFactory.openSession();
	        
	        Criteria criteria = session.createCriteria(Post.class, "post");
	        
	        if(pageNum > 0){
	        	criteria.setFirstResult((pageNum - 1)* Constraints.LIMIT_ROW);
		        criteria.setMaxResults(Constraints.LIMIT_ROW);
	        }
	        
	        criteria.createAlias("user", "us");
	        criteria.setProjection(Projections.projectionList()
		        		.add(Projections.property("id").as("id"))
		        		.add(Projections.property("title").as("title"))
		        		.add(Projections.property("content").as("content"))
		        		.add(Projections.property("created_at").as("created_at"))
		        		.add(Projections.property("modified_at").as("modified_at"))
		        		.add(Projections.property("status").as("status"))
		        		.add(Projections.property("us.id").as("author_id"))
		        		.add(Projections.property("us.username").as("username"))
		        		.add(Projections.property("us.status").as("status"))); 
	 
	        if(author_id > 0 && isForUser){
	        	if(isOwnerUser){
	        		criteria.add(Restrictions.eq("us.id", author_id));
	        	}else{
	        		criteria.add(Restrictions.eq("us.id", author_id));
	        		criteria.add(Restrictions.eq("us.status", Constraints.USER_ACTIVE));
	        		criteria.add(Restrictions.eq("status", Constraints.POST_ACTIVE));
	        	}
	        }else if (author_id > 0 && !isForUser){
	        	Criterion criterionPostUserActive = Restrictions.and(Restrictions.eq("status", Constraints.POST_ACTIVE),
	        			Restrictions.eq("us.status", Constraints.USER_ACTIVE));
	        	Criterion criterionCurrentActive = Restrictions.eq("us.id", author_id);
	        	criteria.add(Restrictions.or(criterionCurrentActive, criterionPostUserActive));
	        }
	        
	        if(description != null){
	        	criteria.add(Restrictions.disjunction()
		        			.add(Restrictions.like("title", "%"+description+"%"))
		        			.add(Restrictions.like("content","%"+description+"%")));
	        }
	        
	        criteria.addOrder(Order.desc("created_at"));
	        
	               
	        if(!criteria.list().isEmpty()){
	        	listPost = new ArrayList<Post>();
	        	List<Object[]> result = criteria.list();
	        	System.out.println("size "+ criteria.list().size());
	        	Post post = null;
	        	for (Iterator<Object[]> it = result.iterator(); it.hasNext();) {
	        	    Object[] myResult = (Object[]) it.next();
	        	    post = new Post();
	        	    post.setId((int) myResult[0]);
	        	    post.setTitle((String) myResult[1]);
	        	    post.setContent((String) myResult[2]);
	        	    post.setCreated_at((Date) myResult[3]);
	        	    post.setModified_at((Date) myResult[4]);
	        	    post.setStatus((int) myResult[5]);
	        	    post.setUser(new User((int) myResult[6], (String) myResult[7], (int) myResult[8]));
	        	    listPost.add(post);
	        	    logger.info("Comment in get all commente for user :"+post.getId());
	        	}
		        return listPost;
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
	 *  get_all_post_size use to get size of post with owner user from database
	 *	
	 *	@param	author_id : user id that owner post
	 *  @param  isForUser : result of get post for user
	 *  @param  isOwnerUser : result of check current user owner or not
	 *
	 *	@return int
	 *	
	 *	
	 *  @exception  DAOException
	 */
	@Override
	public int getAllPostSize(int author_id, String description,
			Boolean isForUser, Boolean isOwnerUser) throws DAOException {
		try{
	        session = this.sessionFactory.openSession();
	        
	        Criteria criteria = session.createCriteria(Post.class, "post");
	        
	        criteria.createAlias("user", "us");
	     	        	 
	        if(author_id > 0 && isForUser){
	        	if(isOwnerUser){
	        		criteria.add(Restrictions.eq("us.id", author_id));
	        	}else{
	        		criteria.add(Restrictions.eq("us.id", author_id));
	        		criteria.add(Restrictions.eq("us.status", Constraints.USER_ACTIVE));
	        		criteria.add(Restrictions.eq("status", Constraints.POST_ACTIVE));
	        	}
	        }else if (author_id > 0 && !isForUser){
	        	Criterion criterionPostUserActive = Restrictions.and(Restrictions.eq("status", Constraints.POST_ACTIVE),
	        			Restrictions.eq("us.status", Constraints.USER_ACTIVE));
	        	Criterion criterionCurrentActive = Restrictions.eq("us.id", author_id);
	        	criteria.add(Restrictions.or(criterionCurrentActive, criterionPostUserActive));
	        }
	        
	        if(description != null){
	        	criteria.add(Restrictions.disjunction()
	        			.add(Restrictions.like("title", "%"+description+"%"))
	        			.add(Restrictions.like("content","%"+description+"%")));
	        }
	        
	        Integer totalPost = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();  
	        return totalPost;
    	}catch(HibernateException ex){
    		ex.printStackTrace();
    		throw new DAOException(ex.getMessage());
    	}finally {
    		   session.close();
    	}
	}

	/**
	 *  deleteByTitle use for delete post by title in the database
	 *	
	 *	@param	title : title of post
	 *
	 *	@return void
	 *	
	 *  @exception  DAOException
	 */
	@Override
	public void deleteByTitle(String title) throws DAOException {
		try{ 
 	        session = this.sessionFactory.openSession();
 	        tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Post.class);
			if(title != null){
	        	criteria.add(Restrictions.eq("title",title));
	        	if(!criteria.list().isEmpty()){
		        	Post post = (Post) criteria.list().get(0);
					session.delete(post);
					logger.info("Post deleted successfully, post details="+post);
	        	}
	        	tx.commit();
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
	 *  findByTitle use for find post by title in the database for unit test
	 *	
	 *	@param	title : title of post
	 *
	 *	@return Post
	 *	
	 *  @exception  DAOException
	 */
	@Override
	public Post findByTitle(String title) throws DAOException {
		try{ 
 	        session = this.sessionFactory.openSession();
 	        tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Post.class);
			if(title != null){
	        	criteria.add(Restrictions.eq("title",title));
	        	Post post = (Post) criteria.list().get(0);
				
	 	        tx.commit();
	 	        logger.info("Post deleted successfully, post details="+post);
	 	        return post;
			}
			return null;
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