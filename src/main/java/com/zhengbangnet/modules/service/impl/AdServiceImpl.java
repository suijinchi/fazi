package com.zhengbangnet.modules.service.impl;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Ad;
import com.zhengbangnet.modules.service.AdService;
import com.zhengbangnet.modules.mapper.AdMapper;

@Service("adServiceImpl")
@Transactional(readOnly=true)
public class AdServiceImpl extends BaseServiceImpl<Ad,Long> implements AdService{

	@Resource(name="adMapper")
	private AdMapper adMapper;

	@Resource(name="adMapper")
	public void setBaseDao(BaseMapper<Ad,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public List<Ad> getBanner() {
		return adMapper.getBanner();
	}

	@Override
	public List<Ad> getBannerByShopId(Long shopId) {
		return adMapper.getBannerByShopId(shopId);
	}
}