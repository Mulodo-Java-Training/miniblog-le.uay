/* 
 * Constraints.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.contraints;

/**
 * The Constraint of project
 * 
 * @author UayLU
 * 
 */
public final class Constraints {
	
	//user error code
	public final static int CODE_2000 = 2000;
	public final static int CODE_2001 = 2001;
	public final static int CODE_2002 = 2002;
	public final static int CODE_2003 = 2003;
	public final static int CODE_2004 = 2004;
	public final static int CODE_2005 = 2005;
	public final static int CODE_2006 = 2006;
	public final static int CODE_2007 = 2007;
	public final static int CODE_2008 = 2008;
	public final static int CODE_2009 = 2009;
	public final static int CODE_2010 = 2010;
	public final static int CODE_2011 = 2011;
	public final static int CODE_2012 = 2012;
	public final static int CODE_2013 = 2013;
	public static final int CODE_2014 = 2014;
	public static final int CODE_2015 = 2015;
	public static final int CODE_2016 = 2016;
	public static final int MAX_USERNAME = 40;
	public static final int MIN_USERNAME = 6;
	public static final int MIN_PASSWORD = 6;
	
	//User Constraints
	public static final String USER = "user";
	public static final String LIST_USER = "listUser";
	public static final String STATUS = "status";
	public static final int USER_ACTIVE = 1;
	public static final int USER_DEACTIVE = 0;
	
	
	//Post error
	public static final int CODE_2500 = 2500;
	public static final int CODE_2501 = 2501;
	public static final int CODE_2502 = 2502;
	public static final int CODE_2503 = 2503;
	public static final int CODE_2504 = 2504;
	public static final int CODE_2505 = 2505;
	public static final int CODE_2506 = 2506;
	public static final int CODE_2507 = 2507;
	public static final int CODE_2508 = 2508;
	public static final int CODE_2509 = 2509;
	public static final int CODE_2510 = 2510;
	
	
	//Post constaints
	public static final int POST_ACTIVE = 1;
	public static final int POST_INACTIVE = 0;
	public static final String LIST_POST = "listPost";
	
	
	//Comment error
	public static final int CODE_3000 = 3000;
	public static final int CODE_3001 = 3001;
	public static final int CODE_3002 = 3002;
	public static final int CODE_3003 = 3003;
	public static final int CODE_3004 = 3004;
	public static final int CODE_3005 = 3005;
	public static final int CODE_3006 = 3006;
	public static final int CODE_3007 = 3007;
	public static final int CODE_3008 = 3008;
	
	
	//Default json
	public static final String META = "meta";
	public static final String DATA = "data";
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String MESSAGES = "messages";
	
	//Contraints
	public static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String REGEX_END_WHITESPACE = "\\s+$";
	
	
	//System error
	public static final int CODE_9001 = 9001;
	
	
	
	//Authentication error
	public static final int CODE_1000 = 1000;
	
	public static final int CODE_1001 = 1001;
	
	public static final int CODE_1002 = 1002;
	
	public static final int CODE_1003 = 1003;
	
	public static final int CODE_1004 = 1004;
	
	public static final int CODE_1005 = 1005;
	
	
	
	//Secret key
	public static final String SECRET_KEY = "miniblog12#$";
	
	//Token
	public static final String ACCESS_KEY = "access_key";
	public static final String TOKEN = "token";
	
	//pagination
	public static final int LIMIT_ROW = 10;
	public static final String PAGE_NUM = "pageNum";
	public static final String LIMIT_ROW_STRING = "limitRow";
	public static final String TOTAL_PAGE = "totalPage";
	public static final String TOTAL_ROW = "totalRow";
	
	//Mesage return success
	public static final int CODE_200 = 200;
	public static final int CODE_201 = 201;
	public static final int CODE_203 = 203;
	public static final int CODE_204 = 204; 	
	public static final int CODE_205 = 205; 	
	public static final int CODE_206 = 206;
	public static final int CODE_207 = 207;
	public static final int CODE_208 = 208;
	public static final int CODE_209 = 209;
	public static final int CODE_210 = 210;
	public static final int CODE_211 = 211;
	public static final int CODE_212 = 212;
	
}
