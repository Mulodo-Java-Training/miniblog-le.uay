/* 
 * Post.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.object;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mulodo.miniblog.utils.JsonDateDeserializer;
import com.mulodo.miniblog.utils.JsonDateSerializer;

/**
 * The post entity work with hibernate
 * 
 * @author UayLU
 */
@JsonAutoDetect
public class Post
{

    private int id;

    private String title;
    
    private String content;

    private Date created_at;

    private Date modified_at;

    private int status;

    private User user;

    private List<Comment> comments;

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getCreated_at()
    {
        return created_at;
    }

    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setCreated_at(Date created_at)
    {
        this.created_at = created_at;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getModified_at()
    {
        return modified_at;
    }

    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setModified_at(Date modified_at)
    {
        this.modified_at = modified_at;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }


}
