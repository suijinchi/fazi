package com.zhengbangnet.modules.service.impl;

import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Payment;
import com.zhengbangnet.modules.mapper.PaymentMapper;
import com.zhengbangnet.modules.service.OrdersService;
import com.zhengbangnet.modules.service.PaymentService;
import com.zhengbangnet.modules.service.RechargeOrderService;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service("paymentServiceImpl")
@Transactional(readOnly=true)
public class PaymentServiceImpl extends BaseServiceImpl<Payment,Long> implements PaymentService{

	@Resource(name="paymentMapper")
	private PaymentMapper paymentMapper;

	@Resource(name="ordersServiceImpl")
	private OrdersService ordersService;

	@Resource(name="rechargeOrderServiceImpl")
	private RechargeOrderService rechargeOrderService;

	@Resource(name="paymentMapper")
	public void setBaseDao(BaseMapper<Payment,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
	
	@Override
	public Payment getBySn(String sn) {
		return paymentMapper.getBySn(sn);
	}

	@Override
	@Transactional
	public void handle(Long id,String thirdSn, Date payDate) {
		
		Payment payment = paymentMapper.getByPrimaryKey(id);
		payment.setPaySn(thirdSn);
		payment.setPayDate(payDate);
		payment.setStatus(Payment.SUCCESS);
		paymentMapper.updateByPrimaryKeySelective(payment);
		
		//充值
		if(payment.getType().equals(Payment.TYPE_RECHARGE)){
			rechargeOrderService.recharge(payment.getPayId(),thirdSn,payDate);
		}else if(payment.getType().equals(Payment.TYPE_ORDERS)){
			String ordersIds = payment.getPayId();
			String[] oids = ordersIds.split(",");
			Long[] ids = (Long[]) ConvertUtils.convert(oids,Long.class);
			ordersService.payOrder(ids,thirdSn,payDate);
		}
	}

	@Override
	public Payment getBySuccess(Long ordersId,Integer type) {
		return paymentMapper.getBySuccess(ordersId,type);
	}


}