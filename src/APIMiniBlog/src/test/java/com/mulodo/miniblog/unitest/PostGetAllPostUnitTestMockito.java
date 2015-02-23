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
import com.mulodo.miniblog.object.Data;
import com.mulodo.miniblog.object.Message;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.rest.controller.PostController;
import com.mulodo.miniblog.service.PostService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.validator.PostValidate;

@RunWith(MockitoJUnitRunner.class)
public class PostGetAllPostUnitTestMockito 
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
	public void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testActiveDeactivePost() throws HandlerException{

		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(Constraints.CODE_2510);
		listError.add(Constraints.CODE_2508);
		
		//==Validate data from client
		List<Message> listMessage = new ArrayList<Message>();
		listMessage.add(new Message(Constraints.CODE_2510));
		Meta meta = new Meta(Constraints.CODE_2500, listMessage);
		when(postValidate.validateGetAllPostForUser("","")).thenReturn(meta);
		
		Response respone =  postController.getAllPostForUser("", "", "");
		assertEquals(Constraints.CODE_200, respone.getStatus());
		
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		
		//compare response error code with list error
		assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//==get all post success==
		//add token
		Token token = new Token();
		token.setAccess_key("3c50c84079be8b88ee1296ca0cfe0d81443291eaaa73c1ddca498323527948b");
		token.setId(1);
		token.setUser(new User(1, "le.uay", 1));
		when(tokenService.findByAccessKey("3c50c84079be8b88ee1296ca0cfe0d81443291eaaa73c1ddca498323527948b")).thenReturn(token);
		//add list post
		Data data = new Data();
		data.setLimitRow(Constraints.LIMIT_ROW);
		data.setPageNum(1);
		data.setTotalPage(1);
		data.setTotalRow(2);
		List<Post> listPost = new ArrayList<Post>();
		listPost.add(new Post());
		listPost.add(new Post());
		data.setListPost(listPost);
		when(postValidate.validateGetAllPostForUser("1","1")).thenReturn(null);
		when(postService.getAllPost(1, token.getUser().getId(), "")).thenReturn(data);
		//call method getALlPost in controller
		respone =  postController.getAllPost("3c50c84079be8b88ee1296ca0cfe0d81443291eaaa73c1ddca498323527948b"
				, "1","");
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.DATA);
		
		//compare response error code with list error
		assertEquals(true, jsonObject.has(Constraints.LIST_POST));
		assertEquals(true, jsonObject.has(Constraints.TOTAL_PAGE));
		assertEquals(true, jsonObject.has(Constraints.TOTAL_ROW));
		assertEquals(true, jsonObject.has(Constraints.LIMIT_ROW_STRING));
		assertEquals(true, jsonObject.has(Constraints.PAGE_NUM));
		
	}
}
