/* 
 * PostDAO.java 
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
import com.mulodo.miniblog.model.Post;

/**
 * The interface of post dao
 * 
 * @author UayLU
 * 
 */
public interface PostDAO extends GenericDAO<Post>{
	
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
	public List<Post> getAllPost(int pageNum, int author_id, String description, Boolean isForUser, Boolean isOwnerUser) throws DAOException;
	
	/**
	 *  get_all_post_size use to get size of post with owner user from database
	 *	
	 *	@param	author_id : user id that owner post
	 *  @param  isForUser : result of check current user owner or not
	 *
	 *	@return int
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public int getAllPostSize(int author_id, String description, Boolean isForUser, Boolean isOwnerUser) throws DAOException;

	
	/**
	 *  deleteByTitle use for delete post by title in the database
	 *	
	 *	@param	title : title of post
	 *
	 *	@return void
	 *	
	 *  @exception  DAOException
	 */
	public void deleteByTitle(String title) throws DAOException;

	/**
	 *  findByTitle use for find post by title in the database for unit test
	 *	
	 *	@param	title : title of post
	 *
	 *	@return Post
	 *	
	 *  @exception  DAOException
	 */
	public Post findByTitle(String title) throws DAOException;
}