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
import com.zhengbangnet.modules.entity.OrdersItem;
import com.zhengbangnet.modules.service.OrdersItemService;

@Controller("adminOrdersItemController")
@RequestMapping("/admin/orders_item")
public class OrdersItemController extends AdminBaseController {

	@Resource(name = "ordersItemServiceImpl")
	private OrdersItemService ordersItemService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = ordersItemService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/orders_item/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/orders_item/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String name,java.math.BigDecimal price,Integer quantity,String sn,String thumbnail,Long ordersId,Long productId,Integer point,String productSpec,
	HttpServletRequest request, HttpSession session) {
		OrdersItem ordersItem = new OrdersItem();
		ordersItem.setCreateDate(new Date());
		ordersItem.setModifyDate(new Date());
		ordersItem.setName(name);
		ordersItem.setPrice(price);
		ordersItem.setQuantity(quantity);
		ordersItem.setSn(sn);
		ordersItem.setThumbnail(thumbnail);
		ordersItem.setOrdersId(ordersId);
		ordersItem.setProductId(productId);
		ordersItem.setPoint(point);
		ordersItem.setProductSpec(productSpec);
		ordersItemService.insertSelective(ordersItem);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		OrdersItem ordersItem = ordersItemService.getByPrimaryKey(id);
		model.addAttribute("ordersItem", ordersItem);
		return "/admin/orders_item/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String name,java.math.BigDecimal price,Integer quantity,String sn,String thumbnail,Long ordersId,Long productId,Integer point,String productSpec,
	Model model, HttpSession session, HttpServletRequest request) {
		OrdersItem ordersItem = ordersItemService.getByPrimaryKey(id);
		if (ordersItem == null) {
			return Message.error("编辑信息不存在");
		}
		ordersItem.setModifyDate(new Date());
		ordersItem.setId(id);
		ordersItem.setName(name);
		ordersItem.setPrice(price);
		ordersItem.setQuantity(quantity);
		ordersItem.setSn(sn);
		ordersItem.setThumbnail(thumbnail);
		ordersItem.setOrdersId(ordersId);
		ordersItem.setProductId(productId);
		ordersItem.setPoint(point);
		ordersItem.setProductSpec(productSpec);
		ordersItemService.updateByPrimaryKey(ordersItem);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			ordersItemService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}