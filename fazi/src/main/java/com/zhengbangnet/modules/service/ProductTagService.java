package com.zhengbangnet.modules.service;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.ProductTag;

public interface ProductTagService extends BaseService<ProductTag,Long> {

	void save(Long[] tagIds, Long id);

	List<ProductTag> getByProductId(Long id);

	void deleteByProductId(Long id);

	/**
	 * 根据标签ID查询
	 * @param id 标签ID
	 * @return
	 */
    List<ProductTag> getByTagId(Long id);
}