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

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.EncrypUtils;

/**
 * The UserControllerUnitTest use create unit test of user controller
 * 
 * @author UayLU
 * 
 */
@SuppressWarnings("deprecation")
public class UserControllerUnitTest {
	
	//declare root user url
	private final String ROOT_USER_URL = "http://localhost:8080/APIMiniBlog/api/users";
	//declare ApplicationContext for getting bean from applicationContext.xml
	private final ApplicationContext appContext = 
	    	  new ClassPathXmlApplicationContext("classpath:/WEB-INF/applicationContext.xml");
	//set seesion bean in applicationContext.xml to userservice
	private final UserService userService = (UserService) appContext.getBean("userService");
	private ClientResponse<String> respone = null;
	private ClientRequest clientRequest = null;
	
	/**
	 *  add_new user for unit testing addNew in usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void add_new() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_USER_URL+"/add");
		
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
		
		///==validate post data== 
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=&password=&firstname="
				+ "&lastname=&email=le.thanh");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//compare status return with status in list error
		assertEquals(true, listError.contains(respone.getStatus()));
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
		
		//==check valid user in database===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=le.tung&password=abcd1234&firstname=uay"
				+ "&lastname=le&email=le.thanh@mulodo.com");
		//post data to add user api
		respone = clientRequest.post(String.class);
		jsonObject = new JSONObject(respone.getEntity().toString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(respone.getStatus()));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//==check valid email in database==
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=le.thanh&password=abcd1234&firstname=uay"
				+ "&lastname=le&email=le.tung@mulodo.com");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(respone.getStatus()));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with 
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//==add success==
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=le.thanh&password=abcd1234&firstname=thanh"
				+ "&lastname=le&email=le.thanh@mulodo.com");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with with success status
		assertEquals(Constraints.CODE_200, jsonObject.get(Constraints.CODE));
		assertEquals(true, listError.contains(respone.getStatus()));
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
	@Test
	public void login() throws Exception{
		
		clientRequest = new ClientRequest(ROOT_USER_URL+"/login");
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
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=&password=");
		//post data to add user api
		respone = clientRequest.post(String.class);
		assertEquals(true, listError.contains(respone.getStatus()));
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
		
		//==check valid user in database===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=le.tung&password=abcd1234");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(respone.getStatus()));
		String access_key = (String) respone.getHeaders().get(Constraints.ACCESS_KEY).get(0);
		assertNotNull(access_key);
		
		//==check already login==
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=le.tung&password=abcd1234");
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(respone.getStatus()));
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
	@Test
	public void logout() throws Exception{
		
		clientRequest = new ClientRequest(ROOT_USER_URL+"/logout");
		
		//post data to add user api
		respone = clientRequest.put(String.class);
		assertEquals(401, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		
		//compare response error code with list error
		assertEquals(Constraints.CODE_1000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_1002, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==logout===
		ClientRequest clientRequestLogin = new ClientRequest(ROOT_USER_URL+"/login");
		clientRequestLogin.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=le.tung&password=abcd1234");
		//post data to add user api
		respone = clientRequestLogin.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//get access_key from login
		String access_key = (String) respone.getHeaders().get(Constraints.ACCESS_KEY).get(0);
		assertNotNull(access_key);
		
		//set access key to header and send to logout api
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		respone = clientRequest.put(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
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
		
		clientRequest = new ClientRequest(ROOT_USER_URL+"/update");
		
		
		String access_key = login_for_test();
	
		
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
		
		///==validate post data== 
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "password=&firstname="
				+ "&lastname=&email=le.tung&status=");
		//set access key to header and send to logout api
				clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//post data to add user api
		respone = clientRequest.put(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
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
		
		//==check invalid password===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "password=abcd12345&firstname=tung"
				+ "&lastname=le&email=le.huy@mulodo.com&status=1");
		//post data to add user api
		respone = clientRequest.put(String.class);
		jsonObject = new JSONObject(respone.getEntity().toString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2012, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==check valid email in database but exclusion match with current user===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "password=abcd1234&firstname=tung"
				+ "&lastname=le&email=le.huy@mulodo.com&status=1");
		//post data to add user api
		respone = clientRequest.put(String.class);
		jsonObject = new JSONObject(respone.getEntity().toString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2009, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==update user===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "password=abcd1234&firstname=uay.le"
				+ "&lastname=le.u&email=le.uay2@mulodo.com&status=0");
		//post data to add user api
		respone = clientRequest.put(String.class);
		jsonObject = new JSONObject(respone.getEntity().toString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		assertEquals(Constraints.CODE_201, jsonObject.get(Constraints.CODE));	
	}
	
	/**
	 *  get user info for unit testing get user info in usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void get_user_info() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_USER_URL+"/getUserInfo");
		
		String access_key = login_for_test();
	
		
		//set access key to header and send to logout api
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//get data from getUserInfo api
		respone = clientRequest.get(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.DATA);
		
		assertNotNull(jsonObject.getJSONObject(Constraints.USER));
		jsonObject = jsonObject.getJSONObject(Constraints.USER);
		assertNotNull(jsonObject.get("id"));
		assertNotNull(jsonObject.get("username"));
		assertNotNull(jsonObject.get("firstname"));
		assertNotNull(jsonObject.get("lastname"));
		assertNotNull(jsonObject.get("created_at"));
		assertNotNull(jsonObject.get("modified_at"));
		
	}

	
	/**
	 *  find_user_by_name for unit testing find_user_by_name in usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void find_user_by_name() throws Exception {
		
		clientRequest = null;
		
		String access_key = login_for_test();
	
		//==validate not null name===
		//set data to path
		clientRequest = new ClientRequest(ROOT_USER_URL+"/findByName?name=");
		//set access key to header and send to logout api
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//get data from getUserInfo api
		respone = clientRequest.get(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		jsonObject.getJSONArray(Constraints.MESSAGES);
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2014, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==get list username active have username, firstname or lastname like name===
		//set data to path
		clientRequest = new ClientRequest(ROOT_USER_URL+"/findByName?name=le.");
		//set access key to header and send to logout api
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//get data from getUserInfo api
		respone = clientRequest.get(String.class);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.DATA);
		
		assertNotNull(jsonObject.getJSONArray(Constraints.LIST_USER));
		assertEquals(true, jsonObject.getJSONArray(Constraints.LIST_USER).length() > 0);
		assertNotNull(jsonObject.getJSONArray(Constraints.LIST_USER).get(0));
		
	}
	
	/**
	 *  change_password for unit testing change password in usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Test
	public void change_password() throws Exception {
		
		clientRequest = new ClientRequest(ROOT_USER_URL+"/chagnePassword");
		

		//==Create list error==
		List<Integer> listError = new ArrayList<Integer>();
		listError.add(200);
		listError.add(Constraints.CODE_2002);
		listError.add(Constraints.CODE_2013);
		listError.add(Constraints.CODE_2012);
		listError.add(Constraints.CODE_2016);
		listError.add(Constraints.CODE_2000);
		
		String access_key = login_for_test();
	
		//==validate password===
		//set access key to header and send to chagne password api
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//==check valid email in database but exclusion match with current user===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "oldPassword=&newPassword=");
		//post data to add user api
		respone = clientRequest.put(String.class);
		JSONObject jsonObject = new JSONObject(respone.getEntity().toString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//set access key to header and send to chagne password api
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//==check valid email in database but exclusion match with current user===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "oldPassword=abcd1234&newPassword=abc");
		//post data to add user api
		respone = clientRequest.put(String.class);
		jsonObject = new JSONObject(respone.getEntity().toString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
		
		//==invlaid current password===
		//set access key to header and send to chagne password api
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//==check valid email in database but exclusion match with current user===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "oldPassword=abcd12345&newPassword=4321dcbaa");
		//post data to add user api
		respone = clientRequest.put(String.class);
		System.out.println(respone.getEntity().toString());
		jsonObject = new JSONObject(respone.getEntity().toString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		assertEquals(Constraints.CODE_2000, jsonObject.get(Constraints.CODE));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		//check code invlaid current password
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(Constraints.CODE_2016, jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE));
		}
		
		//==chagne password success===
		//set access key to header and send to chagne password api
		clientRequest.header(Constraints.ACCESS_KEY, access_key);
		//==check valid email in database but exclusion match with current user===
		clientRequest.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "oldPassword=abcd1234&newPassword=4321dcba");
		//post data to add user api
		respone = clientRequest.put(String.class);
		System.out.println(respone.getEntity().toString());
		jsonObject = new JSONObject(respone.getEntity().toString());
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(Constraints.CODE_200, respone.getStatus());
		assertEquals(Constraints.CODE_203, jsonObject.get(Constraints.CODE));
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
	@Before
	public void  setUpData() throws Exception{
		User user = new User();
		user.setUsername("le.tung");
		user.setPassword(EncrypUtils.encrypData("abcd1234"));
		user.setFirstname("tung");
		user.setLastname("le");
		user.setStatus(1);
		user.setEmail("le.tung@mulodo.com");
		Calendar date = Calendar.getInstance();
		user.setCreated_at(date.getTime());
		user.setModified_at(date.getTime());
		this.userService.add(user);
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
		this.userService.deleteByUsername("le.tung");
	}
	
	/**
	 *  login use for return access_key for api require authentication  usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	private String login_for_test() throws Exception{
		ClientRequest clientRequestLogin = new ClientRequest(ROOT_USER_URL+"/login");
		clientRequestLogin.body(MediaType.APPLICATION_FORM_URLENCODED,""
				+ "username=le.tung&password=abcd1234");
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