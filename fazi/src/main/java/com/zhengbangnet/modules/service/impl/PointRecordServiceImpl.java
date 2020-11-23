package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.PointRecord;
import com.zhengbangnet.modules.service.PointRecordService;
import com.zhengbangnet.modules.mapper.PointRecordMapper;

@Service("pointRecordServiceImpl")
@Transactional(readOnly=true)
public class PointRecordServiceImpl extends BaseServiceImpl<PointRecord,Long> implements PointRecordService{

	@Resource(name="pointRecordMapper")
	private PointRecordMapper pointRecordMapper;

	@Resource(name="pointRecordMapper")
	public void setBaseDao(BaseMapper<PointRecord,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}