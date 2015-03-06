package com.mulodo.miniblog;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulodo.miniblog.constraints.Constraints;
import com.mulodo.miniblog.constraints.ConstraintsMessage;
import com.mulodo.miniblog.constraints.ConstraintsUserError;
import com.mulodo.miniblog.object.Message;
import com.mulodo.miniblog.object.ResponseData;
import com.mulodo.miniblog.object.User;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.InvalidateUtils;

@Controller
public class UserController {

	@Autowired
	@Qualifier("UserServiceImpl")
	private UserService userService;

	@RequestMapping(value = "/mainpage/profile", method = RequestMethod.GET)
	public String getUserInfo(Locale locale, Model model,
			HttpServletRequest request) {

		HttpSession session = request.getSession(true);

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);
		
		ResponseData responseData = userService.getUserInfo(accessKey);
		Boolean isError = false;
		if(responseData == null){
			System.out.println("response data null");
		}
		JSONObject jsonObject = new JSONObject(responseData);
		System.out.println("JSON = "+jsonObject.toString());
		
		if (responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_2000
					.getKey()) {
				model.addAttribute(Constraints.MESSAGE,
						ConstraintsUserError.CODE_2000.getValue());
				isError = true;
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				model.addAttribute(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
				isError = true;
			}
		} else if (responseData.getData() != null) {
			isError = false;
		} else {
			model.addAttribute(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}

		if (!isError) {
			User user = responseData.getData().getUser();
			model.addAttribute("username", user.getUsername());
			model.addAttribute("firstname", user.getFirstname());
			model.addAttribute("lastname", user.getLastname());
			model.addAttribute("email", user.getEmail());
			System.out.println("status ="+user.getStatus());
			if (user.getStatus() == ConstraintsMessage.USER_ACTIVE.getKey()) {
				model.addAttribute("status",
						ConstraintsMessage.USER_ACTIVE.getValue());
			} else {
				model.addAttribute("status",
						ConstraintsMessage.USER_INACTIVE.getValue());
			}
		}
		return "profile";
	}

	@RequestMapping(value = "/mainpage/profile/edit", method = RequestMethod.GET)
	public String editUserInfo(Locale locale, Model model,
			HttpServletRequest request) {

		HttpSession session = request.getSession(true);

		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = userService.getUserInfo(accessKey);
		Boolean isError = false;

		if (responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_2000
					.getKey()) {
				model.addAttribute(Constraints.MESSAGE,
						ConstraintsUserError.CODE_2000.getValue());
				isError = true;
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				model.addAttribute(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
				isError = true;
			}
		} else if (responseData.getData() != null) {
			isError = false;
		} else {
			model.addAttribute(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}

		if (!isError) {
			User user = responseData.getData().getUser();
			model.addAttribute("username", user.getUsername());
			model.addAttribute("firstname", user.getFirstname());
			model.addAttribute("lastname", user.getLastname());
			model.addAttribute("email", user.getEmail());
			if (user.getStatus() == ConstraintsMessage.USER_ACTIVE.getKey()) {
				model.addAttribute("status",
						ConstraintsMessage.USER_ACTIVE.getValue());
			} else {
				model.addAttribute("status",
						ConstraintsMessage.USER_INACTIVE.getValue());
			}
		}
		return "editprofile";
	}

	@RequestMapping(value = "/mainpage/profile/edit", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	public String editUserInfo(@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("status") String status,
			HttpServletRequest request) {
		
		JSONObject jsonObject = new JSONObject();
		
		HttpSession session = request.getSession(true);
		
		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = userService.editUserInfo(firstname,
				lastname, email, password, status, accessKey);
		Boolean isError = false;

		if (responseData.getMeta() != null) {
			if(responseData.getMeta().getCode() == ConstraintsMessage.CODE_201
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE, ConstraintsMessage.CODE_201.getValue());
				isError = false;
				System.out.println("json = "+jsonObject.toString());
				return jsonObject.toString();
			}else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_2000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_2000.getValue());
				isError = true;
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
				isError = true;
			}
		} else if (responseData.getData() != null) {
			isError = false;
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}

