package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Area;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * AreaMapper数据库操作接口类
 * 
 **/

@Repository("areaMapper")
public interface AreaMapper extends BaseMapper<Area,Long>{

	/**
	 * 查询根节点地区
	 */
	List<Area> findRoots();

	/**
	 * 查询子区域
	 */
	List<Area> findChildren(Long parentId);
	
	public Area findByName(String name);
	
	public Area findByFullName(String fullName);

	List<Area> findProvice(String province);

	List<Area> findCity(String city);
	
	List<Area> findParentId(Long parentId);

}