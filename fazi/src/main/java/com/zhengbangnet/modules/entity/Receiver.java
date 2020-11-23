package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Receiver extends BaseEntity {

	/**
	 * 订货人
	 */
	private String orderer;

	/**
	 * 订货人手机号
	 */
	private String ordererNumber;


	/**收货人**/
	private String consignee;

	/**联系电话**/
	private String contactNumber;

	/**地区名称 如：山东省青岛市李沧区**/
	private String areaName;

	/**详细地址**/
	private String address;

	/**邮政编码**/
	private String zipCode;

	/**是否默认 1是 0否**/
	private Integer isDefault;

	/**会员外键**/
	private Long memberId;

	/**area表 区域外键id**/
	private Long areaId;

	public String getOrderer() {
		return orderer;
	}

	public void setOrderer(String orderer) {
		this.orderer = orderer;
	}

	public String getOrdererNumber() {
		return ordererNumber;
	}

	public void setOrdererNumber(String ordererNumber) {
		this.ordererNumber = ordererNumber;
	}

	public void setConsignee(String consignee){
		this.consignee = consignee;
	}

	public String getConsignee(){
		return this.consignee;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return this.contactNumber;
	}

	public void setAreaName(String areaName){
		this.areaName = areaName;
	}

	public String getAreaName(){
		return this.areaName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
	}

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}

	public String getZipCode(){
		return this.zipCode;
	}

	public void setIsDefault(Integer isDefault){
		this.isDefault = isDefault;
	}

	public Integer getIsDefault(){
		return this.isDefault;
	}

	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

	public void setAreaId(Long areaId){
		this.areaId = areaId;
	}

	public Long getAreaId(){
		return this.areaId;
	}

}
