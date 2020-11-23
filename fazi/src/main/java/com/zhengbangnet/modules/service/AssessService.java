package com.zhengbangnet.modules.service;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Assess;

public interface AssessService extends BaseService<Assess,Long> {

	/**
	 * 根据商品ID查询评价
	 * @author suijinchi
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getByProductId(Long id);

}