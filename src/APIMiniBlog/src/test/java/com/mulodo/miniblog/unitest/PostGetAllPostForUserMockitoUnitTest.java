package com.mulodo.miniblog.unitest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.exeption.HandlerException;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.object.Message;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.rest.controller.PostController;
import com.mulodo.miniblog.service.PostService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.validator.PostValidate;

@RunWith(MockitoJUnitRunner.class)
public class PostGetAllPostForUserMockitoUnitTest
{

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @Mock
    private PostValidate postValidate;

    @Mock
    private TokenService tokenService;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testActiveDeactivePost() throws HandlerException
    {

        // ==Validate data from client
        List<Message> listMessage = new ArrayList<Message>();
        listMessage.add(new Message(Constraints.CODE_2508));
        Meta meta = new Meta(Constraints.CODE_2500, listMessage);
        when(postValidate.validateDelete("")).thenReturn(meta);

        Response respone = postController.delete("", "");
        assertEquals(Constraints.CODE_200, respone.getStatus());

        JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
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

        // add token
        Token token = new Token();
        token.setAccess_key("3c50c84079be8b88ee1296ca0cfe0d81443291eaaa73c1ddca498323527948b");
        token.setId(1);
        token.setUser(new User(1, "le.uay", 1));
        when(
                tokenService
                        .findByAccessKey("3c50c84079be8b88ee1296ca0cfe0d81443291eaaa73c1ddca498323527948b"))
                .thenReturn(token);

        // ==Test invalid post in database==
        when(postService.findOne(100000)).thenReturn(null);
        when(postValidate.validateDelete("100000")).thenReturn(null);
        respone = postController.delete(
                "3c50c84079be8b88ee1296ca0cfe0d81443291eaaa73c1ddca498323527948b", "100000");
        // get json object from response
        jsonObject = new JSONObject(respone.getEntity().toString());
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

        // ==Test permission of post in database==
        Post post = new Post();
        post.setId(2);
        post.setTitle("Test post title le.huy");
        post.setContent("Test post content le.huy");
        post.setUser(new User(2, "le.huy", 1));
        when(postService.findOne(2)).thenReturn(post);
        when(postValidate.validateDelete("2")).thenReturn(null);
        respone = postController.delete(
                "3c50c84079be8b88ee1296ca0cfe0d81443291eaaa73c1ddca498323527948b", "2");
        // get json object from response
        jsonObject = new JSONObject(respone.getEntity().toString());
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

        // ==deactive post success==
        post = new Post();
        post.setId(1);
        post.setTitle("Test post title le.uay");
        post.setContent("Test post content le.uay");
        post.setUser(new User(1, "le.uay", 1));
        post.setStatus(0);
        when(postService.findOne(1)).thenReturn(post);
        post.setStatus(1);
        when(postService.delete(1)).thenReturn(true);
        when(postValidate.validateDelete("1")).thenReturn(null);
        respone = postController.delete(
                "3c50c84079be8b88ee1296ca0cfe0d81443291eaaa73c1ddca498323527948b", "1");
        // get json object from response
        jsonObject = new JSONObject(respone.getEntity().toString());
        // get meta json object
        jsonObject = jsonObject.getJSONObject(Constraints.META);
        // compare response error code with list error
        assertEquals(Constraints.CODE_209, jsonObject.get(Constraints.CODE));
    }
}
