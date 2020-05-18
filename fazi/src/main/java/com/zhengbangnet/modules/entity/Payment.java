package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Payment extends BaseEntity {
	
	/**
	 * 未支付
	 */
	public static final int WAIT = 0;
	/**
	 * 已支付
	 */
	public static final Integer SUCCESS = 1;
	
	/**
	 * 普通订单
	 */
	public static final String TYPE_ORDERS = "0";
	
	/**
	 * 会员充值
	 */
	public static final String TYPE_RECHARGE = "1";
	
	/**金额**/
	private java.math.BigDecimal amount;

	/**商户单号**/
	private String sn;

	/**第三方支付单号**/
	private String paySn;

	/**第三方支付时间**/
	private java.util.Date payDate;

	/**会员id**/
	private Long memberId;

	/**支付订单类型 0 订单支付**/
	private String type;

	/**支付订单的id**/
	private String payId;

	/**状态 0未支付1已支付**/
	private Integer status;



	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}

	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn(){
		return this.sn;
	}

	public void setPaySn(String paySn){
		this.paySn = paySn;
	}

	public String getPaySn(){
		return this.paySn;
	}

	public void setPayDate(java.util.Date payDate){
		this.payDate = payDate;
	}

	public java.util.Date getPayDate(){
		return this.payDate;
	}

	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

}
