/* 
 * Meta.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.object;

import java.util.ArrayList;
import java.util.List;

/**
 * The meta object for build response data
 * 
 * @author UayLU
 * 
 */
public class Meta {
	
	private int code;
	private List<Message> messages;
	
	public Meta(int code, List<Message> listMessage) {
		super();
		this.code = code;
		this.messages = listMessage;
	}
	
	public Meta(int code, int codeMessage) {
		super();
		this.code = code;
		List<Message> listMessage = new ArrayList<Message>();
		listMessage.add(new Message(codeMessage));
		this.messages = listMessage;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> message) {
		this.messages = message;
	}
	
	
	
}
