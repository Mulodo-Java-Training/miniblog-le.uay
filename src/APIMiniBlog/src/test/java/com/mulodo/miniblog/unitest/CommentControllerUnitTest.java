package com.mulodo.miniblog.unitest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.Comment;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.rest.controller.CommentController;
import com.mulodo.miniblog.rest.controller.UserController;
import com.mulodo.miniblog.service.CommentService;
import com.mulodo.miniblog.service.PostService;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.EncrypUtils;

@SuppressWarnings("deprecation")
public class CommentControllerUnitTest 
{
	
	
	//declare root url
	private final String ROOT_COMMENT_URL = "http://localhost:8080/APIMiniBlog/api/comments";
	private final String ROOT_USER_URL = "http://localhost:8080/APIMiniBlog/api/users";
	
	//declare ApplicationContext for getting bean from applicationContext.xml
	private final static ApplicationContext appContext = 
	    	  new ClassPathXmlApplicationContext("classpath:/WEB-INF/applicationContext.xml");
	
	//set seesion bean in applicationContext.xml to userservice
	private final static CommentService commentService = (CommentService) appContext.getBean("commentService");
	private final static PostService postService = (PostService) appContext.getBean("postService");
	private final static UserService userService = (UserService) appContext.getBean("userService");
	
	private static Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
	private static POJOResourceFactory noDefaults = new POJOResourceFactory(CommentController.class);
	private static Dispatcher dispatcherLogin = MockDispatcherFactory.createDispatcher();
	private static POJOResourceFactory noDefaultsLogin = new POJOResourceFactory(UserController.class);
	MockHttpResponse response = null;
	
