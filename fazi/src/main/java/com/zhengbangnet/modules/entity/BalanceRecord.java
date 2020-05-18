package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class BalanceRecord extends BaseEntity {

	/**积分**/
	private java.math.BigDecimal balance;

	/**备注**/
	private String memo;

	/****/
	private Long memberId;



	public void setBalance(java.math.BigDecimal balance){
		this.balance = balance;
	}

	public java.math.BigDecimal getBalance(){
		return this.balance;
	}

	public void setMemo(String memo){
		this.memo = memo;
	}

	public String getMemo(){
		return this.memo;
	}

	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

}
