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
import com.zhengbangnet.modules.entity.${beanName};
import com.zhengbangnet.modules.service.${beanName}Service;

@Controller("admin${beanName}Controller")
@RequestMapping("/admin/${table}")
public class ${beanName}Controller extends AdminBaseController {

	@Resource(name = "${humpBeanName}ServiceImpl")
	private ${beanName}Service ${humpBeanName}Service;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = ${humpBeanName}Service.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/${table}/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/${table}/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(<#compress><#list columns as col><#if col!='createDate'&&col!='modifyDate'&&col!='id'>${types[col_index]} ${col},</#if></#list></#compress>
	HttpServletRequest request, HttpSession session) {
		${beanName} ${humpBeanName} = new ${beanName}();
		${humpBeanName}.setCreateDate(new Date());
		${humpBeanName}.setModifyDate(new Date());
		<#list columns as col><#if col!='createDate'&&col!='modifyDate'&&col!='id'>
		${humpBeanName}.set${col?cap_first}(${col});
		</#if></#list>
		${humpBeanName}Service.insertSelective(${humpBeanName});
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		${beanName} ${humpBeanName} = ${humpBeanName}Service.getByPrimaryKey(id);
		model.addAttribute("${humpBeanName}", ${humpBeanName});
		return "/admin/${table}/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(<#compress><#list columns as col><#if col!='createDate'&&col!='modifyDate'>${types[col_index]} ${col},</#if></#list></#compress>
	Model model, HttpSession session, HttpServletRequest request) {
		${beanName} ${humpBeanName} = ${humpBeanName}Service.getByPrimaryKey(id);
		if (${humpBeanName} == null) {
			return Message.error("编辑信息不存在");
		}
		${humpBeanName}.setModifyDate(new Date());
		<#list columns as col><#if col!='createDate'&&col!='modifyDate'>
		${humpBeanName}.set${col?cap_first}(${col});
		</#if></#list>
		${humpBeanName}Service.updateByPrimaryKey(${humpBeanName});
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			${humpBeanName}Service.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}