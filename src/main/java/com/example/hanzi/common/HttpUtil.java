package com.example.hanzi.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
	
	static public Map<String, String> getRequestParameters(HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String paramName = (String)paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if(paramValues.length == 1) {
				if(paramValues[0].length()!=0) {
					map.put(paramName, paramValues[0]);
				}
			}
		}
		return map;
	}
}