/* 
 * User.java 
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
 * The user entity work with hibernate
 * 
 * @author UayLU
 */
@JsonAutoDetect
public class User
{

    private int id;


    private String username;


    private String password;


    private String lastname;

    private String firstname;


    private String email;

    private Date created_at;

    private Date modified_at;

    private int status;

    private List<Comment> comments;

    private List<Post> posts;

    private List<Token> tokens;


    public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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
