package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ProductCategory;
import com.zhengbangnet.modules.service.ProductCategoryService;
import com.zhengbangnet.modules.mapper.ProductCategoryMapper;

@Service("productCategoryServiceImpl")
@Transactional(readOnly=true)
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory,Long> implements ProductCategoryService{

	@Resource(name="productCategoryMapper")
	private ProductCategoryMapper productCategoryMapper;

	@Resource(name="productCategoryMapper")
	public void setBaseDao(BaseMapper<ProductCategory,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}