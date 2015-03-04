/* 
 * Data.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.object;

import java.util.List;


/**
 * The data object for build response data
 * 
 * @author UayLU
 */
public class Data
{

    private User user;
    private List<User> listUser;

    private Post post;
    private List<Post> listPost;
    private int pageNum;
    private int totalPage;
    private int totalRow;
    private int limitRow;

    private List<Comment> listComment;

    public List<Comment> getListComment()
    {
        return listComment;
    }

    public void setListComment(List<Comment> listComment)
    {
        this.listComment = listComment;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<User> getListUser()
    {
        return listUser;
    }

    public void setListUser(List<User> listUser)
    {
        this.listUser = listUser;
    }

    public Post getPost()
    {
        return post;
    }

    public void setPost(Post post)
    {
        this.post = post;
    }

    public List<Post> getListPost()
    {
        return listPost;
    }

    public void setListPost(List<Post> listPost)
    {
        this.listPost = listPost;
    }

    public int getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }

    public int getTotalPage()
    {
        return totalPage;
    }

    public void setTotalPage(int totalPage)
    {
        this.totalPage = totalPage;
    }

    public int getTotalRow()
    {
        return totalRow;
    }

    public void setTotalRow(int totalRow)
    {
        this.totalRow = totalRow;
    }

    public int getLimitRow()
    {
        return limitRow;
    }

    public void setLimitRow(int limitRow)
    {
        this.limitRow = limitRow;
    }

}
