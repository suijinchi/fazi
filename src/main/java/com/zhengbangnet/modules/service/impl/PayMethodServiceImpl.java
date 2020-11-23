package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.PayMethod;
import com.zhengbangnet.modules.service.PayMethodService;
import com.zhengbangnet.modules.mapper.PayMethodMapper;

@Service("payMethodServiceImpl")
@Transactional(readOnly=true)
public class PayMethodServiceImpl extends BaseServiceImpl<PayMethod,Long> implements PayMethodService{

	@Resource(name="payMethodMapper")
	private PayMethodMapper payMethodMapper;

	@Resource(name="payMethodMapper")
	public void setBaseDao(BaseMapper<PayMethod,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}