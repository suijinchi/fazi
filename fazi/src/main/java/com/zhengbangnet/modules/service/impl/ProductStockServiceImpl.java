package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ProductStock;
import com.zhengbangnet.modules.service.ProductStockService;
import com.zhengbangnet.modules.mapper.ProductStockMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productStockServiceImpl")
@Transactional(readOnly=false)
public class ProductStockServiceImpl extends BaseServiceImpl<ProductStock,Long> implements ProductStockService{

	@Resource(name="productStockMapper")
	private ProductStockMapper productStockMapper;

	@Resource(name="productStockMapper")
	public void setBaseDao(BaseMapper<ProductStock,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

    @Override
    public List<Long> getProductStockSpecValue(Long productId) {
        return productStockMapper.getProductStockSpecValue(productId);
    }

	@Override
	public List<Map<String, Object>> getSpecByProductStockId(Long productStockId) {
		return productStockMapper.getSpecByProductStockId(productStockId);
	}

	@Override
	public ProductStock getByProductId(Long productId) {
		return productStockMapper.getByProductId(productId);
	}

	@Override
	public List<ProductStock> findByProductId(Long productId) {
		return productStockMapper.findByProductId(productId);
	}

	//Spring的事务管理默认是针对unchecked exception回滚，也就是默认对Error异常和RuntimeException异常以及其子类进行事务回滚，且必须对抛出异常，若使用try-catch对其异常捕获则不会进行回滚！（Error异常和RuntimeException异常抛出时不需要方法调用throws或try-catch语句）；
	@Override
	public void updateStock(Long productStockId,Integer queryStock,Integer querySoldout,Integer count) {
		Integer num = productStockMapper.updateStock(productStockId,queryStock,querySoldout,count);
		if(num==0){
			throw new RuntimeException("更新库存操作失败");
		}
	}

	@Override
	public void deleteByProductId(Long productId) {
		productStockMapper.deleteByProductId(productId);
	}

	@Override
	public ProductStock getBySpecNameAndProductId(String specName, Long productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("specName", specName);
		params.put("productId", productId);
		return productStockMapper.getBySpecNameAndProductId(params);
	}

	/**
	 * 更新之前商品的状态为作废
	 * @param id
	 */
	public void updateVoidStatus(Long id) {
		productStockMapper.updateVoidStatus(id);
	}
}