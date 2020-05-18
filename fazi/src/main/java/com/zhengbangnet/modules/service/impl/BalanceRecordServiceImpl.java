package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.BalanceRecord;
import com.zhengbangnet.modules.service.BalanceRecordService;
import com.zhengbangnet.modules.mapper.BalanceRecordMapper;

@Service("balanceRecordServiceImpl")
@Transactional(readOnly=true)
public class BalanceRecordServiceImpl extends BaseServiceImpl<BalanceRecord,Long> implements BalanceRecordService{

	@Resource(name="balanceRecordMapper")
	private BalanceRecordMapper balanceRecordMapper;

	@Resource(name="balanceRecordMapper")
	public void setBaseDao(BaseMapper<BalanceRecord,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}