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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.dao.PostDAO;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.object.Data;
import com.mulodo.miniblog.service.PostService;

/**
 * The post service impl
 * 
 * @author UayLU
 */
@Service
public class PostServiceImpl extends GenericServiceImpl<Post> implements PostService
{

    private PostDAO postDAO;

    /**
     * setPostDAO use to set datasource from applicationcontent.xml
     *
     * @param pd
     *            : PostDAO from datasource
     * @return void
     */
    @Autowired
    @Qualifier("postDAO")
    public void setPostDAO(PostDAO pd)
    {
        this.postDAO = pd;
    }

    /**
     * get_all_post use for get all post in the database limit by 10 post per
     * request
     *
     * @param author_id
     *            : user id that owner post
     * @param pageNume
     *            : the number of page you want to get
     * @return List<Post>
     * @exception HandlerException
     */
    @Override
    public Data getAllPostForUser(int pageNum, int author_id, String description, Boolean isOwerUser)
            throws HandlerException
    {
        Data data = null;
        List<Post> listPost = null;
        int totalPost = 0;
        if (pageNum > 0) {
            // get list post folow page number
            // get total post follow condition
            listPost = this.postDAO.getAllPost(pageNum, author_id, description, true, isOwerUser);
            totalPost = this.postDAO.getAllPostSize(author_id, description, true, isOwerUser);
        }
        if (listPost != null) {
            int totalPage = (int) Math.round(totalPost / Constraints.LIMIT_ROW + 0.5);
            // set list post to data object
            data = new Data();
            data.setLimitRow(Constraints.LIMIT_ROW);
            data.setTotalPage(totalPage);
            data.setTotalRow(totalPost);
            data.setPageNum(pageNum);
            data.setListPost(listPost);
        } else {
            // if have no data match with condition.
            // return all variable is zero
            data = new Data();
            data.setListPost(new ArrayList<Post>());
            data.setTotalPage(0);
            data.setTotalRow(0);
            data.setPageNum(0);
            data.setLimitRow(0);
        }
        return data;
    }

    /**
     * get_all_post_for_user use for get all post in the database limit by 10
     * post per request
     *
     * @param author_id
     *            : user id that owner post
     * @param pageNume
     *            : the number of page you want to get
     * @return List<Post>
     * @exception HandlerException
     */
    @Override
    public Data getAllPost(int pageNum, int current_user_id, String description)
            throws HandlerException
    {
        Data data = null;
        List<Post> listPost = null;
        int totalPost = 0;
        if (pageNum > 0) {
            // get list post folow page number
            // get total post follow condition
            listPost = this.postDAO.getAllPost(pageNum, current_user_id, description, false, false);
            totalPost = this.postDAO.getAllPostSize(current_user_id, description, false, false);
        }
        if (listPost != null) {
            int totalPage = (int) Math.round(totalPost / Constraints.LIMIT_ROW + 0.5);
            // set value to data object
            data = new Data();
            data.setLimitRow(Constraints.LIMIT_ROW);
            data.setTotalPage(totalPage);
            data.setTotalRow(totalPost);
            data.setPageNum(pageNum);
            data.setListPost(listPost);
        } else {
            // if have no data match with condition.
            // return all variable is zero
            data = new Data();
            data.setListPost(new ArrayList<Post>());
            data.setTotalPage(0);
            data.setTotalRow(0);
            data.setPageNum(0);
            data.setLimitRow(0);
        }
        return data;

    }

    /**
     * deleteByTitle use for delete post by title in the database
     *
     * @param title
     *            : title of post
     * @return void
     * @exception HandlerException
     */
    @Override
    public void deleteByTitle(String title) throws HandlerException
    {
        this.postDAO.deleteByTitle(title);
    }

    /**
     * findByTitle use for find post by title in the database for unit test
     *
     * @param title
     *            : title of post
     * @return Post
     * @exception HandlerException
     */
    @Override
    public Post findByTitle(String title) throws HandlerException
    {

        return this.postDAO.findByTitle(title);

    }
}