/* 
 * UserControllerUnitTest.java 
 *  
 * 1.0
 * 
 * 2015/02/09
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.unitest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.portlet.MockRenderRequest;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.rest.controller.UserController;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.EncrypUtils;

/**
 * The UserControllerUnitTest use create unit test of user controller
 * 
 * @author UayLU
 * 
 */
@SuppressWarnings("deprecation")
public class UserControllerUnitTest 
{
	
	//declare ApplicationContext for getting bean from applicationContext.xml
	private final ApplicationContext appContext = 
	    	  new ClassPathXmlApplicationContext("classpath:/WEB-INF/applicationContext.xml");
	//set seesion bean in applicationContext.xml to userservice
	private final UserService userService = (UserService) appContext.getBean("userService");
	Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
	POJOResourceFactory noDefaults = new POJOResourceFactory(UserController.class);
	MockHttpResponse response = null;
	
	/**
	 *  add_new user for unit testing addNew in usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
//	@Test
	public void add_new() throws Exception {
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(200);
		listError.add(Constraints.CODE_2000);
		listError.add(Constraints.CODE_2001);
		listError.add(Constraints.CODE_2002);
		listError.add(Constraints.CODE_2003);
		listError.add(Constraints.CODE_2004);
		listError.add(Constraints.CODE_2005);
		listError.add(Constraints.CODE_2009);
		listError.add(Constraints.CODE_2010);
		
		
		MockHttpRequest requestValidateData = MockHttpRequest.post("/users/add");
		response = new MockHttpResponse();
		///==validate post data== 
		requestValidateData.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestValidateData.content("username=&password=&firstname=&lastname=&email=le.thanh".getBytes());
	    dispatcher.invoke(requestValidateData, response);
		//compare status return with status in list error
		assertEquals(true, listError.contains(response.getStatus()));
		//get json object from response
		System.out.println("first ="+response.getContentAsString());
		JSONObject jsonObject = new JSONObject(response.getContentAsString());
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
	
		//==check valid user in database===
		MockHttpRequest requestValidateUsername = MockHttpRequest.post("/users/add");
		requestValidateUsername.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestValidateUsername.content(("username=le.tung&password=abcd1234&firstname=uay&lastname="
				+ "le&email=le.thanh@mulodo.com").getBytes());
		//post data to add user api
		response = new MockHttpResponse();
		dispatcher.invoke(requestValidateUsername, response);
		System.out.println("2 = "+response.getContentAsString());
		jsonObject = new JSONObject(response.getContentAsString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(response.getStatus()));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//==check valid email in database==
		MockHttpRequest requestValidateEmail = MockHttpRequest.post("/users/add");
		requestValidateEmail.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		response = new MockHttpResponse();
		requestValidateEmail.content(("username=le.thanh&password=abcd1234&firstname=uay"
				+ "&lastname=le&email=le.tung@mulodo.com").getBytes());
				
		//post data to add user api
		dispatcher.invoke(requestValidateEmail, response);
		//get json object from response
		jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		System.out.println("3 ="+response.getContentAsString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(response.getStatus()));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with 
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//==add success user in database==
		MockHttpRequest requestAddSuccess = MockHttpRequest.post("/users/add");
		requestAddSuccess.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestAddSuccess.content(("username=le.thanh&password=abcd1234&firstname=thanh"
				+ "&lastname=le&email=le.thanh@mulodo.com").getBytes());
		//post data to add user api
		response = new MockHttpResponse();
		dispatcher.invoke(requestAddSuccess, response);
		//get json object from response
		System.out.println("4 ="+response.getContentAsString());
		jsonObject = new JSONObject(response.getContentAsString());
		System.out.println(jsonObject);
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with with success status
		assertEquals(Constraints.CODE_200, jsonObject.get(Constraints.CODE));
		assertEquals(true, listError.contains(response.getStatus()));
		this.userService.deleteByUsername("le.thanh");
		
		
	}
	
	/**
	 *  login user for unit testing login in usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
//	@Test
	public void login() throws Exception{
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(Constraints.CODE_1000);
		listError.add(Constraints.CODE_1003);
		listError.add(Constraints.CODE_2000);
		listError.add(Constraints.CODE_2001);
		listError.add(Constraints.CODE_2002);
		listError.add(Constraints.CODE_204);
		listError.add(200);
		///==validate data== 
		MockHttpRequest requestValidateData = MockHttpRequest.post("/users/login");
		requestValidateData.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestValidateData.content("username=&password=".getBytes());
		//post data to add user api
		response = new MockHttpResponse();
		dispatcher.invoke(requestValidateData, response);
		assertEquals(true, listError.contains(response.getStatus()));
		//get json object from response
		JSONObject jsonObject = new JSONObject(response.getContentAsString());
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
		
		//==check valid user in database===
		MockHttpRequest requestValidateUser = MockHttpRequest.post("/users/login");
		requestValidateUser.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestValidateUser.content("username=le.tung&password=abcd1234".getBytes());
		//post data to add user api
		response = new MockHttpResponse();
		dispatcher.invoke(requestValidateUser, response);
		//get json object from response
		jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(response.getStatus()));
		String access_key = (String) response.getOutputHeaders().getFirst(Constraints.ACCESS_KEY);
		assertNotNull(access_key);
	
		
		//==check already login==
		MockHttpRequest requestLoginSuccess = MockHttpRequest.post("/users/login");
		requestLoginSuccess.content("username=le.tung&password=abcd1234".getBytes());
		requestLoginSuccess.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestLoginSuccess.addFormHeader(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		response = new MockHttpResponse();
		dispatcher.invoke(requestLoginSuccess, response);
		//get json object from response
		jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(response.getStatus()));
		assertEquals(true, listError.contains(jsonObject.get(Constraints.CODE)));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//get already login and compare with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
	}
	
	/**
	 *  logout user for unit testing logout in usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
//	@Test
	public void logout() throws Exception{
		
		MockHttpRequest requestNullAccessKey = MockHttpRequest.put("/users/logout");
		//post data to add user api
		response = new MockHttpResponse();
		dispatcher.invoke(requestNullAccessKey, response);
		assertEquals(200, response.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		assertFalse(jsonObject.has(Constraints.META));
		
		//get access_key
		String access_key = login_for_test();
		
		MockHttpRequest requestLogoutSuccess = MockHttpRequest.put("/users/logout");
		//set access key to header and send to logout api
		requestLogoutSuccess.header(Constraints.ACCESS_KEY, access_key);
		
		response = new MockHttpResponse();
		dispatcher.invoke(requestLogoutSuccess, response);
		//get json object from response
		jsonObject = new JSONObject(response.getContentAsString());
		System.out.println(" json "+jsonObject);
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare response error code with list error
		assertEquals(Constraints.CODE_205, jsonObject.get(Constraints.CODE));
	
	}
	
	
	/**
	 *  update user for unit testing update in usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void update() throws Exception {
		
		
		
		
		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(200);
		listError.add(Constraints.CODE_2002);
		listError.add(Constraints.CODE_2003);
		listError.add(Constraints.CODE_2004);
		listError.add(Constraints.CODE_2005);
		listError.add(Constraints.CODE_2012);
		listError.add(Constraints.CODE_2015);
		listError.add(Constraints.CODE_2000);
		
		String access_key = login_for_test();
		///==validate post data== 
		MockHttpRequest requestValidateData = MockHttpRequest.put("/users/update");
		requestValidateData.accept(MediaType.APPLICATION_FORM_URLENCODED);
		requestValidateData.header(Constraints.ACCESS_KEY, access_key);
		requestValidateData.content("password=&firstname=&lastname=&email=le.tung&status=".getBytes());
		//set access key to header and send to logout api
		requestValidateData.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		response = new MockHttpResponse();
		dispatcher.invoke(requestValidateData, response);
		System.out.println(response.getContentAsString());
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, response.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		
		//compare response error code with list error
		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
//		//==check invalid password===
//		MockHttpRequest requestValidatePassword = MockHttpRequest.put("/users/update");
//		requestValidateData.accept(MediaType.APPLICATION_FORM_URLENCODED);
//		requestValidateData.header(Constraints.ACCESS_KEY, access_key);
//		requestValidateData.content("password=abcd12345&firstname=tung&lastname=le&email=le.huy@mulodo.com&status=1".getBytes());
//		//set access key to header and send to logout api
//		requestValidateData.header(Constraints.ACCESS_KEY, access_key);
//		//post data to add user api
//		response = new MockHttpResponse();
//		dispatcher.invoke(requestValidateData, response);
//		jsonObject = new JSONObject(response.getContentAsString());
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(Constraints.CODE_2012, jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE));
//		}
//		
//		//==check valid email in database but exclusion match with current user===
//		MockHttpRequest requestValidEmail = MockHttpRequest.put("/users/update");
//		requestValidEmail.accept(MediaType.APPLICATION_FORM_URLENCODED);
//		requestValidEmail.header(Constraints.ACCESS_KEY, access_key);
//		requestValidEmail.content("password=abcd1234&firstname=tung&lastname=le&email=le.huy@mulodo.com&status=1".getBytes());
//		//set access key to header and send to logout api
//		requestValidEmail.header(Constraints.ACCESS_KEY, access_key);
//		//post data to add user api
//		response = new MockHttpResponse();
//		dispatcher.invoke(requestValidEmail, response);
//		jsonObject = new JSONObject(response.getContentAsString());
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(Constraints.CODE_2009, jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE));
//		}
//		
//		//==update user===
//		MockHttpRequest requestUpdateSuccess = MockHttpRequest.put("/users/update");
//		requestUpdateSuccess.accept(MediaType.APPLICATION_FORM_URLENCODED);
//		requestUpdateSuccess.header(Constraints.ACCESS_KEY, access_key);
//		requestUpdateSuccess.content("password=abcd1234&firstname=minh.tung2&lastname=minh.t&email=minh.tung2@mulodo.com&status=0".getBytes());
//		//set access key to header and send to logout api
//		requestUpdateSuccess.header(Constraints.ACCESS_KEY, access_key);
//		//post data to add user api
//		response = new MockHttpResponse();
//		dispatcher.invoke(requestUpdateSuccess, response);
//		jsonObject = new JSONObject(response.getContentAsString());
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		assertEquals(Constraints.CODE_201, jsonObject.get(Constraints.CODE));	
	}
//	
//	/**
//	 *  get user info for unit testing get user info in usercontroller
//	 *	
//	 *	
//	 *  @return 
//	 *  
//	 *  @exception Exception
//	 *	
//	 */
//	@Test
//	public void get_user_info() throws Exception {
//		
//		request = new request(ROOT_USER_URL+"/getUserInfo");
//		
//		String access_key = login_for_test();
//	
//		
//		//set access key to header and send to logout api
//		request.header(Constraints.ACCESS_KEY, access_key);
//		//get data from getUserInfo api
//		response = request.get(String.class);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		//get json object from response
//		JSONObject jsonObject = new JSONObject(response.getContentAsString());
//		//get meta json object
//		jsonObject = jsonObject.getJSONObject(Constraints.DATA);
//		
//		assertNotNull(jsonObject.getJSONObject(Constraints.USER));
//		jsonObject = jsonObject.getJSONObject(Constraints.USER);
//		assertNotNull(jsonObject.get("id"));
//		assertNotNull(jsonObject.get("username"));
//		assertNotNull(jsonObject.get("firstname"));
//		assertNotNull(jsonObject.get("lastname"));
//		assertNotNull(jsonObject.get("created_at"));
//		assertNotNull(jsonObject.get("modified_at"));
//		
//	}
//
//	
//	/**
//	 *  find_user_by_name for unit testing find_user_by_name in usercontroller
//	 *	
//	 *	
//	 *  @return 
//	 *  
//	 *  @exception Exception
//	 *	
//	 */
//	@Test
//	public void find_user_by_name() throws Exception {
//		
//		request = null;
//		
//		String access_key = login_for_test();
//	
//		//==validate not null name===
//		//set data to path
//		request = new request(ROOT_USER_URL+"/findByName?name=");
//		//set access key to header and send to logout api
//		request.header(Constraints.ACCESS_KEY, access_key);
//		//get data from getUserInfo api
//		response = request.get(String.class);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		//get json object from response
//		JSONObject jsonObject = new JSONObject(response.getContentAsString());
//		//get meta json object
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		jsonObject.getJSONArray(Constraints.MESSAGES);
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(Constraints.CODE_2014, jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE));
//		}
//		
//		//==get list username active have username, firstname or lastname like name===
//		//set data to path
//		request = new request(ROOT_USER_URL+"/findByName?name=le.");
//		//set access key to header and send to logout api
//		request.header(Constraints.ACCESS_KEY, access_key);
//		//get data from getUserInfo api
//		response = request.get(String.class);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		//get json object from response
//		jsonObject = new JSONObject(response.getContentAsString());
//		//get meta json object
//		jsonObject = jsonObject.getJSONObject(Constraints.DATA);
//		
//		assertNotNull(jsonObject.getJSONArray(Constraints.LIST_USER));
//		assertNotNull(jsonObject.getJSONArray(Constraints.LIST_USER).get(0));
//		
//	}
//	
//	/**
//	 *  change_password for unit testing change password in usercontroller
//	 *	
//	 *	
//	 *  @return 
//	 *  
//	 *  @exception Exception
//	 *	
//	 */
//	@Test
//	public void change_password() throws Exception {
//		
//		request = new request(ROOT_USER_URL+"/chagnePassword");
//		
//
//		//==Create list error==
//		List<Integer> listError = new ArrayList<Integer>();
//		listError.add(200);
//		listError.add(Constraints.CODE_2002);
//		listError.add(Constraints.CODE_2013);
//		listError.add(Constraints.CODE_2012);
//		listError.add(Constraints.CODE_2016);
//		listError.add(Constraints.CODE_2000);
//		
//		String access_key = login_for_test();
//	
//		//==validate password===
//		//set access key to header and send to chagne password api
//		request.header(Constraints.ACCESS_KEY, access_key);
//		//==check valid email in database but exclusion match with current user===
//		request.body(MediaType.APPLICATION_FORM_URLENCODED,""
//				+ "oldPassword=&newPassword=");
//		//post data to add user api
//		response = request.put(String.class);
//		JSONObject jsonObject = new JSONObject(response.getContentAsString());
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE)));
//		}
//		
//		//set access key to header and send to chagne password api
//		request.header(Constraints.ACCESS_KEY, access_key);
//		//==check valid email in database but exclusion match with current user===
//		request.body(MediaType.APPLICATION_FORM_URLENCODED,""
//				+ "oldPassword=abcd1234&newPassword=abc");
//		//post data to add user api
//		response = request.put(String.class);
//		jsonObject = new JSONObject(response.getContentAsString());
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE)));
//		}
//		
//		//==invlaid current password===
//		//set access key to header and send to chagne password api
//		request.header(Constraints.ACCESS_KEY, access_key);
//		//==check valid email in database but exclusion match with current user===
//		request.body(MediaType.APPLICATION_FORM_URLENCODED,""
//				+ "oldPassword=abcd12345&newPassword=4321dcbaa");
//		//post data to add user api
//		response = request.put(String.class);
//		jsonObject = new JSONObject(response.getContentAsString());
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
//		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
//		//check code invlaid current password
//		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
//			assertEquals(Constraints.CODE_2016, jsonObject.getJSONArray(Constraints.MESSAGES)
//					.getJSONObject(i).get(Constraints.CODE));
//		}
//		
//		//==chagne password success===
//		//set access key to header and send to chagne password api
//		request.header(Constraints.ACCESS_KEY, access_key);
//		//==check valid email in database but exclusion match with current user===
//		request.body(MediaType.APPLICATION_FORM_URLENCODED,""
//				+ "oldPassword=abcd1234&newPassword=4321dcba");
//		//post data to add user api
//		response = request.put(String.class);
//		jsonObject = new JSONObject(response.getContentAsString());
//		jsonObject = jsonObject.getJSONObject(Constraints.META);
//		//compare status return with status in list error
//		assertEquals(Constraints.CODE_200, response.getStatus());
//		assertEquals(Constraints.CODE_203, jsonObject.get(Constraints.CODE));
//	}
	
	/**
	 *  setUpData user for unit testing  usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Before
	public void  setUpData() throws Exception{
		dispatcher.getRegistry().addResourceFactory(noDefaults);
//		Calendar dateUser = Calendar.getInstance();
//		
//		User userTung = new User("le.tung",EncrypUtils.encrypData("abcd1234"), "le", "tung", 
//				"le.tung@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
//		userService.add(userTung);
//		
//		User userUay = new User("le.uay",EncrypUtils.encrypData("abcd1234"), "le", "uay", 
//				"le.uay@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
//		userService.add(userUay);
//		
//		User userHuy = new User("le.huy",EncrypUtils.encrypData("abcd1234"), "le", "huy", 
//				"le.huy@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
//		userService.add(userHuy);
//		
//		User userMinhTung = new User("minh.tung",EncrypUtils.encrypData("abcd1234"), "minh", "tung", 
//				"minh.tung@mulodo.com", dateUser.getTime(), dateUser.getTime(), 0);
//		userService.add(userMinhTung);

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
	@After
	public void clearData() throws Exception{
//		this.userService.deleteByUsername("le.tung");
//		this.userService.deleteByUsername("le.uay");
//		this.userService.deleteByUsername("le.huy");
//		this.userService.deleteByUsername("minh.tung");
		
	}
	
	/**
	 *  login use for return access_key for api require authentication  usercontroller
	 *	
	 *	
	 *  @return String
	 *  
	 *  @exception Exception
	 *	
	 */
	private String login_for_test() throws Exception{
		MockHttpRequest requestLogin = MockHttpRequest.post("/users/login");
		requestLogin.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestLogin.content("username=le.tung&password=abcd1234".getBytes());
		//post data to add user api
		response = new MockHttpResponse();
		dispatcher.invoke(requestLogin, response);
	    
		//get json object from response
		JSONObject jsonObject = new JSONObject(response.getContentAsString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//get access_key from login
		String access_key = (String) response.getOutputHeaders().getFirst(Constraints.ACCESS_KEY);
		return access_key;
	}
}