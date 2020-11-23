package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.PointRecord;
import org.springframework.stereotype.Repository;

/**
 * 
 * PointRecordMapper数据库操作接口类
 * 
 **/

@Repository("pointRecordMapper")
public interface PointRecordMapper extends BaseMapper<PointRecord,Long>{



}