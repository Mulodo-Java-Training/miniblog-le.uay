/* 
 * PostController.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.rest.controller;

import java.util.Calendar;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.object.Data;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.service.PostService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.utils.ApplicationContextUtils;
import com.mulodo.miniblog.utils.BuildJSON;
import com.mulodo.miniblog.validator.PostValidate;


/**
 * The post controller
 * 
 * @author UayLU
 * 
 */
@Controller
@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
public class PostController 
{
	
	//declare postService for get bean from applicationcontext.xml
	private PostService postService;
	
	//declare tokenService for get bean from applicationcontext.xml
	private TokenService tokenService;
	
	/**
	 *  setPostService use to set datasource from applicationcontent.xml
	 *	
	 *	@param	ps : PostService from datasource
	 *
	 *	@return void
	 */
	@Autowired
	@Qualifier("postService")
	public void setPostService(PostService ps){
		this.postService = ps;
	}
	
	/**
	 *  setTokenService use to set datasource from applicationcontent.xml
	 *	
	 *	@param	ts : TokenService from datasource
	 *
	 *	@return void
	 */
	@Autowired
	@Qualifier("tokenService")
	public void setTokenService(TokenService ts){
		this.tokenService = ts;
	}
	
	
	/**
	 *  add_post is api that user use for create new post in miniblog
	 *	
	 *  @param  title : title of post
	 *  @param  content : content of post
	 *    
	 *	@return Response
	 *	
	 *	
	 */
	@RolesAllowed("ADMIN")
    @POST
    @Path("add")
    public Response addPost( @HeaderParam(Constraints.ACCESS_KEY) String access_key,
    		@FormParam("title") String title, @FormParam("content") String content) {
		
		if(tokenService == null || postService == null){
			setDataSource();
		}
		
		//declare jsonObject for build return data
		JSONObject jsonObject = new JSONObject();
		//validate data from client and add to meta
		PostValidate postValidate = new PostValidate();
		Meta meta =  postValidate.validateAddNew(title, content);
		Data data = null;
		//if have error, add error to jsonobject and return to client
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}
		if(access_key != null){
			try {
				//get user login by access_key of token
				System.out.println("access "+access_key);
				Token token = this.tokenService.findByAccessKey(access_key);
				if(token != null){
					//get user from token
					User user = token.getUser();
					Calendar cal = Calendar.getInstance();
					
					//add data to post variable
					Post post = new Post();
					post.setTitle(title);
					post.setContent(content);
					post.setCreated_at(cal.getTime());
					post.setModified_at(cal.getTime());
					post.setUser(user);
					post.setStatus(Constraints.POST_ACTIVE);
					//add new post to database
					this.postService.add(post);
					//set success to response
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_206, 0), null);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
			}
		}
		return Response.status(200).entity(jsonObject.toString()).build();
    }
	
	/**
	 *  active_deactive_post is api that user use for active and deactive post in miniblog
	 *	
	 *  @param  id : post id
	 *  @param  status : new status for post
	 *    
	 *	@return Response
	 *	
	 *	
	 */
	@RolesAllowed("ADMIN")
    @PUT
    @Path("activeDeactive")
	public Response activeDeactivePost(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
			@FormParam("id") String id, @FormParam("status") String status){
		
		if(tokenService == null || postService == null){
			setDataSource();
		}
		
		//declare jsonObject for build return data
		JSONObject jsonObject = new JSONObject();
		
		//validate post data
		PostValidate postValidate = new PostValidate();
		Meta meta = postValidate.validateActiveDeactive(id, status);
		Data data = null;
		//if have error, return error
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}
		try{
			//get token for getting user from accessy_key in header
			Token token = null;
			if(access_key != null){
				token = this.tokenService.findByAccessKey(access_key);
			}
			if(token != null){
				//get post from post id send from client
				int postId = Integer.parseInt(id);
				Post post = this.postService.findOne(postId);
				//if have post and current user have permission on it, call update function
				// else if have post but not have permission on it, return permission error code
				// else return invalid post error code
				if(post != null && post.getUser().getId() == token.getUser().getId()){
					int newStatus = Integer.parseInt(status); 
					post.setStatus(newStatus);
					this.postService.update(post);
					//return success code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_208, 0), null);
				}else if (post != null && post.getUser().getId() != token.getUser().getId()) {
					//return permission error code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_2509), null);
				}else{
					//return invalid error code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_2501), null);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}	
	
	/**
	 *  update_post is api that user use for update post in miniblog
	 *	
	 *  @param  id : post id
	 *  @param  title : title of post
	 *  @param  content : content of post
	 *    
	 *	@return Response
	 *	
	 *	
	 */
	@RolesAllowed("ADMIN")
    @PUT
    @Path("update")
	public Response updatePost(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
			@FormParam("id") String id, @FormParam("title") String title,
			@FormParam("content") String content){
		
		if(tokenService == null || postService == null){
			setDataSource();
		}
		
		//declare jsonObject for build return data
		JSONObject jsonObject = new JSONObject();
		
		//validate post data, if have error, return error code in meta object to client
		PostValidate postValidate = new PostValidate();
		Meta meta = postValidate.validateUpdate(id, title, content);
		Data data = null;
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}	
		try{
			Token token = null;
			if(access_key != null){
				token = this.tokenService.findByAccessKey(access_key);
			}
			if(token != null){
				int postId = Integer.parseInt(id);
				Post post = this.postService.findOne(postId);
				//if have post and current user have permission on it, call update function
				// else if have post but not have permission on it, return permission error code
				// else return invalid post error code
				if(post != null && post.getUser().getId() == token.getUser().getId()){
					post.setContent(content);
					post.setTitle(title);
					Calendar cal = Calendar.getInstance();
					post.setModified_at(cal.getTime());
					this.postService.update(post);
					//return success code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_207, 0), null);
				}else if (post != null && post.getUser().getId() != token.getUser().getId()) {
					//return permission error code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_2509), null);
				}else{
					//return invalid error code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_2501), null);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}	
	
	/**
	 *  delete is api that user use for delete post in miniblog
	 *	
	 *  @param  id : post id
	 *    
	 *	@return Response
	 *	
	 *	
	 */
	@RolesAllowed("ADMIN")
    @DELETE
    @Path("delete/{id}")
	public Response delete(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
			@PathParam("id") String id){
		
		if(tokenService == null || postService == null){
			setDataSource();
		}
		
		//declare jsonObject for build return data
		JSONObject jsonObject = new JSONObject();
		
		//validate post data, if have error, return error code in meta object to client
		PostValidate postValidate = new PostValidate();
		Meta meta = postValidate.validateDelete(id);
		Data data = null;
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}
		try{
			Token token = null;
			if(access_key != null){
				token = this.tokenService.findByAccessKey(access_key);
			}
			if(token != null){
				int postId = Integer.parseInt(id);
				Post post = this.postService.findOne(postId);
				//if have post and current user have permission on it, call update function
				// else if have post but not have permission on it, return permission error code
				// else return invalid post error code
				if(post != null && post.getUser().getId() == token.getUser().getId()){
					this.postService.delete(postId);
					//return success code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_209, 0), null);
				}else if (post != null && post.getUser().getId() != token.getUser().getId()) {
					//return permission error code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_2509), null);
				}else{
					//return invalid error code
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_2501), null);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}	
	
	/**
	 *  getAllPost is api that user use for getting all post in miniblog limit 
	 *  by 10 per request
	 *	
	 *  @param  pageNum : number of page.
	 *    
	 *	@return Response
	 *	
	 *	
	 */
	@RolesAllowed("ADMIN")
    @GET
    @Path("getAllPost")
	public Response getAllPost(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
			@QueryParam("pageNum") String pageNum, @QueryParam("description") String description){
		
		if(tokenService == null || postService == null){
			setDataSource();
		}
		
		//declare jsonObject for build return data
		JSONObject jsonObject = new JSONObject();
		
		//validate post data, if have error, return error code in meta object to client
		PostValidate postValidate = new PostValidate();
		Meta meta = postValidate.validateGetAllPost(pageNum);
		Data data = null;
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}		
		try{
			//get current user login
			Token token =	this.tokenService.findByAccessKey(access_key);
			int pageNumInt = Integer.parseInt(pageNum);
			//get all post of current user and like description(if not null)
			data = this.postService.getAllPost(pageNumInt,token.getUser().getId(), description);
			jsonObject = BuildJSON.buildReturn(meta, data);
		}catch(Exception ex){
			ex.printStackTrace();
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
	/**
	 *  getAllPostForUser is api that user use for getting all post for user in 
	 *  miniblog limit by 10 per request
	 *	
	 *  @param  pageNum : number of page.
	 *    
	 *	@return Response
	 *	
	 *	
	 */
	@RolesAllowed("ADMIN")
    @GET
    @Path("getPostForUser")
	public Response getAllPostForUser(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
			@QueryParam("pageNum") String pageNum, @QueryParam("user_id")  String user_id){
		
		if(tokenService == null || postService == null){
			setDataSource();
		}
		
		//declare jsonObject for build return data
		JSONObject jsonObject = new JSONObject();
		
		//validate post data, if have error, return error code in meta object to client
		PostValidate postValidate = new PostValidate();
		Meta meta = postValidate.validateGetAllPostForUser(pageNum, user_id);
		Data data = null;
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}	
		try{
			//get current user login
			Token token =	this.tokenService.findByAccessKey(access_key);
			int pageNumInt = Integer.parseInt(pageNum);
			//if current user login have same user id send from client,
			//system get all post from database include: all post of current user login, and 
			//post have status active of other users and user have status active
			//if not same, get all post have status active and user have status active
			if(token.getUser().getId() == Integer.parseInt(user_id)){
				data = this.postService.getAllPostForUser(pageNumInt, token.getUser().getId(), true);
			}else{
				data = this.postService.getAllPostForUser(pageNumInt, Integer.parseInt(user_id), false);
			}
			
			jsonObject = BuildJSON.buildReturn(meta, data);
		}catch(Exception ex){
			ex.printStackTrace();
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
	/**
	 *  setDataSource: use for setting datasoure to tokeservice and userservice
	 *	
	 */
	private void setDataSource(){
		tokenService = ApplicationContextUtils.getTokenServiceDataSource();
		postService = ApplicationContextUtils.getPostServiceDataSource();
	}
}