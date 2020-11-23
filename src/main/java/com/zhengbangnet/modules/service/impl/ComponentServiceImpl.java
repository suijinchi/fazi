package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;

import com.zhengbangnet.modules.entity.ProductComponent;
import com.zhengbangnet.modules.entity.ProductTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Component;
import com.zhengbangnet.modules.service.ComponentService;
import com.zhengbangnet.modules.mapper.ComponentMapper;

import java.util.List;

@Service("componentServiceImpl")
@Transactional(readOnly=true)
public class ComponentServiceImpl extends BaseServiceImpl<Component,Long> implements ComponentService{

	@Resource(name="componentMapper")
	private ComponentMapper componentMapper;

	@Resource(name="componentMapper")
	public void setBaseDao(BaseMapper<Component,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public List<Component> findListByProductId(Long productId) {
		return componentMapper.findListByProductId(productId);
	}
}