package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ProductImage;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * ProductImageMapper数据库操作接口类
 * 
 **/

@Repository("productImageMapper")
public interface ProductImageMapper extends BaseMapper<ProductImage,Long>{

	/**
	 * 根据商品ID删除商品图片
	 * @param id
	 */
	void deleteByProductId(Long productId);

	/**
	 * 根据商品ID查询商品图片
	 * @param id
	 * @return
	 */
	List<ProductImage> getByProductId(Long productId);

}