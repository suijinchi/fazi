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
import com.zhengbangnet.modules.entity.ArticleCategory;
import com.zhengbangnet.modules.service.ArticleCategoryService;

@Controller("adminArticleCategoryController")
@RequestMapping("/admin/article_category")
public class ArticleCategoryController extends AdminBaseController {

	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = articleCategoryService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/article_category/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/article_category/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String name,String treePath,Long parent,Integer orders,Integer grade,
	HttpServletRequest request, HttpSession session) {
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setCreateDate(new Date());
		articleCategory.setModifyDate(new Date());
		articleCategory.setName(name);
		articleCategory.setTreePath(treePath);
		articleCategory.setParent(parent);
		articleCategory.setOrders(orders);
		articleCategory.setGrade(grade);
		articleCategoryService.insertSelective(articleCategory);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		ArticleCategory articleCategory = articleCategoryService.getByPrimaryKey(id);
		model.addAttribute("articleCategory", articleCategory);
		return "/admin/article_category/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String name,String treePath,Long parent,Integer orders,Integer grade,
	Model model, HttpSession session, HttpServletRequest request) {
		ArticleCategory articleCategory = articleCategoryService.getByPrimaryKey(id);
		if (articleCategory == null) {
			return Message.error("编辑信息不存在");
		}
		articleCategory.setModifyDate(new Date());
		articleCategory.setId(id);
		articleCategory.setName(name);
		articleCategory.setTreePath(treePath);
		articleCategory.setParent(parent);
		articleCategory.setOrders(orders);
		articleCategory.setGrade(grade);
		articleCategoryService.updateByPrimaryKey(articleCategory);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			articleCategoryService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}