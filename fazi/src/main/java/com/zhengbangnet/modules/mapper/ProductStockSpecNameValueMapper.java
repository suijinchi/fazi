package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ProductStockSpecNameValue;
import com.zhengbangnet.modules.service.ProductStockSpecNameValueService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * ProductStockSpecNameValueMapper数据库操作接口类
 * 
 **/

@Repository("productStockSpecNameValueMapper")
public interface ProductStockSpecNameValueMapper extends BaseMapper<ProductStockSpecNameValue,Long>{

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