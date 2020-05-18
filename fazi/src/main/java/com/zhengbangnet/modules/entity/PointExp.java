package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class PointExp extends BaseEntity {

	/**过期时间**/
	private java.util.Date expDate;

	/**积分额**/
	private Integer amount;

	/**未使用金额**/
	private Integer unuseAmount;

	/****/
	private Long memberId;

	/****/
	private String memo;



	public void setExpDate(java.util.Date expDate){
		this.expDate = expDate;
	}

	public java.util.Date getExpDate(){
		return this.expDate;
	}

	public void setAmount(Integer amount){
		this.amount = amount;
	}

	public Integer getAmount(){
		return this.amount;
	}

	public Integer getUnuseAmount() {
		return unuseAmount;
	}

	public void setUnuseAmount(Integer unuseAmount) {
		this.unuseAmount = unuseAmount;
	}

	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

	public void setMemo(String memo){
		this.memo = memo;
	}

	public String getMemo(){
		return this.memo;
	}

}
