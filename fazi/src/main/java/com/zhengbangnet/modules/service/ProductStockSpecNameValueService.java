package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.ProductStockSpecNameValue;

import java.util.List;

public interface ProductStockSpecNameValueService extends BaseService<ProductStockSpecNameValue,Long> {

    /**
     * 根据商品ID查询
     * @param id
     * @return
     */
    List<ProductStockSpecNameValue> getByProductId(Long id);

    /**
     * 根据productStockId删除
     * @param productStockId
     */
    void deleteByProductStockId(Long productStockId);

    List<ProductStockSpecNameValue> getBySpecNameId(Long specNameId);

    List<ProductStockSpecNameValue> getBySpecValueId(Long specValueId);
}