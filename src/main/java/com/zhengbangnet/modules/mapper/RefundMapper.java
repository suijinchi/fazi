package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Refund;
import org.springframework.stereotype.Repository;

/**
 * 
 * RefundMapper数据库操作接口类
 * 
 **/

@Repository("refundMapper")
public interface RefundMapper extends BaseMapper<Refund,Long> {

    Refund getBySn(String sn);
}