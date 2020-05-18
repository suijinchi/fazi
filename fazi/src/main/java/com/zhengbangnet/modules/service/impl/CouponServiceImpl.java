package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;

import com.zhengbangnet.common.page.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Coupon;
import com.zhengbangnet.modules.service.CouponService;
import com.zhengbangnet.modules.mapper.CouponMapper;

import java.util.*;

@Service("couponServiceImpl")
@Transactional(readOnly=true)
public class CouponServiceImpl extends BaseServiceImpl<Coupon,Long> implements CouponService{

	@Resource(name="couponMapper")
	private CouponMapper couponMapper;

	@Resource(name="couponMapper")
	public void setBaseDao(BaseMapper<Coupon,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public List<Coupon> findListByCart(Long memberId,Date now) {
		return couponMapper.findListByCart(memberId,now);
	}

	@Override
	public Boolean checkCoupon(Long couponId) {
		Integer check = couponMapper.checkCoupon(couponId);
		return check!=null?true:false;
	}
}