package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;
import com.zhengbangnet.common.utils.DateUtils;

import java.util.Date;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class CouponType extends BaseEntity {

	/**标题**/
	private String name;

	/**副标题**/
	private String subname;

	/**1固定时间 2领取*天后 有效*天**/
	private Integer validDateType;

	/**有效开始时间**/
	private java.util.Date validStartDate;

	/**有效结束时间**/
	private java.util.Date validEndDate;

	/**领取*天后有效**/
	private Integer validGetDay;

	/**领取后的有效天数**/
	private Integer validDays;

	/**1代金券 2折扣券 3礼品券**/
	private Integer type;

	/**1代金券 优惠金额**/
	private java.math.BigDecimal cutMoney;

	/**2折扣券 折扣额度 1折 2折**/
	private java.math.BigDecimal discount;

	/**使用条件 1没有条件 2满*元可用**/
	private Integer useType;

	/**使用条件 满多少可使用优惠**/
	private java.math.BigDecimal byFull;

	/**备注**/
	private String memo;

	/**是否注册时赠送 1是 2否**/
	private Integer isReg;

	/**库存**/
	private Integer stock;

	/**每人限领**/
	private Integer getLimit;

	/**电话**/
	private String contact;

	/**使用须知**/
	private String useMemo;

	/**商品使用范围 1全部 2部分商品**/
	private Integer useScope;

	/**已领取**/
	private Integer getNum;

	/**是否显示*/
	private Integer isShow;

	/**
	 * 是否可以与积分同享
	 * 即是否可以 同时使用 积分+券
	 * 0不可以 1可以
	 */
	private Integer isWithPointShare;
	/**
	 * 是否删除
	 * 0未删除
	 * 1已删除
	 */
	private Integer isDeleted;

	public Integer getIsWithPointShare() {
		return isWithPointShare;
	}

	public void setIsWithPointShare(Integer isWithPointShare) {
		this.isWithPointShare = isWithPointShare;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getGetNum() {
		return getNum;
	}

	public void setGetNum(Integer getNum) {
		this.getNum = getNum;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setSubname(String subname){
		this.subname = subname;
	}

	public String getSubname(){
		return this.subname;
	}

	public void setValidDateType(Integer validDateType){
		this.validDateType = validDateType;
	}

	public Integer getValidDateType(){
		return this.validDateType;
	}

	public void setValidStartDate(java.util.Date validStartDate){
		this.validStartDate = validStartDate;
	}

	public java.util.Date getValidStartDate(){
		return this.validStartDate;
	}

	public void setValidEndDate(java.util.Date validEndDate){
		this.validEndDate = validEndDate;
	}

	public java.util.Date getValidEndDate(){
		return this.validEndDate;
	}

	public void setValidGetDay(Integer validGetDay){
		this.validGetDay = validGetDay;
	}

	public Integer getValidGetDay(){
		return this.validGetDay;
	}

	public void setValidDays(Integer validDays){
		this.validDays = validDays;
	}

	public Integer getValidDays(){
		return this.validDays;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setCutMoney(java.math.BigDecimal cutMoney){
		this.cutMoney = cutMoney;
	}

	public java.math.BigDecimal getCutMoney(){
		return this.cutMoney;
	}

	public void setDiscount(java.math.BigDecimal discount){
		this.discount = discount;
	}

	public java.math.BigDecimal getDiscount(){
		return this.discount;
	}

	public void setUseType(Integer useType){
		this.useType = useType;
	}

	public Integer getUseType(){
		return this.useType;
	}

	public void setByFull(java.math.BigDecimal byFull){
		this.byFull = byFull;
	}

	public java.math.BigDecimal getByFull(){
		return this.byFull;
	}

	public void setMemo(String memo){
		this.memo = memo;
	}

	public String getMemo(){
		return this.memo;
	}

	public void setIsReg(Integer isReg){
		this.isReg = isReg;
	}

	public Integer getIsReg(){
		return this.isReg;
	}

	public void setStock(Integer stock){
		this.stock = stock;
	}

	public Integer getStock(){
		return this.stock;
	}

	public void setGetLimit(Integer getLimit){
		this.getLimit = getLimit;
	}

	public Integer getGetLimit(){
		return this.getLimit;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return this.contact;
	}

	public void setUseMemo(String useMemo){
		this.useMemo = useMemo;
	}

	public String getUseMemo(){
		return this.useMemo;
	}

	public void setUseScope(Integer useScope){
		this.useScope = useScope;
	}

	public Integer getUseScope(){
		return this.useScope;
	}

	/**
	 * 获取有效期
	 * @return
	 */
	public String getValidDate(){
		if(getValidDateType()==1){
			return DateUtils.dateToString(getValidStartDate(),"yyyy.MM.dd HH:mm")+" 至 " + DateUtils.dateToString(getValidEndDate(),"yyyy.MM.dd HH:mm");
		}else if(getValidDateType()==2){
			Integer validGetDay = getValidGetDay();
			Integer validDays = getValidDays();
			Date sd = DateUtils.getSomeDaysBeforeAfter(new Date(), validGetDay);
			Date ed = DateUtils.getSomeDaysBeforeAfter(sd, validDays);
			return DateUtils.dateToString(sd,"yyyy.MM.dd HH:mm")+" 至 " + DateUtils.dateToString(ed,"yyyy.MM.dd HH:mm");
		}
		return "";
	}

}
