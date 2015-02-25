/* 
 * BuildJSON.java 
 *  
 * 1.0
 * 
 * 2015/02/02
 *  
 * Copyright (c) 2015 Le U Uay
 * 
 */
package com.mulodo.miniblog.utils;

import org.json.JSONObject;

import com.mulodo.miniblog.contraints.Constraints;
import com.mulodo.miniblog.object.Data;
import com.mulodo.miniblog.object.Meta;

/**
 * The Build json object for build json response data
 * 
 * @author UayLU
 */
public class BuildJSON
{

    /**
     * buildReturn use for build json object
     *
     * @param meta : data object for return error or message
     * @parem data : data of return like get post, get comment..
     * 
     * @return String
     */
    public final static JSONObject buildReturn(Meta meta, Data data)
    {
       
        JSONObject metaJSON = null;
        JSONObject dataJSON = null;

        // if meta is not null, transfer meta to JSON object
        if (meta != null) {
            metaJSON = new JSONObject(meta);
            // remove message json object for success response
            if (meta.getMessages().isEmpty() || meta.getMessages().get(0).getCode() == 0) {
                metaJSON.remove(Constraints.MESSAGES);
            }
        }

        // if data is not null, transfer data to JSON object
        if (data != null) {

            dataJSON = new JSONObject(data);
            
            // isvalid : use for check the have : listpost, listUser, listComment or not 
            boolean isValid = false;

            if (dataJSON.has("listPost")) {
                isValid = true;
            } else if (dataJSON.has("listUser")) {
                isValid = true;
            } else if (dataJSON.has("listComment")) {
                isValid = true;
            }

            // if invalid one of them, remove
            if (!isValid) {
                dataJSON.remove(Constraints.LIMIT_ROW_STRING);
                dataJSON.remove(Constraints.PAGE_NUM);
                dataJSON.remove(Constraints.TOTAL_PAGE);
                dataJSON.remove(Constraints.TOTAL_ROW);
            }

        }
        
        // build json object from meteJSON and dataJSON
        JSONObject result = new JSONObject();
        result.put(Constraints.META, metaJSON);
        result.put(Constraints.DATA, dataJSON);
        return result;
    }

}