		if (isError) {
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsUserError.CODE_2016.getKey()){
					jsonObject.put(Constraints.PASSWORD_ERROR, ConstraintsUserError.CODE_2016.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_2009.getKey()){
					jsonObject.put(Constraints.EMAIL_ERROR, ConstraintsUserError.CODE_2009.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_1002.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1002.getValue());
				}
			}
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/mainpage/profile/changepassword", method = RequestMethod.GET)
	public String changePassword(HttpServletRequest request) {
		
		return "changepassword";
	}
	
	@RequestMapping(value = "/mainpage/profile/changePassword", method = RequestMethod.POST, headers="Accept=application/json")
	@ResponseBody
	public String changePassword(@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword,
			HttpServletRequest request) {
		
		JSONObject jsonObject = new JSONObject();
		
		HttpSession session = request.getSession(true);
		
		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = userService.changePassword(currentPassword,
				newPassword, accessKey);
		Boolean isError = false;

		if (responseData.getMeta() != null) {
			if(responseData.getMeta().getCode() == ConstraintsMessage.CODE_203
					.getKey()) {
				
				isError = false;
				jsonObject.put(Constraints.MESSAGE, ConstraintsMessage.CODE_203.getValue());
				jsonObject.put(Constraints.REDIRECT, true);
				InvalidateUtils.invalidateLogin(request);
				return jsonObject.toString();
			}else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_2000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_2000.getValue());
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

		if (isError) {
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsUserError.CODE_2016.getKey()){
					jsonObject.put(Constraints.PASSWORD_ERROR, ConstraintsUserError.CODE_2016.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_1002.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1002.getValue());
				}
			}
		}
		return jsonObject.toString();
	}
	
	
	@RequestMapping(value = "/mainpage/searchUser", method = RequestMethod.GET)
	public String searchUser(@RequestParam("name") String name, Locale locale, Model model, HttpServletRequest request) {
		
		model.addAttribute("name",name);
		return "searchUser";
	}
	
	@RequestMapping(value = "/mainpage/searchUserAJAX", method = RequestMethod.GET)
	@ResponseBody
	public String searchUserAJAX(@RequestParam("name") String name, HttpServletRequest request) {
		
		JSONObject jsonObject = new JSONObject();
		
		HttpSession session = request.getSession(true);
		
		String accessKey = (String) session
				.getAttribute(Constraints.ACCESS_KEY);

		ResponseData responseData = userService.searchUser(name, accessKey);
		Boolean isError = false;

		if (responseData.getMeta() != null) {
			if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_2000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_2000.getValue());
				isError = true;
			} else if (responseData.getMeta().getCode() == ConstraintsUserError.CODE_1000
					.getKey()) {
				jsonObject.put(Constraints.MESSAGE,
						ConstraintsUserError.CODE_1000.getValue());
				InvalidateUtils.invalidateLogin(request);
				isError = true;
			}
		} else if (responseData.getData() != null) {			
			isError = false;
			JSONObject jsonData = new JSONObject(responseData.getData());
			jsonObject.put(Constraints.DATA,jsonData);
		} else {
			jsonObject.put(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}

		if (isError) {
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsUserError.CODE_2016.getKey()){
					jsonObject.put(Constraints.PASSWORD_ERROR, ConstraintsUserError.CODE_2016.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_1002.getKey()){
					jsonObject.put(Constraints.MESSAGE, ConstraintsUserError.CODE_1002.getValue());
				}
			}
		}
		return jsonObject.toString();
		
	}
	
	
	@RequestMapping(value = "/mainpage/profile/test", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	public String test(
			HttpServletRequest request) {
		
		JSONObject jsonObject = new JSONObject();
		Message mess = new Message();
		mess.setCode(22);
		jsonObject.put("ff", mess);
		return jsonObject.toString();
	}

}
