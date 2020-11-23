package com.zhengbangnet.modules.service;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Cart;
import com.zhengbangnet.modules.entity.CartItem;

public interface CartItemService extends BaseService<CartItem,Long> {

	/**
	 * 根据购物车ID和商品ID查询
	 * @param cartId
	 * @param productId
	 * @return
	 */
	CartItem getByCartItem(CartItem cartItem);
	
	/**
	 * 根据购物车ID和商品ID查询
	 * @param cartId
	 * @param productId
	 * @return
	 */
	List<Map<String, Object>> getListByCartItem(CartItem cartItem);

	/**
	 * 查询购物车中店铺列表
	 * @return
	 */
	List<Map<String, Object>> getShopList(Long carId);

	/**
	 * 删除购物车中全部平台商品
	 */
	void deleteAll();

	/**
	 * 删除购物车中某个店铺全部商品
	 * @param shopId
	 */
	void deleteShop(Long shopId);

	/**
	 * 根据主键连接查询商品信息
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getByCartItemId(Long ids[]);

	/**
	 * @param cartItemIds
	 * @return
	 */
	List<Map<String, Object>> getShopListById(Long[] ids);

	/**
	 *根据购物车ID查询
	 * @author suijinchi
	 * @param cart
	 * @return
	 */
	List<CartItem> getByCartId(Long cartId);


}