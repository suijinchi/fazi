package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Cart;
import com.zhengbangnet.modules.service.CartService;
import com.zhengbangnet.modules.mapper.CartMapper;

@Service("cartServiceImpl")
@Transactional(readOnly=true)
public class CartServiceImpl extends BaseServiceImpl<Cart,Long> implements CartService{

	@Resource(name="cartMapper")
	private CartMapper cartMapper;

	@Resource(name="cartMapper")
	public void setBaseDao(BaseMapper<Cart,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	/**
	 * 根据会员ID查询购物车
	 */
	@Override
	public Cart getByMemberId(Long id) {
		return cartMapper.getByMemberId(id);
	}

	@Override
	public Long getProductCount(Long memberId) {
		return cartMapper.getProductCount(memberId);
	}

	@Override
	@Transactional
	public void clearProduct(Long memberId) {
		cartMapper.clearProduct(memberId);
	}
}