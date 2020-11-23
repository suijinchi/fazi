package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.RechargeOrder;
import org.springframework.stereotype.Repository;

/**
 * 
 * RechargeOrderMapper数据库操作接口类
 * 
 **/

@Repository("rechargeOrderMapper")
public interface RechargeOrderMapper extends BaseMapper<RechargeOrder,Long>{



}