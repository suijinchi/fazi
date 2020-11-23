package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Refund;
import org.weixin4j.pay.refund.WxRefundReqInfo;

public interface RefundService extends BaseService<Refund,Long> {

    Refund getBySn(String out_refund_no);

    void handleWxRefund(Long refundId, WxRefundReqInfo info);
}