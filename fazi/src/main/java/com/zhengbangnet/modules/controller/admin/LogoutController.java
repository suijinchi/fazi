package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.modules.entity.SysAdmin;
import com.zhengbangnet.modules.service.SysAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LogoutController extends AdminBaseController{
	
	@Resource(name="sysAdminServiceImpl")
	private SysAdminService adminService;
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String login(HttpSession session,Model model,HttpServletRequest request){
		session.removeAttribute(SysAdmin.CURRENT_LOGIN_ADMIN);
		return "redirect:/admin/login";
	}
	 
}
