package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.AdLocation;
import org.springframework.stereotype.Repository;

/**
 * 
 * AdLocationMapper数据库操作接口类
 * 
 **/

@Repository("adLocationMapper")
public interface AdLocationMapper extends BaseMapper<AdLocation,Long>{



}