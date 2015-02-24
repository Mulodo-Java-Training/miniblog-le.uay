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

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.utils.BuildJSON;
import com.mulodo.miniblog.utils.ValidatorUtils;

/**
 * This interceptor verify the access permissions for a user
 */
@Provider
@ServerInterceptor
@ContextConfiguration
(
  {
   "file:src/main/webapp/WEB-INF/applicationContext.xml"
  }
)
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter
{
    

    JSONObject jsonObject = null;

    private TokenService tokenService;
    
    /**
     * setTokenService use to set datasource from applicationcontent.xml
     *
     * @param ts
     *            : TokenService from datasource
     * @return void
     */
    @Autowired
    @Qualifier("tokenService")
    public void setTokenService(TokenService ts)
    {
        this.tokenService = ts;
    }
    
    private ResourceMethodInvoker methodInvoker;
    private Method method;
    
    @Override
    public void filter(ContainerRequestContext requestContext)
    {

        System.out.println("interceptor" ); 
        
        methodInvoker = (ResourceMethodInvoker) requestContext
                .getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        method = methodInvoker.getMethod();

        String access_key = getTokenHeader(requestContext);

        String matcherURL = requestContext.getUriInfo().getPath();
        Token token = null;
        try {
            if (access_key != null) {
                token = isValidToken(access_key);
            }
            if (token != null && matcherURL.equals("/users/login")) {

                Calendar cal = Calendar.getInstance();
                if (token != null && cal.getTime().compareTo(token.getExpired_at()) <= 0) {
                    Calendar cal_expired_at = Calendar.getInstance();
                    cal_expired_at.add(Calendar.HOUR_OF_DAY, 1);
                    token.setExpired_at(cal_expired_at.getTime());

                    this.tokenService.update(token);
                    jsonObject = new JSONObject();
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                            Constraints.CODE_1003), null);
                    requestContext.abortWith(Response.status(200).entity(jsonObject.toString())
                            .header(Constraints.ACCESS_KEY, access_key).build());
                    return;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                    Constraints.CODE_9001), null);
            requestContext.abortWith(Response.status(500).entity(jsonObject.toString()).build());
            return;
        }

        // Access allowed for all
        if (!method.isAnnotationPresent(PermitAll.class)) {
            // Access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                jsonObject = new JSONObject();
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                        Constraints.CODE_1004), null);
                requestContext
                        .abortWith(Response.status(403).entity(jsonObject.toString()).build());
                return;
            }

            if (method.isAnnotationPresent(RolesAllowed.class)) {
                // RolesAllowed rolesAnnotation =
                // method.getAnnotation(RolesAllowed.class);
                // Set<String> rolesSet = new
                // HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                Token tokenTemp = null;
                try {
                    if (access_key != null && ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(access_key)) {                           
                        tokenTemp = isValidToken(access_key);
                    } else {
                        jsonObject = new JSONObject();
                        jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                                Constraints.CODE_1002), null);
                        requestContext.abortWith(Response.status(401).entity(jsonObject.toString())
                                .build());
                        return;
                    }
                    Calendar cal = Calendar.getInstance();
                    if (tokenTemp == null || !tokenTemp.getAccess_key().equals(access_key)
                            || cal.getTime().compareTo(tokenTemp.getExpired_at()) > 0) {
                        jsonObject = new JSONObject();
                        jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                                Constraints.CODE_1002), null);
                        requestContext.abortWith(Response.status(401)
                                .entity(jsonObject.toString()).build());
                        return;
                    } else {

                        Calendar cal_expired_at = Calendar.getInstance();
                        cal_expired_at.add(Calendar.HOUR_OF_DAY, 1);
                        tokenTemp.setExpired_at(cal_expired_at.getTime());
                        this.tokenService.update(tokenTemp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonObject = new JSONObject();
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                            Constraints.CODE_9001), null);
                    requestContext.abortWith(Response.status(500).entity(jsonObject.toString())
                            .build());
                    return;
                }

            }
        }
    }

    private Token isValidToken(String access_key) throws HandlerException
    {
        return tokenService.findByAccessKey(access_key);
    }

    private String getTokenHeader(ContainerRequestContext requestContext)
    {
        // Get request headers
        if(requestContext.getHeaders().isEmpty()){
            return null;
        }
        Boolean isValidToken = requestContext.getHeaders().containsKey(Constraints.ACCESS_KEY);
        if (isValidToken) {
            List<String> headers = requestContext.getHeaders().get(Constraints.ACCESS_KEY);
            if (headers != null) {
                return headers.get(0);
            }
        }
        return null;
    }
}
