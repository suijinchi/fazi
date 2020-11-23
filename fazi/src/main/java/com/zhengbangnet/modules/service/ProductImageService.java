package com.zhengbangnet.modules.service;
import java.util.List;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.ProductImage;

public interface ProductImageService extends BaseService<ProductImage,Long> {

	/**
	 * 保存商品图片
	 * @param productImages
	 * @param imgOrders
	 * @param id
	 */
	void save(String[] productImages, Integer[] imgOrders, Long id);

	/**
	 * 根据商品id删除商品图片
	 * @param id
	 */
	void deleteByProductId(Long id);

	/**
	 * 根据商品id查询商品图片
	 * @param id
	 * @return
	 */
	List<ProductImage> getByProductId(Long id);




}