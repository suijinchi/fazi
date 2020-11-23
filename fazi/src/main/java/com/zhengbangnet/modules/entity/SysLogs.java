package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class SysLogs extends BaseEntity {

	/**操作用户名**/
	private String username;

	/****/
	private String type;

	/**操作内容**/
	private String operation;

	/**请求方法**/
	private String method;

	/**请求参数**/
	private String params;

	/**ip地址**/
	private String ip;

	/****/
	private Long sysAdminId;



	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setOperation(String operation){
		this.operation = operation;
	}

	public String getOperation(){
		return this.operation;
	}

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return this.method;
	}

	public void setParams(String params){
		this.params = params;
	}

	public String getParams(){
		return this.params;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}

	public void setSysAdminId(Long sysAdminId){
		this.sysAdminId = sysAdminId;
	}

	public Long getSysAdminId(){
		return this.sysAdminId;
	}

}
