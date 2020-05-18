package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ProductComponent;
import com.zhengbangnet.modules.entity.ProductImage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * ProductComponentMapper数据库操作接口类
 * 
 **/

@Repository("productComponentMapper")
public interface ProductComponentMapper extends BaseMapper<ProductComponent,Long>{

    /**
     * 根据componentId查询
     * @param componentId
     * @return
     */
    List<ProductComponent> getByComponentId(Long componentId);

    /**
     * 根据商品ID删除
     * @param productId
     */
    void deleteByProductId(Long productId);

    /**
     * 根据商品ID查询
     * @param productId
     * @return
     */
    List<ProductComponent> getByProductId(Long productId);
}