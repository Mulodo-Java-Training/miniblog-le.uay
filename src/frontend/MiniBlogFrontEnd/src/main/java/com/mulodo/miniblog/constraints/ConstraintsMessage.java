package com.mulodo.miniblog.constraints;

public enum ConstraintsMessage {
	
	CODE_200(200, "Create account success"),
	CODE_201(201, "Account update success"),
	CODE_202(202, "Delete account success"),
	CODE_203(203, "Change password success"),
	CODE_204(204, "Login success"),
	CODE_205(205, "Logout success"),
	CODE_206(206, "Create post success"),
	CODE_207(207, "Post update success"),
	CODE_208(208, "Update Post's status success"),
	CODE_209(209, "Delete post success"),
	CODE_210(210, "Create comment success"),
	CODE_211(211, "Update comment success"),
	CODE_212(212, "Delete comment success"),
	USER_ACTIVE(1, "Active"),
	USER_INACTIVE(0, "Inactive");
	
	
	private int key;
	private String value;
	
	ConstraintsMessage(int key, String value ) {
		this.key = key;
		this.value = value;
	}
	
	public int getKey(){
		return key;
	}
	
	public String getValue(){
		return value;
	}
	
}
