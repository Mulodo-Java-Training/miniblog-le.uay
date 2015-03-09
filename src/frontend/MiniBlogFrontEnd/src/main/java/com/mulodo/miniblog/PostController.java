package com.mulodo.miniblog;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulodo.miniblog.constraints.Constraints;
import com.mulodo.miniblog.constraints.ConstraintsCommentError;
import com.mulodo.miniblog.constraints.ConstraintsMessage;
import com.mulodo.miniblog.constraints.ConstraintsPostError;
import com.mulodo.miniblog.constraints.ConstraintsUserError;
import com.mulodo.miniblog.object.Message;
import com.mulodo.miniblog.object.ResponseData;
import com.mulodo.miniblog.service.CommentService;
import com.mulodo.miniblog.service.PostService;

@Controller
public class PostController {
	
	
	@Autowired
	@Qualifier("PostServiceImpl")
	private PostService postService;
	
	@Autowired
	@Qualifier("CommentServiceImpl")
	private CommentService commentService;

	@RequestMapping(value = "/mainpage/getAllPost", method = RequestMethod.GET)
	@ResponseBody
	public String getAllPost(@RequestParam("pageNum") String pageNum,
			@RequestParam("description") String description,
			HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);
		
		ResponseData responseData = null;

		try{
			responseData = postService.getAllPost(accessKey,
					Integer.parseInt(pageNum), description);
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsPostError.CODE_2500.getValue());
			return jsonObject.toString();
		}
		if (responseData != null && responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsPostError.CODE_2500
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE, ConstraintsPostError.CODE_2500.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1000.getValue());
			}
		} else if (responseData.getData() != null) {
			jsonObject = new JSONObject(responseData.getData());
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/mainpage/userpage", method = RequestMethod.GET)
	public String userPage(HttpServletRequest request){
	
		return "userpage";
	}
	
	@RequestMapping(value = "/mainpage/getAllPostForUser", method = RequestMethod.GET)
	@ResponseBody
	public String getAllPostForUser(@RequestParam("pageNum") String pageNum,
			@RequestParam("userId") String userId, @RequestParam("description") String description,
			HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = null;

		try{
			responseData = postService.getAllPostForUser(accessKey,
					Integer.parseInt(pageNum), Integer.parseInt(userId), description);
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsPostError.CODE_2500.getValue());
			return jsonObject.toString();
		}
		if (responseData != null && responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsPostError.CODE_2500
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE, ConstraintsPostError.CODE_2500.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1000.getValue());
			}
		} else if (responseData.getData() != null) {
			jsonObject = new JSONObject(responseData.getData());
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/mainpage/addpost", method = RequestMethod.GET)
	public String addPost(HttpServletRequest request) {

		return "addpost";
 	}

	@RequestMapping(value = "/mainpage/addpost", method = RequestMethod.POST)
	@ResponseBody
	public String addPost(@RequestParam("title") String title,
			@RequestParam("content") String content, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = null;

		try{
			responseData = postService.addPost(accessKey, title,
					content);
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsPostError.CODE_2500.getValue());
			return jsonObject.toString();
		}
		if (responseData != null && responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsMessage.CODE_206
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsMessage.CODE_206.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsPostError.CODE_2500
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsPostError.CODE_2500.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
			}
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/mainpage/post/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deletePost(@RequestParam("postId") String postId,
			HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);
		
		ResponseData responseData = null;

		try{
			responseData = postService.deletePost(accessKey, Integer.parseInt(postId));
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsPostError.CODE_2500.getValue());
			return jsonObject.toString();
		}
		if (responseData != null && responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsMessage.CODE_209
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsMessage.CODE_209.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsPostError.CODE_2500
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsPostError.CODE_2500.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
			}
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/mainpage/post/edit", method = RequestMethod.POST)
	@ResponseBody
	public String editPost(@RequestParam("postId") String postId,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("status") String status, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		Boolean isError = false;
		ResponseData responseData = null;
		try{
			responseData = postService.editPost(accessKey,
					Integer.parseInt(postId), title, content,
					Integer.parseInt(status));
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsPostError.CODE_2500.getValue());
			return jsonObject.toString();
		}
		System.out.println("id= "+postId);
		System.out.println("json = "+new JSONObject(responseData));
		if (responseData != null && responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsMessage.CODE_207
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsMessage.CODE_207.getValue());
			} if (responseData.getMeta().getCode() == ConstraintsMessage.CODE_208
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsMessage.CODE_207.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsPostError.CODE_2500
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsPostError.CODE_2500.getValue());
				isError = true;
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
				isError = true;
			}
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		
		if(isError){
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsPostError.CODE_2501.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsPostError.CODE_2501.getValue());
				}else if(ms.getCode() == ConstraintsPostError.CODE_2509.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsPostError.CODE_2509.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_1002.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1002.getValue());
				}
			}
		}
		
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/mainpage/detailpost", method = RequestMethod.GET)
	public String detailPost(HttpServletRequest request) {

		return "detailpost";
 	}
	
	@RequestMapping(value = "/mainpage/editpost", method = RequestMethod.GET)
	public String editPost(HttpServletRequest request) {

		return "editpost";
 	}

	
	@RequestMapping(value = "/mainpage/getPostInfo", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	public String getPostInfo(@RequestParam("postId") String postId, HttpServletRequest request) {
		
		
		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonDataPost = null;
		JSONObject jsonDataPostComment = null;
		ObjectMapper objectMapper = new ObjectMapper();
		Boolean isError = false;

		String accessKey = (String) session.getAttribute(Constraints.ACCESS_KEY);
		
		ResponseData responseData = null;
		
		
		
		try{
			responseData = postService.getPostInfor(accessKey,
					Integer.parseInt(postId));
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsPostError.CODE_2500.getValue());
			return jsonObject.toString();
		}
		
		if (responseData != null && responseData.getMeta() != null) {
			isError = true;
		} else if(responseData != null && responseData.getData() != null){
			isError = false;
			
			try {
				jsonDataPost = new JSONObject(objectMapper.writeValueAsString(responseData.getData())); 
			}catch (Exception e) {
				jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
			}
			jsonDataPost.remove("limitRow");
			jsonDataPost.remove("listComment");
			jsonDataPost.remove("listPost");
			jsonDataPost.remove("listUser");
			jsonDataPost.remove("pageNum");
			jsonDataPost.remove("totalPage");
			jsonDataPost.remove("totalRow");
			jsonDataPost.remove("user");
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		
		if(isError){
			if (responseData.getMeta().getCode() == ConstraintsPostError.CODE_2500
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsPostError.CODE_2500.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
			}
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsPostError.CODE_2509.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsPostError.CODE_2509.getValue());
				}else if(ms.getCode() == ConstraintsPostError.CODE_2501.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsPostError.CODE_2501.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_1002.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1002.getValue());
				}
			}
		}else{
			try{
				responseData = commentService.getAllCommentForPost(accessKey,
						Integer.parseInt(postId));
			}catch(NumberFormatException ex){
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsCommentError.CODE_3000.getValue());
				return jsonObject.toString();
			}
			if (responseData != null && responseData.getMeta() != null) {
				isError = true;
			} else if(responseData != null && responseData.getData() != null){
				isError = false;
				try {
					jsonDataPostComment = new JSONObject(objectMapper.writeValueAsString(responseData.getData())); 
				}catch (Exception e) {
					jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
				}
				
			} else {
				jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
			}
		}
		
		if(isError){
			if (responseData.getMeta().getCode() == ConstraintsCommentError.CODE_3000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsCommentError.CODE_3000.getValue());
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
			}
		}else{
			
			System.out.println("is null="+jsonDataPostComment.getJSONArray("listComment").isNull(0));
			if(!jsonDataPostComment.getJSONArray("listComment").isNull(0)){
				
				jsonDataPost.getJSONObject("post").put("comments", jsonDataPostComment.getJSONArray("listComment"));
			}
			
			jsonObject.put("data", jsonDataPost);
			System.out.println("json post infor = "+jsonObject.toString());
		}
		
		return jsonObject.toString();
 	}

}
