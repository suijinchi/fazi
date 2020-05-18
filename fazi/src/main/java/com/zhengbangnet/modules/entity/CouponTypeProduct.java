package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class CouponTypeProduct extends BaseEntity {

	/**优惠券类型id**/
	private Long couponTypeId;

	/**商品id**/
	private Long productId;



	public void setCouponTypeId(Long couponTypeId){
		this.couponTypeId = couponTypeId;
	}

	public Long getCouponTypeId(){
		return this.couponTypeId;
	}

	public void setProductId(Long productId){
		this.productId = productId;
	}

	public Long getProductId(){
		return this.productId;
	}

}
