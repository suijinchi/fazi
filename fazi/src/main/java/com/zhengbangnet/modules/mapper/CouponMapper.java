package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 
 * CouponMapper数据库操作接口类
 * 
 **/

@Repository("couponMapper")
public interface CouponMapper extends BaseMapper<Coupon,Long>{

    List<Coupon> findListByCart(@Param("memberId") Long memberId,@Param("now") Date now);

    /**
     * 检测购物车中的优惠券是否符合要求
     * @param couponId
     * @return true:符合 false:不符合
     */
    Integer checkCoupon(Long couponId);

    /**
     * 通过订单id退回使用的券
     * @param ordersId
     * @return
     */
    void returnCouponByOrdersId(Long ordersId);
}