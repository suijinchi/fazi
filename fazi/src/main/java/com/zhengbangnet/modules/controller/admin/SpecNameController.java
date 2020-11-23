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
import com.zhengbangnet.modules.entity.SpecName;
import com.zhengbangnet.modules.service.SpecNameService;

@Controller("adminSpecNameController")
@RequestMapping("/admin/spec_name")
public class SpecNameController extends AdminBaseController {

	@Resource(name = "specNameServiceImpl")
	private SpecNameService specNameService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = specNameService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/spec_name/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/spec_name/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String name,
	HttpServletRequest request, HttpSession session) {
		SpecName specName = new SpecName();
		specName.setCreateDate(new Date());
		specName.setModifyDate(new Date());
		specName.setName(name);
		specNameService.insertSelective(specName);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		SpecName specName = specNameService.getByPrimaryKey(id);
		model.addAttribute("specName", specName);
		return "/admin/spec_name/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String name,
	Model model, HttpSession session, HttpServletRequest request) {
		SpecName specName = specNameService.getByPrimaryKey(id);
		if (specName == null) {
			return Message.error("编辑信息不存在");
		}
		specName.setModifyDate(new Date());
		specName.setId(id);
		specName.setName(name);
		specNameService.updateByPrimaryKey(specName);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			specNameService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}