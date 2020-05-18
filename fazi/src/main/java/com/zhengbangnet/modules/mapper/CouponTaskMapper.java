package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.CouponTask;
import org.springframework.stereotype.Repository;

/**
 * 
 * CouponTaskMapper数据库操作接口类
 * 
 **/

@Repository("couponTaskMapper")
public interface CouponTaskMapper extends BaseMapper<CouponTask,Long>{



}