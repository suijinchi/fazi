package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ProductStockSpecNameValue;
import com.zhengbangnet.modules.service.ProductStockSpecNameValueService;
import com.zhengbangnet.modules.mapper.ProductStockSpecNameValueMapper;

import java.util.List;

@Service("productStockSpecNameValueServiceImpl")
@Transactional(readOnly=false)
public class ProductStockSpecNameValueServiceImpl extends BaseServiceImpl<ProductStockSpecNameValue,Long> implements ProductStockSpecNameValueService{

	@Resource(name="productStockSpecNameValueMapper")
	private ProductStockSpecNameValueMapper productStockSpecNameValueMapper;

	@Resource(name="productStockSpecNameValueMapper")
	public void setBaseDao(BaseMapper<ProductStockSpecNameValue,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public List<ProductStockSpecNameValue> getByProductId(Long id) {
		return productStockSpecNameValueMapper.getByProductId(id);
	}

	@Override
	public void deleteByProductStockId(Long productStockId) {
		productStockSpecNameValueMapper.deleteByProductStockId(productStockId);
	}

	@Override
	public List<ProductStockSpecNameValue> getBySpecNameId(Long specNameId) {
		return productStockSpecNameValueMapper.getBySpecNameId(specNameId);
	}

	@Override
	public List<ProductStockSpecNameValue> getBySpecValueId(Long specValueId) {
		return productStockSpecNameValueMapper.getBySpecValueId(specValueId);
	}
}