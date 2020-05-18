package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.CouponType;
import com.zhengbangnet.modules.entity.CouponTypeProduct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * CouponTypeProductMapper数据库操作接口类
 * 
 **/

@Repository("couponTypeProductMapper")
public interface CouponTypeProductMapper extends BaseMapper<CouponTypeProduct,Long>{

}