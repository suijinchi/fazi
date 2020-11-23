package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.SysRole;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * SysRoleMapper数据库操作接口类
 * 
 **/

@Repository("sysRoleMapper")
public interface SysRoleMapper extends BaseMapper<SysRole,Long>{

	/**
	 * 根据管理员id查询角色
	 * @param sysAdminid
	 * @return
	 */
	List<SysRole> findBySysAdmin(@Param("sysAdminid")Long sysAdminid);

}