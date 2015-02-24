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
package com.mulodo.miniblog.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The user entity work with hibernate
 * 
 * @author UayLU
 */
@Entity
@Table(name = "User")
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, name = "username", length = 40)
    private String username;

    @Column(nullable = false, name = "password", length = 72)
    private String password;

    @Column(nullable = false, name = "lastname", length = 45)
    private String lastname;

    @Column(nullable = false, name = "firstname", length = 45)
    private String firstname;

    @Column(name = "email", length = 100)
    private String email;

    @Column(nullable = false, name = "created_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(nullable = false, name = "modified_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified_at;

    @Column(nullable = false, name = "status")
    private int status;

    @OneToMany(targetEntity = Comment.class, mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(targetEntity = Post.class, mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(targetEntity = Token.class, mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Token> tokens;

    public User() {
        super();
    }

    public User(int id, String username, int status) {
        super();
        this.id = id;
        this.username = username;
        this.status = status;
    }

    public User(String username, String password, String lastname, String firstname, String email,
            Date created_at, Date modified_at, int status) {
        super();
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.status = status;
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

    public Date getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(Date created_at)
    {
        this.created_at = created_at;
    }

    public Date getModified_at()
    {
        return modified_at;
    }

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
