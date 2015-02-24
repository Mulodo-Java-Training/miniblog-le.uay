/* 
 * CommentDAO.java 
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

import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Comment;

/**
 * The interface of comment dao
 * 
 * @author UayLU
 */
public interface CommentDAO extends GenericDAO<Comment>
{

    /**
     * getAllCommentForPost use to get all comment for post.
     *
     * @param post_id
     *            : id of post for search
     * @param isOwnerPost
     *            : result of check current user owner or not
     * @return Boolean
     * @exception HandlerException
     */
    public List<Comment> getAllCommentForPost(int post_id, Boolean isOwnerPost)
            throws HandlerException;

    /**
     * getAllCommentForUser use to get all comment for user
     *
     * @param post_id
     *            : id of post for search
     * @param isOwnerUser
     *            : result of check current user owner or not
     * @return Boolean
     * @exception HandlerException
     */
    public List<Comment> getAllCommentForUser(int user_id, Boolean isOwnerUser)
            throws HandlerException;
}
