package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class PointRecord extends BaseEntity {

	/**积分**/
	private Integer point;

	/**备注**/
	private String memo;

	/****/
	private Long memberId;

	private Integer surplusAmount;

	public Integer getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(Integer surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public void setPoint(Integer point){
		this.point = point;
	}

	public Integer getPoint(){
		return this.point;
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
