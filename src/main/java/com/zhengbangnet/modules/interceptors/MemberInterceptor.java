package com.zhengbangnet.modules.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhengbangnet.modules.service.MemberService;

import java.net.URLEncoder;


public class MemberInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println(request.getServletPath());
		Long id = memberService.getCurrentId(request);
		
		if (id != null) {
			return true;
		} else {
			String loginUrl = "/mobile/wechat/index";
			String requestType = request.getHeader("X-Requested-With");
			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
				response.addHeader("loginUrl", request.getContextPath()+loginUrl);
				response.addHeader("loginStatus", "accessDenied");
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return false;
			} else {
				String action = request.getRequestURL().toString();
				if(StringUtils.isNotBlank(request.getQueryString())){
					action = action+"?"+request.getQueryString();
				}
				response.sendRedirect(request.getContextPath() + loginUrl+"?action="+ URLEncoder.encode(action,"utf-8"));
				return false;
			}
		}
	}

}
