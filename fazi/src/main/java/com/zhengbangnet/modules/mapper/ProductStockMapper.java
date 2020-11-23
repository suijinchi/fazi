package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ProductStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * ProductStockMapper数据库操作接口类
 * 
 **/

@Repository("productStockMapper")
public interface ProductStockMapper extends BaseMapper<ProductStock,Long>{

    /**
     * 根据商品ID获取商品规格
     * @param productStockId
     * @return
     */
    List<Map<String,Object>> getSpecByProductStockId(Long productStockId);

    /**
     * 根据商品id查询
     * @param productId
     * @return
     */
    ProductStock getByProductId(Long productId);

    /**
     * 查询商品规格值id
     * @param productId
     * @return
     */
    List<Long> getProductStockSpecValue(Long productId);

    /**
     * 更新库存
     * @param productStockId
     * @param queryStock
     * @param querySoldout
     * @param count
     * @return
     */
    Integer updateStock(@Param("productStockId") Long productStockId, @Param("queryStock")Integer queryStock, @Param("querySoldout")Integer querySoldout, @Param("count")Integer count);

    /**
     * 根据productId删除
     * @param productId
     */
    void deleteByProductId(Long productId);

    /**
     * 根据商品查询
     * @param productId
     * @return
     */
    List<ProductStock> findByProductId(Long productId);

    /**
     * 根据规格名称和productId查询
     * @param params
     * @return
     */
    ProductStock getBySpecNameAndProductId(Map<String, Object> params);

    void updateVoidStatus(Long id);
}