package com.mulodo.miniblog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContext implements ApplicationContextAware
{

    private static ApplicationContext requestContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        requestContext = applicationContext;
    }

    /**
     * getBean use for getting bean from application context
     *
     * @param beanName : name of bean in applicationcontenxt.xml
     * 
     * @return Object
     */
    public static Object getBean(String beanName)
    {
        // if have no current application context, set new application contenxt
        if (requestContext == null) {
            requestContext = new ClassPathXmlApplicationContext("classpath:/WEB-INF/applicationContext.xml");
        }
        return requestContext.getBean(beanName);
    }

}
