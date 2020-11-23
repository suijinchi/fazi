package com.zhengbangnet.modules.service.impl;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ProductImage;
import com.zhengbangnet.modules.entity.ProductTag;
import com.zhengbangnet.modules.service.ProductImageService;
import com.zhengbangnet.modules.mapper.ProductImageMapper;

@Service("productImageServiceImpl")
@Transactional(readOnly=false)
public class ProductImageServiceImpl extends BaseServiceImpl<ProductImage,Long> implements ProductImageService{

	@Resource(name="productImageMapper")
	private ProductImageMapper productImageMapper;

	@Resource(name="productImageMapper")
	public void setBaseDao(BaseMapper<ProductImage,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public void save(String[] productImages, Integer[] imgOrders, Long id) {
		// TODO Auto-generated method stub
		for(int i=0; i<productImages.length; i++){
			ProductImage pi = new ProductImage();
			pi.setCreateDate(new Date());
			pi.setModifyDate(new Date());
			pi.setImgUrl(productImages[i]);
			if (imgOrders != null && imgOrders.length > 1) {
				pi.setOrders(imgOrders[i]);
			}			
			pi.setProductId(id);
			productImageMapper.insert(pi);
		}		
	}

	@Override
	public void deleteByProductId(Long id) {
		// TODO Auto-generated method stub
		productImageMapper.deleteByProductId(id);
	}

	@Override
	public List<ProductImage> getByProductId(Long id) {
		// TODO Auto-generated method stub
		return productImageMapper.getByProductId(id);
	}

}