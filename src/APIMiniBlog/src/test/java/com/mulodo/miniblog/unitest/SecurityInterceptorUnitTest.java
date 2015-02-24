package com.mulodo.miniblog.unitest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.rest.controller.PostController;
import com.mulodo.miniblog.rest.controller.UserController;
import com.mulodo.miniblog.rest.security.SecurityInterceptor;
import com.mulodo.miniblog.service.TokenService;

public class SecurityInterceptorUnitTest
{

    @InjectMocks
    private SecurityInterceptor securityInterceptor;
    
    @Mock
    private ContainerRequestContext requestContext;
    
    @Mock
    private TokenService tokenService;
    
    @Mock
    private ResourceMethodInvoker methodInvoker;
    
    
    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testAccessDeniedWithNullAccessKey() throws Exception
    {
        UserController userController = new UserController(); 
        Class<? extends UserController> user = userController.getClass();
        Method method = user.getMethod("getUserInfo", String.class);
        
        when(methodInvoker.getMethod()).thenReturn(method);
        when(requestContext.getHeaders()).thenReturn(null);
        
        securityInterceptor.filter(requestContext);

        
        
    }
    
 

}
