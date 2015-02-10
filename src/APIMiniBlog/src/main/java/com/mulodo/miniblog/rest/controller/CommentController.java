/* 
 * CommentController.java 
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
import java.util.List;

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
import com.mulodo.miniblog.exeption.ServiceException;
import com.mulodo.miniblog.model.Comment;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.object.Data;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.service.CommentService;
import com.mulodo.miniblog.service.PostService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.utils.BuildJSON;
import com.mulodo.miniblog.validator.CommentValidate;

/**
 * The comment controller
 * 
 * @author UayLU
 * 
 */
@Controller
@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
public class CommentController {
	
	private PostService postService;
	
	private TokenService tokenService;
	
	private CommentService commentService;
	
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
	 *  setPostService use to set datasource from applicationcontent.xml
	 *	
	 *	@param	ps : PostService from datasource
	 *
	 *	@return void
	 */
	@Autowired
	@Qualifier("tokenService")
	public void setTokenService(TokenService ts){
		this.tokenService = ts;
	}
	
	/**
	 *  setCommentService use to set datasource from applicationcontent.xml
	 *	
	 *	@param	cs : CommentService from datasource
	 *
	 *	@return void
	 */
	@Autowired
	@Qualifier("commentService")
	public void setCommentService(CommentService cs){
		this.commentService = cs;
	}
	
	@RolesAllowed("ADMIN")
    @POST
    @Path("add")
    public Response add_comment( @HeaderParam(Constraints.ACCESS_KEY) String access_key,
    		@FormParam("post_id") String post_id, @FormParam("content") String content) {
		
		JSONObject jsonObject = new JSONObject();
		
		Meta meta =  CommentValidate.validateAddNew(post_id, content);
		Data data = null;
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}
		if(access_key != null){
			try {
			
				Token token = this.tokenService.findByAccessKey(access_key);
				if(token != null){
					
					User user = token.getUser();
					Calendar cal = Calendar.getInstance();
					
					Post post = this.postService.findOne(Integer.parseInt(post_id));
					if(post == null){
						jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000, Constraints.CODE_3002), null);
						return Response.status(200).entity(jsonObject.toString()).build();
					}
					Comment comment = new Comment();
					comment.setContent(content);
					comment.setCreated_at(cal.getTime());
					comment.setModified_at(cal.getTime());
					comment.setPost(post);
					comment.setUser(user);
					this.commentService.add(comment);
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_210, 0), null);
				}
			}catch(ServiceException ex){
				jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
			}catch(Exception ex){
				ex.printStackTrace();
				jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
			}
		}
		return Response.status(200).entity(jsonObject.toString()).build();
    }
	
	@RolesAllowed("ADMIN")
    @PUT
    @Path("update")
    public Response update_comment( @HeaderParam(Constraints.ACCESS_KEY) String access_key,
    		@FormParam("comment_id") String comment_id, @FormParam("content") String content) {
		
		JSONObject jsonObject = new JSONObject();
		
		Meta meta =  CommentValidate.validateUpdate(comment_id, content);
		Data data = null;
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}
		if(access_key != null){
			try {
			
				Token token = this.tokenService.findByAccessKey(access_key);
				if(token != null){
					
					Calendar cal = Calendar.getInstance();
					
					Comment comment = this.commentService.findOne(Integer.parseInt(comment_id));
					if(comment == null){
						jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000, Constraints.CODE_3006), null);
						return Response.status(200).entity(jsonObject.toString()).build();
					}
					if(comment.getUser().getId() != token.getUser().getId()){
						jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000, Constraints.CODE_3008), null);
						return Response.status(200).entity(jsonObject.toString()).build();
					}
					comment.setContent(content);
					comment.setModified_at(cal.getTime());
					this.commentService.update(comment);
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_211, 0), null);
				}
			}catch(ServiceException ex){
				jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
			}catch(Exception ex){
				ex.printStackTrace();
				jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
			}
		}
		return Response.status(200).entity(jsonObject.toString()).build();
    }
	
	@RolesAllowed("ADMIN")
    @DELETE
    @Path("delete/{id}")
	public Response delelte(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
			@PathParam("id") String id){
		
		JSONObject jsonObject = new JSONObject();
		
		Meta meta = CommentValidate.validateDelete(id);
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
				int commentId = Integer.parseInt(id);
				Comment comment = this.commentService.findOne(commentId);
				if(comment != null && comment.getUser().getId() == token.getUser().getId()){
					this.postService.delete(commentId);
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_212, 0), null);
				}else if (comment != null && comment.getUser().getId() != token.getUser().getId()) {
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000, Constraints.CODE_3008), null);
				}else{
					jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000, Constraints.CODE_3006), null);
				}
			}
		}catch(ServiceException ex){
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}catch(Exception ex){
			ex.printStackTrace();
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}	
	
	@RolesAllowed("ADMIN")
    @GET
    @Path("getByPost")
	public Response getAllCommentForPost(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
			@QueryParam("post_id") String post_id){
		
		JSONObject jsonObject = new JSONObject();
		
		Meta meta = CommentValidate.validateGetAllCommentForPost(post_id);
		Data data = null;
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}	
		try{
			Token token = this.tokenService.findByAccessKey(access_key);
			
			Post post = this.postService.findOne(Integer.parseInt(post_id));
			List<Comment> listComment = null;
			if(post.getUser().getId() == token.getUser().getId()){
				listComment = this.commentService.getAllCommentForPost(Integer.parseInt(post_id), true);
			}else{
				listComment = this.commentService.getAllCommentForPost(Integer.parseInt(post_id), false);
			}
			
			data = new Data();
			data.setListComment(listComment);
			jsonObject = BuildJSON.buildReturn(meta, data);
		}catch(ServiceException ex){
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}catch(Exception ex){
			ex.printStackTrace();
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
	@RolesAllowed("ADMIN")
    @GET
    @Path("getByUser")
	public Response getAllCommentForUser(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
			@QueryParam("user_id") String user_id){
		
		JSONObject jsonObject = new JSONObject();
		
		Meta meta = CommentValidate.validateGetAllCommentForUser(user_id);
		Data data = null;
		if(meta != null){
			jsonObject = BuildJSON.buildReturn(meta, data);
			return Response.status(200).entity(jsonObject.toString()).build();
		}	
		try{
			Token token = this.tokenService.findByAccessKey(access_key);
			
			int userIdDao = Integer.parseInt(user_id);
			List<Comment> listComment = null;
			if(token.getUser().getId() == userIdDao){
				listComment = this.commentService.getAllCommentForUser(token.getUser().getId(), true);
			}else{
				listComment = this.commentService.getAllCommentForUser(token.getUser().getId(), false);
			}
			data = new Data();
			data.setListComment(listComment);
			jsonObject = BuildJSON.buildReturn(null, data);
		}catch(ServiceException ex){
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}catch(Exception ex){
			ex.printStackTrace();
			jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500, Constraints.CODE_9001), null);
		}
		
		return Response.status(200).entity(jsonObject.toString()).build();
	}
}
