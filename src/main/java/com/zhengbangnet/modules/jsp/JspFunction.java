package com.zhengbangnet.modules.jsp;

import org.apache.commons.lang3.StringUtils;

/**
 * jsp函数
 */
public class JspFunction {
	
	public static Object defaultValue(Object value,String defaultValue){
		String df = "";
		if(StringUtils.isBlank(defaultValue)){
			df = "-";
		}else{
			df = defaultValue;
		}
		if(value==null){
			if(StringUtils.isBlank(defaultValue)){
				return "-";
			}
			return defaultValue;
		}
		if(value instanceof String){
			if(StringUtils.isBlank((String)value)){
				return df;
			}
		}
		return value;
	}
	
	public static String toString(Object value){
		if(value!=null){
			return value+"";
		}
		return "";
	}
	
}
