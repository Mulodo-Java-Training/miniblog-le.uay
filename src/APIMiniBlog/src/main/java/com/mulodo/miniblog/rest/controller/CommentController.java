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
import com.mulodo.miniblog.utils.SpringApplicationContext;
import com.mulodo.miniblog.validator.CommentValidate;

/**
 * The comment controller
 * 
 * @author UayLU
 */
@Controller
@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
public class CommentController
{

    private PostService postService;

    private TokenService tokenService;

    private CommentService commentService;

    /**
     * setPostService use to set datasource from applicationcontent.xml
     *
     * @param ps
     *            : PostService from datasource
     * @return void
     */
    @Autowired
    @Qualifier("postService")
    public void setPostService(PostService ps)
    {
        this.postService = ps;
    }

    /**
     * setTokenService use to set datasource from applicationcontent.xml
     *
     * @param ps
     *            : TokenService from datasource
     * @return void
     */
    @Autowired
    @Qualifier("tokenService")
    public void setTokenService(TokenService ts)
    {
        this.tokenService = ts;
    }

    /**
     * setCommentService use to set datasource from applicationcontent.xml
     *
     * @param cs
     *            : CommentService from datasource
     * @return void
     */
    @Autowired
    @Qualifier("commentService")
    public void setCommentService(CommentService cs)
    {
        this.commentService = cs;
    }

