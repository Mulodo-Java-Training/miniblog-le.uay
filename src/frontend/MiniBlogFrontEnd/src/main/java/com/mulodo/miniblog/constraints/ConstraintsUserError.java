package com.mulodo.miniblog.constraints;

public enum ConstraintsUserError {
	CODE_2000(2000, "Have some thing error--Try again later."),
	CODE_2001(2001, "Username is required."),
	CODE_2002(2002, "Password is required."),
	CODE_2003(2003, "Lastname is required -- Less than 45 character"),
	CODE_2004(2004, "Firstname is required -- Less than 45 character"),
	CODE_2005(2005, "Email is  invalid format -- Less than 100 character"),
	CODE_2006(2006, "User id is required"),
	CODE_2007(2007, "User status is required"),
	CODE_2008(2008, "User is invalid"),
	CODE_2009(2009, "Email is exited - Pleased add different email"),
	CODE_2010(2010, "User is exited - Pleased add different user"),
	CODE_2011(2011, "Invalid username - From 6 to 40 "),
	CODE_2012(2012, "Invalid password - More than 6 character"),
	CODE_2013(2013, "New password is required"),
	CODE_2014(2014, "Please add text search"),
	CODE_2015(2015, "User status is invald"),
	CODE_2016(2016, "Invalid password"), 
	CODE_1001(1001, "Login failed"),
	CODE_1002(1002, "Access denied for this resource Make sure you already login and have permission to access this resource"),
	CODE_1003(1003, "Your already login");
	
	
	private int key;
	private String value;
	
	ConstraintsUserError(int key, String value ) {
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
