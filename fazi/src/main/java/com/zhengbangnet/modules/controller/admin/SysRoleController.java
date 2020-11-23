/*
 * 
 * 
 * 
 */
package com.zhengbangnet.modules.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.zhengbangnet.modules.entity.SysMenu;
import com.zhengbangnet.modules.entity.SysRole;
import com.zhengbangnet.modules.service.SysAdminService;
import com.zhengbangnet.modules.service.SysMenuService;
import com.zhengbangnet.modules.service.SysRoleService;

/**
 * 
 */
@Controller("adminSysRoleController")
@RequestMapping("/admin/role")
public class SysRoleController extends AdminBaseController {

	@Resource(name = "sysAdminServiceImpl")
	private SysAdminService sysAdminService;

	@Resource(name = "sysRoleServiceImpl")
	private SysRoleService sysRoleService;

	@Resource(name = "sysMenuServiceImpl")
	private SysMenuService sysMenuService;

	/**
	 * 角色列表
	 */
	@SysLog(module="角色管理",method="查询角色")
	@RequestMapping("/list")
	public String list(Long companyId, String name, Pageable pageable, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageable", pageable);
		map.put("name", name);
		Page<Map<String, Object>> page = sysRoleService.findPageByParams(map);
		model.addAttribute("page", page);
		model.addAttribute("name", name);
		return "/admin/role/list";
	}

	/**
	 * 角色添加
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		List<SysMenu> sysMenuList = sysMenuService.findAll();
		model.addAttribute("sysMenuList", sysMenuList);
		return "/admin/role/add";
	}

	/**
	 * 保存添加的角色
	 */
	@SysLog(module="角色管理",method="添加角色")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String name, String description, String ids, Model model, HttpSession session, HttpServletRequest request) {

		if (StringUtils.isBlank(name)) {
			return Message.error("角色名称不能为空");
		}
		String[] idsArr = null;
		if (StringUtils.isNotBlank(ids)) {
			idsArr = ids.split(",");
		}
		Long[] menuIds = null;
		if (idsArr != null && idsArr.length > 0) {
			menuIds = new Long[idsArr.length];
			for (int i = 0; i < idsArr.length; i++) {
				menuIds[i] = Long.parseLong(idsArr[i]);
			}
		}
		sysRoleService.save(name, description, menuIds);
		return Message.success("添加成功");
	}

	/**
	 * 角色编辑
	 */
	@RequestMapping("/edit")
	public String edit(Long id, Model model) {
		try {
			SysRole sysRole = sysRoleService.getByPrimaryKey(id);
			List<SysMenu> sysMenuList = sysMenuService.findAll();
			List<Map<String, Object>> checkedList = sysMenuService.findBySysRole(sysRole.getId());
			model.addAttribute("sysRole", sysRole);
			model.addAttribute("sysMenuList", sysMenuList);
			model.addAttribute("checkedList", checkedList);
			model.addAttribute("current", sysAdminService.getCurrent());
			return "/admin/role/edit";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "error/404";
		}
	}

	/**
	 * 保存更新的角色
	 */
	@SysLog(module="角色管理",method="更新角色")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Model model, Long sysRoleId, String name, String description, String ids, HttpSession session, HttpServletRequest request) {

		SysRole sysRole = sysRoleService.getByPrimaryKey(sysRoleId);
		if (sysRole == null) {
			return Message.error("角色不存在");
		}

		if (StringUtils.isBlank(name)) {
			return Message.error("角色名称不能为空");
		}
		String[] idsArr = null;
		if (StringUtils.isNotBlank(ids)) {
			idsArr = ids.split(",");
		}
		Long[] menuIds = null;
		if (idsArr != null && idsArr.length > 0) {
			menuIds = new Long[idsArr.length];
			for (int i = 0; i < idsArr.length; i++) {
				menuIds[i] = Long.parseLong(idsArr[i]);
			}
		}
		sysRoleService.update(sysRoleId, name, description, menuIds);
		return Message.success("修改成功");
	}

	/**
	 * 删除角色(含批量删除)
	 * 
	 * @param ids
	 *            角色id
	 */
	@SysLog(module="角色管理",method="删除角色")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message list(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			sysRoleService.deleteByPrimaryKeys(ids);
			for(int i=0; i<ids.length; i++){
				sysRoleService.deleteBySysRoleId(ids[i]);
			}
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}