package com.zhengbangnet.common.lang;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

public class Setting implements Serializable {

    @Ignore
    private static final long serialVersionUID = 8394845169926020928L;

    /**
     * 分隔符
     */
    @Ignore
    private static final String SEPARATOR = ",";

    /**
     * 短信id
     */
    private String msgUid;

    /**
     * 客服电话
     */
    private String csTel;

    /**
     * 最小提现金额
     */
    private BigDecimal openInvoiceAmount;

    /**
     * 提现费率
     */
    private BigDecimal invoiceAmountRatio;

    /**
     * 积分比
     */
    private BigDecimal integralRatio;

    /**
     * 短信秘钥
     */
    private String msgKey;

    /**
     * 注册短信内容
     */
    private String msgContent;

    /**
     * 短信签名
     */
    private String msgSign;

    /**
     * 短信模板
     */
    private String msgTemplate;

    /**
     * 域名
     */
    private String domain;
    private String cookieDomain;
    private String cookiePath;
    private String ip;

    /**
     * API接口地址
     */
    private String apiDomain;

    /**
     * 是否调试
     */
    private Boolean isDebug;

    /**
     * 是否发送短信
     */
    private Boolean isSendMsg;

    /**
     * 图片扩展名
     */
    private String uploadImageExtension;

    private String uploadImagePath;

    /**
     * 文档类扩展名
     */
    private String uploadDocExtension;

    private String uploadDocPath;

    private String uploadMediaPath;

    private String uploadMediaExtension;

    private String downloadPath;

    /**
     * ip每天最大发送量
     */
    private Integer ipMaxMsgPerDay;

    /**
     * 手机号每天最大发送量
     */
    private Integer mobileMaxMsgPerDay;

    /**
     * 短信过期时间 单位分钟
     */
    private Integer msgExpireTime;

    /**
     * 短信发送间隔时间 单位秒
     */
    private Integer msgIntervalTime;

    /**
     * 默认头像
     */
    private String defaultHead;

    /**
     * 积分抵扣比例
     * 100:1
     * 100积分抵扣1元
     */
    private Integer pointRatio;

    /**
     * 配送费
     */
    private BigDecimal shippingFee;

    /**
     * 最小充值金额
     */
    private BigDecimal minRechargeAmount;

    /**
     * 积分有效年份
     */
    private Integer pointValidMonth;

    /**
     * 首页弹窗标题
     */
    private String popupTitle;
    /**
     * 首页弹窗图片url
     */
    private String popupImgUrl;
    /**
     * 首页弹窗按钮提示
     */
    private String popupBtnText;
    /**
     * 首页弹窗链接地址
     */
    private String popupLink;
    /**
     * 首页弹窗开关
     * 0关闭
     * 1打开
     */
    private Integer popupSwitch;

    /**
     * 弹窗内容
     */
    private String popupContent;
    /**
     * 二维码图片banner
     */
    private String qrcodeImgUrl;

    /**
     * 推荐人获取的优惠券id
     */
    private Long recommendCouponId;

    /**
     * 新用户注册获取的优惠券id
     */
    private Long registeCouponId;

    /**
     * 会员福利
     */
    private String memberWelfareUrl;

    /**
     * 签到积分
     */
    private Integer signPoint;

    private Long signCouponId;

    private Integer registePoint;

    private Integer assessPoint;

    public Integer getAssessPoint() {
        return assessPoint;
    }

    public void setAssessPoint(Integer assessPoint) {
        this.assessPoint = assessPoint;
    }

    public Integer getRegistePoint() {
        return registePoint;
    }

    public void setRegistePoint(Integer registePoint) {
        this.registePoint = registePoint;
    }

    public Long getSignCouponId() {
        return signCouponId;
    }

    public void setSignCouponId(Long signCouponId) {
        this.signCouponId = signCouponId;
    }

    public Integer getSignPoint() {
        return signPoint;
    }

    public void setSignPoint(Integer signPoint) {
        this.signPoint = signPoint;
    }

    public Long getRegisteCouponId() {
        return registeCouponId;
    }

    public void setRegisteCouponId(Long registeCouponId) {
        this.registeCouponId = registeCouponId;
    }

    public String getMemberWelfareUrl() {
        return memberWelfareUrl;
    }

    public void setMemberWelfareUrl(String memberWelfareUrl) {
        this.memberWelfareUrl = memberWelfareUrl;
    }

    public Long getRecommendCouponId() {
        return recommendCouponId;
    }

    public void setRecommendCouponId(Long recommendCouponId) {
        this.recommendCouponId = recommendCouponId;
    }

    public String getQrcodeImgUrl() {
        return qrcodeImgUrl;
    }

    public void setQrcodeImgUrl(String qrcodeImgUrl) {
        this.qrcodeImgUrl = qrcodeImgUrl;
    }

    public String getPopupContent() {
        return popupContent;
    }

