package com.zhengbangnet.modules.service.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Assess;
import com.zhengbangnet.modules.service.AssessService;
import com.zhengbangnet.modules.mapper.AssessMapper;

@Service("assessServiceImpl")
@Transactional(readOnly=true)
public class AssessServiceImpl extends BaseServiceImpl<Assess,Long> implements AssessService{

	@Resource(name="assessMapper")
	private AssessMapper assessMapper;

	@Resource(name="assessMapper")
	public void setBaseDao(BaseMapper<Assess,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	/**
	 * 根据商品ID查询评价
	 * @author suijinchi
	 * @param id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getByProductId(Long id) {
		return assessMapper.getByProductId(id);
	}
}