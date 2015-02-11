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
 * 
 */
public class BuildJSON {

	public final static JSONObject buildReturn(Meta meta, Data data){
		JSONObject metaJSON = null;
		JSONObject dataJSON = null;
		
		if(meta != null){
			metaJSON = new JSONObject(meta);
			if(meta.getMessages().isEmpty() ||meta.getMessages().get(0).getCode() == 0 ){
				metaJSON.remove(Constraints.MESSAGES);
			}
		}
		
		if(data != null){
			
			dataJSON= new JSONObject(data);
			boolean isValid = false;
				
			if(dataJSON.has("listPost")){
				isValid = true;
			}else if(dataJSON.has("listUser")){
				isValid = true;
			}else if(dataJSON.has("listComment")){
				isValid = true;
			}
			
			if(!isValid){
				dataJSON.remove(Constraints.LIMIT_ROW_STRING);
				dataJSON.remove(Constraints.PAGE_NUM);
				dataJSON.remove(Constraints.TOTAL_PAGE);
				dataJSON.remove(Constraints.TOTAL_ROW);
			}
			
		}
		JSONObject result = new JSONObject();
		result.put(Constraints.META, metaJSON);
		result.put(Constraints.DATA, dataJSON);
		return result;
	}
	
}
