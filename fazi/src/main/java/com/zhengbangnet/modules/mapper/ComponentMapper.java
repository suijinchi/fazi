package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * ComponentMapper数据库操作接口类
 * 
 **/

@Repository("componentMapper")
public interface ComponentMapper extends BaseMapper<Component,Long>{


    List<Component> findListByProductId(Long productId);

}