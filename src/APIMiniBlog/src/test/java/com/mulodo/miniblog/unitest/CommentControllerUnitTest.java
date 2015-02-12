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
import com.mulodo.miniblog.model.Comment;
import com.mulodo.miniblog.model.Post;
import com.mulodo.miniblog.model.User;
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
	
	private ClientResponse<String> respone = null;
	private ClientRequest clientRequest = null;
	
	/**
	 *  addComment post for unit testing addComment in commentcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	//@Test
	public void addComment() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_COMMENT_URL+"/add");
		String access_key = login("le.tung","abcd1234");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(200);
		listError.add(Constraints.CODE_3001);
		listError.add(Constraints.CODE_3005);
		listError.add(Constraints.CODE_3007);
		
		///==validate post data== 
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "post_id=&content=");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
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
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "post_id=1000000&content=Content comment for test");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
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
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "post_id="+post.getId()+"&content=Content comment for test");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
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
		post = postService.findByTitle("Title 2 le.huy");
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "post_id="+post.getId()+"&content=Content comment for test");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_210, jsonObject.get(Constraints.CODE));

	}
	
	
	/**
	 *  updateComment post for unit testing updateComment in commentcontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void updateComment() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_COMMENT_URL+"/update");
		String access_key = login("le.tung","abcd1234");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(200);
		listError.add(Constraints.CODE_3001);
		listError.add(Constraints.CODE_3005);
		listError.add(Constraints.CODE_3006);
		
		///==validate post data== 
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "comment_id=&content=");
		//post data to add user api
		respone = clientRequest.put(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
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


		//==check invalid comment==
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "comment_id=10000&content=Comment for test");
		//post data to add user api
		respone = clientRequest.put(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_3000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_3006, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==check valid permission on comment==
		Post post = postService.findByTitle("Title 1 nguyen.tung");
		System.out.println("json post ="+new JSONObject(post));
//		Set<Comment> setComment = post.getComments();
//		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
//				+ "comment_id=100000&content=");
//		//post data to add user api
//		respone = clientRequest.post(String.class);
//		//get json object from response
//		jsonObject = new JSONObject(respone.getEntity().toString());
//		//get meta json object
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare response error code with list error
//		assertEquals(Constraints.CODE_3000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		
//		//compare response error code with list error
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(Constraints.CODE_3007, jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE));
//		}
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
		
		Calendar date = Calendar.getInstance();
		Comment comment = null;
		Post post = null;
		
		post = new Post("Title 1 le.uay","Content 1 le.uay",date.getTime(),date.getTime()
				,1,new User(1, "le.uay", 1));
		postService.add(post);
		post = postService.findByTitle("Title 1 le.uay");
		comment = new Comment("Post title 1 le.uay comment 1 le.uay", date.getTime(), date.getTime(), new User(1, "le.uay", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.uay comment 2 le.huy", date.getTime(), date.getTime(), new User(2, "le.huy", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.uay comment 3 le.uay", date.getTime(), date.getTime(), new User(1, "le.uay", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.uay comment 4 nguyen.tung", date.getTime(), date.getTime(), new User(3, "nguyen.tung", 0), post);
		commentService.add(comment);
		
		
		date.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 le.uay","Content 2 le.uay",date.getTime(),date.getTime()
				,0,new User(1, "le.uay", 1));
		postService.add(post);
		post = postService.findByTitle("Title 2 le.uay");
		comment = new Comment("Post title 2 le.uay comment 1 le.huy", date.getTime(), date.getTime(), new User(2, "le.huy", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.uay comment 2 le.uay", date.getTime(), date.getTime(), new User(1, "le.uay", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.uay comment 3 nguyen.tung", date.getTime(), date.getTime(), new User(3, "nguyen.tung", 0), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.uay comment 4 le.uay", date.getTime(), date.getTime(), new User(1, "le.uay", 1), post);
		commentService.add(comment);

		
		date.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 1 le.huy","Content 1 le.huy",date.getTime(),date.getTime()
				,0,new User(2, "le.huy", 0));
		postService.add(post);
		post = postService.findByTitle("Title 1 le.huy");
		comment = new Comment("Post title 1 le.huy comment 1 le.huy", date.getTime(), date.getTime(), new User(2, "le.huy", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.huy comment 2 le.uay", date.getTime(), date.getTime(), new User(1, "le.uay", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 1 le.huy comment 3 nguyen.tung", date.getTime(), date.getTime(), new User(3, "nguyen.tung", 0), post);
		commentService.add(comment);
		
		date.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 le.huy","Content 2 le.huy",date.getTime(),date.getTime()
				,1,new User(2, "le.huy", 1));
		postService.add(post);
		post = postService.findByTitle("Title 2 le.huy");
		comment = new Comment("Post title 2 le.huy comment 1 nguyen.tung", date.getTime(), date.getTime(), new User(3, "nguyen.tung", 0), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.huy comment 2 le.uay", date.getTime(), date.getTime(), new User(1, "le.uay", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Post title 2 le.huy comment 3 le.huy", date.getTime(), date.getTime(), new User(2, "le.huy", 1), post);
		commentService.add(comment);
		
		
		date.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 1 nguyen.tung","Content 1 nguyen.tung",date.getTime(),date.getTime()
				,0,new User(3, "nguyen.tung", 0));
		postService.add(post);
		post = postService.findByTitle("Title 1 nguyen.tung");
		comment = new Comment("Title 1 nguyen.tung le.huy comment 1 nguyen.tung", date.getTime(), date.getTime(), new User(3, "nguyen.tung", 0), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Title 1 nguyen.tung le.huy comment 2 le.uay", date.getTime(), date.getTime(), new User(1, "le.uay", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Title 1 nguyen.tung le.huy comment 3 le.huy", date.getTime(), date.getTime(), new User(2, "le.huy", 1), post);
		commentService.add(comment);
		
		date.add(Calendar.HOUR_OF_DAY, 1);
		post = new Post("Title 2 nguyen.tung","Content 2 nguyen.tung",date.getTime(),date.getTime()
				,1,new User(3, "nguyen.tung", 0));
		postService.add(post);
		post = postService.findByTitle("Title 2 nguyen.tung");
		comment = new Comment("Title 2 nguyen.tung le.huy comment 1 le.uay", date.getTime(), date.getTime(), new User(1, "le.uay", 1), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Title 2 nguyen.tung le.huy comment 2 nguyen.tung", date.getTime(), date.getTime(), new User(3, "nguyen.tung", 0), post);
		commentService.add(comment);
		date.add(Calendar.HOUR_OF_DAY, 1);
		comment = new Comment("Title 2 nguyen.tung le.huy comment 3 le.huy", date.getTime(), date.getTime(), new User(2, "le.huy", 1), post);
		commentService.add(comment);
		
		User user = new User();
		user.setUsername("le.tung");
		user.setPassword(EncrypUtils.encrypData("abcd1234"));
		user.setFirstname("tung");
		user.setLastname("le");
		user.setEmail("le.tung@mulodo.com");
		user.setStatus(1);
		Calendar dateUser = Calendar.getInstance();
		user.setCreated_at(dateUser.getTime());
		user.setModified_at(dateUser.getTime());
		userService.add(user);
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
