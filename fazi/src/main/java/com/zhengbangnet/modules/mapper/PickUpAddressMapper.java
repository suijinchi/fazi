package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.PickUpAddress;
import org.springframework.stereotype.Repository;

/**
 * 
 * PickUpAddressMapper数据库操作接口类
 * 
 **/

@Repository("pickUpAddressMapper")
public interface PickUpAddressMapper extends BaseMapper<PickUpAddress,Long>{



}