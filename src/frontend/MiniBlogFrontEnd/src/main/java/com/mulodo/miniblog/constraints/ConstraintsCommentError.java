package com.mulodo.miniblog.constraints;

public enum ConstraintsCommentError {
	
	CODE_3000(3000, "Have some thing error--Try again later."),
	CODE_3001(3001, "Comment's content is required."),
	CODE_3002(3002, "Post is required."),
	CODE_3003(3003, "User is required."),
	CODE_3004(3004, "Comment id is required."),
	CODE_3005(3005, "Comment is invalid - Less than 500 characters."),
	CODE_3006(3006, "Comment id is invalid"),
	CODE_3007(3007, "Post id is invalid."),
	CODE_3008(3008, "You have no permission on this comment.");
	
	private int key;
	private String value;
	
	ConstraintsCommentError(int key, String value ) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
