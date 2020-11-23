package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.RechargeType;
import com.zhengbangnet.modules.service.RechargeTypeService;
import com.zhengbangnet.modules.mapper.RechargeTypeMapper;

@Service("rechargeTypeServiceImpl")
@Transactional(readOnly=true)
public class RechargeTypeServiceImpl extends BaseServiceImpl<RechargeType,Long> implements RechargeTypeService{

	@Resource(name="rechargeTypeMapper")
	private RechargeTypeMapper rechargeTypeMapper;

	@Resource(name="rechargeTypeMapper")
	public void setBaseDao(BaseMapper<RechargeType,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}