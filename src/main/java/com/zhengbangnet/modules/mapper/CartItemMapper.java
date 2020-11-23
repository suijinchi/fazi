package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.CartItem;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * CartItemMapper数据库操作接口类
 * @author suijinchi
 *
 */
@Repository("cartItemMapper")
public interface CartItemMapper extends BaseMapper<CartItem,Long>{
	/**
	 * 根据购物车ID和商品ID查询
	 * @param cartItem
	 * @return
	 */
	CartItem getByCartItem(CartItem cartItem);

	/**
	 * 根据购物车ID和商品ID查询
	 * @param cartItem
	 * @return
	 */
	List<Map<String, Object>> getListByCartItem(CartItem cartItem);

	/**
	 * 根据购物车ID查询店铺列表
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
	List<Map<String, Object>> getByCartItemId(@Param("ids")Long ids[]);

	/**
	 * 根据ids[]查询店铺列表
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> getShopListById(@Param("ids")Long[] ids);

	/**
	 * 根据购物车ID和商品ID从购物车明细中获取商品数量
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	CartItem getQuantityByCartIdAndProductId(Map<String, Object> params);

	/**
	 * 根据购物车ID和商品ID从购物车明细中删除商品
	 * @author suijinchi
	 * @param map
	 */
	void deleteByCartIdAndProductId(Map<String, Object> map);

	/**
	 * 根据购物车ID查询
	 * @author suijinchi
	 * @param cartId
	 * @return
	 */
	List<CartItem> getByCartId(Long cartId);

}