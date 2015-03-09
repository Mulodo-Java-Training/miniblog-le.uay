/* 
 * UserControllerUnitTest.java 
 *  
 * 1.0
 * 
 * 2015/02/11
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.unittest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.rest.controller.PostController;
import com.mulodo.miniblog.rest.controller.UserController;
import com.mulodo.miniblog.service.PostService;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.EncrypUtils;
import com.mulodo.miniblog.utils.SpringApplicationContext;

/**
 * The UserControllerUnitTest use create unit test of user controller
 * 
 * @author UayLU
 */
public class PostControllerUnitTest
{

    // set seesion bean in applicationContext.xml to userservice
    private final static PostService postService = (PostService) SpringApplicationContext
            .getBean("postService");
    private final static UserService userService = (UserService) SpringApplicationContext
            .getBean("userService");

    private static Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
    private static POJOResourceFactory noDefaults = new POJOResourceFactory(PostController.class);
    private static Dispatcher dispatcherLogin = MockDispatcherFactory.createDispatcher();
    private static POJOResourceFactory noDefaultsLogin = new POJOResourceFactory(
            UserController.class);
    MockHttpResponse response = null;

    /**
     * add_new post for unit testing addNew in postcontroller
     *
     * @return
     * @exception Exception
     */
    @Test
    public void add_new() throws Exception
    {

        String access_key = login("le.tung", "abcd1234");

        // ==Create list error==
        List<Integer> listError = new ArrayList<Integer>();
        listError.add(200);
        listError.add(Constraints.CODE_2500);
        listError.add(Constraints.CODE_2502);
        listError.add(Constraints.CODE_2503);
        listError.add(Constraints.CODE_2504);
        listError.add(Constraints.CODE_2505);

        // /==validate post data==
        MockHttpRequest requestValidateData = MockHttpRequest.post("/posts/add");
        // /==validate post data==
        requestValidateData.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestValidateData.content("title=&content=".getBytes());
        requestValidateData.header(Constraints.ACCESS_KEY, access_key);

        // call method add
        response = new MockHttpResponse();
        dispatcher.invoke(requestValidateData, response);

        // post data to add user api
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);

