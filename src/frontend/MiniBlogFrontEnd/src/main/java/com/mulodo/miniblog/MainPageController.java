package com.mulodo.miniblog;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mulodo.miniblog.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainPageController {
	
	@Autowired
    @Qualifier("UserServiceImpl")
	private UserService userService;

	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/mainpage", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "mainpage";
	}

}
