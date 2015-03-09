package com.mulodo.miniblog.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulodo.miniblog.constraints.Constraints;
import com.mulodo.miniblog.object.ResponseData;
import com.mulodo.miniblog.service.UserService;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

	
	private RestTemplate restTemplate;
	private ResponseEntity<ResponseData> responseEntity;
	private Charset charset = Charset.forName("UTF-8");
	private MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", charset);
	
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

	@Override
	public ResponseData getUserInfo(String accessKey) {

		restTemplate = new RestTemplate();
		
		ResponseData responseData = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		try{
			responseEntity = restTemplate.exchange(Constraints.ROOT_URL+"users/getUserInfo", HttpMethod.GET, request, ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();
		}catch(HttpClientErrorException ex){
			responseData = getMetaWhenUnAuthor(ex);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return responseData;
	}
	
	@Override
	public ResponseData editUserInfo(String firstname,
			String lastname, String email, String password, String status, String accessKey) {
		
		restTemplate = new RestTemplate();
		
		ResponseData responseData = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		String requestBody = "password="+password+"&firstname="+firstname+"&lastname="+lastname+""
				+ "&email="+email+"&status="+status;
		
		HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
		
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		try{
			
			responseEntity = restTemplate.exchange(Constraints.ROOT_URL+"users/update", HttpMethod.PUT, request, ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();
			
		}catch(HttpClientErrorException ex){
			responseData = getMetaWhenUnAuthor(ex);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return responseData;
	}

	@Override
	public ResponseData changePassword(String currentPassword,
			String newPassword, String accessKey) {
		
		
		restTemplate = new RestTemplate();
		
		ResponseData responseData = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		String requestBody = "oldPassword="+currentPassword+"&newPassword="+newPassword;
		
		HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
		
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		try{
			
			responseEntity = restTemplate.exchange(Constraints.ROOT_URL+"users/chagnePassword", HttpMethod.PUT, request, ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();
			
		}catch(HttpClientErrorException ex){
			responseData = getMetaWhenUnAuthor(ex);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return responseData;
	}
	
	
	@Override
	public ResponseData searchUser(String name, String accessKey) {
		restTemplate = new RestTemplate();
		
		ResponseData responseData = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		try{
			
			responseEntity = restTemplate.exchange(Constraints.ROOT_URL+"users/findByName?name="+name, HttpMethod.GET, request, ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();
			
		}catch(HttpClientErrorException ex){
			responseData = getMetaWhenUnAuthor(ex);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return responseData;
	}
	
	private ResponseData getMetaWhenUnAuthor(HttpClientErrorException ex){
		String responseString = null;
		ResponseData responseData= null;
		if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED || ex.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {

            responseString = ex.getResponseBodyAsString();

            ObjectMapper mapper = new ObjectMapper();

            try {
				responseData = mapper.readValue(responseString,
						ResponseData.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            return responseData;
        }else{
        	return null;
        }
		
	}


}
