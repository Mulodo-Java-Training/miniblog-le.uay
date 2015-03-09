/* 
 * SecurityInterceptor.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.rest.security;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.json.JSONObject;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.utils.BuildJSON;
import com.mulodo.miniblog.utils.SpringApplicationContext;
import com.mulodo.miniblog.utils.ValidatorUtils;

/**
 * This interceptor verify the access permissions for a user
 */
@Provider
@ServerInterceptor
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter
{

    // get data source from current application context
    private TokenService tokenService = (TokenService) SpringApplicationContext
            .getBean("tokenService");

    private ResourceMethodInvoker methodInvoker;
    private Method method;

    @Override
    public void filter(ContainerRequestContext requestContext)
    {

        // get method invoker from request context
        methodInvoker = (ResourceMethodInvoker) requestContext
                .getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");

        // get type of method in api (permission on every api)
        method = methodInvoker.getMethod();

        // get information from request context: access_key, url information
        String access_key = getTokenHeader(requestContext);
        UriInfo uri = requestContext.getUriInfo();
        String matcherURL = uri.getPath();

        // create token for get information of user from access_key
        JSONObject jsonObject = null;

        try {

            // request have url same: /users/login
            // bonus 1 hours for token and return error 1003: already login
            if (matcherURL.equals("/users/login")) {
                Token tokenLogin = null;
                // get information from access_key
                if (access_key != null) {
                    tokenLogin = tokenService.findByAccessKey(access_key);
                }

                Calendar cal = Calendar.getInstance();
                if (tokenLogin != null && cal.getTime().compareTo(tokenLogin.getExpired_at()) <= 0) {

                    // add one hours in token
                    Calendar cal_expired_at = Calendar.getInstance();
                    cal_expired_at.add(Calendar.HOUR_OF_DAY, 1);
                    tokenLogin.setExpired_at(cal_expired_at.getTime());
                    // call update method for updating expired time
                    this.tokenService.update(tokenLogin);
                    jsonObject = new JSONObject();
                    // build error code 1003 and return
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                            Constraints.CODE_1003), null);
                    requestContext.abortWith(Response.status(200).entity(jsonObject.toString())
                            .header(Constraints.ACCESS_KEY, access_key).build());
                    return;
                }
            }
        } catch (Exception ex) {
            // return code 9001 if have system error
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                    Constraints.CODE_9001), null);
            requestContext.abortWith(Response.status(500).entity(jsonObject.toString()).build());
            return;
        }

        // check if API require permission (in this project, it require: login)
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            // RolesAllowed rolesAnnotation =
            // method.getAnnotation(RolesAllowed.class);
            // Set<String> rolesSet = new
            // HashSet<String>(Arrays.asList(rolesAnnotation.value()));
            Token token = null;
            try {
                // get token from access key
                if (access_key != null && ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(access_key)) {
                    token = tokenService.findByAccessKey(access_key);
                } else {
                    // if access key is null, return message to client require
                    // permission
                    jsonObject = new JSONObject();
                    // build error code with code 1002
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                            Constraints.CODE_1002), null);
                    requestContext.abortWith(Response.status(401).entity(jsonObject.toString())
                            .build());
                    return;
                }

                Calendar cal = Calendar.getInstance();
                // if invlaid access key in database, return error code for
                // requiring permission(login)
                if (token == null || cal.getTime().compareTo(token.getExpired_at()) > 0) {
                    jsonObject = new JSONObject();
                    // build error code with code 1002
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                            Constraints.CODE_1002), null);
                    requestContext.abortWith(Response.status(401).entity(jsonObject.toString())
                            .build());
                    return;
                } else {
                    // if valid access key in database, add one hour to token
                    Calendar cal_expired_at = Calendar.getInstance();
                    cal_expired_at.add(Calendar.HOUR_OF_DAY, 1);
                    token.setExpired_at(cal_expired_at.getTime());
                    // update expired time to database
                    this.tokenService.update(token);
                }
            } catch (Exception ex) {
                // build error code when have system error
                jsonObject = new JSONObject();
                // build 9001 error code
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                        Constraints.CODE_9001), null);
                requestContext
                        .abortWith(Response.status(500).entity(jsonObject.toString()).build());
                return;
            }

        }

    }
    
    /**
     * getTokenHeader use for get access key from header
     *
     * @param requestContext
     *            : content of request
     * @return String
     */
    private String getTokenHeader(ContainerRequestContext requestContext)
    {
        // get all valua in header to MultivalueMap
        MultivaluedMap<String, String> listHeader = requestContext.getHeaders();
        // check valid access key in header
        Boolean isValidToken = listHeader.containsKey(Constraints.ACCESS_KEY);
        if (isValidToken) {
            //get value of access key from header
            List<String> headers = listHeader.get(Constraints.ACCESS_KEY);
            if (headers != null) {
                return headers.get(0);
            }
        }
        
        // return null if invalid
        return null;
    }
}