	/**
	 *  addComment post for unit testing addComment in commentcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void addComment() throws Exception {
		
		String access_key = login_for_test("le.tung","abcd1234");
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(200);
		listError.add(Constraints.CODE_3001);
		listError.add(Constraints.CODE_3005);
		listError.add(Constraints.CODE_3007);
		
		///==validate post data==
		MockHttpRequest requestValidateData = MockHttpRequest.post("/comments/add");
		///==validate post data== 
		requestValidateData.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestValidateData.content("post_id=&content=".getBytes());
		requestValidateData.header(Constraints.ACCESS_KEY, access_key);
		
		//call method add
		response = new MockHttpResponse();
	    dispatcher.invoke(requestValidateData, response);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, response.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		
		//compare response error code with list error
		assertEquals(Constraints.CODE_3000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}


		//==check invalid post==
		MockHttpRequest requestInvalidPost = MockHttpRequest.post("/comments/add");
		///==validate post data== 
		requestInvalidPost.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestInvalidPost.content("post_id=1000000&content=Content comment for test".getBytes());
		requestInvalidPost.header(Constraints.ACCESS_KEY, access_key);
		
		//call method add
		response = new MockHttpResponse();
	    dispatcher.invoke(requestInvalidPost, response);
		//get json object from response
		jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_3000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_3007, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		
		//==check inactive post==
		Post post = postService.findByTitle("Title 2 le.uay");
		MockHttpRequest requestIsInactivePost = MockHttpRequest.post("/comments/add");
		///==validate post data== 
		requestIsInactivePost.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestIsInactivePost.content(("post_id="+post.getId()+"&content=Content comment for test").getBytes());
		requestIsInactivePost.header(Constraints.ACCESS_KEY, access_key);
		
		//call method add
		response = new MockHttpResponse();
	    dispatcher.invoke(requestIsInactivePost, response);
		//get json object from response
		jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_3000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_3007, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==add comment to active post==
		post = postService.findByTitle("Title 2 le.huy");
		MockHttpRequest requestIsActivePost = MockHttpRequest.post("/comments/add");
		///==validate post data== 
		requestIsActivePost.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestIsActivePost.content(("post_id="+post.getId()+"&content=Content comment for test").getBytes());
		requestIsActivePost.header(Constraints.ACCESS_KEY, access_key);
		
		//call method add
		response = new MockHttpResponse();
	    dispatcher.invoke(requestIsActivePost, response);
		//get json object from response
		jsonObject = new JSONObject(response.getContentAsString());

		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_210, jsonObject.get(Constraints.CODE));

	}
	
//	
//	/**
//	 *  updateComment post for unit testing updateComment in commentcontroller
//	 *	
//	 *	
//	 *  @return 
//	 *  
//	 *  @exception Exception
//	 *	
//	 */
//	@Test
//	public void updateComment() throws Exception {
//		
//		clientRequest = new ClientRequest(ROOT_COMMENT_URL+"/update");
//		String access_key = login("le.tung","abcd1234");
//		clientRequest.header(Constraints.ACCESS_KEY, access_key);
//		
//		//==Create list error==
//		List<Integer> listError = new ArrayList<Integer>();
//		listError.add(200);
//		listError.add(Constraints.CODE_3001);
//		listError.add(Constraints.CODE_3005);
//		listError.add(Constraints.CODE_3006);
//		
//		///==validate post data== 
//		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
//				+ "comment_id=&content=");
//		//post data to add user api
//		response = clientRequest.put(String.class);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		//get json object from response
//		JSONObject jsonObject = new JSONObject(response.getContentAsString());
//		//get meta json object
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		
//		//compare response error code with list error
//		assertEquals(Constraints.CODE_3000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		
//		//compare response error code with list error
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE)));
//		}
//
//
//		//==check invalid comment==
//		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
//				+ "comment_id=10000&content=Comment for test");
//		//post data to add user api
//		response = clientRequest.put(String.class);
//		//get json object from response
//		jsonObject = new JSONObject(response.getContentAsString());
//		//get meta json object
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare response error code with list error
//		assertEquals(Constraints.CODE_3000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		//compare response error code with list error
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(Constraints.CODE_3006, jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE));
//		}
//		
//		//==check valid permission on comment==
//		Post post = postService.findByTitle("Title 1 nguyen.tung");
//		System.out.println("json post ="+new JSONObject(post));
////		Set<Comment> setComment = post.getComments();
////		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
////				+ "comment_id=100000&content=");
////		//post data to add user api
////		response = clientRequest.post(String.class);
////		//get json object from response
////		jsonObject = new JSONObject(response.getContentAsString());
////		//get meta json object
////		jsonObject = jsonObject.getJSONObject(Constraints.META);
////		//compare response error code with list error
////		assertEquals(Constraints.CODE_3000, jsonObject.get(Constraints.CODE));
////		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
////		
////		//compare response error code with list error
////		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
////			assertEquals(Constraints.CODE_3007, jsonObject.getJSONArray(Constraints.MESSAGES)
////					.getJSONObject(i).get(Constraints.CODE));
////		}
//	}
//	
	
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
		dispatcher.getRegistry().addResourceFactory(noDefaults);
		dispatcherLogin.getRegistry().addResourceFactory(noDefaultsLogin);
		
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
		
		
		Comment comment = null;
		Post post = null;
		
		post = new Post("Title 1 le.uay","Content 1 le.uay",datePost.getTime(),datePost.getTime()
				,1, userUay);
		postService.add(post);
		post = postService.findByTitle("Title 1 le.uay");
		comment = new Comment("Post title 1 le.uay comment 1 le.uay", datePost.getTime(), datePost.getTime(), userUay, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.uay comment 2 le.huy", datePost.getTime(), datePost.getTime(), userHuy, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.uay comment 3 le.uay", datePost.getTime(), datePost.getTime(), userUay, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.uay comment 4 minh.tung", datePost.getTime(), datePost.getTime(), userMinhTung, post);
		commentService.add(comment);
		
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 le.uay","Content 2 le.uay",datePost.getTime(),datePost.getTime()
				,0, userUay);
		postService.add(post);
		post = postService.findByTitle("Title 2 le.uay");
		comment = new Comment("Post title 2 le.uay comment 1 le.huy", datePost.getTime(), datePost.getTime(), userHuy, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.uay comment 2 le.uay", datePost.getTime(), datePost.getTime(), userUay, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.uay comment 3 minh.tung", datePost.getTime(), datePost.getTime(), userMinhTung, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.uay comment 4 le.uay", datePost.getTime(), datePost.getTime(), userUay, post);
		commentService.add(comment);

		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 1 le.huy","Content 1 le.huy",datePost.getTime(),datePost.getTime()
				,0,userHuy);
		postService.add(post);
		post = postService.findByTitle("Title 1 le.huy");
		comment = new Comment("Post title 1 le.huy comment 1 le.huy", datePost.getTime(), datePost.getTime(), userHuy, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.huy comment 2 le.uay", datePost.getTime(), datePost.getTime(), userUay, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.huy comment 3 minh.tung", datePost.getTime(), datePost.getTime(), userMinhTung, post);
		commentService.add(comment);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 le.huy","Content 2 le.huy",datePost.getTime(),datePost.getTime()
				,1,userHuy);
		postService.add(post);
		post = postService.findByTitle("Title 2 le.huy");
		comment = new Comment("Post title 2 le.huy comment 1 nguyen.tung", datePost.getTime(), datePost.getTime(), userMinhTung, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.huy comment 2 le.uay", datePost.getTime(), datePost.getTime(), userUay, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.huy comment 3 le.huy", datePost.getTime(), datePost.getTime(), userHuy, post);
		commentService.add(comment);
		
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 1 nguyen.tung","Content 1 nguyen.tung",datePost.getTime(),datePost.getTime()
				,0, userMinhTung);
		postService.add(post);
		post = postService.findByTitle("Title 1 nguyen.tung");
		comment = new Comment("Title 1 nguyen.tung le.huy comment 1 minh.tung", datePost.getTime(), datePost.getTime(), userMinhTung, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Title 1 nguyen.tung le.huy comment 2 le.uay", datePost.getTime(), datePost.getTime(), userUay, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Title 1 nguyen.tung le.huy comment 3 le.huy", datePost.getTime(), datePost.getTime(), userHuy, post);
		commentService.add(comment);
		
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 nguyen.tung","Content 2 nguyen.tung",datePost.getTime(),datePost.getTime()
				,1, userMinhTung);
		postService.add(post);
		post = postService.findByTitle("Title 2 nguyen.tung");
		comment = new Comment("Title 2 nguyen.tung le.huy comment 1 le.uay", datePost.getTime(), datePost.getTime(), userUay, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Title 2 nguyen.tung le.huy comment 2 minh.tung", datePost.getTime(), datePost.getTime(), userMinhTung, post);
		commentService.add(comment);
		datePost.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Title 2 nguyen.tung le.huy comment 3 le.huy", datePost.getTime(), datePost.getTime(), userHuy, post);
		commentService.add(comment);
	
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
		
		postService.deleteByTitle("Title 1 le.uay");
		postService.deleteByTitle("Title 2 le.uay");
		postService.deleteByTitle("Title 1 le.huy");
		postService.deleteByTitle("Title 2 le.huy");
		postService.deleteByTitle("Title 1 nguyen.tung");
		postService.deleteByTitle("Title 2 nguyen.tung");
		
		userService.deleteByUsername("le.tung");
		userService.deleteByUsername("le.huy");
		userService.deleteByUsername("le.uay");
		userService.deleteByUsername("minh.tung");
		
	}
	
	
	/**
	 *  login use for return access_key for api require authentication  commentcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	private String login_for_test(String username, String password) throws Exception{
		MockHttpRequest requestLogin = MockHttpRequest.post("/users/login");
		requestLogin.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestLogin.content(("username="+username+"&password="+password).getBytes());
		//post data to add user api
		response = new MockHttpResponse();
		dispatcherLogin.invoke(requestLogin, response);
	    
		//get json object from response
		JSONObject jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//get access_key from login
		String access_key = (String) response.getOutputHeaders().getFirst(Constraints.ACCESS_KEY);
		return access_key;
	}
}
