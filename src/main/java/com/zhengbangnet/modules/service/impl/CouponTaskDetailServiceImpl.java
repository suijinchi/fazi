package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.CouponTaskDetail;
import com.zhengbangnet.modules.service.CouponTaskDetailService;
import com.zhengbangnet.modules.mapper.CouponTaskDetailMapper;

@Service("couponTaskDetailServiceImpl")
@Transactional(readOnly=true)
public class CouponTaskDetailServiceImpl extends BaseServiceImpl<CouponTaskDetail,Long> implements CouponTaskDetailService{

	@Resource(name="couponTaskDetailMapper")
	private CouponTaskDetailMapper couponTaskDetailMapper;

	@Resource(name="couponTaskDetailMapper")
	public void setBaseDao(BaseMapper<CouponTaskDetail,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}