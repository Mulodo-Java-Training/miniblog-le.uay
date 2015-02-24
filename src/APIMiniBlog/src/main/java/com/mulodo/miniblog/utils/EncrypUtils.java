/* 
 * EncrypUtils.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import com.mulodo.miniblog.contraints.Constraints;

/**
 * The EncrypUtils use to encryp string to sha 256
 * 
 * @author UayLU
 */
public class EncrypUtils
{
    public final static String encrypData(String str) throws NoSuchAlgorithmException
    {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(str.getBytes());
        byte[] mdbytes = md.digest();

        // convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
        }
        return hexString.toString();
    }

    public final static String buildAccessKey(String username, String password)
            throws NoSuchAlgorithmException
    {

        Calendar date = Calendar.getInstance();

        return encrypData(Constraints.SECRET_KEY + username + password + date.getTime());

    }
}
