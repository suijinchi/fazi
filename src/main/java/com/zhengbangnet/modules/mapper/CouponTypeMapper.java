package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.CouponType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * CouponTypeMapper数据库操作接口类
 * 
 **/

@Repository("couponTypeMapper")
public interface CouponTypeMapper extends BaseMapper<CouponType,Long>{

    List<CouponType> findValidListByParams(Map<String, Object> params);

    /**
     * 分页查询有效的券列表
     */
    Long getValidCountByParams(Map<String, Object> params);
    List<Map<String,Object>> findValidPageByParams(Map<String, Object> params);

}