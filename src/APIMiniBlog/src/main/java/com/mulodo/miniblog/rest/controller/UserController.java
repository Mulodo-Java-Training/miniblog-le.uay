/* 
 * UserController.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.rest.controller;

import java.util.Calendar;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.object.Data;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.BuildJSON;
import com.mulodo.miniblog.utils.EncrypUtils;
import com.mulodo.miniblog.utils.SpringApplicationContext;
import com.mulodo.miniblog.validator.UserValidate;

/**
 * The user controller
 * 
 * @author UayLU
 */
@Controller
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class UserController
{

    private UserService userService;
    private TokenService tokenService;

    /**
     * setUserService use to set datasource from applicationcontent.xml
     *
     * @param us
     *            : UserService from datasource
     * @return void
     */
    @Autowired
    @Qualifier("userService")
    public void setUserService(UserService us)
    {
        this.userService = us;
    }

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

    /**
     * add_user is api that user use for create new user in miniblog
     *
     * @param username
     *            : username of user
     * @param password
     *            : password of user
     * @param firstname
     *            : firstname of user
     * @param lastname
     *            : lastname of user
     * @param email
     *            : email of user
     * @return Reponse
     */
    @PermitAll
    @POST
    @Path("add")
    public Response addUser(@FormParam("username") String username,
            @FormParam("password") String password, @FormParam("firstname") String firstname,
            @FormParam("lastname") String lastname, @FormParam("email") String email)
    {

        if (tokenService == null || userService == null) {
            setDataSource();
        }

        JSONObject jsonObject = new JSONObject();
        // validate data from client and add to meta
        Meta meta = UserValidate.validateAddNew(username, firstname, lastname, password, email);
        Data data = null;
        // if have any error, add meta to jsonobject and return to client
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
        try {
            // check exists user in database
            Boolean check = this.userService.isUserExits(username);
            if (check == true) {
                // if exists user in database, return code error
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                        Constraints.CODE_2010), null);
                return Response.status(200).entity(jsonObject.toString()).build();
            }
            // check exists email in database
            check = this.userService.isEmailExits(
                    email.replaceAll(Constraints.REGEX_END_WHITESPACE, ""), null);
            if (check == true) {
                // if exists email in database, return code error
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                        Constraints.CODE_2009), null);
                return Response.status(200).entity(jsonObject.toString()).build();
            }

            // ceate new user with data from client
            User user = new User();
            user.setUsername(username.replaceAll(Constraints.REGEX_END_WHITESPACE, ""));
            user.setFirstname(firstname.replaceAll(Constraints.REGEX_END_WHITESPACE, ""));
            user.setLastname(lastname.replaceAll(Constraints.REGEX_END_WHITESPACE, ""));
            password = password.replaceAll(Constraints.REGEX_END_WHITESPACE, "");
            password = EncrypUtils.encrypData(password);
            user.setPassword(password);
            user.setEmail(email.replaceAll(Constraints.REGEX_END_WHITESPACE, ""));
            user.setStatus(Constraints.USER_ACTIVE);
            Calendar date = Calendar.getInstance();
            user.setCreated_at(date.getTime());
            user.setModified_at(date.getTime());

            // cal user service to add data to database, return code success
            this.userService.add(user);
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_200, 0), null);
        } catch (Exception ex) {
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                    Constraints.CODE_9001), null);
        }

        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * update_user is api that user use for update new information user in
     * miniblog
     *
     * @param firstname
     *            : firstname of user
     * @param lastname
     *            : lastname of user
     * @param email
     *            : email of user
     * @param password
     *            : current password of user
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @PUT
    @Path("update")
    public Response updateUser(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
            @FormParam("password") String password, @FormParam("firstname") String firstname,
            @FormParam("lastname") String lastname, @FormParam("email") String email,
            @FormParam("status") String status)
    {

        if (tokenService == null || userService == null) {
            setDataSource();
        }

        JSONObject jsonObject = new JSONObject();

        // validate data from client and add to meta
        Meta meta = UserValidate.validateUpdate(firstname, lastname, email, status, password);
        Data data = null;
        // if have any error, add meta to jsonobject and return to client
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
        try {
            // get token from access_key
            Token token = null;
            if (access_key != null) {
                token = this.tokenService.findByAccessKey(access_key);
            }

            if (token != null) {

                // encryp password and check valid password with current user
                // login
                password = EncrypUtils.encrypData(password);
                Boolean isValid = true;
                isValid = token.getUser().getPassword().equals(password);
                if (!isValid) {
                    // if password did not match, return code error
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                            Constraints.CODE_2012), null);
                    return Response.status(Constraints.CODE_200).entity(jsonObject.toString())
                            .build();
                }
                // check exists email in database, exclusion current user's
                // email in database
                isValid = this.userService.isEmailExits(
                        email.replaceAll(Constraints.REGEX_END_WHITESPACE, ""), token.getUser());
                if (isValid) {
                    // if exists email in database, return code error
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                            Constraints.CODE_2009), null);
                    return Response.status(200).entity(jsonObject.toString()).build();
                }
                // get user from token, and change data
                User user = token.getUser();
                user.setFirstname(firstname.replaceAll(Constraints.REGEX_END_WHITESPACE, ""));
                user.setLastname(lastname.replaceAll(Constraints.REGEX_END_WHITESPACE, ""));
                user.setEmail(email.replaceAll(Constraints.REGEX_END_WHITESPACE, ""));
                user.setStatus(Integer.parseInt(status));
                Calendar date = Calendar.getInstance();
                user.setModified_at(date.getTime());
                // cal user service to update
                this.userService.update(user);
                // return message success
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_201, 0), null);
            }
        } catch (Exception ex) {
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                    Constraints.CODE_9001), null);
        }

        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * findByName is api that user use for finding other user by name
     *
     * @param name
     *            : name is username, firstname or lastname of user.
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @GET
    @Path("findByName")
    public Response findByName(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
            @QueryParam("name") String name)
    {

        if (tokenService == null || userService == null) {
            setDataSource();
        }

        JSONObject jsonObject = new JSONObject();
        // validate data from client
        Meta meta = UserValidate.validateFindUserByName(name);
        Data data = null;
        // if have any error, add meta to jsonobject and return to client
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
        List<User> listUser = null;
        try {
            // get list user from userservice match with name (firstname,
            // lastname, firstname)
            listUser = this.userService.findByName(name);
            data = new Data();
            // set list user to data

            data.setListUser(listUser);
            // add data to jsonobject and return json to client
            jsonObject = BuildJSON.buildReturn(meta, data);

        } catch (Exception ex) {
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                    Constraints.CODE_9001), null);
        }

        return Response.status(200).entity(jsonObject.toString())
                .header(Constraints.ACCESS_KEY, access_key).build();
    }

    /**
     * get_user_info is api that user use for get owner information
     *
     * @param access_key
     *            : access key in token table
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @GET
    @Path("getUserInfo")
    public Response getUserInfo(@HeaderParam(Constraints.ACCESS_KEY) String access_key)
    {

        if (tokenService == null || userService == null) {
            setDataSource();
        }

        JSONObject jsonObject = new JSONObject();
        Data data = null;
        try {
            Token token = null;
            User user = null;
            // find token by access_key of token
            if (access_key != null) {
                token = this.tokenService.findByAccessKey(access_key);
            }
            if (token != null) {
                // get user from token
                user = token.getUser();
                // set password null
                user.setPassword(null);
                // add password to data and return client
                data = new Data();
                data.setUser(user);
                jsonObject = BuildJSON.buildReturn(null, data);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                    Constraints.CODE_9001), null);
        }
        // jsonObject.put("user", user);
        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * login is api that user use for login to miniblog
     *
     * @param username
     *            : username of user
     * @param password
     *            : password of user
     * @return Response
     */
    @PermitAll
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username,
            @FormParam("password") String password)
    {

        if (tokenService == null || userService == null) {
            setDataSource();
        }

        JSONObject jsonObject = new JSONObject();
        // validate data frome client with uservalidate class
        Meta meta = UserValidate.validateLogin(username, password);
        Data data = null;
        // if have any error, and meta to json and return code error
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
        try {
            // check valid login with data from database
            User user = this.userService.isValidLogin(username, password);
            if (user != null) {
                // if valid login, create new token and set new data to this
                // token
                Token token = new Token();
                Calendar date = Calendar.getInstance();
                String access_key = EncrypUtils.buildAccessKey(username, password);
                token.setAccess_key(access_key);
                token.setCreated_at(date.getTime());
                date.add(Calendar.HOUR_OF_DAY, 1);
                token.setExpired_at(date.getTime());
                token.setUser(user);
                // add new token to database match with this user
                this.tokenService.add(token);

                // if success, return access_key through header
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_204, 0), null);
                return Response.status(200).entity(jsonObject.toString())
                        .header(Constraints.ACCESS_KEY, access_key).build();
            } else {
                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                        Constraints.CODE_1001), null);
            }
        } catch (Exception ex) {
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                    Constraints.CODE_9001), null);
        }

        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * login is api that user use for logout to miniblog
     *
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @PUT
    @Path("logout")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response logout(@HeaderParam(Constraints.ACCESS_KEY) String access_key)
    {

        if (tokenService == null || userService == null) {
            setDataSource();
        }

        JSONObject jsonObject = new JSONObject();
        Token token = null;

        try {

            // get current token by access_key
            if (access_key != null) {
                token = this.tokenService.findByAccessKey(access_key);
            }

            if (token != null) {
                // if valid access_key, delete current access_key
                this.tokenService.deleteByAccessKey(access_key);
                // if success, return message success

                jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_205, 0), null);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_1000,
                    Constraints.CODE_9001), null);
        }

        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * logout is api that user use for logout miniblog
     *
     * @param oldPassword
     *            : current password of user
     * @param newPassword
     *            : new password of user
     * @return Response
     */
    @RolesAllowed("ADMIN")
    @PUT
    @Path("chagnePassword")
    public Response chagnePassword(@HeaderParam(Constraints.ACCESS_KEY) String access_key,
            @FormParam("oldPassword") String oldPassword,
            @FormParam("newPassword") String newPassword)
    {

        if (tokenService == null || userService == null) {
            setDataSource();
        }

        JSONObject jsonObject = new JSONObject();
        // validate data from client
        Meta meta = UserValidate.validateChangePassword(oldPassword, newPassword);
        Data data = null;
        // if have any error, add meta to json and return to client
        if (meta != null) {
            jsonObject = BuildJSON.buildReturn(meta, data);
            return Response.status(200).entity(jsonObject.toString()).build();
        }

        try {

            Token token = null;
            if (access_key != null) {
                token = this.tokenService.findByAccessKey(access_key);
            }

            // get current user by access_key of token

            if (token != null) {
                // get user from token
                User user = token.getUser();
                // encryp old password and compare with password save in
                // database
                oldPassword = EncrypUtils.encrypData(oldPassword);
                if (oldPassword.equals(user.getPassword())) {
                    // if match, set new password to user and cal update in
                    // userservice to update
                    newPassword = EncrypUtils.encrypData(newPassword);
                    user.setPassword(newPassword);
                    userService.update(user);
                    // after success, delete all old token in database.
                    this.tokenService.deleteByUser(user);
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_203, 0), null);
                } else {
                    jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                            Constraints.CODE_2016), null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = BuildJSON.buildReturn(new Meta(Constraints.CODE_2000,
                    Constraints.CODE_9001), null);
        }
        return Response.status(200).entity(jsonObject.toString()).build();
    }

    /**
     * setDataSource: use for setting datasoure to tokeservice and userservice
     */
    private void setDataSource()
    {
          
        tokenService = (TokenService) SpringApplicationContext.getBean("tokenService");
        userService = (UserService) SpringApplicationContext.getBean("userService");
    }
}