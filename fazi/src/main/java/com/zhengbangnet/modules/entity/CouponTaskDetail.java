package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class CouponTaskDetail extends BaseEntity {

	/**优惠券任务id**/
	private Long couponTaskId;

	/**会员id**/
	private Long memberId;

	/**优惠券id**/
	private Long couponId;

	/**优惠券类型id**/
	private Long couponTypeId;



	public void setCouponTaskId(Long couponTaskId){
		this.couponTaskId = couponTaskId;
	}

	public Long getCouponTaskId(){
		return this.couponTaskId;
	}

	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

	public void setCouponId(Long couponId){
		this.couponId = couponId;
	}

	public Long getCouponId(){
		return this.couponId;
	}

	public void setCouponTypeId(Long couponTypeId){
		this.couponTypeId = couponTypeId;
	}

	public Long getCouponTypeId(){
		return this.couponTypeId;
	}

}
