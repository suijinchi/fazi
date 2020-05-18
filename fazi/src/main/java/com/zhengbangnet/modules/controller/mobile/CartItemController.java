package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.modules.entity.Cart;
import com.zhengbangnet.modules.entity.CartItem;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.service.CartItemService;
import com.zhengbangnet.modules.service.CartService;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/mobile/cartItem")
public class CartItemController extends AdminBaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	/**
	 * 删除购物车中全部平台商品
	 */
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public @ResponseBody Message addCart(HttpServletRequest request, HttpSession session) {		
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);	
		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if (cart != null) {
			cartItemService.deleteAll();
			return Message.success("删除成功");
		}	
		return Message.error("删除失败");
	}
	
	/**
	 * 删除购物车中某个店铺全部商品
	 */
	@RequestMapping(value = "/deleteShop", method = RequestMethod.POST)
	public @ResponseBody Message deleteShop(Long shopId, HttpServletRequest request, HttpSession session) {
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);	
		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if (cart != null) {
			cartItemService.deleteShop(shopId);
			return Message.success("删除成功");
		}	
		return Message.error("删除失败");
	}
	
	/**
	 * 删除购物车中某个商品
	 */
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
	public @ResponseBody Message deleteProduct(Long id, HttpServletRequest request, HttpSession session) {
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);	
		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if (cart != null) {
			cartItemService.deleteByPrimaryKey(id);
			return Message.success("删除成功");
		}	
		return Message.error("删除失败");
	}
	
	/**
	 * 增加购物车中某个商品的数量（+1）
	 */
	@RequestMapping(value = "/addQuantity", method = RequestMethod.POST)
	public @ResponseBody Integer addQuantity(Long id, HttpServletRequest request, HttpSession session) {
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);	
		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if (cart != null) {
			CartItem cartItem = cartItemService.getByPrimaryKey(id);
			if (cartItem != null) {
				Integer quantity = cartItem.getQuantity();
				cartItem.setQuantity(quantity+1);
				cartItemService.updateByPrimaryKeySelective(cartItem);
				return quantity+1;
			}
		}	
		return null;
	}
	
	/**
	 * 减少购物车中某个商品的数量（-1）
	 */
	@RequestMapping(value = "/reduceQuantity", method = RequestMethod.POST)
	public @ResponseBody Integer reduceQuantity(Long id, HttpServletRequest request, HttpSession session) {
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);	
		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if (cart != null) {
			CartItem cartItem = cartItemService.getByPrimaryKey(id);
			if (cartItem != null) {
				Integer quantity = cartItem.getQuantity();
				cartItem.setQuantity(quantity-1);
				cartItemService.updateByPrimaryKeySelective(cartItem);
				return quantity-1;
			}
		}	
		return null;
	}
	
}
