package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class RechargeOrder extends BaseEntity {

	/**
	 * 未支付
	 */
	public static final String UNPAY = "0";

	/**
	 * 已支付
	 */
	public static final String PAID = "1";

	/**充值订单编号**/
	private String sn;

	/**充值金额**/
	private java.math.BigDecimal amount;

	/**状态 0未支付 1已支付**/
	private String status;

	/**优惠金额**/
	private java.math.BigDecimal benefitAmount;

	/**支付成功时间**/
	private java.util.Date payDate;

	/**第三方支付编号**/
	private String paySn;

	/****/
	private Long memberId;

	/**充值方式(1支付宝2微信)**/
	private Long payMethodId;

	/**赠送金额**/
	private java.math.BigDecimal giveAmount;

	/**类型
	 * 0用户自充值
	 * 1用户通过代理商充值
	 **/
	private Integer type;

	/**费率**/
	private java.math.BigDecimal taxRatio;

	/**
	 * 代理商id
	 */
	private Long agentId;

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn(){
		return this.sn;
	}

	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}

	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setBenefitAmount(java.math.BigDecimal benefitAmount){
		this.benefitAmount = benefitAmount;
	}

	public java.math.BigDecimal getBenefitAmount(){
		return this.benefitAmount;
	}

	public void setPayDate(java.util.Date payDate){
		this.payDate = payDate;
	}

	public java.util.Date getPayDate(){
		return this.payDate;
	}

	public void setPaySn(String paySn){
		this.paySn = paySn;
	}

	public String getPaySn(){
		return this.paySn;
	}

	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

	public void setPayMethodId(Long payMethodId){
		this.payMethodId = payMethodId;
	}

	public Long getPayMethodId(){
		return this.payMethodId;
	}

	public void setGiveAmount(java.math.BigDecimal giveAmount){
		this.giveAmount = giveAmount;
	}

	public java.math.BigDecimal getGiveAmount(){
		return this.giveAmount;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setTaxRatio(java.math.BigDecimal taxRatio){
		this.taxRatio = taxRatio;
	}

	public java.math.BigDecimal getTaxRatio(){
		return this.taxRatio;
	}

}
