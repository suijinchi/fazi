package com.zhengbangnet.modules.service.impl;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Area;
import com.zhengbangnet.modules.service.AreaService;
import com.zhengbangnet.modules.mapper.AreaMapper;

@Service("areaServiceImpl")
@Transactional(readOnly=true)
public class AreaServiceImpl extends BaseServiceImpl<Area,Long> implements AreaService{

	@Resource(name="areaMapper")
	private AreaMapper areaMapper;

	@Resource(name="areaMapper")
	public void setBaseDao(BaseMapper<Area,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
	
	@Transactional(readOnly = true)
	public List<Area> findRoots() {
		return areaMapper.findRoots();
	}

	@Override
	public List<Area> findChildren(Long parentId) {
		return areaMapper.findChildren(parentId);
	}


	@Override
	public Area getByName(String areaName) {
		
		return areaMapper.findByName(areaName);
	}

	@Override
	public Area findByFullName(String fullName) {
		
		return areaMapper.findByFullName(fullName);
	}

	@Override
	public List<Area> findProvince(String province) {
		
		return areaMapper.findProvice(province);
	}

	@Override
	public List<Area> findCity(String city) {
		
		return areaMapper.findCity(city);
	}

	@Override
	public List<Area> findParentId(Long parentId) {
		
		return areaMapper.findParentId(parentId);
	}
	
}