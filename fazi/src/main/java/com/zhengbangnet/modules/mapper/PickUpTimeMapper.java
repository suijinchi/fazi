package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.PickUpTime;
import org.springframework.stereotype.Repository;

/**
 * 
 * PickUpTimeMapper数据库操作接口类
 * 
 **/

@Repository("pickUpTimeMapper")
public interface PickUpTimeMapper extends BaseMapper<PickUpTime,Long>{



}