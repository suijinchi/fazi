package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Sign;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * SignMapper数据库操作接口类
 * 
 **/

@Repository("signMapper")
public interface SignMapper extends BaseMapper<Sign,Long>{


    List<Map<String,Object>> findDateByMember(Map<String, Object> params);
}