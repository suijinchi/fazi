package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.CouponTypeProduct;
import com.zhengbangnet.modules.service.CouponTypeProductService;
import com.zhengbangnet.modules.mapper.CouponTypeProductMapper;

@Service("couponTypeProductServiceImpl")
@Transactional(readOnly=true)
public class CouponTypeProductServiceImpl extends BaseServiceImpl<CouponTypeProduct,Long> implements CouponTypeProductService{

	@Resource(name="couponTypeProductMapper")
	private CouponTypeProductMapper couponTypeProductMapper;

	@Resource(name="couponTypeProductMapper")
	public void setBaseDao(BaseMapper<CouponTypeProduct,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}