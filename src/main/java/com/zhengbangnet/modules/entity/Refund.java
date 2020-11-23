package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;

import java.math.BigDecimal;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Refund extends BaseEntity {

	/**
	 * 退款中
	 */
	public static final Integer REFUNDING = 1;
	/**
	 * 已到账
	 */
	public static final Integer REFUNED = 2;

	/**订单id**/
	private Long ordersId;

	/**所属会员id**/
	private Long memberId;

	/**管理员id**/
	private Long adminId;

	/**三方支付退款（微信）**/
	private java.math.BigDecimal thirdPayRefund;

	/**余额退款**/
	private java.math.BigDecimal balancePayRefund;

	private Integer pointPayRefund;

	/**平台退款单号**/
	private String sn;

	/**微信退款单号**/
	private String thirdSn;

	/**退款时间**/
	private java.util.Date refundDate;

	/**状态 1退款中 2退款已到账**/
	private Integer status;



	public void setOrdersId(Long ordersId){
		this.ordersId = ordersId;
	}

	public Long getOrdersId(){
		return this.ordersId;
	}

	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

	public void setAdminId(Long adminId){
		this.adminId = adminId;
	}

	public Long getAdminId(){
		return this.adminId;
	}

	public void setThirdPayRefund(java.math.BigDecimal thirdPayRefund){
		this.thirdPayRefund = thirdPayRefund;
	}

	public java.math.BigDecimal getThirdPayRefund(){
		return this.thirdPayRefund;
	}

	public void setBalancePayRefund(java.math.BigDecimal balancePayRefund){
		this.balancePayRefund = balancePayRefund;
	}

	public java.math.BigDecimal getBalancePayRefund(){
		return this.balancePayRefund;
	}

	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn(){
		return this.sn;
	}

	public void setThirdSn(String thirdSn){
		this.thirdSn = thirdSn;
	}

	public String getThirdSn(){
		return this.thirdSn;
	}

	public void setRefundDate(java.util.Date refundDate){
		this.refundDate = refundDate;
	}

	public java.util.Date getRefundDate(){
		return this.refundDate;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public Integer getPointPayRefund() {
		return pointPayRefund;
	}

	public void setPointPayRefund(Integer pointPayRefund) {
		this.pointPayRefund = pointPayRefund;
	}
}
