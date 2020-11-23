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
import com.zhengbangnet.modules.entity.ProductImage;
import com.zhengbangnet.modules.service.ProductImageService;

@Controller("adminProductImageController")
@RequestMapping("/admin/product_image")
public class ProductImageController extends AdminBaseController {

	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = productImageService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/product_image/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/product_image/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(Long productId,Integer orders,String imgUrl,
	HttpServletRequest request, HttpSession session) {
		ProductImage productImage = new ProductImage();
		productImage.setCreateDate(new Date());
		productImage.setModifyDate(new Date());
		productImage.setProductId(productId);
		productImage.setOrders(orders);
		productImage.setImgUrl(imgUrl);
		productImageService.insertSelective(productImage);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		ProductImage productImage = productImageService.getByPrimaryKey(id);
		model.addAttribute("productImage", productImage);
		return "/admin/product_image/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,Long productId,Integer orders,String imgUrl,
	Model model, HttpSession session, HttpServletRequest request) {
		ProductImage productImage = productImageService.getByPrimaryKey(id);
		if (productImage == null) {
			return Message.error("编辑信息不存在");
		}
		productImage.setModifyDate(new Date());
		productImage.setId(id);
		productImage.setProductId(productId);
		productImage.setOrders(orders);
		productImage.setImgUrl(imgUrl);
		productImageService.updateByPrimaryKey(productImage);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			productImageService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}