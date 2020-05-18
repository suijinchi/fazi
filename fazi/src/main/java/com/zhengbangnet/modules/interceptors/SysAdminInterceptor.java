package com.zhengbangnet.modules.interceptors;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhengbangnet.modules.entity.SysAdmin;
import com.zhengbangnet.modules.entity.SysMenu;
import com.zhengbangnet.modules.service.SysAdminService;
import com.zhengbangnet.modules.service.SysMenuService;


public class SysAdminInterceptor extends HandlerInterceptorAdapter {

	/** "重定向URL"参数名称 */
	private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";
	
	@Resource(name = "sysAdminServiceImpl")
	private SysAdminService sysAdminService;
	
	@Resource(name="sysMenuServiceImpl")
	private SysMenuService sysMenuService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute(SysAdmin.CURRENT_LOGIN_ADMIN);
		if (id != null) {
			System.out.println(request.getServletPath());
			//权限判断 - add by hawkbird date 2018-03-22
			//解析当前请求url
			String servletPath = request.getServletPath();
			//过滤出参数--?
			if(servletPath.indexOf("?")>0){
				servletPath = servletPath.split("?")[0];
			}			
			//查询当前用户角色菜单URL
			List<SysMenu> sysMenuList = sysMenuService.findBySysAdmin(id);			
			//判断
			boolean authorization = false;
			if(sysMenuList!=null&&sysMenuList.size()>0){
				for(SysMenu menu:sysMenuList){
					String menuUrl = menu.getUrl();
					if(menuUrl==null||menuUrl.length()==0)
						continue;
					if(menuUrl.indexOf("?")>0){
						menuUrl = menuUrl.split("?")[0];
					}
					if(menuUrl.length()==0)
						continue;
					//
					if(menuUrl.equals(servletPath)){
						authorization = true;
						break;
					}
				}
			}
			
			//TODO 后期删除
			authorization = true;
			
			//没有权限跳回登录页
			if(!authorization){
				String loginUrl = "/admin/login";
				String requestType = request.getHeader("X-Requested-With");
				if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
					response.addHeader("loginUrl", request.getContextPath()+loginUrl);
					response.addHeader("loginStatus", "accessDenied");
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return false;
				} else {
					if (request.getMethod().equalsIgnoreCase("GET")) {
						String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
						response.sendRedirect(request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, "utf-8"));
					} else {
						response.sendRedirect(request.getContextPath() + loginUrl);
					}
					return false;
				}				
			}
			//end added
			return true;
		} else {
			String loginUrl = "/admin/login";

			String requestType = request.getHeader("X-Requested-With");
			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
				response.addHeader("loginUrl", request.getContextPath()+loginUrl);
				response.addHeader("loginStatus", "accessDenied");
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return false;
			} else {
				if (request.getMethod().equalsIgnoreCase("GET")) {
					String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
					response.sendRedirect(request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, "utf-8"));
				} else {
					response.sendRedirect(request.getContextPath() + loginUrl);
				}
				return false;
			}
		}
	}

}
