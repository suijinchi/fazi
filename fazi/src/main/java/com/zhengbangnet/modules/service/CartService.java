package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Cart;

public interface CartService extends BaseService<Cart,Long> {

	/**
	 * 根据会员ID查询购物车
	 * @param id
	 * @return
	 */
	Cart getByMemberId(Long id);

	/**
	 * 查询购物车商品数量
	 * @param memberId
	 * @return
	 */
    Long getProductCount(Long memberId);

    void clearProduct(Long id);
}