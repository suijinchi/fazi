package com.zhengbangnet.modules.service.impl;

import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Orders;
import com.zhengbangnet.modules.entity.OrdersItem;
import com.zhengbangnet.modules.entity.Product;
import com.zhengbangnet.modules.entity.ProductStock;
import com.zhengbangnet.modules.mapper.*;
import com.zhengbangnet.modules.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly=false)
@Service("productServiceImpl")
public class ProductServiceImpl extends BaseServiceImpl<Product,Long> implements ProductService{

	@Resource(name="productMapper")
	private ProductMapper productMapper;

	@Resource(name="ordersMapper")
	private OrdersMapper ordersMapper;

	@Resource(name="ordersItemMapper")
	private OrdersItemMapper ordersItemMapper;

	@Resource(name="productStockMapper")
	private ProductStockMapper productStockMapper;

	@Resource(name="productStockSpecNameValueMapper")
	private ProductStockSpecNameValueMapper productStockSpecNameValueMapper;

	@Resource(name="productMapper")
	public void setBaseDao(BaseMapper<Product,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
	
	@Override
	public void productUpAndDown(Long[] ids, Integer i) {
		for(Long id : ids){
			if (i==1) {
				productMapper.updateProductUp(id);
			} else {
				productMapper.updateProductDown(id);
			}
		}	
	}

	@Override
	public Product getBySn(String sn) {
		return productMapper.getBySn(sn);
	}

	@Override
	public List<Map<String, Object>> getByProductCategoryId(Long productCategoryId) {
		return productMapper.getByProductCategoryId(productCategoryId);
	}

	@Override
	public Long findCountsByParams(Map<String, Object> params) {
		return productMapper.findCountsByParams(params);
	}

	@Override
	public List<Map<String, Object>> findSaleSort() {
		return productMapper.findSaleSort();
	}

	@Override
	public List<Map<String, Object>> findSpecNameAndValue(Long productId) {
		List<Map<String, Object>> specName = productMapper.findSpecName(productId);
		for(Map<String,Object> sn : specName){
			String name = (String)sn.get("name");
			Long id = (Long)sn.get("id");
			List<Map<String, Object>> specValue = productMapper.findSpecValue(productId,id);
			sn.put("specValue",specValue);
		}
		return specName;
	}

	@Override
	public List<Map<String, Object>> findSpecNameAndValue(Long productId, Long[] specValues) {
		List<Map<String, Object>> specName = productMapper.findSpecName(productId);
		for(Map<String,Object> sn : specName){
			String name = (String)sn.get("name");
			Long id = (Long)sn.get("id");
			List<Long> specValue = productMapper.findSpecValueId(productId,id,specValues);
			sn.put("specValue",specValue);
		}
		return specName;
	}

    @Override
    public List<HashMap<String, Object>> findSpecProduct(Long productId) {
	    Map<String,Object> params = new HashMap<String,Object>();
	    params.put("productId",productId);
		params.put("isVoid",0);
		params.put("isDisabled",0);
        List<HashMap<String, Object>> psList = productStockMapper.findListByParams(params);
        for(Map<String,Object> map:psList){
            Long productStockId = (Long)map.get("id");
            params.clear();
            params.put("productStockId",productStockId);
            List<HashMap<String, Object>> pssnv = productStockSpecNameValueMapper.findListByParams(params);
            map.put("pssnv",pssnv);
        }
        return psList;
    }

}