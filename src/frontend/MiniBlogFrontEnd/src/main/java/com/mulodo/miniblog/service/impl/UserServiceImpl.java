package com.mulodo.miniblog.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.mulodo.miniblog.constraints.Constraints;
import com.mulodo.miniblog.object.ResponseData;
import com.mulodo.miniblog.service.UserService;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

	
	private RestTemplate restTemplate;
	private ResponseEntity<ResponseData> responseEntity;
	
	@Override
	public ResponseData register(String username, String firstname,
			String lastname, String email, String password) {
		
		restTemplate = new RestTemplate();
		
		ResponseData responseData = null;
		MultiValueMap<String, String> postUser = new LinkedMultiValueMap<String, String>();
		
		postUser.add("username", username);
		postUser.add("firstname", firstname);
		postUser.add("lastname", lastname);
		postUser.add("email", email);
		postUser.add("password", password);
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		try{
			responseEntity = restTemplate.postForEntity(Constraints.ROOT_URL+"users/add", postUser, ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return responseData;
	}

	@Override
	public ResponseData login(String username, String password) {
		
		restTemplate = new RestTemplate();
		
		ResponseData responseData = null;
		MultiValueMap<String, String> postUser = new LinkedMultiValueMap<String, String>();
		
		postUser.add("username", username);
		postUser.add("password", password);
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		try{
			responseEntity = restTemplate.postForEntity(Constraints.ROOT_URL+"users/login", postUser, ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();
			String header = responseEntity.getHeaders().getFirst(Constraints.ACCESS_KEY);
			responseData.setHeader(header);
			System.out.println("header = "+header);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return responseData;
	}

}
