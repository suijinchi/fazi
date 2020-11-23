package com.zhengbangnet.modules.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Receiver;
import com.zhengbangnet.modules.service.ReceiverService;
import com.zhengbangnet.modules.mapper.ReceiverMapper;

@Service("receiverServiceImpl")
@Transactional(readOnly = true)
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, Long>implements ReceiverService {

	@Resource(name = "receiverMapper")
	private ReceiverMapper receiverMapper;

	@Resource(name = "receiverMapper")
	public void setBaseDao(BaseMapper<Receiver, Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	/**
	 * 依据会员ID查询收货人地址信息 add by hawkbird date 2018-04-04
	 */
	@Override
	public List<Map<String, Object>> queryAddressByMemberId(long memberId) {
		return receiverMapper.queryAddressByMemberId(memberId);
	}

	/** 
	 * 依据会员ID取消默认地址 add by hawkbird date 2018-04-08
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public int updateNoDefaultByMemberId(long memberId) {		
		return receiverMapper.updateNoDefaultByMemberId(memberId);
	}

	@Override
	public Receiver getDefault(Long memberId) {
		return receiverMapper.getDefault(memberId);
	}


}