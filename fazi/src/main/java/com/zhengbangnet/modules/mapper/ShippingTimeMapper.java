package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ShippingTime;
import org.springframework.stereotype.Repository;

/**
 * 
 * ShippingTimeMapper数据库操作接口类
 * 
 **/

@Repository("shippingTimeMapper")
public interface ShippingTimeMapper extends BaseMapper<ShippingTime,Long>{

    ShippingTime getByTime(String pstime);
}