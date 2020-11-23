package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Agent;
import com.zhengbangnet.modules.service.AgentService;
import com.zhengbangnet.modules.mapper.AgentMapper;

@Service("agentServiceImpl")
@Transactional(readOnly=true)
public class AgentServiceImpl extends BaseServiceImpl<Agent,Long> implements AgentService{

	@Resource(name="agentMapper")
	private AgentMapper agentMapper;

	@Resource(name="agentMapper")
	public void setBaseDao(BaseMapper<Agent,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}