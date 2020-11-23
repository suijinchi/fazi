package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class SysAdminRole extends BaseEntity {

	/****/
	private Long sysAdmin;

	/****/
	private Long sysRole;



	public void setSysAdmin(Long sysAdmin){
		this.sysAdmin = sysAdmin;
	}

	public Long getSysAdmin(){
		return this.sysAdmin;
	}

	public void setSysRole(Long sysRole){
		this.sysRole = sysRole;
	}

	public Long getSysRole(){
		return this.sysRole;
	}

}
