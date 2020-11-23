package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Cart;
import org.springframework.stereotype.Repository;

/**
 * 
 * CartMapper数据库操作接口类
 * 
 **/

@Repository("cartMapper")
public interface CartMapper extends BaseMapper<Cart,Long>{

	/**
	 * 根据会员ID查询购物车
	 * @param memberId
	 * @return
	 */
	Cart getByMemberId(Long memberId);

	/**
	 * 查询购物车商品数量
	 * @param memberId
	 * @return
	 */
    Long getProductCount(Long memberId);

    void clearProduct(Long memberId);
}