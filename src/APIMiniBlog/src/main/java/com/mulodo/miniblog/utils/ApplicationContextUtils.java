package com.mulodo.miniblog.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mulodo.miniblog.service.CommentService;
import com.mulodo.miniblog.service.PostService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.service.UserService;

public class ApplicationContextUtils
{
    private static ApplicationContext appContext = new ClassPathXmlApplicationContext(
            "classpath:/WEB-INF/applicationContext.xml");

    public static TokenService getTokenServiceDataSource()
    {
        return (TokenService) appContext.getBean("tokenService");
    }

    public static UserService getUserServiceDataSource()
    {
        return (UserService) appContext.getBean("userService");
    }

    public static PostService getPostServiceDataSource()
    {
        return (PostService) appContext.getBean("postService");
    }

    public static CommentService getCommentServiceDataSource()
    {
        return (CommentService) appContext.getBean("commentService");
    }
}
