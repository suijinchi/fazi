package com.zhengbangnet.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


public class URLUtil {

	public static String getRequestUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			url += "?" + queryString;
		}
		return url;
	}

}
