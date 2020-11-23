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
import com.zhengbangnet.modules.entity.SpecValue;
import com.zhengbangnet.modules.service.SpecValueService;

@Controller("adminSpecValueController")
@RequestMapping("/admin/spec_value")
public class SpecValueController extends AdminBaseController {

	@Resource(name = "specValueServiceImpl")
	private SpecValueService specValueService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = specValueService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/spec_value/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/spec_value/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(Long specNameId,String value,
	HttpServletRequest request, HttpSession session) {
		SpecValue specValue = new SpecValue();
		specValue.setCreateDate(new Date());
		specValue.setModifyDate(new Date());
		specValue.setSpecNameId(specNameId);
		specValue.setValue(value);
		specValueService.insertSelective(specValue);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		SpecValue specValue = specValueService.getByPrimaryKey(id);
		model.addAttribute("specValue", specValue);
		return "/admin/spec_value/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,Long specNameId,String value,
	Model model, HttpSession session, HttpServletRequest request) {
		SpecValue specValue = specValueService.getByPrimaryKey(id);
		if (specValue == null) {
			return Message.error("编辑信息不存在");
		}
		specValue.setModifyDate(new Date());
		specValue.setId(id);
		specValue.setSpecNameId(specNameId);
		specValue.setValue(value);
		specValueService.updateByPrimaryKey(specValue);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			specValueService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}