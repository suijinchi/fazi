package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class SysAdmin extends BaseEntity {
	
	public static final String CURRENT_LOGIN_ADMIN = "currentLoginAdmin";

	/**是否启用**/
	private String isEnabled;

	/**是否是系统内置**/
	private String isSystem;

	/**名称**/
	private String name;

	/**密码**/
	private String password;

	/**用户名**/
	private String username;

	/**上次登录ip**/
	private String lastIp;

	/**上次登录时间**/
	private java.util.Date lastLoginDate;

	/**IP**/
	private String ip;

	/**登录时间**/
	private java.util.Date loginDate;

	/**登录次数**/
	private Integer loginTimes;

	/**电话**/
	private String phone;

	/**邮箱**/
	private String email;

	/**备注**/
	private String memo;

	/**性别(0女，1男)**/
	private String sex;





	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setLastIp(String lastIp){
		this.lastIp = lastIp;
	}

	public String getLastIp(){
		return this.lastIp;
	}

	public void setLastLoginDate(java.util.Date lastLoginDate){
		this.lastLoginDate = lastLoginDate;
	}

	public java.util.Date getLastLoginDate(){
		return this.lastLoginDate;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}

	public void setLoginDate(java.util.Date loginDate){
		this.loginDate = loginDate;
	}

	public java.util.Date getLoginDate(){
		return this.loginDate;
	}

	public void setLoginTimes(Integer loginTimes){
		this.loginTimes = loginTimes;
	}

	public Integer getLoginTimes(){
		return this.loginTimes;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setMemo(String memo){
		this.memo = memo;
	}

	public String getMemo(){
		return this.memo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}





}
