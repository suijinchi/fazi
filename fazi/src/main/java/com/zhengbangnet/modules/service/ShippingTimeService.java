package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.ShippingTime;

public interface ShippingTimeService extends BaseService<ShippingTime,Long> {


    ShippingTime getByTime(String pstime);
}