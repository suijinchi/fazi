package com.zhengbangnet.modules.service.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Cart;
import com.zhengbangnet.modules.entity.CartItem;
import com.zhengbangnet.modules.service.CartItemService;
import com.zhengbangnet.modules.mapper.CartItemMapper;

/**
 * 
 * @author suijinchi
 *
 */
@Service("cartItemServiceImpl")
@Transactional(readOnly=false)
public class CartItemServiceImpl extends BaseServiceImpl<CartItem,Long> implements CartItemService{

	@Resource(name="cartItemMapper")
	private CartItemMapper cartItemMapper;

	@Resource(name="cartItemMapper")
	public void setBaseDao(BaseMapper<CartItem,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	/**
	 * 根据购物车ID和商品ID查询
	 */
	@Override
	public CartItem getByCartItem(CartItem cartItem) {
		return cartItemMapper.getByCartItem(cartItem);
	}
	
	/**
	 * 根据购物车ID和商品ID查询
	 */
	@Override
	public List<Map<String, Object>> getListByCartItem(CartItem cartItem) {
		return cartItemMapper.getListByCartItem(cartItem);
	}

	/**
	 * 查询购物车中店铺列表
	 */
	@Override
	public List<Map<String, Object>> getShopList(Long carId) {
		return cartItemMapper.getShopList(carId);
	}

	/**
	 * 删除购物车中全部平台商品
	 */
	@Override
	public void deleteAll() {
		cartItemMapper.deleteAll();
	}

	/**
	 * 删除购物车中某个店铺全部商品
	 */
	@Override
	public void deleteShop(Long shopId) {
		cartItemMapper.deleteShop(shopId);
	}

	/**
	 * 根据主键连接查询商品信息
	 */
	@Override
	public List<Map<String, Object>> getByCartItemId(Long ids[]) {
		return cartItemMapper.getByCartItemId(ids);
	}

	/**
	 * 根据ids[]查询店铺列表
	 */
	@Override
	public List<Map<String, Object>> getShopListById(Long[] ids) {
		return cartItemMapper.getShopListById(ids);
	}

	/**
	 * 根据购物车ID查询
	 * @author suijinchi
	 * @param cart
	 * @return
	 */
	@Override
	public List<CartItem> getByCartId(Long cartId) {
		return cartItemMapper.getByCartId(cartId);
	}
}