        // compare response error code with list error
        assertEquals(true, listError.contains(jsonObject.get(Constraints.CODE)));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(
                    true,
                    listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
                            .getJSONObject(i).get(Constraints.CODE)));
        }

        // ==add success==
        MockHttpRequest requestAddPostSuccess = MockHttpRequest.post("/posts/add");
        // /==validate post data==
        requestAddPostSuccess.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestAddPostSuccess.content("title=Title for test&content=Content for test".getBytes());
        requestAddPostSuccess.header(Constraints.ACCESS_KEY, access_key);

        // call method add
        response = new MockHttpResponse();
        dispatcher.invoke(requestAddPostSuccess, response);

        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare status return with status add success
        assertEquals(Constraints.CODE_206, jsonObject.get(Constraints.CODE));
        assertEquals(Constraints.CODE_200, response.getStatus());

    }

    /**
     * update post for unit testing update in postcontroller
     *
     * @return
     * @exception Exception
     */
    @Test
    public void update() throws Exception
    {

        String access_key = login("le.uay", "abcd1234");

        // ==Create list error==
        List<Integer> listError = new ArrayList<Integer>();
        listError.add(Constraints.CODE_2500);
        listError.add(Constraints.CODE_2502);
        listError.add(Constraints.CODE_2503);
        listError.add(Constraints.CODE_2504);
        listError.add(Constraints.CODE_2505);
        listError.add(Constraints.CODE_2508);

        // /==validate post data==
        MockHttpRequest requestPostData = MockHttpRequest.put("/posts/update");
        // /==validate post data==
        requestPostData.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestPostData.content("id=-1&title=&content=".getBytes());
        requestPostData.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestPostData, response);

        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);

        // compare response error code with list error
        assertEquals(true, listError.contains(jsonObject.get(Constraints.CODE)));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(
                    true,
                    listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
                            .getJSONObject(i).get(Constraints.CODE)));
        }

        // ==update success==
        Post post = postService.findByTitle("Title 1 le.uay");
        MockHttpRequest requestUpdateSuccess = MockHttpRequest.put("/posts/update");
        // /==validate post data==
        requestUpdateSuccess.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestUpdateSuccess
                .content(("id=" + post.getId() + "&title=Title for test&content=Content for test")
                        .getBytes());
        requestUpdateSuccess.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestUpdateSuccess, response);

        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare status return with status add success
        assertEquals(Constraints.CODE_207, jsonObject.get(Constraints.CODE));
        assertEquals(Constraints.CODE_200, response.getStatus());

    }

    /**
     * delete post for unit testing delete in postcontroller
     *
     * @return
     * @exception Exception
     */
    @Test
    public void delete() throws Exception
    {

        String access_key = login("le.uay", "abcd1234");

        // /==validate post data==

        // post data to add user api
        MockHttpRequest requestValidateData = MockHttpRequest.delete("/posts/delete/abcd");
        // /==validate post data==
        requestValidateData.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestValidateData, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);

        // compare response error code with list error
        assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(Constraints.CODE_2508, jsonObject.getJSONArray(Constraints.MESSAGES)
                    .getJSONObject(i).get(Constraints.CODE));
        }

        // ==post is invalid in database==
        MockHttpRequest requestInvalidPost = MockHttpRequest.delete("/posts/delete/10000");
        // /==validate post data==
        requestInvalidPost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestInvalidPost, response);

        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare response error code with list error
        assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(Constraints.CODE_2501, jsonObject.getJSONArray(Constraints.MESSAGES)
                    .getJSONObject(i).get(Constraints.CODE));
        }

        // ==validate permission of current login user on this post==
        Post post = postService.findByTitle("Title 4 nguyen.tung");
        MockHttpRequest requestPermissionPost = MockHttpRequest.delete("/posts/delete/"
                + post.getId());
        // /==validate post data==
        requestPermissionPost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestPermissionPost, response);

        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare response error code with list error
        assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(Constraints.CODE_2509, jsonObject.getJSONArray(Constraints.MESSAGES)
                    .getJSONObject(i).get(Constraints.CODE));
        }

        // ==delete success==
        post = postService.findByTitle("Title 2 le.uay");
        MockHttpRequest requestDeletePost = MockHttpRequest.delete("/posts/delete/" + post.getId());
        // /==validate post data==
        requestDeletePost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestDeletePost, response);

        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare response error code with list error
        assertEquals(Constraints.CODE_209, jsonObject.get(Constraints.CODE));

    }

    /**
     * activeDeactive post for unit testing activeDeactive in postcontroller
     *
     * @return
     * @exception Exception
     */
    @Test
    public void activeDeactive() throws Exception
    {

        String access_key = login("le.uay", "abcd1234");

        // ==Create list error==
        List<Integer> listError = new ArrayList<Integer>();
        listError.add(Constraints.CODE_2500);
        listError.add(Constraints.CODE_2507);
        listError.add(Constraints.CODE_2508);

        // ==validate post data==
        MockHttpRequest requestValidateData = MockHttpRequest.put("/posts/activeDeactive");
        // /==validate post data==
        requestValidateData.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestValidateData.content("id=ab&status=-1".getBytes());
        requestValidateData.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestValidateData, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);

        // compare response error code with list error
        assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(
                    true,
                    listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
                            .getJSONObject(i).get(Constraints.CODE)));
        }

        // ==post is invalid in database==
        // post data to add user api
        MockHttpRequest requestInvalidaPost = MockHttpRequest.put("/posts/activeDeactive");
        // /==validate post data==
        requestInvalidaPost.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestInvalidaPost.content("id=10000&status=1".getBytes());
        requestInvalidaPost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestInvalidaPost, response);

        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare response error code with list error
        assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(Constraints.CODE_2501, jsonObject.getJSONArray(Constraints.MESSAGES)
                    .getJSONObject(i).get(Constraints.CODE));
        }

        // ==validate permission of current login user on this post==
        Post post = postService.findByTitle("Title 4 nguyen.tung");
        MockHttpRequest requestPermissionOnPost = MockHttpRequest.put("/posts/activeDeactive");
        // /==validate post data==
        requestPermissionOnPost.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestPermissionOnPost.content(("id=" + post.getId() + "&status=1").getBytes());
        requestPermissionOnPost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestPermissionOnPost, response);

        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare response error code with list error
        assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(Constraints.CODE_2509, jsonObject.getJSONArray(Constraints.MESSAGES)
                    .getJSONObject(i).get(Constraints.CODE));
        }

        // ==deactive post "title 2 le.uay" of le.uay user==
        post = postService.findByTitle("Title 3");
        post = postService.findByTitle("Title 3 le.uay");

        MockHttpRequest requestDeactivePost = MockHttpRequest.put("/posts/activeDeactive");
        // /==validate post data==
        requestDeactivePost.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestDeactivePost.content(("id=" + post.getId() + "&status=1").getBytes());
        requestDeactivePost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestDeactivePost, response);

        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare response error code with list error
        assertEquals(Constraints.CODE_208, jsonObject.get(Constraints.CODE));

    }

    /**
     * get_all_post post for unit testing get all post in postcontroller
     *
     * @return
     * @exception Exception
     */
    @Test
    public void get_all_post() throws Exception
    {

        String access_key = login("le.uay", "abcd1234");

        // ==validate post data==
        MockHttpRequest requestValidateData = MockHttpRequest
                .get("/posts/getAllPost?pageNum=ab&description=");
        // /==validate post data==
        requestValidateData.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestValidateData, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);

        // compare response error code with list error
        assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(Constraints.CODE_2510, jsonObject.getJSONArray(Constraints.MESSAGES)
                    .getJSONObject(i).get(Constraints.CODE));
        }

        // ==get all post==
        MockHttpRequest requestGetAllPost = MockHttpRequest
                .get("/posts/getAllPost?pageNum=1&description=");
        // /==validate post data==
        requestGetAllPost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestGetAllPost, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.DATA);

        // compare response error code with list error
        assertEquals(true, jsonObject.has(Constraints.LIST_POST));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_PAGE));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_ROW));
        assertEquals(true, jsonObject.has(Constraints.LIMIT_ROW_STRING));
        assertEquals(true, jsonObject.has(Constraints.PAGE_NUM));

        // ==get all post==
        MockHttpRequest requestGetNullPost = MockHttpRequest
                .get("/posts/getAllPost?pageNum=1&description=abcd");
        // /==validate post data==
        requestGetNullPost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestGetNullPost, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.DATA);

        // compare response error code with list error
        assertEquals(true, jsonObject.has(Constraints.LIST_POST));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_PAGE));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_ROW));
        assertEquals(true, jsonObject.has(Constraints.LIMIT_ROW_STRING));
        assertEquals(true, jsonObject.has(Constraints.PAGE_NUM));

    }

    /**
     * get_all_post_for_user post for unit testing get all post for user in
     * postcontroller
     *
     * @return
     * @exception Exception
     */
    @Test
    public void get_all_post_for_user() throws Exception
    {

        String access_key = login("le.uay", "abcd1234");

        // ==Create list error==
        List<Integer> listError = new ArrayList<Integer>();
        listError.add(Constraints.CODE_2510);
        listError.add(Constraints.CODE_2508);

        // ==validate post data==
        MockHttpRequest requestValidateData = MockHttpRequest
                .get("/posts/getPostForUser?pageNum=ab&user_id=cd");
        // /==validate post data==
        requestValidateData.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestValidateData, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);

        // compare response error code with list error
        assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
        assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));

        // compare response error code with list error
        for (int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++) {
            assertEquals(
                    true,
                    listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
                            .getJSONObject(i).get(Constraints.CODE)));
        }

        // ==get all post for owner user==
        User user = userService.findByUsername("le.uay");
        MockHttpRequest requestGetPostForOwnerUser = MockHttpRequest
                .get("/posts/getPostForUser?pageNum=1&user_id=" + user.getId());
        // /==validate post data==
        requestGetPostForOwnerUser.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestGetPostForOwnerUser, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.DATA);

        // compare response error code with list error
        assertEquals(true, jsonObject.has(Constraints.LIST_POST));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_PAGE));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_ROW));
        assertEquals(true, jsonObject.has(Constraints.LIMIT_ROW_STRING));
        assertEquals(true, jsonObject.has(Constraints.PAGE_NUM));

        // ==get all post for one user==
        user = userService.findByUsername("le.huy");
        MockHttpRequest requestGetPostForOneUser = MockHttpRequest
                .get("/posts/getPostForUser?pageNum=1&user_id=" + user.getId());
        // /==validate post data==
        requestGetPostForOneUser.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestGetPostForOneUser, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.DATA);

        // compare response error code with list error
        assertEquals(true, jsonObject.has(Constraints.LIST_POST));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_PAGE));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_ROW));
        assertEquals(true, jsonObject.has(Constraints.LIMIT_ROW_STRING));
        assertEquals(true, jsonObject.has(Constraints.PAGE_NUM));

        // ==get all post==
        MockHttpRequest requestGetNullPost = MockHttpRequest
                .get("/posts/getPostForUser?pageNum=6&user_id=" + user.getId());
        // /==validate post data==
        requestGetNullPost.header(Constraints.ACCESS_KEY, access_key);

        // post data to add user api
        response = new MockHttpResponse();
        dispatcher.invoke(requestGetNullPost, response);
        // compare status return with status in list error
        assertEquals(Constraints.CODE_200, response.getStatus());
        // get json object from response
        jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.DATA);

        // compare response error code with list error
        assertEquals(true, jsonObject.has(Constraints.LIST_POST));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_PAGE));
        assertEquals(true, jsonObject.has(Constraints.TOTAL_ROW));
        assertEquals(true, jsonObject.has(Constraints.LIMIT_ROW_STRING));
        assertEquals(true, jsonObject.has(Constraints.PAGE_NUM));

    }

    /**
     * setUpData user for unit testing usercontroller
     *
     * @return
     * @exception Exception
     */
    @BeforeClass
    public static void setUpData() throws Exception
    {
        dispatcher.getRegistry().addResourceFactory(noDefaults);
        dispatcherLogin.getRegistry().addResourceFactory(noDefaultsLogin);

        Calendar datePost = Calendar.getInstance();
        Calendar dateUser = Calendar.getInstance();

        User userTung = new User("le.tung", EncrypUtils.encrypData("abcd1234"), "le", "tung",
                "le.tung@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
        userService.add(userTung);

        User userUay = new User("le.uay", EncrypUtils.encrypData("abcd1234"), "le", "uay",
                "le.uay@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
        userService.add(userUay);

        User userHuy = new User("le.huy", EncrypUtils.encrypData("abcd1234"), "le", "huy",
                "le.huy@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
        userService.add(userHuy);

        User userMinhTung = new User("minh.tung", EncrypUtils.encrypData("abcd1234"), "minh",
                "tung", "minh.tung@mulodo.com", dateUser.getTime(), dateUser.getTime(), 0);
        userService.add(userMinhTung);

        userHuy = userService.findByUsername("le.huy");
        userUay = userService.findByUsername("le.uay");
        userMinhTung = userService.findByUsername("minh.tung");

        Post post = new Post("Title 1 le.uay", "Content 1 le.uay", datePost.getTime(),
                datePost.getTime(), 1, userUay);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 2 le.uay", "Content 2 le.uay", datePost.getTime(),
                datePost.getTime(), 0, userUay);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 3 le.uay", "Content 3 le.uay", datePost.getTime(),
                datePost.getTime(), 1, userUay);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 4 le.uay", "Content 4 le.uay", datePost.getTime(),
                datePost.getTime(), 0, userUay);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 1 le.huy", "Content 1 le.huy", datePost.getTime(),
                datePost.getTime(), 0, userHuy);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 2 le.huy", "Content 2 le.huy", datePost.getTime(),
                datePost.getTime(), 1, userHuy);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 3 le.huy", "Content 3 le.huy", datePost.getTime(),
                datePost.getTime(), 1, userHuy);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 4 le.huy", "Content 4 le.huy", datePost.getTime(),
                datePost.getTime(), 1, userHuy);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 1 nguyen.tung", "Content 1 nguyen.tung", datePost.getTime(),
                datePost.getTime(), 0, userMinhTung);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 2 nguyen.tung", "Content 2 nguyen.tung", datePost.getTime(),
                datePost.getTime(), 1, userMinhTung);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 3 nguyen.tung", "Content 3 nguyen.tung", datePost.getTime(),
                datePost.getTime(), 0, userMinhTung);
        postService.add(post);

        datePost.add(Calendar.HOUR_OF_DAY, 1);
        post = new Post("Title 4 nguyen.tung", "Content 4 nguyen.tung", datePost.getTime(),
                datePost.getTime(), 1, userMinhTung);
        postService.add(post);

    }

    /**
     * callBackUpdate user for unit testing usercontroller
     *
     * @return
     * @exception Exception
     */
    @AfterClass
    public static void clearData() throws Exception
    {
        postService.deleteByTitle("Title for test");
        postService.deleteByTitle("Title 2 le.uay");
        postService.deleteByTitle("Title 3 le.uay");
        postService.deleteByTitle("Title 4 le.uay");
        postService.deleteByTitle("Title 1 le.huy");
        postService.deleteByTitle("Title 2 le.huy");
        postService.deleteByTitle("Title 3 le.huy");
        postService.deleteByTitle("Title 4 le.huy");
        postService.deleteByTitle("Title 1 nguyen.tung");
        postService.deleteByTitle("Title 2 nguyen.tung");
        postService.deleteByTitle("Title 3 nguyen.tung");
        postService.deleteByTitle("Title 4 nguyen.tung");

        userService.deleteByUsername("le.tung");
        userService.deleteByUsername("le.huy");
        userService.deleteByUsername("le.uay");
        userService.deleteByUsername("minh.tung");
    }

    /**
     * login use for return access_key for api require authentication
     * postcontroller
     *
     * @return
     * @exception Exception
     */
    private String login(String username, String password) throws Exception
    {

        MockHttpRequest requestLogin = MockHttpRequest.post("/users/login");
        requestLogin.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestLogin.content(("username=" + username + "&password=" + password).getBytes());
        // post data to add user api
        response = new MockHttpResponse();
        dispatcherLogin.invoke(requestLogin, response);

        // get json object from response
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // get access_key from login
        String access_key = (String) response.getOutputHeaders().getFirst(Constraints.ACCESS_KEY);
        return access_key;
    }
}
