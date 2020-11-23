package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;


@Controller("adminProductCategoryController")
@RequestMapping("/admin/productCategory")
public class ProductCategoryController extends AdminBaseController {

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 列表 create by suijinchi on 2018-06-02
	 * @param pageable
	 * @param model
	 * @return
	 */
	@SysLog(module="商品分类管理",method="查询商品分类列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page<Map<String, Object>> page = productCategoryService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/product_category/list";
	}

	/**
	 * 添加页面 create by suijinchi on 2018-06-22
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "/admin/product_category/add";
	}

	/**
	 * 保存 create by suijinchi on 2018-06-22
	 */
	@SysLog(module="商品分类管理",method="添加商品分类")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String name,String indexShowUrl, Integer orders) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCreateDate(new Date());
		productCategory.setModifyDate(new Date());
		productCategory.setName(name);
		productCategory.setIsDisplay(1);
		productCategory.setIndexShowUrl(indexShowUrl);
		productCategory.setOrders(orders);
		productCategoryService.insert(productCategory);
		return Message.success("添加成功");
	}

	/**
	 * 编辑页面 create by suijinchi on 2018-06-22
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		ProductCategory productCategory = productCategoryService.getByPrimaryKey(id);
		model.addAttribute("productCategory", productCategory);
		return "/admin/product_category/edit";
	}

	/**
	 * 更新 create by suijinchi on 2018-06-22
	 */
	@SysLog(module="商品分类管理",method="更新商品分类")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id, String name,String indexShowUrl, Integer orders) {
		ProductCategory productCategory = productCategoryService.getByPrimaryKey(id);
		productCategory.setModifyDate(new Date());
		productCategory.setName(name);
		productCategory.setOrders(orders);
		productCategory.setIndexShowUrl(indexShowUrl);
		productCategory.setIsDisplay(1);
		productCategoryService.updateByPrimaryKey(productCategory);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除) create by suijinchi on 2018-06-22
	 */
	@SysLog(module="商品分类管理",method="删除商品分类")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		for(int i=0; i<ids.length; i++){
			List<Map<String, Object>> productList = productService.getByProductCategoryId(ids[i]);
			if (productList.size() > 0) {
				return Message.error("分类下面存在商品");
			}
		}
		if (ids != null && ids.length > 0) {
			productCategoryService.deleteByPrimaryKeys(ids);
			for(int i=0; i<ids.length; i++){
				productCategoryService.deleteByPrimaryKeys(ids);
			}
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}