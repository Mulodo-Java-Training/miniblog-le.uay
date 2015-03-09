package com.mulodo.miniblog;

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
public class CommentController {

	@Autowired
	@Qualifier("PostServiceImpl")
	private PostService postService;
	
	@Autowired
	@Qualifier("CommentServiceImpl")
	private CommentService commentService;
	
	@RequestMapping(value = "/mainpage/comments/deleteComment", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	public String deleteComment(@RequestParam("commentId") String commentId, HttpServletRequest request) {
		
		
		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		Boolean isError = false;
		ResponseData responseData = null;
		
		System.out.println("comment id = "+commentId);
		
		try{
			responseData = commentService.deleteComment(accessKey,
					Integer.parseInt(commentId));
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsPostError.CODE_2500.getValue());
			return jsonObject.toString();
		}
		
		if (responseData != null && responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsMessage.CODE_212
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsMessage.CODE_212.getKey());
			} else if (responseData.getMeta().getCode() == ConstraintsCommentError.CODE_3000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsCommentError.CODE_3000.getValue());
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
				if(ms.getCode() == ConstraintsCommentError.CODE_3008.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsCommentError.CODE_3008.getValue());
				}else if(ms.getCode() == ConstraintsCommentError.CODE_3006.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsCommentError.CODE_3006.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_1002.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1002.getValue());
				}
			}
		}
		
		return jsonObject.toString();
 	}
	
	@RequestMapping(value = "/mainpage/comments/addcomment", method = RequestMethod.POST)
	@ResponseBody
	public String addComment(@RequestParam("postId") String postId,
			@RequestParam("content") String content, HttpServletRequest request) {

		System.out.println("post id client = "+ postId);
		System.out.println("content client = "+ content);
		
		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = null;

		try{
			responseData = commentService.addComment(accessKey, Integer.parseInt(postId),
					content);
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsCommentError.CODE_3000.getValue());
			return jsonObject.toString();
		}
		Boolean isError = false;
		if (responseData != null && responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsMessage.CODE_210
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsMessage.CODE_210.getValue());
				isError = false;
			} else if (responseData.getMeta().getCode() == ConstraintsCommentError.CODE_3000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsCommentError.CODE_3000.getValue());
				isError = true;
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
			}
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		
		if(isError){
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsCommentError.CODE_3007.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsCommentError.CODE_3007.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_1002.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1002.getValue());
				}
			}
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/mainpage/comments/editcomment", method = RequestMethod.POST)
	@ResponseBody
	public String editComment(@RequestParam("commentId") String commentId,
			@RequestParam("content") String content, HttpServletRequest request) {

		System.out.println("commentId client = "+ commentId);
		System.out.println("content client = "+ content);
		
		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = null;

		try{
			responseData = commentService.editComment(accessKey, Integer.parseInt(commentId),
					content);
		}catch(NumberFormatException ex){
			jsonObject.put(Constraints.MESSAGE,
					ConstraintsPostError.CODE_2500.getValue());
			return jsonObject.toString();
		}
		Boolean isError = false;
		if (responseData != null && responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsMessage.CODE_211
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsMessage.CODE_211.getValue());
				isError = false;
			} else if (responseData.getMeta().getCode() == ConstraintsCommentError.CODE_3000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsCommentError.CODE_3000.getValue());
				isError = true;
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
			}
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		
		if(isError){
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsCommentError.CODE_3006.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsCommentError.CODE_3006.getValue());
				}else if(ms.getCode() == ConstraintsCommentError.CODE_3008.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsCommentError.CODE_3008.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_1002.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1002.getValue());
				}
			}
		}
		return jsonObject.toString();
	}
	
}
