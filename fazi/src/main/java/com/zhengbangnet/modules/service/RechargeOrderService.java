package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.RechargeOrder;

import java.util.Date;

public interface RechargeOrderService extends BaseService<RechargeOrder,Long> {

    void recharge(String id, String thirdSn, Date payDate);

}