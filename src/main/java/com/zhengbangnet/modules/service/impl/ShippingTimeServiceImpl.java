package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ShippingTime;
import com.zhengbangnet.modules.service.ShippingTimeService;
import com.zhengbangnet.modules.mapper.ShippingTimeMapper;

@Service("shippingTimeServiceImpl")
@Transactional(readOnly=true)
public class ShippingTimeServiceImpl extends BaseServiceImpl<ShippingTime,Long> implements ShippingTimeService{

	@Resource(name="shippingTimeMapper")
	private ShippingTimeMapper shippingTimeMapper;

	@Resource(name="shippingTimeMapper")
	public void setBaseDao(BaseMapper<ShippingTime,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public ShippingTime getByTime(String pstime) {
		return shippingTimeMapper.getByTime(pstime);
	}
}