package com.znsd.pdh.util;

import com.mongodb.util.JSON;

public class JsonUtils {
	
	/**
	 * 校验字符串是否为json格式
	 * @param json 需要校验的json串
	 * @return
	 */
	public static boolean isJson(String json) {
		try {
			JSON.parse(json);
			return true ; 
		}catch(Exception e) {
			return false;
		}
	}

}
