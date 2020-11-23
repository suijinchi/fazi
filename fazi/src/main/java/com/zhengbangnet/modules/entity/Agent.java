package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Agent extends BaseEntity {

	/**代理商名称**/
	private String name;

	/**账号**/
	private String username;

	/**密码**/
	private String password;

	/**管理员姓名**/
	private String adminName;

	/**管理员手机号**/
	private String adminMobile;



	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setAdminName(String adminName){
		this.adminName = adminName;
	}

	public String getAdminName(){
		return this.adminName;
	}

	public void setAdminMobile(String adminMobile){
		this.adminMobile = adminMobile;
	}

	public String getAdminMobile(){
		return this.adminMobile;
	}

}
