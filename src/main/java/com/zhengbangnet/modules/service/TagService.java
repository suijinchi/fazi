package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Tag;

import java.util.List;

public interface TagService extends BaseService<Tag,Long> {

    /**
     * 根据标签类型查询
     * @param type 标签类型
     * @return
     */
    List<Tag> findByType(Integer type);
}