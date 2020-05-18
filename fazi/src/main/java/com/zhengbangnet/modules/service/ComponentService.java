package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Component;

import java.util.List;

public interface ComponentService extends BaseService<Component,Long> {

    /**
     * 根据商品查询成分
     * @param productId
     * @return
     */
    List<Component> findListByProductId(Long productId);}