package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.MessageLog;
import com.zhengbangnet.modules.service.MessageLogService;
import com.zhengbangnet.modules.mapper.MessageLogMapper;

import java.util.Date;

@Service("messageLogServiceImpl")
@Transactional(readOnly=true)
public class MessageLogServiceImpl extends BaseServiceImpl<MessageLog,Long> implements MessageLogService{

	@Resource(name="messageLogMapper")
	private MessageLogMapper messageLogMapper;

	@Resource(name="messageLogMapper")
	public void setBaseDao(BaseMapper<MessageLog,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public MessageLog getByMobile(String mobile,Integer type) {
		return messageLogMapper.getByMobile(mobile,type);
	}

	@Override
	public Long findCountByIp(String ip) {
		return messageLogMapper.findCountByIp(ip,new Date());
	}

	@Override
	public Long findCountByMobile(String ip) {
		return messageLogMapper.findCountByMobile(ip,new Date());
	}

}