    public void setPopupContent(String popupContent) {
        this.popupContent = popupContent;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public void setPopupTitle(String popupTitle) {
        this.popupTitle = popupTitle;
    }

    public String getPopupImgUrl() {
        return popupImgUrl;
    }

    public void setPopupImgUrl(String popupImgUrl) {
        this.popupImgUrl = popupImgUrl;
    }

    public String getPopupBtnText() {
        return popupBtnText;
    }

    public void setPopupBtnText(String popupBtnText) {
        this.popupBtnText = popupBtnText;
    }

    public String getPopupLink() {
        return popupLink;
    }

    public void setPopupLink(String popupLink) {
        this.popupLink = popupLink;
    }

    public Integer getPopupSwitch() {
        return popupSwitch;
    }

    public void setPopupSwitch(Integer popupSwitch) {
        this.popupSwitch = popupSwitch;
    }

    public Integer getPointValidMonth() {
        return pointValidMonth;
    }

    public void setPointValidMonth(Integer pointValidMonth) {
        this.pointValidMonth = pointValidMonth;
    }

    public BigDecimal getMinRechargeAmount() {
        return minRechargeAmount;
    }

    public void setMinRechargeAmount(BigDecimal minRechargeAmount) {
        this.minRechargeAmount = minRechargeAmount;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String[] getUploadImageExtensions() {
        return StringUtils.split(uploadImageExtension, SEPARATOR);
    }

    public String[] getUploadDocExtensions() {
        return StringUtils.split(uploadDocExtension, SEPARATOR);
    }

    public Boolean getDebug() {
        return isDebug;
    }

    public void setDebug(Boolean debug) {
        isDebug = debug;
    }

    public Boolean getSendMsg() {
        return isSendMsg;
    }

    public void setSendMsg(Boolean sendMsg) {
        isSendMsg = sendMsg;
    }

    public Integer getPointRatio() {
        return pointRatio;
    }

    public void setPointRatio(Integer pointRatio) {
        this.pointRatio = pointRatio;
    }

    public String getMsgUid() {
        return msgUid;
    }

    public void setMsgUid(String msgUid) {
        this.msgUid = msgUid;
    }

    public BigDecimal getOpenInvoiceAmount() {
        return openInvoiceAmount;
    }

    public void setOpenInvoiceAmount(BigDecimal openInvoiceAmount) {
        this.openInvoiceAmount = openInvoiceAmount;
    }

    public BigDecimal getInvoiceAmountRatio() {
        return invoiceAmountRatio;
    }

    public void setInvoiceAmountRatio(BigDecimal invoiceAmountRatio) {
        this.invoiceAmountRatio = invoiceAmountRatio;
    }

    public BigDecimal getIntegralRatio() {
        return integralRatio;
    }

    public void setIntegralRatio(BigDecimal integralRatio) {
        this.integralRatio = integralRatio;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgSign() {
        return msgSign;
    }

    public void setMsgSign(String msgSign) {
        this.msgSign = msgSign;
    }

    public String getMsgTemplate() {
        return msgTemplate;
    }

    public void setMsgTemplate(String msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getApiDomain() {
        return apiDomain;
    }

    public void setApiDomain(String apiDomain) {
        this.apiDomain = apiDomain;
    }

    public Boolean getIsDebug() {
        return isDebug;
    }

    public void setIsDebug(Boolean isDebug) {
        this.isDebug = isDebug;
    }

    public Boolean getIsSendMsg() {
        return isSendMsg;
    }

    public void setIsSendMsg(Boolean isSendMsg) {
        this.isSendMsg = isSendMsg;
    }

    public String getUploadImageExtension() {
        return uploadImageExtension;
    }

    public void setUploadImageExtension(String uploadImageExtension) {
        this.uploadImageExtension = uploadImageExtension;
    }

    public String getUploadImagePath() {
        return uploadImagePath;
    }

    public void setUploadImagePath(String uploadImagePath) {
        this.uploadImagePath = uploadImagePath;
    }

    public String getUploadDocExtension() {
        return uploadDocExtension;
    }

    public void setUploadDocExtension(String uploadDocExtension) {
        this.uploadDocExtension = uploadDocExtension;
    }

    public String getUploadDocPath() {
        return uploadDocPath;
    }

    public void setUploadDocPath(String uploadDocPath) {
        this.uploadDocPath = uploadDocPath;
    }

    public String getUploadMediaPath() {
        return uploadMediaPath;
    }

    public void setUploadMediaPath(String uploadMediaPath) {
        this.uploadMediaPath = uploadMediaPath;
    }

    public String getUploadMediaExtension() {
        return uploadMediaExtension;
    }

    public void setUploadMediaExtension(String uploadMediaExtension) {
        this.uploadMediaExtension = uploadMediaExtension;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public Integer getIpMaxMsgPerDay() {
        return ipMaxMsgPerDay;
    }

    public void setIpMaxMsgPerDay(Integer ipMaxMsgPerDay) {
        this.ipMaxMsgPerDay = ipMaxMsgPerDay;
    }

    public Integer getMobileMaxMsgPerDay() {
        return mobileMaxMsgPerDay;
    }

    public void setMobileMaxMsgPerDay(Integer mobileMaxMsgPerDay) {
        this.mobileMaxMsgPerDay = mobileMaxMsgPerDay;
    }

    public Integer getMsgExpireTime() {
        return msgExpireTime;
    }

    public void setMsgExpireTime(Integer msgExpireTime) {
        this.msgExpireTime = msgExpireTime;
    }

    public Integer getMsgIntervalTime() {
        return msgIntervalTime;
    }

    public void setMsgIntervalTime(Integer msgIntervalTime) {
        this.msgIntervalTime = msgIntervalTime;
    }

    public String getDefaultHead() {
        return defaultHead;
    }

    public void setDefaultHead(String defaultHead) {
        this.defaultHead = defaultHead;
    }

    public String getCsTel() {
        return csTel;
    }

    public void setCsTel(String csTel) {
        this.csTel = csTel;
    }
}
