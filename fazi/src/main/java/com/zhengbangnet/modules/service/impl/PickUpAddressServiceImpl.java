package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.PickUpAddress;
import com.zhengbangnet.modules.service.PickUpAddressService;
import com.zhengbangnet.modules.mapper.PickUpAddressMapper;

@Service("pickUpAddressServiceImpl")
@Transactional(readOnly=true)
public class PickUpAddressServiceImpl extends BaseServiceImpl<PickUpAddress,Long> implements PickUpAddressService{

	@Resource(name="pickUpAddressMapper")
	private PickUpAddressMapper pickUpAddressMapper;

	@Resource(name="pickUpAddressMapper")
	public void setBaseDao(BaseMapper<PickUpAddress,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}