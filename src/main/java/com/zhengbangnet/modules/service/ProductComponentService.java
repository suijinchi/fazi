package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.ProductComponent;
import com.zhengbangnet.modules.entity.ProductImage;

import java.util.List;

public interface ProductComponentService extends BaseService<ProductComponent,Long> {

    void save(Long[] componentIds, Long id);

    /**
     * 根据属性ID查询
     * @param
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
     * @param
     * @return
     */
    List<ProductComponent> getByProductId(Long productId);
}