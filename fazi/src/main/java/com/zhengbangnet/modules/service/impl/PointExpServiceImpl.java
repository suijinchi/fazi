package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.PointExp;
import com.zhengbangnet.modules.service.PointExpService;
import com.zhengbangnet.modules.mapper.PointExpMapper;

@Service("pointExpServiceImpl")
@Transactional(readOnly=true)
public class PointExpServiceImpl extends BaseServiceImpl<PointExp,Long> implements PointExpService{

	@Resource(name="pointExpMapper")
	private PointExpMapper pointExpMapper;

	@Resource(name="pointExpMapper")
	public void setBaseDao(BaseMapper<PointExp,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}