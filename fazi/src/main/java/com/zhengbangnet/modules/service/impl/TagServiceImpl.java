package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Tag;
import com.zhengbangnet.modules.service.TagService;
import com.zhengbangnet.modules.mapper.TagMapper;

import java.util.List;

@Service("tagServiceImpl")
@Transactional(readOnly=true)
public class TagServiceImpl extends BaseServiceImpl<Tag,Long> implements TagService{

	@Resource(name="tagMapper")
	private TagMapper tagMapper;

	@Resource(name="tagMapper")
	public void setBaseDao(BaseMapper<Tag,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public List<Tag> findByType(Integer type) {
		return tagMapper.findByType(type);
	}
}