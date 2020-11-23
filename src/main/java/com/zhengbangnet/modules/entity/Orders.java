package com.zhengbangnet.modules.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.zhengbangnet.common.entity.BaseEntity;
import com.zhengbangnet.common.utils.DateUtils;

public class Orders extends BaseEntity {

    /**
     * 订单金额
     * 即会员实际使用 人民币支付的金额（扣除）
     **/
    private java.math.BigDecimal amount;

    /**
     * 订单积分价格
     **/
    private Integer point;

    /**
     * 下单人ID
     **/
    private Long memberId;

    /**
     * 订单状态 0未确认 1已确认 2已完成 3已取消
     * 4申请退款
     * 5退款中 6已退款
     **/
    private Integer ordersStatus;

    /**
     * 支付状态 0未支付 1已支付
     **/
    private Integer payStatus;

    /**
     * 发货状态 0待发货 1已发货 2确认收货
     **/
    private Integer shippingStatus;

    /**
     * 客服确认状态
     * 0未确认
     * 1已确认
     */
    private Integer serviceConfirmStatus;

    /**
     * 发货方式0自提 1快递
     */
    private Integer shippingMethod;

    /**
     * 订单编号
     **/
    private String sn;

    /**
     * 支付时间
     **/
    private Date payDate;

    /**
     * 发货时间
     **/
    private Date shippingDate;

    /**
     * 确认收货时间
     */
    private Date confirmReceiveDate;

    /**
     * 订单完成时间
     */
    private Date completeDate;

    /**
     * 订单取消时间
     */
    private Date cancelDate;

    /**
     * 收款人姓名
     **/
    private String name;

    /**
     * 收货人手机号
     **/
    private String mobile;

    /**
     * 收货地区id
     **/
    private Long areaId;

    /**
     * 收货地址
     **/
    private String address;

    /**
     * 买家备注留言
     **/
    private String memo;

    /**
     * 下单人
     */
    private String orderer;
    /**
     * 下单人手机号
     */
    private String ordererMobile;

    /**
     * 评价状态 1已评价 0未评价
     */
    private Integer assessStatus;

    /**
     * 订单支付类型（0微信支付 1余额支付）
     */
    private Integer payType;

    /**
     * 订单总金额 商品金额+邮费
     */
    private java.math.BigDecimal sumAmount;

    /**
     * 积分支付额 积分总金额
     */
    private Integer pointPay;

    /**
     * 积分抵扣金额
     */
    private java.math.BigDecimal pointOffset;

    /**
     * 余额支付金额
     */
    private java.math.BigDecimal balancePay;

    /**
     * 第三方支付金额
     */
    private java.math.BigDecimal thirdPay;

    /**
     * 卡券抵扣金额
     */
    private java.math.BigDecimal couponPay;

    /**
     * 配送费
     */
    private java.math.BigDecimal shippingFee;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 快递单号
     */
    private String expressSn;

    /**
     * 配送时间
     */
    private String shippingTime;

    /**
     * 自提时间
     */
    private String pickUpTime;

    /**
     * 自提地点
     */
    private String pickUpAddress;

    /**
     * 订单奖励积分
     */
    private Integer pointReward;

    /**
     * 是否已配送提醒
     */
    private Integer isShippingMsg;
    /**
     * 配送消息日期
     */
    private Date shippingMsgDate;


    /**
     * 打折金额
     */
    private BigDecimal discountPay;

    /**
     * 调整金额
     */
    private BigDecimal offsetAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 标记名称 1红 2黄 3绿 4蓝 5深蓝
     */
    private String flagname;
    /**
     * 标记信息
     */
    private String flaginfo;
    /**
     * 标记icon
     */
    private String flagicon;

    /**
     * 微信支付记录
     */
    private BigDecimal wxPayRecord;

