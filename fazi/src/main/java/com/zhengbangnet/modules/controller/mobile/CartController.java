package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.lock.ProductLock;
import com.zhengbangnet.modules.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author suijinchi
 *
 */
@Controller
@RequestMapping("/mobile/cart")
public class CartController extends AdminBaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;

	@Resource(name = "birthdayCardServiceImpl")
	private BirthdayCardService birthdayCardService;

	/**
	 * 跳转購物車页面
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);

		//清空下架商品
		cartService.clearProduct(member.getId());

		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if (cart != null) {
			//查询购物车中的平台商品
			CartItem c = new CartItem();
			c.setCartId(cart.getId());
			List<Map<String, Object>> cartItemList = cartItemService.getListByCartItem(c);
			model.addAttribute("cartItemList", cartItemList);
		}
		model.addAttribute("member", member);

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productCategoryId",100);//推荐商品
		params.put("isMarketable",1);
		List<HashMap<String, Object>> list = productService.findListByParams(params);
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Long id = (Long)map.get("id");
			ProductStock ps = productStockService.getByProductId(id);
			map.put("productStock",ps);
		}
		model.addAttribute("recommendList", list);

		List<BirthdayCard> birthdayCardList = birthdayCardService.findAll();
		model.addAttribute("birthdayCardList", birthdayCardList);

		return "/mobile/cart/list";
	}
	
	/**
	 * 添加商品到购物车
	 */
	@RequestMapping(value = "/addCart", method = RequestMethod.POST)
	public @ResponseBody Message addCart(Integer counts, Long skuId, Model model, HttpServletRequest request, HttpSession session) {

		if(counts==null||counts<0){
			counts = 1;
		}

		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);

		//查询商品库存
		ProductStock ps = productStockService.getByPrimaryKey(skuId);
		if(ps==null){
			return Message.error("商品不存在");
		}
		Product product = productService.getByPrimaryKey(ps.getProductId());
		if(product==null){
			return Message.error("商品不存在");
		}
		if(product.getIsMarketable()==0){
			return Message.error("该商品已下架");
		}

		if(product.getSellDate()!=null&&product.getSellDate().after(new Date())){
			return Message.error("还没有到抢购时间哦~"+ DateUtils.dateToString(product.getSellDate(),"yyyy-MM-dd HH:mm")+"可进行购买");
		}

		Long productId = product.getId();

		//生成购物车
		Cart cart = null;
		if (cartService.getByMemberId(member.getId()) != null) {
			cart = cartService.getByMemberId(member.getId());			
		} else {
			cart = new Cart();
			cart.setCreateDate(new Date());
			cart.setModifyDate(new Date());
			cart.setMemberId(member.getId());
			cartService.insert(cart);
		}

		Integer sumBuyNum = 0;
		//获取购物车ID
		Long cartId = cart.getId();
		//购物车添加商品
		CartItem cartItem = null;
		CartItem c = new CartItem();
		c.setCartId(cartId);
		c.setProductId(productId);
		c.setProductStockId(skuId);
		CartItem dbCartItem = cartItemService.getByCartItem(c);
		if(dbCartItem!=null) {
			sumBuyNum = dbCartItem.getQuantity() + counts;
		}else{
			sumBuyNum = counts;
		}

		/*
		Object lock = ProductLock.getLock(ps.getId());
		synchronized (lock){
		}
		*/
		ps = productStockService.getByPrimaryKey(skuId);
		if(ps.getStock()==0){
			return Message.error("商品库存不足");
		}
		if (sumBuyNum > ps.getStock()) {
			return Message.error("购物车数量已大于商品库存");
		}
		if(dbCartItem!=null){
			cartItem = cartItemService.getByCartItem(c);
			cartItem.setModifeDate(new Date());
			cartItem.setQuantity(cartItem.getQuantity() + counts);
			cartItemService.updateByPrimaryKeySelective(cartItem);
		} else {
			cartItem = new CartItem();
			cartItem.setCreateDate(new Date());
			cartItem.setModifeDate(new Date());
			cartItem.setCartId(cartId);
			cartItem.setProductId(productId);
			cartItem.setQuantity(counts);
			cartItem.setProductStockId(skuId);
			cartItemService.insert(cartItem);
		}
		return Message.success("添加成功");
	}

	/**
	 * 购物车数量
	 */
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	public @ResponseBody Long cartCount(Integer counts, Long productId,Long skuId, Model model, HttpServletRequest request, HttpSession session) {
		Long memberId = memberService.getCurrentId(request);
		Long count = cartService.getProductCount(memberId);
		return count;
	}

	@RequestMapping(value = "/addQuantity", method = RequestMethod.POST)
	public @ResponseBody Message addQuantity(Long id, HttpServletRequest request, HttpSession session) {
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);
		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if(cart==null){
			return Message.error("购物车不存在");
		}
		CartItem cartItem = cartItemService.getByPrimaryKey(id);
		if(cartItem==null){
			return Message.error("商品不存在");
		}
		Integer quantity = cartItem.getQuantity()+1;
		ProductStock ps = productStockService.getByPrimaryKey(cartItem.getProductStockId());
		if (quantity > ps.getStock()) {
			return Message.error("购物车数量已大于商品库存");
		}
		cartItem.setQuantity(quantity);
		cartItemService.updateByPrimaryKeySelective(cartItem);
		Message success = Message.success("查询成功");
		success.putDatas("quantity",quantity);
		return success;

	}

	/**
	 * 减少购物车中某个商品的数量（-1）
	 */
	@RequestMapping(value = "/reduceQuantity", method = RequestMethod.POST)
	public @ResponseBody Message reduceQuantity(Long id, HttpServletRequest request, HttpSession session) {
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);
		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if(cart==null){
			return Message.error("购物车不存在");
		}
		CartItem cartItem = cartItemService.getByPrimaryKey(id);
		if(cartItem==null){
			return Message.error("商品不存在");
		}
		Integer quantity = cartItem.getQuantity()-1;
		if(quantity<=0){
			quantity = 1;
		}
		ProductStock ps = productStockService.getByPrimaryKey(cartItem.getProductStockId());
		if (quantity > ps.getStock()) {
			cartItem.setQuantity(ps.getStock());
			cartItemService.updateByPrimaryKeySelective(cartItem);
			return Message.error("购物车数量已大于商品库存");
		}
		cartItem.setQuantity(quantity);
		cartItemService.updateByPrimaryKeySelective(cartItem);
		Message success = Message.success("查询成功");
		success.putDatas("quantity",quantity);
		return success;
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

	@RequestMapping(value = "/birthdayCard", method = RequestMethod.POST)
	public @ResponseBody Message birthdayCard(Long cartItemId,String content, HttpServletRequest request, HttpSession session) {
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);
		//查询当前登录用户的购物车
		Cart cart = cartService.getByMemberId(member.getId());
		if (cart != null) {
			CartItem ci = cartItemService.getByPrimaryKey(cartItemId);
			ci.setBirthdayCard(content);
			cartItemService.updateByPrimaryKey(ci);
			return Message.success("成功");
		}
		return Message.error("失败");
	}

}
