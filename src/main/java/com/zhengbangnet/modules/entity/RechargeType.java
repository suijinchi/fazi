package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class RechargeType extends BaseEntity {

	/****/
	private String name;

	/****/
	private java.math.BigDecimal amount;

	/****/
	private Integer orders;

	/****/
	private java.math.BigDecimal giveAmount;

	/****/
	private String memo;

	private Integer isShow;

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}

	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

	public void setGiveAmount(java.math.BigDecimal giveAmount){
		this.giveAmount = giveAmount;
	}

	public java.math.BigDecimal getGiveAmount(){
		return this.giveAmount;
	}

	public void setMemo(String memo){
		this.memo = memo;
	}

	public String getMemo(){
		return this.memo;
	}

}
