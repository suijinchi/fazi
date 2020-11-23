package com.zhengbangnet.modules.service;
import java.util.Date;
import java.util.Map;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Payment;

public interface PaymentService extends BaseService<Payment,Long> {

	/**
	 * 根据单号查询
	 * @param sn
	 * @return
	 */
	Payment getBySn(String sn);

	/**
	 * 处理支付
	 * @param id
	 * @param thirdSn
	 * @param payDate
	 */
	void handle(Long id,String thirdSn, Date payDate);

	Payment getBySuccess(Long ordersId,Integer type);
}