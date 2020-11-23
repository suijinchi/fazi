package com.zhengbangnet.modules.service.impl;

import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.BalanceRecord;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.Orders;
import com.zhengbangnet.modules.entity.Refund;
import com.zhengbangnet.modules.mapper.BalanceRecordMapper;
import com.zhengbangnet.modules.mapper.MemberMapper;
import com.zhengbangnet.modules.mapper.OrdersMapper;
import com.zhengbangnet.modules.mapper.RefundMapper;
import com.zhengbangnet.modules.service.RefundService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin4j.pay.refund.WxRefundReqInfo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service("refundServiceImpl")
@Transactional(readOnly=true)
public class RefundServiceImpl extends BaseServiceImpl<Refund,Long> implements RefundService {

	@Resource(name="refundMapper")
	private RefundMapper refundMapper;

	@Resource(name="ordersMapper")
	private OrdersMapper ordersMapper;

	@Resource(name="memberMapper")
	private MemberMapper memberMapper;

	@Resource(name = "balanceRecordMapper")
	private BalanceRecordMapper balanceRecordMapper;

	@Resource(name="refundMapper")
	public void setBaseDao(BaseMapper<Refund,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public Refund getBySn(String sn) {
		return refundMapper.getBySn(sn);
	}

	@Override
	@Transactional
	public void handleWxRefund(Long refundId, WxRefundReqInfo info) {
		Refund refund = refundMapper.getByPrimaryKey(refundId);
		//更新退款数据
		Refund re = new Refund();
		re.setId(refundId);
		re.setStatus(Refund.REFUNED);//已到账
		re.setRefundDate(new Date());
		re.setRefundDate(DateUtils.stringToDate(info.getSuccess_time(), DateUtils.patternD));
		refundMapper.updateByPrimaryKeySelective(re);
//		Member member = memberMapper.getByPrimaryKey(order.getMemberId());
		//更新订单状态
		Long ordersId = refund.getOrdersId();
		Orders order = ordersMapper.getByPrimaryKey(ordersId);
		order.setModifyDate(new Date());
		order.setOrdersStatus(6);//已退款
		ordersMapper.updateByPrimaryKeySelective(order);
	}
}