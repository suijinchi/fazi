package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.SpecName;
import com.zhengbangnet.modules.service.SpecNameService;
import com.zhengbangnet.modules.mapper.SpecNameMapper;

@Service("specNameServiceImpl")
@Transactional(readOnly=true)
public class SpecNameServiceImpl extends BaseServiceImpl<SpecName,Long> implements SpecNameService{

	@Resource(name="specNameMapper")
	private SpecNameMapper specNameMapper;

	@Resource(name="specNameMapper")
	public void setBaseDao(BaseMapper<SpecName,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}