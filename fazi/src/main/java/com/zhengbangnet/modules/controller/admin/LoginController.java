package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.utils.HttpContextUtils;
import com.zhengbangnet.common.utils.WebUtils;
import com.zhengbangnet.modules.entity.SysAdmin;
import com.zhengbangnet.modules.entity.SysLogs;
import com.zhengbangnet.modules.service.SysAdminService;
import com.zhengbangnet.modules.service.SysLogsService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/admin")
@Scope("prototype")
public class LoginController extends AdminBaseController {

	@Resource(name = "sysAdminServiceImpl")
	private SysAdminService adminService;
	
	@Resource(name = "sysLogsServiceImpl")
	private SysLogsService sysLogsService;

	/**
	 * 跳转登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "/admin/login/login";
	}

	/**
	 * 登录验证
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Message login(String ip,String username, String password, String captchaId, String captcha, HttpServletRequest request, HttpSession session, Model model) {
		/*if (!captchaService.isValid(captchaId, captcha)) {
			return Message.error("验证码错误");
		}
		*/
		 password=DigestUtils.md5Hex(password);
		 SysAdmin admin=adminService.getByUsername(username);
		 if(admin!=null){
			 if(admin.getIsEnabled().equals("0")){
				 return Message.error("该用户已停用");
			 }
			 if(admin.getPassword().equals(password)){
				 admin.setLoginTimes(admin.getLoginTimes()+1);
				 admin.setLastIp(admin.getIp());
				 admin.setLastLoginDate(admin.getLoginDate());
				 admin.setIp(WebUtils.getRemoteHost(request));
				 admin.setLoginDate(new Date());
				 adminService.updateByPrimaryKeySelective(admin);
				 session.setAttribute(SysAdmin.CURRENT_LOGIN_ADMIN, admin.getId());
				 Long id = admin.getId();
				 SysAdmin ad = adminService.getByPrimaryKey(id);
				 String name = ad.getName();
				 session.setAttribute("loginName", name);
				 String username2 = ad.getUsername();
				 session.setAttribute("username", username2);
				 /*新增日志*/
				 SysLogs sysLogs = new SysLogs();
			 	 HttpServletRequest httpRequest = HttpContextUtils.getHttpServletRequest();				
			 	 sysLogs.setIp(WebUtils.getRemoteHost(httpRequest));					
				 SysAdmin current = adminService.getCurrent();
				 sysLogs.setMethod("com.zhengbangnet.modules.controller.admin.LoginController.login()");
				 sysLogs.setType("用户登录");
				 sysLogs.setOperation("用户登录");
				 sysLogs.setUsername(current.getUsername());
				 sysLogs.setSysAdminId(current.getId());
				 sysLogs.setCreateDate(new Date());
				 sysLogs.setModifyDate(new Date());
				 sysLogsService.insertSelective(sysLogs);
				 /*新增日志*/
				 return Message.success("登录成功");
			 }
		 }
		 return Message.error("用户名或者密码错误");
	}

}
