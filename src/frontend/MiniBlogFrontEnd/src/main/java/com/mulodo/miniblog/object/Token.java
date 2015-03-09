/* 
 * Token.java 
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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mulodo.miniblog.utils.JsonDateDeserializer;
import com.mulodo.miniblog.utils.JsonDateSerializer;


/**
 * The token entity work with hibernate
 * 
 * @author UayLU
 */
@JsonAutoDetect
public class Token
{

    private int id;

    private String access_key;

    private Date created_at;

    private Date expired_at;

    private User user;

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

    public String getAccess_key()
    {
        return access_key;
    }

    public void setAccess_key(String access_key)
    {
        this.access_key = access_key;
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
    public Date getExpired_at()
    {
        return expired_at;
    }

    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setExpired_at(Date expired_at)
    {
        this.expired_at = expired_at;
    }

}
