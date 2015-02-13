package com.mulodo.miniblog.unitest;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.Calendar;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.model.User;
import com.mulodo.miniblog.rest.controller.UserController;
import com.mulodo.miniblog.service.UserService;
import com.mulodo.miniblog.utils.EncrypUtils;

public class test {
	
	//declare ApplicationContext for getting bean from applicationContext.xml
	private final ApplicationContext appContext = 
	    	  new ClassPathXmlApplicationContext("classpath:/WEB-INF/applicationContext.xml");
	//set seesion bean in applicationContext.xml to userservice
	private final UserService userService = (UserService) appContext.getBean("userService");

	@Test
	public void testA() {
		try {
			Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
			POJOResourceFactory noDefaults = new POJOResourceFactory(UserController.class);
		    dispatcher.getRegistry().addResourceFactory(noDefaults);
		    MockHttpRequest request = MockHttpRequest.post("/users/login");
		    request.contentType(MediaType.APPLICATION_FORM_URLENCODED);
		    request.content("username=le.tung&password=abcd1234".getBytes());
	        MockHttpResponse responsed = new MockHttpResponse();
	        
	        System.out.println("url = "+ request.getUri().getPath());
	        dispatcher.invoke(request, responsed);
	        
	        String result = responsed.getContentAsString();
	        System.out.println(responsed.getOutputHeaders().getFirst(Constraints.ACCESS_KEY));
	        System.out.println("result = "+result);
	        
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
				
	}
	
	/**
	 *  setUpData user for unit testing  usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@Before
	public void  setUpData() throws Exception{
		Calendar dateUser = Calendar.getInstance();
		
		User userTung = new User("le.tung",EncrypUtils.encrypData("abcd1234"), "le", "tung", 
				"le.tung@mulodo.com", dateUser.getTime(), dateUser.getTime(), 1);
		userService.add(userTung);

	}
	
	
	/**
	 *  callBackUpdate user for unit testing  usercontroller
	 *	
	 *	
	 *  @return 
	 *  
	 *  @exception Exception
	 *	
	 */
	@After
	public void clearData() throws Exception{
		this.userService.deleteByUsername("le.tung");
		
	}

}
