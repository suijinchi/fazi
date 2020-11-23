package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ProductCategory;
import org.springframework.stereotype.Repository;

/**
 * 
 * ProductCategoryMapper数据库操作接口类
 * 
 **/

@Repository("productCategoryMapper")
public interface ProductCategoryMapper extends BaseMapper<ProductCategory,Long>{



}