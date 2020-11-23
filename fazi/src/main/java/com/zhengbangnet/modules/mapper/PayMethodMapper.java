package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.PayMethod;
import org.springframework.stereotype.Repository;

/**
 * 
 * PayMethodMapper数据库操作接口类
 * 
 **/

@Repository("payMethodMapper")
public interface PayMethodMapper extends BaseMapper<PayMethod,Long>{



}