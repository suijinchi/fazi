package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.AdLocation;
import com.zhengbangnet.modules.service.AdLocationService;
import com.zhengbangnet.modules.mapper.AdLocationMapper;

@Service("adLocationServiceImpl")
@Transactional(readOnly=true)
public class AdLocationServiceImpl extends BaseServiceImpl<AdLocation,Long> implements AdLocationService{

	@Resource(name="adLocationMapper")
	private AdLocationMapper adLocationMapper;

	@Resource(name="adLocationMapper")
	public void setBaseDao(BaseMapper<AdLocation,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}