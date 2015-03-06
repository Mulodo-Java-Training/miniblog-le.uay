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
import com.mulodo.miniblog.constraints.ConstraintsMessage;
import com.mulodo.miniblog.constraints.ConstraintsPostError;
import com.mulodo.miniblog.constraints.ConstraintsUserError;
import com.mulodo.miniblog.object.Message;
import com.mulodo.miniblog.object.ResponseData;
import com.mulodo.miniblog.service.PostService;

@Controller
public class PostController {

	@Autowired
	@Qualifier("PostServiceImpl")
	private PostService postService;

	@RequestMapping(value = "/mainpage/getAllPost", method = RequestMethod.GET)
	@ResponseBody
	public String getUserInfo(@RequestParam("pageNum") String pageNum,
			@RequestParam("description") String description,
			HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		JSONObject jsonObject = new JSONObject();

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = postService.getAllPost(accessKey,
				Integer.parseInt(pageNum), description);
		Boolean isError = false;

		if (responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsPostError.CODE_2500
					.getKey()) {

				isError = true;
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {

				isError = true;
			}
		} else if (responseData.getData() != null) {
			isError = false;
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}

		if (!isError) {

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

		ResponseData responseData = postService.addPost(accessKey, title,
				content);

		if (responseData.getMeta() != null) {
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

		ResponseData responseData = postService.deletePost(accessKey, postId);

		if (responseData.getMeta() != null) {
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

		ResponseData responseData = postService.editPost(accessKey,
				Integer.parseInt(postId), title, content,
				Integer.parseInt(status));
		Boolean isError = false;
		if (responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsMessage.CODE_209
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsMessage.CODE_209.getValue());
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

}
