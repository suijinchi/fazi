package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;

import com.zhengbangnet.modules.entity.ProductImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ProductComponent;
import com.zhengbangnet.modules.service.ProductComponentService;
import com.zhengbangnet.modules.mapper.ProductComponentMapper;

import java.util.List;

@Service("productComponentServiceImpl")
@Transactional(readOnly=false)
public class ProductComponentServiceImpl extends BaseServiceImpl<ProductComponent,Long> implements ProductComponentService{

	@Resource(name="productComponentMapper")
	private ProductComponentMapper productComponentMapper;

	@Resource(name="productComponentMapper")
	public void setBaseDao(BaseMapper<ProductComponent,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public void save(Long[] componentIds, Long id) {
		for(int i=0; i<componentIds.length; i++){
			ProductComponent pc = new ProductComponent();
			pc.setProductId(id);
			pc.setComponentId(componentIds[i]);
			productComponentMapper.insert(pc);
		}
	}

	@Override
	public List<ProductComponent> getByComponentId(Long componentId) {
		return productComponentMapper.getByComponentId(componentId);
	}

	@Override
	public void deleteByProductId(Long productId) {
		productComponentMapper.deleteByProductId(productId);
	}

	@Override
	public List<ProductComponent> getByProductId(Long productId) {
		return productComponentMapper.getByProductId(productId);
	}
}