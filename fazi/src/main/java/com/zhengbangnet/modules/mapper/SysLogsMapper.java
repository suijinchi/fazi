package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.SysLogs;
import org.springframework.stereotype.Repository;

/**
 * 
 * SysLogsMapper数据库操作接口类
 * 
 **/

@Repository("sysLogsMapper")
public interface SysLogsMapper extends BaseMapper<SysLogs,Long>{



}