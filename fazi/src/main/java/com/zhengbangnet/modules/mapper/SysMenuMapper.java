package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.SysMenu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * SysMenuMapper数据库操作接口类
 * 
 **/

@Repository("sysMenuMapper")
public interface SysMenuMapper extends BaseMapper<SysMenu,Long>{

	List<SysMenu> findBySysAdmin(@Param("sysAdminId")Long sysAdminId);
	
	List<Map<String, Object>> findBySysRole(@Param("sysRoleId")Long sysRoleId);

}