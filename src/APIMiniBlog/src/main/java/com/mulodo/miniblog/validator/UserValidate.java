/* 
 * UserValidate.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.validator;

import java.util.ArrayList;
import java.util.List;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.object.Message;
import com.mulodo.miniblog.object.Meta;
import com.mulodo.miniblog.utils.ValidatorUtils;

/**
 * The UserValidate use to validate user data
 * 
 * @author UayLU
 * 
 */
public class UserValidate {

	/**
	 *  validate_add_new use for validate add new data user
	 *	
	 *	@param	username : username of user
	 *  @param  firstname : firstname of user
	 *  @param  lastname : lastname of user
	 *  @param  password : password of user
	 *  @param  email : email of user
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateAddNew(String username, String firstname, String lastname, 
			String password, String email) {
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		Boolean isValid = true;
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(username);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2001));
		}
		if(isValid){
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(username, Constraints.MIN_USERNAME, Constraints.MAX_USERNAME);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2011));
			}
		}
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(password);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2002));
		}
		if(isValid){
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(password, Constraints.MIN_PASSWORD, 0);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2012));
			}
		}
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(lastname);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2003));
		}else{
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(lastname, 1, 45);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2003));
			}
		}
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(firstname);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2004));
		}else{
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(firstname, 1, 45);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2004));
			}
		}
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(email);
		if(isValid){
			isValid = ValidatorUtils.isValidEmail(email);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2005));
			}else{
				//check valid length of string
				isValid = ValidatorUtils.isValidLength(email, 1, 100);
				if(!isValid){
					//check if have error, add message error to listmessage
					listMessage.add(new Message(Constraints.CODE_2005));
				}
			}
			
		}
		
		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2000, listMessage);
			return meta;
		}
		return null;
	}
	
	/**
	 *  validate_update use for validate update data user
	 *	
	 *  @param  firstname : firstname of user
	 *  @param  lastname : lastname of user
	 *  @param  email : email of user
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateUpdate(String firstname, String lastname, 
					String email, String status, String password) {
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		Boolean isValid = true;
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(lastname);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2003));
		} else{
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(lastname, 1, 45);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2003));
			}
		}
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(firstname);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2004));
		}else{
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(firstname, 1, 45);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2004));
			}
		}
		
		if(email != null){
			isValid = ValidatorUtils.isValidEmail(email);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2005));
			}
		}
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(password);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2002));
		}
		if(isValid){
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(password, Constraints.MIN_PASSWORD, 0);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2012));
			}
		}
		
		isValid = ValidatorUtils.isStatusInteger(status);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2015));
		}else{
			//check if have error, add message error to listmessage
			if(Integer.parseInt(status) != Constraints.USER_ACTIVE && Integer.parseInt(status) != Constraints.USER_DEACTIVE){
				listMessage.add(new Message(Constraints.CODE_2015));
			}
		}
		
		if(!listMessage.isEmpty() ){
			Meta meta = new Meta(Constraints.CODE_2000, listMessage);
			return meta;
		}
		return null;
	}
		
	/**
	 *  validate_add_new use for validate add new data user
	 *	
	 *	@param	oldpassword : current password of user
	 *  @param  newpassword : new password of user
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateChangePassword(String oldpassword, String newpassword) {
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		Boolean isValid = true;
		
		isValid = true;
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(oldpassword);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2002));
		}
		
		isValid = true;
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(newpassword);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2013));
		}
		if(isValid){
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(newpassword, Constraints.MIN_PASSWORD, 0);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2012));
			}
		}
		
		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2000, listMessage);
			return meta;
		}
		return null;
	}
	
	/**
	 *  validate_login use for login
	 *	
	 *	@param	username : username of user
	 *  @param  password : password of user	
	 *
	 *  @return Meta
	 *	
	 */
	public static Meta validateLogin(String username, String password){
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		Boolean isValid = true;
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(username);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2001));
		}
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(password);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2002));
		}

		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2000, listMessage);
			return meta;
		}
		return null;
	}
	
	
	/**
	 *  validate_find_user_infor use for validate data name from client
	 *	
	 *	@param	name : username, firstname or lastname of user
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateFindUserInfo(String name){
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		
		Boolean isValid = true;
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(name);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2014));
		}
		

		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2000, listMessage);
			return meta;
		}
		return null;
	}
}