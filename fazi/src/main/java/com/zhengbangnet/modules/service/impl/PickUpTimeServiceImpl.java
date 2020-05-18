package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.PickUpTime;
import com.zhengbangnet.modules.service.PickUpTimeService;
import com.zhengbangnet.modules.mapper.PickUpTimeMapper;

@Service("pickUpTimeServiceImpl")
@Transactional(readOnly=true)
public class PickUpTimeServiceImpl extends BaseServiceImpl<PickUpTime,Long> implements PickUpTimeService{

	@Resource(name="pickUpTimeMapper")
	private PickUpTimeMapper pickUpTimeMapper;

	@Resource(name="pickUpTimeMapper")
	public void setBaseDao(BaseMapper<PickUpTime,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}