package com.zhengbangnet.common.utils;

import javax.servlet.http.HttpServletRequest;


public class RequestUtils {

	/**
	 * 是否是Ajax异步请求
	 * @param request
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String requestedWith = request.getHeader("X-Requested-With");
		if(requestedWith!=null&&requestedWith.indexOf("XMLHttpRequest")!=-1){
			return true;
		}
		return false;
	}
	
}
