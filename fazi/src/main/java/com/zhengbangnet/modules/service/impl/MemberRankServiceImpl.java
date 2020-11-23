package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.MemberRank;
import com.zhengbangnet.modules.service.MemberRankService;
import com.zhengbangnet.modules.mapper.MemberRankMapper;

@Service("memberRankServiceImpl")
@Transactional(readOnly=true)
public class MemberRankServiceImpl extends BaseServiceImpl<MemberRank,Long> implements MemberRankService{

	@Resource(name="memberRankMapper")
	private MemberRankMapper memberRankMapper;

	@Resource(name="memberRankMapper")
	public void setBaseDao(BaseMapper<MemberRank,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public MemberRank getDefault() {
		return memberRankMapper.getDefault();
	}
}