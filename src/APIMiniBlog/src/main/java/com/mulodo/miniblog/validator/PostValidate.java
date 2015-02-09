/* 
 * PostValidate.java 
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
 * The PostValidate use to validate post data
 * 
 * @author UayLU
 * 
 */
public class PostValidate {
	
	/**
	 *  validate_add_new use for validate add new data post
	 *	
	 *	@param	title : title of post
	 *  @param  content : content of post
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateAddNew(String title, String content){
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		
		Boolean isValid;
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(content);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2503));
		}else{
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(content, 1, 3000);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2505));
			}
		}
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(title);		
		if(!isValid){
			listMessage.add(new Message(Constraints.CODE_2502));
		}else{
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(title, 1, 150);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2504));
			}
		}
		
		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2500, listMessage);
			return meta;
		}
		return null;
	}
	
	/**
	 *  validate_active_deactive use for active deactive post
	 *	
	 *	@param	id : id of post
	 *  @param  status : status of post
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateActiveDeactive(String id, String status){
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		
		Boolean isValid = ValidatorUtils.isPositiveInteger(id);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2508));
		}
		
		isValid = ValidatorUtils.isStatusInteger(status);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2507));
		}
		
		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2500, listMessage);
			return meta;
		}
		return null;
	}

	/**
	 *  validate_active_deactive use for active deactive post
	 *	
	 *	@param	id : id of post
	 *  @param  title : title of post
	 *  @param  content : content of post
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateUpdate(String id, String title,
			String content) {
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		
		Boolean isValid;
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(content);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2503));
		}else{
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(title, 1, 3000);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2505));
			}
		}
		
		//check null empty or white space if string
		isValid = ValidatorUtils.isNotNullNotEmptyNotWhiteSpace(title);		
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2502));
		}else{
			//check valid length of string
			isValid = ValidatorUtils.isValidLength(title, 1, 150);
			if(!isValid){
				//check if have error, add message error to listmessage
				listMessage.add(new Message(Constraints.CODE_2504));
			}
		}
		
		isValid = ValidatorUtils.isPositiveInteger(id);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2508));
		}
		
		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2500, listMessage);
			return meta;
		}
		return null;
	}

	/**
	 *  validate_delete use for delete post
	 *	
	 *	@param	id : id of post
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateDelete(String id) {
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		
		Boolean isValid = ValidatorUtils.isPositiveInteger(id);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2508));
		}
		
		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2500, listMessage);
			return meta;
		}
		return null;
	}

	/**
	 *  validate_get_all_post use for get all post
	 *	
	 *	@param	pageNum : Number of pape
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateGetAllPost(String pageNum) {
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		Boolean isValid = ValidatorUtils.isPositiveInteger(pageNum);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2510));
		}
		
		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2500, listMessage);
			return meta;
		}
		return null;
	}
	
	/**
	 *  validate_get_all_post_for_user use for get all post for user
	 *	
	 *	@param	pageNum : Number of pape
	 *  @param  user_id : id of user
	 *	
	 *  @return Meta
	 *	
	 */
	public static Meta validateGetAllPostForUser(String pageNum, String user_id) {
		
		//List message contain error message validate
		List<Message> listMessage = new ArrayList<Message>();
		Boolean isValid = ValidatorUtils.isPositiveInteger(pageNum);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2510));
		}
		
		isValid = ValidatorUtils.isPositiveInteger(user_id);
		if(!isValid){
			//check if have error, add message error to listmessage
			listMessage.add(new Message(Constraints.CODE_2508));
		}
		
		if(!listMessage.isEmpty() ){
			//check if have error, add message error to listmessage
			Meta meta = new Meta(Constraints.CODE_2500, listMessage);
			return meta;
		}
		return null;
	}
}