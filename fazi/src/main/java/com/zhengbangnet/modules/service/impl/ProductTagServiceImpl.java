package com.zhengbangnet.modules.service.impl;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ProductTag;
import com.zhengbangnet.modules.service.ProductTagService;
import com.zhengbangnet.modules.mapper.ProductTagMapper;

@Service("productTagServiceImpl")
@Transactional(readOnly=false)
public class ProductTagServiceImpl extends BaseServiceImpl<ProductTag,Long> implements ProductTagService{

	@Resource(name="productTagMapper")
	private ProductTagMapper productTagMapper;

	@Resource(name="productTagMapper")
	public void setBaseDao(BaseMapper<ProductTag,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public void save(Long[] tagIds, Long id) {
		for(int i=0; i<tagIds.length; i++){
			ProductTag pt = new ProductTag();
			pt.setProductId(id);
			pt.setTagId(tagIds[i]);
			productTagMapper.insert(pt);
		}		
	}

	@Override
	public List<ProductTag> getByProductId(Long id) {
		return productTagMapper.getByProductId(id);
	}

	@Override
	public void deleteByProductId(Long id) {
		productTagMapper.deleteByProductId(id);
	}

	@Override
	public List<ProductTag> getByTagId(Long id) {
		return productTagMapper.getByTagId(id);
	}
}