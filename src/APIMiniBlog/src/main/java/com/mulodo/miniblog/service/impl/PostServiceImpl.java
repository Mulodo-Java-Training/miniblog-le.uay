/* 
 * PostServiceImpl.java 
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

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.dao.PostDAO;
import com.mulodo.miniblog.exeption.DAOException;
import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.object.Data;
import com.mulodo.miniblog.service.PostService;

/**
 * The post service impl
 * 
 * @author UayLU
 * 
 */
@Service
public class PostServiceImpl extends GenericServiceImpl<Post> implements PostService{
	
	private PostDAO postDAO;
	
	/**
	 *  setPostDAO use to set datasource from applicationcontent.xml
	 *	
	 *	@param	pd : PostDAO from datasource
	 *
	 *	@return void
	 */
	@Autowired
	@Qualifier("postDAO")
	public void setPostDAO(PostDAO pd){
		this.postDAO = pd;
	}

	/**
	 *  get_all_post use for get all post in the database limit by 10 post per request
	 *	
	 *	@param	author_id : user id that owner post
	 *  @param  pageNume: the number of page you want to get
	 *
	 *	@return List<Post>
	 *	
	 *  @exception  DAOException
	 */
	@Override
	public Data getAllPostForUser(int pageNum, int author_id, Boolean isOwerUser)
			throws ServiceException {
		Data data = null;
		try{
			if(pageNum > 0 && author_id >= 0){
				List<Post> listPost = null; 
				int totalPost = 0;
				if(pageNum > 0){
					listPost =  this.postDAO.getAllPost(pageNum, author_id, true, isOwerUser);
					totalPost = this.postDAO.getAllPostSize(author_id, true, isOwerUser);
				}
				if(listPost != null){
					int totalPage = (int) Math.round(totalPost/Constraints.LIMIT_ROW + 0.5);
					data = new Data();
					data.setLimitRow(Constraints.LIMIT_ROW);
					data.setTotalPage(totalPage);
					data.setTotalRow(totalPost);
					data.setPageNum(pageNum);
					data.setListPost(listPost);
				}
				return data;
			}
			return null;
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	/**
	 *  get_all_post_for_user use for get all post in the database limit by 10 post per request
	 *	
	 *	@param	author_id : user id that owner post
	 *  @param  pageNume: the number of page you want to get
	 *
	 *	@return List<Post>
	 *	
	 *  @exception  DAOException
	 */
	@Override
	public Data getAllPost(int pageNum, int current_user_id) throws ServiceException {
		Data data = null;
		try{
			List<Post> listPost = null; 
			int totalPost = 0;
			if(pageNum > 0){
				listPost =  this.postDAO.getAllPost(pageNum, current_user_id, false, false);
				totalPost = this.postDAO.getAllPostSize(current_user_id, false, false);
			}
			if(!listPost.isEmpty()){
				int totalPage = (int) Math.round(totalPost/Constraints.LIMIT_ROW + 0.5);
				data = new Data();
				data.setLimitRow(Constraints.LIMIT_ROW);
				data.setTotalPage(totalPage);
				data.setTotalRow(totalPost);
				data.setPageNum(pageNum);
				data.setListPost(listPost);
			}
			return data;
		}catch(DAOException ex){
			throw new ServiceException(ex.getMessage());
		}
	}
}