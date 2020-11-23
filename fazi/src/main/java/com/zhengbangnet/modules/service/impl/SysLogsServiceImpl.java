package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.SysLogs;
import com.zhengbangnet.modules.service.SysLogsService;
import com.zhengbangnet.modules.mapper.SysLogsMapper;

@Service("sysLogsServiceImpl")
@Transactional(readOnly=false)
public class SysLogsServiceImpl extends BaseServiceImpl<SysLogs,Long> implements SysLogsService{

	@Resource(name="sysLogsMapper")
	private SysLogsMapper sysLogsMapper;

	@Resource(name="sysLogsMapper")
	public void setBaseDao(BaseMapper<SysLogs,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}