    /*
    OPENTM414261577
    详细内容
         {{first.DATA}}
         订单编号：{{keyword1.DATA}}
         订单金额：{{keyword2.DATA}}
         收货人电话：{{keyword3.DATA}}
         收货人姓名：{{keyword4.DATA}}
         收货人地址：{{keyword5.DATA}}
         {{remark.DATA}}
     内容示例
         亲，您购买的商品已经发货啦，配送时间大概7到15分钟不等，请耐心等候哦！
         订单编号：1513149767
         订单金额：22.1
         收货人电话：18380807232
         收货人姓名：刘先生
         收货人地址：成都市高新区保利星座三栋309
         感谢您的购买，期待下次光临。
    */

    public BigDecimal getWxPayRecord() {
        return wxPayRecord;
    }

    public void setWxPayRecord(BigDecimal wxPayRecord) {
        this.wxPayRecord = wxPayRecord;
    }

    public Integer getServiceConfirmStatus() {
        return serviceConfirmStatus;
    }

    public void setServiceConfirmStatus(Integer serviceConfirmStatus) {
        this.serviceConfirmStatus = serviceConfirmStatus;
    }

    public BigDecimal getOffsetAmount() {
        return offsetAmount;
    }

    public void setOffsetAmount(BigDecimal offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    public String getFlagname() {
        return flagname;
    }

    public void setFlagname(String flagname) {
        this.flagname = flagname;
    }

    public String getFlaginfo() {
        return flaginfo;
    }

    public void setFlaginfo(String flaginfo) {
        this.flaginfo = flaginfo;
    }

    public String getFlagicon() {
        return flagicon;
    }

    public void setFlagicon(String flagicon) {
        this.flagicon = flagicon;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public BigDecimal getDiscountPay() {
        return discountPay;
    }

    public void setDiscountPay(BigDecimal discountPay) {
        this.discountPay = discountPay;
    }

    public Integer getIsShippingMsg() {
        return isShippingMsg;
    }

    public void setIsShippingMsg(Integer isShippingMsg) {
        this.isShippingMsg = isShippingMsg;
    }

    public Date getShippingMsgDate() {
        return shippingMsgDate;
    }

    public void setShippingMsgDate(Date shippingMsgDate) {
        this.shippingMsgDate = shippingMsgDate;
    }

    public Integer getPointReward() {
        return pointReward;
    }

    public void setPointReward(Integer pointReward) {
        this.pointReward = pointReward;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getOrdererMobile() {
        return ordererMobile;
    }

    public void setOrdererMobile(String ordererMobile) {
        this.ordererMobile = ordererMobile;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getExpressSn() {
        return expressSn;
    }

    public void setExpressSn(String expressSn) {
        this.expressSn = expressSn;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Integer getPointPay() {
        return pointPay;
    }

    public void setPointPay(Integer pointPay) {
        this.pointPay = pointPay;
    }

    public BigDecimal getPointOffset() {
        return pointOffset;
    }

    public void setPointOffset(BigDecimal pointOffset) {
        this.pointOffset = pointOffset;
    }

    public BigDecimal getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(BigDecimal balancePay) {
        this.balancePay = balancePay;
    }

    public BigDecimal getThirdPay() {
        return thirdPay;
    }

    public void setThirdPay(BigDecimal thirdPay) {
        this.thirdPay = thirdPay;
    }

    public BigDecimal getCouponPay() {
        return couponPay;
    }

    public void setCouponPay(BigDecimal couponPay) {
        this.couponPay = couponPay;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getOrdersStatus() {
        return ordersStatus;
    }

    public void setOrdersStatus(Integer ordersStatus) {
        this.ordersStatus = ordersStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Integer getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(Integer shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Date getConfirmReceiveDate() {
        return confirmReceiveDate;
    }

    public void setConfirmReceiveDate(Date confirmReceiveDate) {
        this.confirmReceiveDate = confirmReceiveDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getAssessStatus() {
        return assessStatus;
    }

    public void setAssessStatus(Integer assessStatus) {
        this.assessStatus = assessStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getTimeAgo() {

        Date payDate = getPayDate();
        if(payDate!=null) {
            Date nowDate = new Date();
            Integer min = DateUtils.getMinutes(payDate, nowDate);
            if (min/60 == 0) {
                return min + "分钟前";
            } else {
                return min/60 + "小时前";
            }
        }
        return "";
    }

}
