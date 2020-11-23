package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Agent;
import org.springframework.stereotype.Repository;

/**
 * 
 * AgentMapper数据库操作接口类
 * 
 **/

@Repository("agentMapper")
public interface AgentMapper extends BaseMapper<Agent,Long>{



}