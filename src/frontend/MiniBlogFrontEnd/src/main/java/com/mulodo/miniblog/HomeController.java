package com.mulodo.miniblog;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mulodo.miniblog.constraints.Constraints;
import com.mulodo.miniblog.constraints.ConstraintsMessage;
import com.mulodo.miniblog.constraints.ConstraintsUserError;
import com.mulodo.miniblog.object.Message;
import com.mulodo.miniblog.object.ResponseData;
import com.mulodo.miniblog.security.CustomAuthenticationProvider;
import com.mulodo.miniblog.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
    @Qualifier("UserServiceImpl")
	private UserService userService;
	
	@Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model , HttpServletRequest request) {
				
		HttpSession session = request.getSession(true);
		if(session.getAttribute(Constraints.ACCESS_KEY) != null){
			return "redirect:mainpage";
		}
		
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String home(HttpServletRequest request, @RequestParam("username") String username, 
			@RequestParam("password") String password, Locale locale, Model model) {
		
		
		HttpSession session = request.getSession(true);
		ResponseData responseData = userService.login(username, password);
		Boolean isError = false;
		
		if(responseData.getMeta() != null){
			if(responseData.getMeta().getCode() == ConstraintsMessage.CODE_204.getKey()){
				
				session.setAttribute("access_key", responseData.getHeader());
				session.setMaxInactiveInterval(3600);
				UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(responseData.getHeader(),
	                    null, AuthorityUtils.createAuthorityList("ROLE_USER"));
				Authentication authentication = customAuthenticationProvider.authenticate(authen);
				SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(authentication);
				return "redirect:mainpage";
			}else if(responseData.getMeta().getCode() == ConstraintsUserError.CODE_2000.getKey()){
				isError = true;
			}
		}else{
			model.addAttribute(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		
		if(isError){
			model.addAttribute(Constraints.MESSAGE, ConstraintsUserError.CODE_2000.getValue());
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsUserError.CODE_1001.getKey()){
					model.addAttribute(Constraints.MESSAGE, ConstraintsUserError.CODE_1001.getValue());
				}
			}
			return "home";
		}
		
		return "redirect:mainpage";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Locale locale, Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Locale locale, Model model) {
		
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestParam("username") String username,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("email") String email,
			@RequestParam("password") String password, Locale locale, Model model) {
		
		
		ResponseData responseData =  userService.register(username, firstname, lastname, email, password);
		Boolean isError = false;
		
		if(responseData.getMeta() != null){
			if(responseData.getMeta().getCode() == ConstraintsMessage.CODE_200.getKey()){
				model.addAttribute(Constraints.MESSAGE, ConstraintsMessage.CODE_200.getValue());
			}else if(responseData.getMeta().getCode() == ConstraintsUserError.CODE_2000.getKey()){
				isError = true;
			}
		}else{
			model.addAttribute(Constraints.MESSAGE, Constraints.COMMOM_ERROR);
		}
		
		if(isError){
			model.addAttribute(Constraints.MESSAGE, ConstraintsUserError.CODE_2000.getValue());
			List<Message> listMessage = responseData.getMeta().getMessages();
			for(Message ms : listMessage){
				if(ms.getCode() == ConstraintsUserError.CODE_2009.getKey()){
					System.out.println("key "+ConstraintsUserError.CODE_2009.getKey());
					System.out.println("key "+ConstraintsUserError.CODE_2009.getValue());
					model.addAttribute(Constraints.EMAIL_ERROR, ConstraintsUserError.CODE_2009.getValue());
				}else if(ms.getCode() == ConstraintsUserError.CODE_2010.getKey()){
					model.addAttribute(Constraints.USERNAME_ERROR, ConstraintsUserError.CODE_2010.getValue());
				}
			}
			
			model.addAttribute("username", username);
			model.addAttribute("firstname", firstname);
			model.addAttribute("lastname", lastname);
			model.addAttribute("email", email);
		}
		
		return "register";
	}

}
