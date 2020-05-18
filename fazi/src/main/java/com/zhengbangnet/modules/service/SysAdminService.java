package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.SysAdmin;

public interface SysAdminService extends BaseService<SysAdmin,Long> {

	/**
	 * 通过用户名查找管理员
	 * @param username用户名
	 * @return
	 */
	public SysAdmin getByUsername(String username);

	/**
	 * 获取当前登录管理员
	 */
	public SysAdmin getCurrent();
	
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	public SysAdmin getByMobile(String mobile);
	
	/**
	 * 保存管理员
	 * @param admin
	 * @param roleIds
	 */
	public void save(SysAdmin admin, Long[] roleIds);
	
	/**
	 * 更新管理员
	 * @param admin
	 * @param roleIds
	 */
	public void update(SysAdmin admin, Long[] roleIds);

	public void deleteBySysAdminId(Long id);

}