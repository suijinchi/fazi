package com.zhengbangnet.common.utils;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类
 * http://www.oschina.net/code/snippet_2283492_51950
 */
public class JsonUtils {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	private JsonUtils(){
		
	}
	
	/**
	 * 对象转换为json字符串
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj){
		String result = "";
		try {
			result =mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void toJson(HttpServletResponse response, String contentType, Object value)
	  {
	    Assert.notNull(response);
	    Assert.hasText(contentType);
	    try
	    {
	      response.setContentType(contentType);
	      mapper.writeValue(response.getWriter(), value);
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
	  }
	
}
