package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.SpecValue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * SpecValueMapper数据库操作接口类
 * 
 **/

@Repository("specValueMapper")
public interface SpecValueMapper extends BaseMapper<SpecValue,Long>{

    List<SpecValue> getBySpecNameId(Long specNameId);

    void deleteBySpecNameId(Long specNameId);
}