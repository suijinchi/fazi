package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.BalanceRecord;
import org.springframework.stereotype.Repository;

/**
 * 
 * BalanceRecordMapper数据库操作接口类
 * 
 **/

@Repository("balanceRecordMapper")
public interface BalanceRecordMapper extends BaseMapper<BalanceRecord,Long>{



}