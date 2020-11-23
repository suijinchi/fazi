package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class SysRoleMenu extends BaseEntity {

	/****/
	private Long sysRole;

	/****/
	private Long sysMenu;



	public void setSysRole(Long sysRole){
		this.sysRole = sysRole;
	}

	public Long getSysRole(){
		return this.sysRole;
	}

	public void setSysMenu(Long sysMenu){
		this.sysMenu = sysMenu;
	}

	public Long getSysMenu(){
		return this.sysMenu;
	}

}
