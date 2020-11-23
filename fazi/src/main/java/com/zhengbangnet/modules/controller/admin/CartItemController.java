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
import com.zhengbangnet.modules.entity.CartItem;
import com.zhengbangnet.modules.service.CartItemService;

@Controller("adminCartItemController")
@RequestMapping("/admin/cart_item")
public class CartItemController extends AdminBaseController {

	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = cartItemService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/cart_item/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/cart_item/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(java.util.Date modifeDate,Long cartId,Long productId,Integer quantity,Long shopId,String productSpec,
	HttpServletRequest request, HttpSession session) {
		CartItem cartItem = new CartItem();
		cartItem.setCreateDate(new Date());
		cartItem.setModifyDate(new Date());
		cartItem.setModifeDate(modifeDate);
		cartItem.setCartId(cartId);
		cartItem.setProductId(productId);
		cartItem.setQuantity(quantity);
		cartItem.setShopId(shopId);
		cartItem.setProductSpec(productSpec);
		cartItemService.insertSelective(cartItem);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		CartItem cartItem = cartItemService.getByPrimaryKey(id);
		model.addAttribute("cartItem", cartItem);
		return "/admin/cart_item/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,java.util.Date modifeDate,Long cartId,Long productId,Integer quantity,Long shopId,String productSpec,
	Model model, HttpSession session, HttpServletRequest request) {
		CartItem cartItem = cartItemService.getByPrimaryKey(id);
		if (cartItem == null) {
			return Message.error("编辑信息不存在");
		}
		cartItem.setModifyDate(new Date());
		cartItem.setId(id);
		cartItem.setModifeDate(modifeDate);
		cartItem.setCartId(cartId);
		cartItem.setProductId(productId);
		cartItem.setQuantity(quantity);
		cartItem.setShopId(shopId);
		cartItem.setProductSpec(productSpec);
		cartItemService.updateByPrimaryKey(cartItem);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			cartItemService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}