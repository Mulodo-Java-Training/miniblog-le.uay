/* 
 * CommentValidate.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.validator;

import java.util.ArrayList;
import java.util.List;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.object.Message;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.utils.ValidatorUtils;

/**
 * The CommentValidate use to validate comment data
 * 
 * @author UayLU
 */
public class CommentValidate
{

    /**
     * validate_add_new use for validate add new data comment
     *
     * @param user_id
     *            : post_id of post
     * @param content
     *            : content of post
     * @return Meta
     */
    public static Meta validateAddNew(String comment_id, String content)
    {

        // List message contain error message validate
        List<Message> listMessage = new ArrayList<Message>();

        Boolean isValid;
        // check null empty or white space if string
        isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(content);
        if (!isValid) {
            // check if have error, add message error to listmessage
            listMessage.add(new Message(Constraints.CODE_3001));
        } else {
            // check valid length of string
            isValid = ValidatorUtils.isValidLength(content, 1, 500);
            if (!isValid) {
                // check if have error, add message error to listmessage
                listMessage.add(new Message(Constraints.CODE_3005));
            }
        }

        isValid = ValidatorUtils.isPositiveInteger(comment_id);
        if (!isValid) {
            // check if have error, add message error to listmessage
            listMessage.add(new Message(Constraints.CODE_3007));
        }

        if (!listMessage.isEmpty()) {
            // check if have error, add message error to listmessage
            Meta meta = new Meta(Constraints.CODE_3000, listMessage);
            return meta;
        }
        return null;
    }

    /**
     * validate_update use for validate update data comment
     *
     * @param comment_id
     *            : comment_id of comment
     * @param content
     *            : content of post
     * @return Meta
     */
    public static Meta validateUpdate(String comment_id, String content)
    {

        // List message contain error message validate
        List<Message> listMessage = new ArrayList<Message>();

        Boolean isValid;
        // check null empty or white space if string
        isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(content);
        if (!isValid) {
            // check if have error, add message error to listmessage
            listMessage.add(new Message(Constraints.CODE_3001));
        } else {
            // check valid length of string
            isValid = ValidatorUtils.isValidLength(content, 1, 500);
            if (!isValid) {
                // check if have error, add message error to listmessage
                listMessage.add(new Message(Constraints.CODE_3005));
            }
        }

        isValid = ValidatorUtils.isPositiveInteger(comment_id);
        if (!isValid) {
            // check if have error, add message error to listmessage
            listMessage.add(new Message(Constraints.CODE_3006));
        }

        if (!listMessage.isEmpty()) {
            // check if have error, add message error to listmessage
            Meta meta = new Meta(Constraints.CODE_3000, listMessage);
            return meta;
        }
        return null;
    }

    /**
     * validate_delete use for validate delete comment
     *
     * @param comment_id
     *            : comment_id of comment
     * @return Meta
     */
    public static Meta validateDelete(String comment_id)
    {

        // List message contain error message validate
        List<Message> listMessage = new ArrayList<Message>();

        Boolean isValid;

        isValid = ValidatorUtils.isPositiveInteger(comment_id);
        if (!isValid) {
            listMessage.add(new Message(Constraints.CODE_3006));
        }

        if (!listMessage.isEmpty()) {
            Meta meta = new Meta(Constraints.CODE_3000, listMessage);
            return meta;
        }
        return null;
    }

    /**
     * validate_get_all_comment_for_post use for validate get all comment for
     * user
     *
     * @param post_id
     *            : post_id of post
     * @return Meta
     */
    public static Meta validateGetAllCommentForPost(String post_id)
    {

        // List message contain error message validate
        List<Message> listMessage = new ArrayList<Message>();

        Boolean isValid;

        isValid = ValidatorUtils.isPositiveInteger(post_id);
        if (!isValid) {
            // check if have error, add message error to listmessage
            listMessage.add(new Message(Constraints.CODE_3002));
        }

        if (!listMessage.isEmpty()) {
            // check if have error, add message error to listmessage
            Meta meta = new Meta(Constraints.CODE_3000, listMessage);
            return meta;
        }
        return null;
    }

    /**
     * validate_get_all_comment_for_user use for validate get all comment for
     * user
     *
     * @param user_id
     *            : user_id of user
     * @return Meta
     */
    public static Meta validateGetAllCommentForUser(String user_id)
    {

        // List message contain error message validate
        List<Message> listMessage = new ArrayList<Message>();

        Boolean isValid;

        isValid = ValidatorUtils.isPositiveInteger(user_id);
        if (!isValid) {
            // check if have error, add message error to listmessage
            listMessage.add(new Message(Constraints.CODE_3003));
        }

        if (!listMessage.isEmpty()) {
            // check if have error, add message error to listmessage
            Meta meta = new Meta(Constraints.CODE_3000, listMessage);
            return meta;
        }
        return null;
    }
}
