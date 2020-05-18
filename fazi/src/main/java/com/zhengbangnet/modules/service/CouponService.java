package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Coupon;

import java.util.Date;
import java.util.List;

public interface CouponService extends BaseService<Coupon,Long> {


    List<Coupon> findListByCart(Long id,Date now);

    /**
     * 检测购物车中的优惠券是否符合要求
     * @param couponId
     * @return true:符合 false:不符合
     */
    Boolean checkCoupon(Long couponId);
}