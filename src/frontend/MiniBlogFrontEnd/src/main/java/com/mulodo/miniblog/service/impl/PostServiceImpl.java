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
import com.mulodo.miniblog.constraints.ConstraintsMessage;
import com.mulodo.miniblog.object.ResponseData;
import com.mulodo.miniblog.service.PostService;

@Service("PostServiceImpl")
public class PostServiceImpl implements PostService {

	private RestTemplate restTemplate;
	private ResponseEntity<ResponseData> responseEntity;
	private Charset charset = Charset.forName("UTF-8");
	private MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", charset);

	@Override
	public ResponseData getAllPost(String accessKey, int pageNum,
			String description) {

		restTemplate = new RestTemplate();
		
		ResponseData responseData = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		try {

			responseEntity = restTemplate.exchange(Constraints.ROOT_URL
					+ "posts/getAllPost?pageNum=" + pageNum + "&description="
							+ description, HttpMethod.GET, request,
					ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();

		} catch (HttpClientErrorException ex) {
			responseData = getMetaWhenUnAuthor(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return responseData;
	}

	@Override
	public ResponseData addPost(String accessKey, String title, String content) {

		restTemplate = new RestTemplate();

		ResponseData responseData = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		String requestBody = "title=" + title + "&content=" + content;
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("title", title);
		map.add("content", content); 
		
//		HttpEntity<String> request = new HttpEntity<String>(requestBody,
//				headers);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		System.out.println("requestBody = "+requestBody);
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		try {

//			responseEntity = restTemplate
//					.exchange(Constraints.ROOT_URL + "posts/add",
//							HttpMethod.POST, request, ResponseData.class);
			responseEntity = restTemplate.postForEntity(Constraints.ROOT_URL+"posts/add", request, ResponseData.class); 
			responseData = new ResponseData();
			responseData = responseEntity.getBody();

		} catch (HttpClientErrorException ex) {
			responseData = getMetaWhenUnAuthor(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return responseData;
	}

	@Override
	public ResponseData deletePost(String accessKey, int postId) {

		restTemplate = new RestTemplate();

		ResponseData responseData = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		HttpEntity<String> request = new HttpEntity<String>(headers);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		try {
			responseEntity = restTemplate.exchange(Constraints.ROOT_URL
					+ "posts/delete/" + postId, HttpMethod.DELETE, request,
					ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();
		} catch (HttpClientErrorException ex) {
			responseData = getMetaWhenUnAuthor(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return responseData;
	}

	@Override
	public ResponseData editPost(String accessKey, int postId, String title,
			String content, int status) {

		restTemplate = new RestTemplate();

		ResponseData responseData = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("title", title);
		map.add("content", content); 
		
//		HttpEntity<String> request = new HttpEntity<String>(requestBody,
//				headers);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		try {

			responseEntity = restTemplate.exchange(Constraints.ROOT_URL
					+ "posts/update", HttpMethod.PUT, request,
					ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();

		} catch (HttpClientErrorException ex) {
			responseData = getMetaWhenUnAuthor(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (status != 0
				&& responseData.getMeta() != null
				&& responseData.getMeta().getCode() == ConstraintsMessage.CODE_207
						.getKey()) {
			responseData = activeDeactivePost(accessKey, postId, status);
		}

		return responseData;
	}

	@Override
	public ResponseData activeDeactivePost(String accessKey, int postId,
			int status) {

		restTemplate = new RestTemplate();

		ResponseData responseData = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		String requestBody = "id=" + postId + "&status=" + status;

		HttpEntity<String> request = new HttpEntity<String>(requestBody,
				headers);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		try {

			responseEntity = restTemplate.exchange(Constraints.ROOT_URL
					+ "posts/activeDeactive", HttpMethod.PUT, request,
					ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();

		} catch (HttpClientErrorException ex) {
			responseData = getMetaWhenUnAuthor(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseData;
	}
	
	@Override
	public ResponseData getAllPostForUser(String accessKey, int pageNum,
			int userId, String description) {
		
		restTemplate = new RestTemplate();

		ResponseData responseData = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		try {

			responseEntity = restTemplate.exchange(Constraints.ROOT_URL
					+ "posts/getPostForUser?pageNum=" + pageNum + "&user_id="
					+ userId +"&description="+description, HttpMethod.GET, request,
					ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();

		} catch (HttpClientErrorException ex) {
			responseData = getMetaWhenUnAuthor(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return responseData;
	}


	@Override
	public ResponseData getPostInfor(String accessKey, int postId) {
		restTemplate = new RestTemplate();

		ResponseData responseData = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		try {

			responseEntity = restTemplate.exchange(Constraints.ROOT_URL
					+ "posts/getPostInfo?id=" + postId, HttpMethod.GET, request,
					ResponseData.class);
			responseData = new ResponseData();
			responseData = responseEntity.getBody();

		} catch (HttpClientErrorException ex) {
			responseData = getMetaWhenUnAuthor(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseData;
	}
	
	private ResponseData getMetaWhenUnAuthor(HttpClientErrorException ex) {
		String responseString = null;
		ResponseData responseData = null;
		if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED
				|| ex.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {

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
		} else {
			return null;
		}
	}

}
