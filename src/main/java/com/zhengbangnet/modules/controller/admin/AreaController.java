package com.zhengbangnet.modules.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.zhengbangnet.modules.entity.Area;
import com.zhengbangnet.modules.service.AreaService;

@Controller("adminAreaController")
@RequestMapping("/admin/area")
public class AreaController extends AdminBaseController {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = areaService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/area/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/area/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String fullName,String name,String treePath,Long parentId,Integer orders,
	HttpServletRequest request, HttpSession session) {
		Area area = new Area();
		area.setCreateDate(new Date());
		area.setModifyDate(new Date());
		area.setFullName(fullName);
		area.setName(name);
		area.setTreePath(treePath);
		area.setParentId(parentId);
		area.setOrders(orders);
		areaService.insertSelective(area);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		Area area = areaService.getByPrimaryKey(id);
		model.addAttribute("area", area);
		return "/admin/area/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String fullName,String name,String treePath,Long parentId,Integer orders,
	Model model, HttpSession session, HttpServletRequest request) {
		Area area = areaService.getByPrimaryKey(id);
		if (area == null) {
			return Message.error("编辑信息不存在");
		}
		area.setModifyDate(new Date());
		area.setId(id);
		area.setFullName(fullName);
		area.setName(name);
		area.setTreePath(treePath);
		area.setParentId(parentId);
		area.setOrders(orders);
		areaService.updateByPrimaryKey(area);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			areaService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}