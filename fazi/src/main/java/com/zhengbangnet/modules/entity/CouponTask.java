package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class CouponTask extends BaseEntity {

	/**查询参数**/
	private String params;

	/**发放优惠券id 逗号分隔**/
	private String couponTypeIds;

	/****/
	private String memberIds;

	/**是否执行完成0未完成 1已完成**/
	private Integer isCompleted;

	/**当前执行记录数**/
	private Integer currentRecord;

	/**0选择会员id，1查询条件**/
	private Integer type;

	/**发送操作管理员id**/
	private Long sysAdminId;



	public void setParams(String params){
		this.params = params;
	}

	public String getParams(){
		return this.params;
	}

	public void setCouponTypeIds(String couponTypeIds){
		this.couponTypeIds = couponTypeIds;
	}

	public String getCouponTypeIds(){
		return this.couponTypeIds;
	}

	public void setMemberIds(String memberIds){
		this.memberIds = memberIds;
	}

	public String getMemberIds(){
		return this.memberIds;
	}

	public void setIsCompleted(Integer isCompleted){
		this.isCompleted = isCompleted;
	}

	public Integer getIsCompleted(){
		return this.isCompleted;
	}

	public void setCurrentRecord(Integer currentRecord){
		this.currentRecord = currentRecord;
	}

	public Integer getCurrentRecord(){
		return this.currentRecord;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setSysAdminId(Long sysAdminId){
		this.sysAdminId = sysAdminId;
	}

	public Long getSysAdminId(){
		return this.sysAdminId;
	}

}
