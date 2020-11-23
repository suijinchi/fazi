package com.zhengbangnet.modules.service;
import java.util.List;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.SysRole;

public interface SysRoleService extends BaseService<SysRole,Long> {

	/**
	 * 根据会员id查询所属角色
	 * @param sysAdminid
	 * @return
	 */
	List<SysRole> findBySysAdmin(Long sysAdminid);
	
	/**
	 * 保存角色
	 */
	void save(String name, String description, Long[] menuIds);
	
	/**
	 * 角色更新
	 * @param sysRoleId
	 * @param name
	 * @param isQueryAll 
	 * @param companyId 
	 * @param description
	 * @param menuIds
	 * @param organization 组织权限
	 * @param groupsIds 
	 * @param companyIds 
	 * @param modules 
	 */
	void update(Long sysRoleId, String name, String description, Long[] menuIds);

	/**
	 * 根据角色ID从角色菜单中间表中删除数据
	 * @param id
	 */
	void deleteBySysRoleId(Long id);

}