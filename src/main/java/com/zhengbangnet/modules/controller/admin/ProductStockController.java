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
import com.zhengbangnet.modules.entity.ProductStock;
import com.zhengbangnet.modules.service.ProductStockService;

@Controller("adminProductStockController")
@RequestMapping("/admin/product_stock")
public class ProductStockController extends AdminBaseController {

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = productStockService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/product_stock/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/product_stock/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(java.math.BigDecimal price,Integer stock,String imgUrl,String size,String tableware,String shareNum,Long productId,String sn,
	HttpServletRequest request, HttpSession session) {
		ProductStock productStock = new ProductStock();
		productStock.setCreateDate(new Date());
		productStock.setModifyDate(new Date());
		productStock.setPrice(price);
		productStock.setStock(stock);
		productStock.setImgUrl(imgUrl);
		productStock.setSize(size);
		productStock.setTableware(tableware);
		productStock.setShareNum(shareNum);
		productStock.setProductId(productId);
		productStock.setSn(sn);
		productStockService.insertSelective(productStock);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		ProductStock productStock = productStockService.getByPrimaryKey(id);
		model.addAttribute("productStock", productStock);
		return "/admin/product_stock/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,java.math.BigDecimal price,Integer stock,String imgUrl,String size,String tableware,String shareNum,Long productId,String sn,
	Model model, HttpSession session, HttpServletRequest request) {
		ProductStock productStock = productStockService.getByPrimaryKey(id);
		if (productStock == null) {
			return Message.error("编辑信息不存在");
		}
		productStock.setModifyDate(new Date());
		productStock.setId(id);
		productStock.setPrice(price);
		productStock.setStock(stock);
		productStock.setImgUrl(imgUrl);
		productStock.setSize(size);
		productStock.setTableware(tableware);
		productStock.setShareNum(shareNum);
		productStock.setProductId(productId);
		productStock.setSn(sn);
		productStockService.updateByPrimaryKey(productStock);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			productStockService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}