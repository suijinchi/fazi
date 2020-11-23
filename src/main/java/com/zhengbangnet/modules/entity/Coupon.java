package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;

import java.util.Date;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Coupon extends BaseEntity {

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

	/**代金券 优惠金额**/
	private java.math.BigDecimal cutMoney;

	/**2折扣券 折扣额度 1折 2折**/
	private java.math.BigDecimal discount;

	/**使用条件 1没有条件 2满*元可用**/
	private Integer useType;

	/**使用条件 满多少可使用优惠**/
	private java.math.BigDecimal byFull;

	/**备注**/
	private String memo;

	/**使用须知**/
	private String useMemo;

	/**商品使用范围 1全部 2部分商品(coupon_type_product中间表)**/
	private Integer useScope;

	/**优惠券类型表**/
	private Long couponTypeId;

	/**
	 * 使用日期
	 */
	private Date useDate;

	/**
	 * 0未使用 1已使用
	 */
	private Integer status;

	private Long memberId;

	/**
	 * 使用该券的订单id
	 */
	private Long ordersId;

	private Long sourceMemberId;

	/**
	 * 0自助领取，1推荐好友，2后台发放，3签到
	 */
	private Integer scene;

	/**
	 * 是否可以与积分同享
	 * 即是否可以 同时使用 积分+券
	 * 0不可以 1可以
	 */
	private Integer isWithPointShare;

	private Long sysAdminId;

	public Long getSysAdminId() {
		return sysAdminId;
	}

	public void setSysAdminId(Long sysAdminId) {
		this.sysAdminId = sysAdminId;
	}

	public Integer getIsWithPointShare() {
		return isWithPointShare;
	}

	public void setIsWithPointShare(Integer isWithPointShare) {
		this.isWithPointShare = isWithPointShare;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}

	public Long getSourceMemberId() {
		return sourceMemberId;
	}

	public void setSourceMemberId(Long sourceMemberId) {
		this.sourceMemberId = sourceMemberId;
	}

	public Long getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(Long ordersId) {
		this.ordersId = ordersId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
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

	public void setCouponTypeId(Long couponTypeId){
		this.couponTypeId = couponTypeId;
	}

	public Long getCouponTypeId(){
		return this.couponTypeId;
	}

}