    /**
     * addComment use to add comment to database
     *
     * @param access_key
     *            : access key of token
     * @param post_id
     *            : id of post
     * @param content
     *            : content of post
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @POST
    @Path("add")
    public Response addComment(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
            @FormParam("post_id") String post_id, @FormParam("content") String content)
    {
        
        // get bean from application context if tokenSerivec, postService,
        // commentService was null
        if (tokenService == null || postService == null || commentService == null) {
            setDataSource();
        }

        //create json object for return
        JSONObject jsonObject = new JSONObject();

        //call validate method
        Meta meta = CommentValidate.validateAddNew(post_id, content);
        Data data = null;
        //if have error in meta object, return it to client
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
       
        Token token = null;
        try {
            //get token from access_key
            if (access_key != null) {
                token = this.tokenService.findByAccessKey(access_key);
            }
            if (token != null) {
                //get user infor from token
                User user = token.getUser();
                Calendar cal = Calendar.getInstance();
                //find post form post_id
                Post post = this.postService.findOne(Integer.parseInt(post_id));
                //if post null or post have status inactive return error to client
                if (post == null) {
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000,
                            Constraints.CODE_3007), null);
                    return Response.status(200).entity(jsonObject.toString()).build();
                } else if (post.getStatus() == Constraints.POST_INACTIVE) {
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000,
                            Constraints.CODE_3007), null);
                    return Response.status(200).entity(jsonObject.toString()).build();
                }
                //create new comment object
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setCreated_at(cal.getTime());
                comment.setModified_at(cal.getTime());
                comment.setPost(post);
                comment.setUser(user);
                //have no more error, add new comment to database
                this.commentService.add(comment);
                //create jsonobject success
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_210, 0), null);
            }      
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500,
                    Constraints.CODE_9001), null);
        }
       
        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * updateComment use to update comment to database
     *
     * @param access_key
     *            : access key of token
     * @param comment_id
     *            : id of comment
     * @param content
     *            : content of post
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @PUT
    @Path("update")
    public Response updateComment(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
            @FormParam("comment_id") String comment_id, @FormParam("content") String content)
    {
        // get bean from application context if tokenSerivec, postService,
        // commentService was null
        if (tokenService == null || postService == null || commentService == null) {
            setDataSource();
        }

        // create json object for return
        JSONObject jsonObject = new JSONObject();

        // call validate method
        Meta meta = CommentValidate.validateUpdate(comment_id, content);
        Data data = null;
        // if have error in meta object, return it to client
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }

        try {
            Token token = null;
            // get token from access_key
            if (access_key != null) {
                token = this.tokenService.findByAccessKey(access_key);
            }
            if (token != null) {

                Calendar cal = Calendar.getInstance();
                // find comment by comment_id in database
                Comment comment = this.commentService.findOne(Integer.parseInt(comment_id));
                //if invalid comment, return invalid code
                if (comment == null) {
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000,
                            Constraints.CODE_3006), null);
                    return Response.status(200).entity(jsonObject.toString()).build();
                }
                //if user have no permission in this comment, return error code
                if (comment.getUser().getId() != token.getUser().getId()) {
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000,
                            Constraints.CODE_3008), null);
                    return Response.status(200).entity(jsonObject.toString()).build();
                }
                //update comment
                comment.setContent(content);
                comment.setModified_at(cal.getTime());
                //call update method to update comment to database
                this.commentService.update(comment);
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_211, 0), null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500,
                    Constraints.CODE_9001), null);
        }
        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * delete use to delete comment to database
     *
     * @param access_key
     *            : access key of token
     * @param id
     *            : id of comment
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @DELETE
    @Path("delete/{id}")
    public Response delete(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
            @PathParam("id") String id)
    {
        // get bean from application context if tokenSerivec, postService,
        // commentService was null
        if (tokenService == null || postService == null || commentService == null) {
            setDataSource();
        }

        //create json object for return
        JSONObject jsonObject = new JSONObject();

        //call validate method
        Meta meta = CommentValidate.validateDelete(id);
        Data data = null;
        // if have error in meta object, return it to client        
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
        try {
            Token token = null;
            // get token from access_key            
            if (access_key != null) {
                token = this.tokenService.findByAccessKey(access_key);
            }
            if (token != null) {
                int commentId = Integer.parseInt(id);
                //find comment by comment id
                Comment comment = this.commentService.findOne(commentId);
                //have 3 options: 
                // if user have permission in valid commnet, call delete method
                // if user have no permission in this comment, return error code
                // if invalid comment in database, return error code
                if (comment != null && comment.getUser().getId() == token.getUser().getId()) {
                    this.commentService.delete(commentId);
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_212, 0), null);
                } else if (comment != null && comment.getUser().getId() != token.getUser().getId()) {
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000,
                            Constraints.CODE_3008), null);
                } else {
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000,
                            Constraints.CODE_3006), null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // build error message
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_3000,
                    Constraints.CODE_9001), null);
        }

        //return to client
        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * getAllCommentForPost use to get all comment for post to database
     *
     * @param access_key
     *            : access key of token
     * @param post_id
     *            : id of post
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @GET
    @Path("getByPost")
    public Response getAllCommentForPost(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
            @QueryParam("post_id") String post_id)
    {
        // get bean from application context if tokenSerivec, postService,
        // commentService was null
        if (tokenService == null || postService == null || commentService == null) {
            setDataSource();
        }
        
        //create json object for return
        JSONObject jsonObject = new JSONObject();

        //call validate method
        Meta meta = CommentValidate.validateGetAllCommentForPost(post_id);
        Data data = null;
        // if have error in meta object, return it to client        
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
        try {
            // get token from access_key
            Token token = this.tokenService.findByAccessKey(access_key);
            //get post from post service
            Post post = this.postService.findOne(Integer.parseInt(post_id));
            List<Comment> listComment = null;
            // if post null, return error code
            if (post == null) {
                meta = new Meta(Constraints.CODE_3000, Constraints.CODE_3007);
                jsonObject = BuildJSON.buildReturn(meta, data);
                return Response.status(200).entity(jsonObject.toString()).build();
            }
            // if user is owner post, get all comment in this post whatever status
            // if user is not owner post, get all comment on only active post
            if (post.getUser().getId() == token.getUser().getId()) {
                listComment = this.commentService.getAllCommentForPost(Integer.parseInt(post_id),
                        true);
            } else {
                listComment = this.commentService.getAllCommentForPost(Integer.parseInt(post_id),
                        false);
            }

            //buid return data
            data = new Data();
            data.setListComment(listComment);
            jsonObject = BuildJSON.buildReturn(meta, data);
        } catch (Exception ex) {
            ex.printStackTrace();
            // build error code 9001
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500,
                    Constraints.CODE_9001), null);
        }

        // return result to client
        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * getAllCommentForUser use to get all comment for user to database
     *
     * @param access_key
     *            : access key of token
     * @param user_id
     *            : id of user
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @GET
    @Path("getByUser")
    public Response getAllCommentForUser(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
            @QueryParam("user_id") String user_id)
    {
        // get bean from application context if tokenSerivec, postService,
        // commentService was null
        if (tokenService == null || postService == null || commentService == null) {
            setDataSource();
        }

        //create json object for return
        JSONObject jsonObject = new JSONObject();

        //call validate method
        Meta meta = CommentValidate.validateGetAllCommentForUser(user_id);
        Data data = null;
        // if have error in meta object, return it to client        
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
        try {
            // get token from access_key
            Token token = this.tokenService.findByAccessKey(access_key);

            int userIdRequest = Integer.parseInt(user_id);
            List<Comment> listComment = null;
            // if is current user, get all comment of user
            // if not current user, get all comment with post have active status and user
            //  have active status
            if (token.getUser().getId() == userIdRequest) {
                listComment = this.commentService.getAllCommentForUser(token.getUser().getId(),
                        true);
            } else {
                listComment = this.commentService.getAllCommentForUser(token.getUser().getId(),
                        false);
            }
            
            // build data return
            data = new Data();
            data.setListComment(listComment);
            jsonObject = BuildJSON.buildReturn(null, data);
        } catch (Exception ex) {
            ex.printStackTrace();
            // have system error, build data with code 9001
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2500,
                    Constraints.CODE_9001), null);
        }

        // return data to client
        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * setDataSource: use for setting datasoure to tokeservice, userservice and
     * postservice
     */
    private void setDataSource()
    {
        //get bean (data source) from current application contenxt 
        tokenService = (TokenService) SpringApplicationContext.getBean("tokenService");
        postService = (PostService) SpringApplicationContext.getBean("postService");
        commentService = (CommentService) SpringApplicationContext.getBean("commentService");
    }
}
