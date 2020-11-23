package com.zhengbangnet.modules.service;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.SysMenu;

public interface SysMenuService extends BaseService<SysMenu,Long> {

	/**
	 * 根据管理员id查询菜单
	 * @param id
	 * @return
	 */
	List<SysMenu> findBySysAdmin(Long adminId);

	/**
	 * 根据角色id查询菜单
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> findBySysRole(Long roleId);

}