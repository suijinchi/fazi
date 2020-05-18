package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;

import com.zhengbangnet.modules.entity.BalanceRecord;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.mapper.BalanceRecordMapper;
import com.zhengbangnet.modules.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.RechargeOrder;
import com.zhengbangnet.modules.service.RechargeOrderService;
import com.zhengbangnet.modules.mapper.RechargeOrderMapper;

import java.math.BigDecimal;
import java.util.Date;

@Service("rechargeOrderServiceImpl")
@Transactional(readOnly=true)
public class RechargeOrderServiceImpl extends BaseServiceImpl<RechargeOrder,Long> implements RechargeOrderService{

	@Resource(name="rechargeOrderMapper")
	private RechargeOrderMapper rechargeOrderMapper;

	@Resource(name="memberMapper")
	private MemberMapper memberMapper;

	@Resource(name="balanceRecordMapper")
	private BalanceRecordMapper balanceRecordMapper;

	@Resource(name="rechargeOrderMapper")
	public void setBaseDao(BaseMapper<RechargeOrder,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public void recharge(String id, String thirdSn, Date payDate) {

		RechargeOrder ro = rechargeOrderMapper.getByPrimaryKey(Long.parseLong(id));
		ro.setStatus(RechargeOrder.PAID);
		ro.setPayDate(payDate);
		ro.setModifyDate(new Date());
		ro.setPaySn(thirdSn);
		rechargeOrderMapper.updateByPrimaryKeySelective(ro);

		//充值金额
		BigDecimal amount = ro.getAmount();
		BigDecimal giveAmount = ro.getGiveAmount();
		amount = amount.add(giveAmount);

		Long memberId = ro.getMemberId();
		Member member = memberMapper.getByPrimaryKey(memberId);
		member.setBalance(member.getBalance().add(amount));
		member.setHistoryBalance(member.getHistoryBalance().add(amount));
		memberMapper.updateByPrimaryKeySelective(member);

		String memo = "";
		if(giveAmount.compareTo(BigDecimal.ZERO)>0){
			memo = "(赠送"+giveAmount+"元)";
		}

		BalanceRecord br = new BalanceRecord();
		br.setCreateDate(new Date());
		br.setModifyDate(new Date());
		br.setMemberId(memberId);
		br.setBalance(amount);
		br.setMemo("余额充值"+memo);
		balanceRecordMapper.insertSelective(br);

	}

}