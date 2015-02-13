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
package com.mulodo.miniblog.unitest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.service.PostService;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.EncrypUtils;

/**
 * The UserControllerUnitTest use create unit test of user controller
 * 
 * @author UayLU
 * 
 */
@SuppressWarnings("deprecation")
public class PostControllerUnitTest 
{
	
	//declare root user url
	private final String ROOT_POST_URL = "http://localhost:8080/APIMiniBlog/api/posts";
	private final String ROOT_USER_URL = "http://localhost:8080/APIMiniBlog/api/users";
	//declare ApplicationContext for getting bean from applicationContext.xml
	private final static ApplicationContext appContext = 
	    	  new ClassPathXmlApplicationContext("classpath:/WEB-INF/applicationContext.xml");
	
	//set seesion bean in applicationContext.xml to userservice
	private final static PostService postService = (PostService) appContext.getBean("postService");
	private final static UserService userService = (UserService) appContext.getBean("userService");
	
	private ClientResponse<String> respone = null;
	private ClientRequest clientRequest = null;
	
	
	/**
	 *  add_new post for unit testing addNew in postcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void add_new() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_POST_URL+"/add");
		String access_key = login("le.tung","abcd1234");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(200);
		listError.add(Constraints.CODE_2500);
		listError.add(Constraints.CODE_2502);
		listError.add(Constraints.CODE_2503);
		listError.add(Constraints.CODE_2504);
		listError.add(Constraints.CODE_2505);
		
		///==validate post data== 
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "title=&content=");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		
		//compare response error code with list error
		assertEquals(true, listError.contains(jsonObject.get(Constraints.CODE)));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//==add success==
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "title=Title for test&content=Content for test");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status add success
		assertEquals(Constraints.CODE_206, jsonObject.get(Constraints.CODE));
		assertEquals(Constraints.CODE_200, respone.getStatus());
		
	}
	
	/**
	 *  update post for unit testing update in postcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void update() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_POST_URL+"/update");
		String access_key = login("le.uay","abcd1234");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(Constraints.CODE_2500);
		listError.add(Constraints.CODE_2502);
		listError.add(Constraints.CODE_2503);
		listError.add(Constraints.CODE_2504);
		listError.add(Constraints.CODE_2505);
		listError.add(Constraints.CODE_2508);
		
		///==validate post data== 
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "it=ab&title=&content=");
		//post data to add user api
		respone = clientRequest.put(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		
		//compare response error code with list error
		assertEquals(true, listError.contains(jsonObject.get(Constraints.CODE)));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//==update success==
		Post post = postService.findByTitle("Title 1 le.uay");
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "id="+post.getId()+"&title=Title for test&content=Content for test");
		//post data to add user api
		respone = clientRequest.put(String.class);
		
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status add success
		assertEquals(Constraints.CODE_207, jsonObject.get(Constraints.CODE));
		assertEquals(Constraints.CODE_200, respone.getStatus());
		
	}
	
	
	/**
	 *  delete post for unit testing delete in postcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void delete() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_POST_URL+"/delete/abcd");
		String access_key = login("le.uay","abcd1234");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);

		///==validate post data== 
		
		//post data to add user api
		respone = clientRequest.delete(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		
		//compare response error code with list error
		assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2508, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==post is invalid in database==
		clientRequest = new ClientRequest(ROOT_POST_URL+"/delete/10000");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.delete(String.class);
		
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2501, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		
		//==validate permission of current login user on this post==
		Post post = postService.findByTitle("Title 4 nguyen.tung");
		clientRequest = new ClientRequest(ROOT_POST_URL+"/delete/"+post.getId());
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.delete(String.class);
		
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2509, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==delete success==
		post = postService.findByTitle("Title 2 le.uay");
		clientRequest = new ClientRequest(ROOT_POST_URL+"/delete/"+post.getId());
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.delete(String.class);
		
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_209, jsonObject.get(Constraints.CODE));
	
		
	}
	
	/**
	 *  activeDeactive post for unit testing activeDeactive in postcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void activeDeactive() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_POST_URL+"/activeDeactive");
		String access_key = login("le.uay","abcd1234");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(Constraints.CODE_2500);
		listError.add(Constraints.CODE_2507);
		listError.add(Constraints.CODE_2508);

		//==validate post data== 
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "id=ab&status=");
		//post data to add user api
		respone = clientRequest.put(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
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
		
		//==post is invalid in database==		
		//post data to add user api
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "id=10000&status=1");
		respone = clientRequest.put(String.class);
		
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2501, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		
		//==validate permission of current login user on this post==
		Post post = postService.findByTitle("Title 4 nguyen.tung");
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "id="+post.getId()+"&status=1");
		//post data to add user api
		respone = clientRequest.put(String.class);
		
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		System.out.println(jsonObject);
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2509, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==deactive post "title 2 le.uay" of le.uay user==
		post = postService.findByTitle("Title 2 le.uay");
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "id="+post.getId()+"&status=1");
		//post data to add user api
		respone = clientRequest.put(String.class);
		
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_208, jsonObject.get(Constraints.CODE));
	
		
	}
	
	
	/**
	 *  get_all_post post for unit testing get all post in postcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void get_all_post() throws Exception {
		
		
		String access_key = login("le.uay","abcd1234");
	

		//==validate post data==
		clientRequest = new ClientRequest(ROOT_POST_URL+"/getAllPost?pageNum=ab&description=");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.get(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		
		//compare response error code with list error
		assertEquals(Constraints.CODE_2500, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2510, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==get all post==
		clientRequest = new ClientRequest(ROOT_POST_URL+"/getAllPost?pageNum=1&description=");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.get(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.DATA);
		
		//compare response error code with list error
		assertEquals(true, jsonObject.has(Constraints.LIST_POST));
		assertEquals(true, jsonObject.getJSONArray(Constraints.LIST_POST).length() > 0);
		assertEquals(true, jsonObject.has(Constraints.TOTAL_PAGE));
		assertEquals(true, jsonObject.has(Constraints.TOTAL_ROW));
		assertEquals(true, jsonObject.has(Constraints.LIMIT_ROW_STRING));
		assertEquals(true, jsonObject.has(Constraints.PAGE_NUM));
		
	}
	
	
	/**
	 *  get_all_post_for_user post for unit testing get all post for user in postcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void get_all_post_for_user() throws Exception {
		
		String access_key = login("le.uay","abcd1234");
	
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(Constraints.CODE_2510);
		listError.add(Constraints.CODE_2508);
		
		//==validate post data==
		clientRequest = new ClientRequest(ROOT_POST_URL+"/getPostForUser?pageNum=ab&user_id=cd");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.get(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
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
		
		//==get all post==
		User user = userService.findByUsername("le.uay");
		clientRequest = new ClientRequest(ROOT_POST_URL+"/getPostForUser?pageNum=1&user_id="+ user.getId());
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.get(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.DATA);
		
		//compare response error code with list error
		assertEquals(true, jsonObject.has(Constraints.LIST_POST));
		assertEquals(true, jsonObject.getJSONArray(Constraints.LIST_POST).length() > 0);
		assertEquals(true, jsonObject.has(Constraints.TOTAL_PAGE));
		assertEquals(true, jsonObject.has(Constraints.TOTAL_ROW));
		assertEquals(true, jsonObject.has(Constraints.LIMIT_ROW_STRING));
		assertEquals(true, jsonObject.has(Constraints.PAGE_NUM));
		
	}
	
	/**
	 *  setUpData user for unit testing  usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@BeforeClass
	public static void  setUpData() throws Exception{
		
		Calendar datePost = Calendar.getInstance();
		Calendar dateUser = Calendar.getInstance();
		
		User userTung = new User("le.tung",EncrypUtils.encrypData("abcd1234"), "le", "tung", 
				"le.tung@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
		userService.add(userTung);
		
		User userUay = new User("le.uay",EncrypUtils.encrypData("abcd1234"), "le", "uay", 
				"le.uay@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
		userService.add(userUay);
		
		User userHuy = new User("le.huy",EncrypUtils.encrypData("abcd1234"), "le", "huy", 
				"le.huy@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
		userService.add(userHuy);
		
		User userMinhTung = new User("minh.tung",EncrypUtils.encrypData("abcd1234"), "minh", "tung", 
				"minh.tung@mulodo.com", dateUser.getTime(), dateUser.getTime(), 0);
		userService.add(userMinhTung);
		
		userHuy = userService.findByUsername("le.huy");
		userUay = userService.findByUsername("le.uay");
		userMinhTung = userService.findByUsername("minh.tung");
		
		Post post = new Post("Title 1 le.uay","Content 1 le.uay",datePost.getTime(),datePost.getTime()
				,1, userUay);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 le.uay","Content 2 le.uay",datePost.getTime(),datePost.getTime()
				,0, userUay);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 3 le.uay","Content 3 le.uay",datePost.getTime(),datePost.getTime()
				,1, userUay);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 4 le.uay","Content 4 le.uay",datePost.getTime(),datePost.getTime()
				,0, userUay);
		postService.add(post);

		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 1 le.huy","Content 1 le.huy",datePost.getTime(),datePost.getTime()
				,0, userHuy);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 le.huy","Content 2 le.huy",datePost.getTime(),datePost.getTime()
				,1, userHuy);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 3 le.huy","Content 3 le.huy",datePost.getTime(),datePost.getTime()
				,1, userHuy);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 4 le.huy","Content 4 le.huy",datePost.getTime(),datePost.getTime()
				,1, userHuy);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 1 nguyen.tung","Content 1 nguyen.tung",datePost.getTime(),datePost.getTime()
				,0, userMinhTung);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 nguyen.tung","Content 2 nguyen.tung",datePost.getTime(),datePost.getTime()
				,1, userMinhTung);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 3 nguyen.tung","Content 3 nguyen.tung",datePost.getTime(),datePost.getTime()
				,0, userMinhTung);
		postService.add(post);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 4 nguyen.tung","Content 4 nguyen.tung",datePost.getTime(),datePost.getTime()
				,1, userMinhTung);
		postService.add(post);
		
	}
	
	
	/**
	 *  callBackUpdate user for unit testing  usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@AfterClass
	public static void clearData() throws Exception{
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
	 *  login use for return access_key for api require authentication  postcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	private String login(String username, String password) throws Exception{
		ClientRequest clientRequestLogin = new ClientRequest(ROOT_USER_URL+"/login");
		clientRequestLogin.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username="+username+"&password="+password);
		//post data to add user api
		respone = clientRequestLogin.post(String.class);
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//get access_key from login
		String access_key = (String) respone.getHeaders().get(Constraints.ACCESS_KEY).get(0);
		return access_key;
	}
}
