package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Ad;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * AdMapper数据库操作接口类
 * 
 **/

@Repository("adMapper")
public interface AdMapper extends BaseMapper<Ad,Long>{

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