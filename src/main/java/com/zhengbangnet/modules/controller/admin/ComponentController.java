package com.zhengbangnet.modules.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zhengbangnet.modules.entity.ProductComponent;
import com.zhengbangnet.modules.service.ProductComponentService;
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
import com.zhengbangnet.modules.entity.Component;
import com.zhengbangnet.modules.service.ComponentService;

@Controller("adminComponentController")
@RequestMapping("/admin/component")
public class ComponentController extends AdminBaseController {

	@Resource(name = "componentServiceImpl")
	private ComponentService componentService;

	@Resource(name = "productComponentServiceImpl")
	private ProductComponentService productComponentService;

	/**
	 * 列表 create by suijinchi on 2018-06-26
	 * @param pageable
	 * @param model
	 * @return
	 */
	@SysLog(module="商品成分管理",method="查询商品成分列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page<Map<String, Object>> page = componentService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/component/list";
	}

	/**
	 * 添加页面 create by suijinchi on 2018-06-26
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "/admin/component/add";
	}

	/**
	 * 保存 create by suijinchi on 2018-06-26
	 */
	@SysLog(module="商品成分管理",method="添加商品成分")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(String name, String imgUrl) {
		Component component = new Component();
		component.setCreateDate(new Date());
		component.setModifyDate(new Date());
		component.setName(name);
		component.setImgUrl(imgUrl);
		componentService.insert(component);
		return Message.success("添加成功");
	}

	/**
	 * 编辑页面 create by suijinchi on 2018-06-26
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		Component component = componentService.getByPrimaryKey(id);
		model.addAttribute("component", component);
		return "/admin/component/edit";
	}

	/**
	 * 更新 create by suijinchi on 2018-06-26
	 */
	@SysLog(module="商品成分管理",method="更新商品成分")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id, String name, String imgUrl) {
		Component component = componentService.getByPrimaryKey(id);
		component.setModifyDate(new Date());
		component.setName(name);
		component.setImgUrl(imgUrl);
		componentService.updateByPrimaryKey(component);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除) create by suijinchi on 2018-06-26
	 */
	@SysLog(module="商品成分管理",method="删除商品成分")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		for(int i=0; i<ids.length; i++){
			List<ProductComponent> productComponentList = productComponentService.getByComponentId(ids[i]);
			if (productComponentList.size() > 0) {
				return Message.error("成分下面存在商品");
			}
		}
		if (ids != null && ids.length > 0) {
			componentService.deleteByPrimaryKeys(ids);
			for(int i=0; i<ids.length; i++){
				componentService.deleteByPrimaryKeys(ids);
			}
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}