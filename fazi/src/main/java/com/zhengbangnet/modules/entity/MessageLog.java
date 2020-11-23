package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;
import com.zhengbangnet.common.utils.SettingUtils;

import java.util.Date;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class MessageLog extends BaseEntity {
	/**
	 * 完善资料
	 */
	public static final Integer COMPLETE = 0;

	/**ip地址**/
	private String ip;

	/**手机号**/
	private String mobile;

	/**验证码**/
	private String msgCode;

	/**发送时间**/
	private java.util.Date sendDate;

	/**类型 0注册 1登录**/
	private Integer type;

	/**是否已使用 0未使用 1使用**/
	private Integer isUsed;



	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setMsgCode(String msgCode){
		this.msgCode = msgCode;
	}

	public String getMsgCode(){
		return this.msgCode;
	}

	public void setSendDate(java.util.Date sendDate){
		this.sendDate = sendDate;
	}

	public java.util.Date getSendDate(){
		return this.sendDate;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setIsUsed(Integer isUsed){
		this.isUsed = isUsed;
	}

	public Integer getIsUsed(){
		return this.isUsed;
	}

	/**
	 * 验证码是否过期
	 */
	public boolean isExpire(){
		return (new Date().getTime()-getSendDate().getTime())>=(SettingUtils.get().getMsgExpireTime()*60*1000);
	}

	/**
	 * 验证码是否符合超出发送间隔
	 */
	public boolean isOutInterval(){
		return (new Date().getTime()-getSendDate().getTime())>=SettingUtils.get().getMsgIntervalTime()*1000;
	}

}
