/* 
 * PostService.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.service;

import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.object.Data;

/**
 * The interface of post service
 * 
 * @author UayLU
 * 
 */
public interface PostService extends GenericService<Post>{
	
	/**
	 *  get_all_post use for get all post in the database limit by 10 post per request
	 *	
	 *	@param	author_id : user id that owner post
	 *  @param  pageNume: the number of page you want to get
	 *
	 *	@return List<Post>
	 *	
	 *  @exception  ServiceException
	 */
	public Data getAllPost(int pageNum, int current_user_id, String description) throws ServiceException;
	
	
	/**
	 *  get_all_post_for_user use for get all post in the database limit by 10 post per request
	 *	
	 *	@param	author_id : user id that owner post
	 *  @param  pageNume: the number of page you want to get
	 *
	 *	@return List<Post>
	 *	
	 *  @exception  ServiceException
	 */
	public Data getAllPostForUser(int pageNum, int author_id, Boolean isOwnerUser) throws ServiceException;

	/**
	 *  deleteByTitle use for delete post by title in the database for unit test
	 *	
	 *	@param	title : title of post
	 *
	 *	@return void
	 *	
	 *  @exception  ServiceException
	 */
	public void deleteByTitle(String title) throws ServiceException;

	/**
	 *  findByTitle use for find post by title in the database for unit test
	 *	
	 *	@param	title : title of post
	 *
	 *	@return Post
	 *	
	 *  @exception  ServiceException
	 */
	public Post findByTitle(String title) throws ServiceException;
}
