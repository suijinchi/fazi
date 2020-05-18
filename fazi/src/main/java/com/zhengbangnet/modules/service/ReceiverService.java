package com.zhengbangnet.modules.service;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Receiver;

public interface ReceiverService extends BaseService<Receiver,Long> {

	/**
	 * 依据会员ID查询收货人地址信息 add by hawkbird date 2018-04-04
	 */
	List<Map<String, Object>> queryAddressByMemberId(long memberId);
	/**
	 * 依据会员ID取消默认地址 add by hawkbird date 2018-04-08
	 */
	int updateNoDefaultByMemberId(long memberId);

	Receiver getDefault(Long memberId);
}