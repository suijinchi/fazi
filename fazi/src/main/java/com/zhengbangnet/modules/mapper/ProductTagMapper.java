package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ProductTag;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * ProductTagMapper数据库操作接口类
 * 
 **/

@Repository("productTagMapper")
public interface ProductTagMapper extends BaseMapper<ProductTag,Long>{

	 /**  
     * 插入一个实体  
     */  
	Long insert(ProductTag entity) ;

	List<ProductTag> getByProductId(Long id);

	void deleteByProductId(Long id);

	/**
	 * 根据标签ID查询
	 * @param id 标签ID
	 * @return
	 */
    List<ProductTag> getByTagId(Long id);
}