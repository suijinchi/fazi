package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.CouponTask;
import com.zhengbangnet.modules.service.CouponTaskService;
import com.zhengbangnet.modules.mapper.CouponTaskMapper;

@Service("couponTaskServiceImpl")
@Transactional(readOnly=true)
public class CouponTaskServiceImpl extends BaseServiceImpl<CouponTask,Long> implements CouponTaskService{

	@Resource(name="couponTaskMapper")
	private CouponTaskMapper couponTaskMapper;

	@Resource(name="couponTaskMapper")
	public void setBaseDao(BaseMapper<CouponTask,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}