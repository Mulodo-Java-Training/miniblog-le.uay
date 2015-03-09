package com.mulodo.miniblog.unittest.security;

import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.rest.controller.UserController;
import com.mulodo.miniblog.rest.security.SecurityInterceptor;
import com.mulodo.miniblog.service.TokenService;

/**
 * The UserControllerUnitTest use create unit test of user controller
 * 
 * @author UayLU
 */
@RunWith(MockitoJUnitRunner.class)
public class SecurityInterceptorUnitTest
{

    @InjectMocks
    private SecurityInterceptor securityInterceptor;

    @Mock
    private ContainerRequestContext requestContext;

    @Mock
    private ResourceMethodInvoker methodInvoker;

    @Mock
    private TokenService tokenService;

    @Mock
    private MultivaluedMap<String, String> listHeader;

    @Mock
    private UriInfo uri;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAccessDeniedNullAccessKey() throws NoSuchMethodException, SecurityException
    {
        UserController userController = new UserController();
        Class<? extends UserController> user = userController.getClass();
        Method method = null;

        method = user.getMethod("getUserInfo", String.class);

        when(requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker"))
                .thenReturn(methodInvoker);
        when(methodInvoker.getMethod()).thenReturn(method);
        when(requestContext.getHeaders()).thenReturn(listHeader);
        when(listHeader.containsKey(Constraints.ACCESS_KEY)).thenReturn(false);
        when(requestContext.getUriInfo()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/users/getUserInfo");
        securityInterceptor.filter(requestContext);
    }

    @Test
    public void testAccessDeniedInvalidAccessKeyInDatabase() throws NoSuchMethodException,
            SecurityException, HandlerException
    {
        UserController userController = new UserController();
        Class<? extends UserController> user = userController.getClass();
        Method method = null;

        method = user.getMethod("getUserInfo", String.class);

        when(requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker"))
                .thenReturn(methodInvoker);
        when(methodInvoker.getMethod()).thenReturn(method);
        when(requestContext.getHeaders()).thenReturn(listHeader);
        when(listHeader.containsKey(Constraints.ACCESS_KEY)).thenReturn(true);
        List<String> headers = new ArrayList<String>();
        headers.add("passwordForTest");
        when(listHeader.get(Constraints.ACCESS_KEY)).thenReturn(headers);
        when(requestContext.getUriInfo()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/users/getUserInfo");
        when(tokenService.findByAccessKey(headers.get(0))).thenReturn(null);
        securityInterceptor.filter(requestContext);
    }

    @Test
    public void testAccessDeniedOutOfDateAccessKey() throws NoSuchMethodException,
            SecurityException, HandlerException
    {
        UserController userController = new UserController();
        Class<? extends UserController> user = userController.getClass();
        Method method = null;

        method = user.getMethod("getUserInfo", String.class);

        when(requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker"))
                .thenReturn(methodInvoker);
        when(methodInvoker.getMethod()).thenReturn(method);
        when(requestContext.getHeaders()).thenReturn(listHeader);
        when(listHeader.containsKey(Constraints.ACCESS_KEY)).thenReturn(true);
        List<String> headers = new ArrayList<String>();
        headers.add("passwordForTest");
        when(listHeader.get(Constraints.ACCESS_KEY)).thenReturn(headers);
        when(requestContext.getUriInfo()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/users/getUserInfo");
        Token token = new Token();
        token.setAccess_key(headers.get(0));
        Calendar expired_at = Calendar.getInstance();
        expired_at.add(Calendar.HOUR_OF_DAY, -1);
        token.setExpired_at(expired_at.getTime());
        when(tokenService.findByAccessKey(headers.get(0))).thenReturn(token);
        securityInterceptor.filter(requestContext);
    }

    @Test
    public void testAccessSuccess() throws NoSuchMethodException, SecurityException,
            HandlerException
    {
        UserController userController = new UserController();
        Class<? extends UserController> user = userController.getClass();
        Method method = null;

        method = user.getMethod("getUserInfo", String.class);

        when(requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker"))
                .thenReturn(methodInvoker);
        when(methodInvoker.getMethod()).thenReturn(method);
        when(requestContext.getHeaders()).thenReturn(listHeader);
        when(listHeader.containsKey(Constraints.ACCESS_KEY)).thenReturn(true);
        List<String> headers = new ArrayList<String>();
        headers.add("passwordForTest");
        when(listHeader.get(Constraints.ACCESS_KEY)).thenReturn(headers);
        when(requestContext.getUriInfo()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/users/getUserInfo");
        Token token = new Token();
        token.setAccess_key(headers.get(0));
        Calendar expired_at = Calendar.getInstance();
        expired_at.add(Calendar.HOUR_OF_DAY, 1);
        token.setExpired_at(expired_at.getTime());
        when(tokenService.findByAccessKey(headers.get(0))).thenReturn(token);
        securityInterceptor.filter(requestContext);
    }

    @Test
    public void testAlreadyLogin() throws NoSuchMethodException, SecurityException,
            HandlerException
    {
        UserController userController = new UserController();
        Class<? extends UserController> user = userController.getClass();
        Method method = null;

        method = user.getMethod("getUserInfo", String.class);

        when(requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker"))
                .thenReturn(methodInvoker);
        when(methodInvoker.getMethod()).thenReturn(method);
        when(requestContext.getHeaders()).thenReturn(listHeader);
        when(listHeader.containsKey(Constraints.ACCESS_KEY)).thenReturn(true);
        List<String> headers = new ArrayList<String>();
        headers.add("passwordForTest");
        when(listHeader.get(Constraints.ACCESS_KEY)).thenReturn(headers);
        when(requestContext.getUriInfo()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/users/login");
        Token token = new Token();
        token.setAccess_key(headers.get(0));
        Calendar expired_at = Calendar.getInstance();
        expired_at.add(Calendar.HOUR_OF_DAY, 1);
        token.setExpired_at(expired_at.getTime());
        when(tokenService.findByAccessKey(headers.get(0))).thenReturn(token);
        securityInterceptor.filter(requestContext);
    }

    @Test
    public void testException() throws NoSuchMethodException, SecurityException, HandlerException
    {
        UserController userController = new UserController();
        Class<? extends UserController> user = userController.getClass();
        Method method = null;

        method = user.getMethod("getUserInfo", String.class);

        when(requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker"))
                .thenReturn(methodInvoker);
        when(methodInvoker.getMethod()).thenReturn(method);
        when(requestContext.getHeaders()).thenReturn(listHeader);
        when(listHeader.containsKey(Constraints.ACCESS_KEY)).thenReturn(true);
        List<String> headers = new ArrayList<String>();
        headers.add("passwordForTest");
        when(listHeader.get(Constraints.ACCESS_KEY)).thenReturn(headers);
        when(requestContext.getUriInfo()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/users/login");
        when(tokenService.findByAccessKey(headers.get(0))).thenThrow(new HandlerException());
        securityInterceptor.filter(requestContext);
    }

    @Test
    public void testExceptionNotLogin() throws NoSuchMethodException, SecurityException,
            HandlerException
    {
        UserController userController = new UserController();
        Class<? extends UserController> user = userController.getClass();
        Method method = null;

        method = user.getMethod("getUserInfo", String.class);

        when(requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker"))
                .thenReturn(methodInvoker);
        when(methodInvoker.getMethod()).thenReturn(method);
        when(requestContext.getHeaders()).thenReturn(listHeader);
        when(listHeader.containsKey(Constraints.ACCESS_KEY)).thenReturn(true);
        List<String> headers = new ArrayList<String>();
        headers.add("passwordForTest");
        when(listHeader.get(Constraints.ACCESS_KEY)).thenReturn(headers);
        when(requestContext.getUriInfo()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/users/getUserInfo");
        when(tokenService.findByAccessKey(headers.get(0))).thenReturn(new Token());
        when(tokenService.update(new Token())).thenThrow(new HandlerException());
        securityInterceptor.filter(requestContext);
    }
}
