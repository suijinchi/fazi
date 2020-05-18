package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * TagMapper数据库操作接口类
 * 
 **/

@Repository("tagMapper")
public interface TagMapper extends BaseMapper<Tag,Long>{


    /**
     * 根据type查询
     * @param type
     * @return
     */
    List<Tag> findByType(Integer type);
}