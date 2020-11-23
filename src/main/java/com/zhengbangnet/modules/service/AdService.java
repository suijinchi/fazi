package com.zhengbangnet.modules.service;
import java.util.List;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Ad;

public interface AdService extends BaseService<Ad,Long> {

	/**
	 * 获取平台banner
	 * @return
	 */
	List<Ad> getBanner();

	/**
	 * 获取店铺banner
	 * @param shopId
	 * @return
	 */
	List<Ad> getBannerByShopId(Long shopId);


}