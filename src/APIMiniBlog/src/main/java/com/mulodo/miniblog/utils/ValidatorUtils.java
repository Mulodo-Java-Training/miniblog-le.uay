/* 
 * ValidatorUtils.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mulodo.miniblog.contraints.Constraints;

/**
 * The ValidatorUtils use to validate data
 * 
 * @author UayLU
 */
public final class ValidatorUtils
{

    /**
     * isNotNullNotEmptyNotWhiteSpace use for check not null, not empty, not white space
     *
     * @param string : string use for checking
     * 
     * @return Boolean
     */
    public static Boolean isNotNullNotEmptyNotWhiteSpace(String string)
    {
        return string != null && !string.isEmpty() && !string.trim().isEmpty();
    }

    
    /**
     * isValidLength use for check valid length of variable
     *
     * @param string : string use for checking
     * @parem min    : min of string
     * @parem max    : max of string
     * 
     * @return Boolean
     */
    public static Boolean isValidLength(String string, int min, int max)
    {
        if (min > 0 && string.length() < min) {
            return false;
        } else if (max > 0 && string.length() > max) {
            return false;
        }
        return true;
    }

    /**
     * isPositiveInteger use for check valid number like id of posts
     *
     * @param string : string use for checking
     * 
     * @return Boolean
     */
    public static Boolean isPositiveInteger(String string)
    {

        try {
            int i = Integer.parseInt(string);
            if (i > 0) {
                return true;
            } else {
                return false;
            }

        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * isStatusInteger use for check valid status
     *
     * @param string : string of status from client
     * 
     * @return Boolean
     */
    public static Boolean isStatusInteger(String string)
    {
        try {
            int i = Integer.parseInt(string);
            if (i == 0 || i == 1) {
                return true;
            } else {
                return false;
            }

        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * isValidEmail use for check valid email format
     *
     * @param email : string of email from client
     * 
     * @return Boolean
     */
    public static Boolean isValidEmail(String email)
    {
        if (!isNotNullNotEmptyNotWhiteSpace(email)) {
            return false;
        }
        Pattern pattern = Pattern.compile(Constraints.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
