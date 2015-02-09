/* 
 * CommentServiceImpl.java 
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

import com.mulodo.miniblog.dao.CommentDAO;
import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.model.Comment;
import com.mulodo.miniblog.service.CommentService;

/**
 * The comment service impl
 * 
 * @author UayLU
 * 
 */
@Service
public class CommentServiceImpl extends GenericServiceImpl<Comment> implements CommentService{
	
	private CommentDAO commentDAO;
	
	/**
	 *  setCommentDAO use to set datasource from applicationcontent.xml
	 *	
	 *	@param	cd : CommentDAO from datasource
	 *
	 *	@return void
	 */
	@Autowired
	@Qualifier("commentDAO")
	public void setCommentDAO(CommentDAO cd){
		this.commentDAO = cd;
	}

	/**
	 *  get_all_comment_for_post use to call get all comment for post function in 
	 *  commentdao for post.
	 *	
	 *	@param	post_id : id of post for search
	 *  @param  isOwnerPost : result of check current user owner or not
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	public List<Comment> getAllCommentForPost(int post_id, Boolean isOwnerPost)
			throws ServiceException {
		try {
			return this.commentDAO.getAllCommentForPost(post_id, isOwnerPost);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 *  get_all_comment_for_user use to call get all comment for user function in 
	 *  commentdao for post.
	 *	
	 *	@param	post_id : id of post for search
	 *  @param  isOwnerPost : result of check current user owner or not
	 *
	 *	@return Boolean
	 *	
	 *	
	 *  @exception  ServiceException
	 */
	@Override
	public List<Comment> getAllCommentForUser(int user_id, Boolean isOwnerUser)
			throws ServiceException {
		try {
			return this.commentDAO.getAllCommentForUser(user_id, isOwnerUser);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}