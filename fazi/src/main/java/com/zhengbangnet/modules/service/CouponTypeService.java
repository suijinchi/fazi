package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.CouponType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CouponTypeService extends BaseService<CouponType,Long> {

    /**
     * 保存优惠券类型
     * @param couponType
     * @param ctp
     */
    void save(CouponType couponType, Long[] ctp);

    void update(CouponType ct, Long[] ctp);

    /**
     * 根据商品查询有效的优惠券
     * @param params
     * @return
     */
    List<CouponType> findValidListByParams(Map<String, Object> params);

    /**
     * 领取优惠券
     * @param couponTypeId
     * @param memberId
     */
    void getCoupon(Long couponTypeId, Long memberId);

    void deleteByPrimaryKeys(Long[] ids);

    Page<Map<String,Object>> findValidPageByParams(Map<String, Object> params);
}