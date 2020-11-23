package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.BirthdayCard;
import com.zhengbangnet.modules.service.BirthdayCardService;
import com.zhengbangnet.modules.mapper.BirthdayCardMapper;

@Service("birthdayCardServiceImpl")
@Transactional(readOnly=true)
public class BirthdayCardServiceImpl extends BaseServiceImpl<BirthdayCard,Long> implements BirthdayCardService{

	@Resource(name="birthdayCardMapper")
	private BirthdayCardMapper birthdayCardMapper;

	@Resource(name="birthdayCardMapper")
	public void setBaseDao(BaseMapper<BirthdayCard,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}