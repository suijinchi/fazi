package com.zhengbangnet.modules.service;
import java.util.List;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Area;

public interface AreaService extends BaseService<Area,Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @return 顶级地区
	 */
	List<Area> findRoots();

	/**
	 * 查询子区域
	 * @param parentId
	 * @return
	 */
	List<Area> findChildren(Long parentId);

	/**
	 * 根据名称查找
	 * @param areaName
	 * @return
	 */
	Area getByName(String areaName);
	
	Area findByFullName(String fullName);

	List<Area> findProvince(String province);

	List<Area> findCity(String city);
	
	List<Area> findParentId(Long parentId);
	
}