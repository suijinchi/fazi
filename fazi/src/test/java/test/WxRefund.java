package test;

import org.weixin4j.WXXMLUtil;
import org.weixin4j.pay.refund.WxRefundReqInfo;

public class WxRefund {

    public static void main(String[] args) throws Exception {

        String reqInfo = "<root>\n" +
                "<out_refund_no><![CDATA[RW201810291602385718_201810291602357498]]></out_refund_no>\n" +
                "<out_trade_no><![CDATA[W201810291602385718]]></out_trade_no>\n" +
                "<refund_account><![CDATA[REFUND_SOURCE_RECHARGE_FUNDS]]></refund_account>\n" +
                "<refund_fee><![CDATA[1]]></refund_fee>\n" +
                "<refund_id><![CDATA[50000308522018102906843663058]]></refund_id>\n" +
                "<refund_recv_accout><![CDATA[支付用户零钱]]></refund_recv_accout>\n" +
                "<refund_request_source><![CDATA[API]]></refund_request_source>\n" +
                "<refund_status><![CDATA[SUCCESS]]></refund_status>\n" +
                "<settlement_refund_fee><![CDATA[1]]></settlement_refund_fee>\n" +
                "<settlement_total_fee><![CDATA[1]]></settlement_total_fee>\n" +
                "<success_time><![CDATA[2018-10-29 16:19:48]]></success_time>\n" +
                "<total_fee><![CDATA[1]]></total_fee>\n" +
                "<transaction_id><![CDATA[4200000212201810293953747403]]></transaction_id>\n" +
                "</root>";

        WxRefundReqInfo info = (WxRefundReqInfo) WXXMLUtil.xmlToBean(reqInfo,WxRefundReqInfo.class);
        System.out.println(info);

    }

}
