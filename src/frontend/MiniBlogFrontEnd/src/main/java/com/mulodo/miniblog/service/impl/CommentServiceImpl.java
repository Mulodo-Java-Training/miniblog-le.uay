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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulodo.miniblog.constraints.Constraints;
import com.mulodo.miniblog.object.ResponseData;
import com.mulodo.miniblog.service.CommentService;

@Service("CommentServiceImpl")
public class CommentServiceImpl implements CommentService {
	
	private RestTemplate restTemplate = null;
	private ResponseEntity<ResponseData> responseEntity;
	private Charset charset = Charset.forName("UTF-8");
	private MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", charset);

	@Override
	public ResponseData getAllCommentForPost(String accessKey, int postId) {
		
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
					+ "comments/getByPost?post_id=" + postId, HttpMethod.GET, request,
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
	public ResponseData deleteComment(String accessKey, int commentId) {

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
					+ "comments/delete/" + commentId, HttpMethod.DELETE, request,
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
	public ResponseData addComment(String accessKey, int postId,
			String content) {
		
		restTemplate = new RestTemplate();

		ResponseData responseData = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		String requestBody = "post_id=" + postId + "&content=" + content;

		HttpEntity<String> request = new HttpEntity<String>(requestBody,
				headers);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		try {

			responseEntity = restTemplate
					.exchange(Constraints.ROOT_URL + "comments/add",
							HttpMethod.POST, request, ResponseData.class);
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
	public ResponseData editComment(String accessKey, int commentId,
			String content) {
		
		restTemplate = new RestTemplate();

		ResponseData responseData = null;

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constraints.ACCESS_KEY, accessKey);
		headers.setContentType(mediaType);

		String requestBody = "comment_id=" + commentId + "&content="
				+ content;

		HttpEntity<String> request = new HttpEntity<String>(requestBody,
				headers);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		try {

			responseEntity = restTemplate.exchange(Constraints.ROOT_URL
					+ "comments/update", HttpMethod.PUT, request,
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
