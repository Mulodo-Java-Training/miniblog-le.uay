/* 
 * UserControllerUnitTest.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.unitest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;
import org.junit.Test;

import com.mulodo.miniblog.contraints.Constraints;

/**
 * The UserControllerUnitTest use create unit test of user controller
 * 
 * @author UayLU
 * 
 */
@SuppressWarnings("deprecation")
public class UserControllerUnitTest {
	
	private String ROOT_USER_URL = "http://localhost:8080/test/api/users";
	
	@Test
	public void add_new() throws Exception {
		
		ClientRequest clientRequest = new ClientRequest(ROOT_USER_URL+"/add");
		ClientResponse<String> respone;
		
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
				+ "&lastname=&email=le.uay");
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
				+ "username=le.uay&password=abcd1234&firstname=uay"
				+ "&lastname=le&email=le.uay@mulodo.com");
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
				+ "username=le.uay2&password=abcd1234&firstname=uay"
				+ "&lastname=le&email=le.uay@mulodo.com");
		//post data to add user api
		respone = clientRequest.post(String.class);
		//get json object from response
		jsonObject = new JSONObject(respone.getEntity().toString());
		//get meta json object
		jsonObject = jsonObject.getJSONObject(Constraints.META);
		//compare status return with status in list error
		assertEquals(true, listError.contains(respone.getStatus()));
		assertNotNull(jsonObject.getJSONArray(Constraints.MESSAGES));
		
		//compare response error code with list error
		for(int i = 0; i < jsonObject.getJSONArray(Constraints.MESSAGES).length(); i++){
			assertEquals(true, listError.contains(jsonObject.getJSONArray(Constraints.MESSAGES)
					.getJSONObject(i).get(Constraints.CODE)));
		}
	}
	
	@Test
	public void login() throws Exception{
		
		ClientRequest clientRequest = new ClientRequest(ROOT_USER_URL+"/login");
		//==Create list error==
		ClientResponse<String> respone;
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
				+ "username=le.uay&password=abcd1234");
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
				+ "username=le.uay&password=abcd1234");
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

}
