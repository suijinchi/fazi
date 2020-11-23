package com.zhengbangnet.modules.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.SysAdmin;
import com.zhengbangnet.modules.entity.SysRole;
import com.zhengbangnet.modules.service.SysAdminService;
import com.zhengbangnet.modules.service.SysMenuService;
import com.zhengbangnet.modules.service.SysRoleService;

/**
 * 管理员管理
 * 
 */
@Controller
@RequestMapping("/admin/admin")
public class SysAdminController extends AdminBaseController {

	@Resource(name = "sysAdminServiceImpl")
	private SysAdminService sysAdminService;

	@Resource(name = "sysRoleServiceImpl")
	private SysRoleService sysRoleService;

	@Resource(name = "sysMenuServiceImpl")
	private SysMenuService sysMenuService;

	/**
	 * 管理员列表
	 * 
	 * @param name
	 *            管理员姓名
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 */
	@SysLog(module="管理员管理",method="查询管理员列表")
	@RequestMapping(value = "/list")
	public String home(String username, String startTime, String endTime, Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("pageable", pageable);
		Page<Map<String, Object>> adminPage = sysAdminService.findPageByParams(map);
		model.addAttribute("username", username);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("page", adminPage);
		return "/admin/admin/list";
	}

	/**
	 * 添加管理员页面跳转
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
		List<SysRole> sysRoleList = sysRoleService.findAll();
		model.addAttribute("sysRoleList", sysRoleList);
		return "/admin/admin/add";
	}

	/**
	 * 保存添加的管理员
	 */
	@SysLog(module="管理员管理",method="添加管理员")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(Model model, String name, String username, String password, String phone, Long[] adminRole, String sex, String email, String memo, HttpServletRequest request, HttpSession session) {

		if (phone != null && !phone.equals("")) {
			SysAdmin moibleAdmin = sysAdminService.getByMobile(phone);
			if (moibleAdmin != null) {
				return Message.error("该手机号用户已存在");
			}
		}
		SysAdmin admin = new SysAdmin();
		admin = new SysAdmin();
		admin.setLoginTimes(0);
		admin.setCreateDate(new Date());
		admin.setModifyDate(null);
		admin.setUsername(username);
		admin.setName(name);
		admin.setPassword(DigestUtils.md5Hex(password));
		admin.setPhone(phone);
		admin.setLoginTimes(0);
		admin.setIsEnabled("1");
		admin.setIsSystem("0");
		admin.setSex(sex);
		admin.setEmail(email);
		admin.setMemo(memo);
		sysAdminService.save(admin, adminRole);
		return Message.success("添加成功");
	}

	/**
	 * 编辑管理员
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		try {
			SysAdmin admin = sysAdminService.getByPrimaryKey(id);
			if (admin == null) {
				return ERROR_VIEW;
			}
			List<SysRole> sysRoleList = sysRoleService.findAll();
			List<SysRole> checkedList = sysRoleService.findBySysAdmin(id);
			model.addAttribute("admin", admin);
			model.addAttribute("sysRoleList", sysRoleList);
			model.addAttribute("checkedList", checkedList);
			return "/admin/admin/edit";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "error/404";
		}
	}

	/**
	 * 更新编辑的管理员
	 */
	@SysLog(module="管理员管理",method="更新管理员")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Model model, Long id, Long[] adminRole,String name, String username, String password, String phone, String email, String memo, String sex, HttpSession session, HttpServletRequest request) {

		SysAdmin admin = sysAdminService.getByPrimaryKey(id);
		if (admin == null) {
			return Message.error("该用户不存在");
		}

		SysAdmin moibleAdmin = sysAdminService.getByMobile(phone);
		if (moibleAdmin != null && moibleAdmin.getId().longValue() != admin.getId().longValue()) {
			return Message.error("该手机号用户已存在");
		}

		if (StringUtils.isNotBlank(password)) {
			admin.setPassword(DigestUtils.md5Hex(password));
		}
		admin.setModifyDate(new Date());
		admin.setUsername(username);
		admin.setName(name);
		admin.setPhone(phone);
		admin.setEmail(email);
		admin.setMemo(memo);
		admin.setSex(sex);
		sysAdminService.update(admin, adminRole);
		return Message.success("修改成功");
	}

	/**
	 * 删除管理员(含批量删除)
	 * 
	 * @param ids
	 */
	@SysLog(module="管理员管理",method="删除管理员")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message list(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			sysAdminService.deleteByPrimaryKeys(ids);
			for(int i=0; i<ids.length; i++){
				sysAdminService.deleteBySysAdminId(ids[i]);
			}
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

	/**
	 * 管理员禁用/启用
	 * 
	 * @param sysAdminId
	 *            管理员id
	 */
	@SysLog(module="管理员管理",method="禁用/启用管理员")
	@RequestMapping(value = "/adminStatus", method = RequestMethod.POST)
	public @ResponseBody Message adminStatus(Long sysAdminId, Model model, HttpSession session, HttpServletRequest request) {
		SysAdmin admin = sysAdminService.getByPrimaryKey(sysAdminId);
		if (admin == null) {
			return Message.error("该用户不存在");
		}
		if (admin.getIsEnabled().equals("0")) {
			admin.setIsEnabled("1");
		} else {
			admin.setIsEnabled("0");
		}
		sysAdminService.updateByPrimaryKeySelective(admin);
		return SUCCESS_MESSAGE;
	}

}
