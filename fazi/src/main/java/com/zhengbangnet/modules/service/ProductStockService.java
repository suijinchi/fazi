package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.ProductStock;

import java.util.List;
import java.util.Map;

public interface ProductStockService extends BaseService<ProductStock,Long> {

    /**
     * 查询商品规格值id
     * @param productId
     * @return
     */
    List<Long> getProductStockSpecValue(Long productId);

    /**
     * 根据商品ID获取商品规格
     * @param productStockId
     * @return
     */
    List<Map<String,Object>> getSpecByProductStockId(Long productStockId);

    /**
     * 根据商品查询
     * @param productId
     * @return
     */
    ProductStock getByProductId(Long productId);

    /**
     * 根据商品查询
     * @param productId
     * @return
     */
    List<ProductStock> findByProductId(Long productId);

    /**
     * 更新库存业务
     * @param productStockId skuid
     * @param queryStock 查询出来的库存
     * @param querySoldout 查询出来的销量
     * @param count 更新数量
     * @return
     */
    void updateStock(Long productStockId,Integer queryStock,Integer querySoldout,Integer count);

    /**
     * 根据productId删除
     * @param productId
     */
    void deleteByProductId(Long productId);

    /**
     * 根据规格名称和productId查询
     * @param specName
     * @param productId
     * @return
     */
    ProductStock getBySpecNameAndProductId(String specName, Long productId);

    /**
     * 更新之前商品的状态为作废
     * @param id
     */
    void updateVoidStatus(Long id);
}