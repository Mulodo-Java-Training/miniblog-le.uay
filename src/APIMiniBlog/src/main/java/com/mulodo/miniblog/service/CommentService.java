/* 
 * CommentService.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Comment;

/**
 * The interface of comment service
 * 
 * @author UayLU
 */
public interface CommentService extends GenericService<Comment>
{

    /**
     * get_all_comment_for_post use to call get all comment for post function in
     * commentdao for post.
     *
     * @param post_id
     *            : id of post for search
     * @param isOwnerPost
     *            : result of check current user owner or not
     * @return Boolean
     * @exception ServiceException
     */
    public List<Comment> getAllCommentForPost(int post_id, Boolean isOwnerPost)
            throws HandlerException;

    /**
     * get_all_comment_for_user use to call get all comment for user function in
     * commentdao for post.
     *
     * @param post_id
     *            : id of post for search
     * @param isOwnerPost
     *            : result of check current user owner or not
     * @return Boolean
     * @exception ServiceException
     */
    public List<Comment> getAllCommentForUser(int user_id, Boolean isOwnerUser)
            throws HandlerException;

    /**
     * findByTitle use for find comment by content in the database for unit test
     *
     * @param content
     *            : content of comment
     * @return Comment
     * @exception HandlerException
     */
    public Comment findByContent(String content) throws HandlerException;
}
