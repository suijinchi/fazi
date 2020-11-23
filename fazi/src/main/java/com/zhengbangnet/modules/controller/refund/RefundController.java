package com.zhengbangnet.modules.controller.refund;

import com.zhengbangnet.common.utils.MD5Util;
import com.zhengbangnet.modules.entity.Refund;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.PaymentService;
import com.zhengbangnet.modules.service.RechargeOrderService;
import com.zhengbangnet.modules.service.RefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.weixin4j.Configuration;
import org.weixin4j.WXXMLUtil;
import org.weixin4j.Weixin;
import org.weixin4j.misc.BASE64Decoder;
import org.weixin4j.pay.ReturnMsg;
import org.weixin4j.pay.refund.WxRefundNotifyResult;
import org.weixin4j.pay.refund.WxRefundReqInfo;
import org.weixin4j.util.AES256Utils;
import org.weixin4j.util.XStreamFactory;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Controller("wxRefundController")
@RequestMapping("/wx/refund")
public class RefundController {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name="rechargeOrderServiceImpl")
	private RechargeOrderService rechargeOrderService;
	
	@Resource(name="paymentServiceImpl")
	private PaymentService paymentService;
	
	@Resource(name="refundServiceImpl")
	private RefundService refundService;
	
	@Resource(name="weixin")
	private Weixin weixin;
	
	@RequestMapping(value = "/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			logger.info("收到微信支付回调 start");
			ReturnMsg returnMsg = new ReturnMsg();
			returnMsg.setReturn_code("FAIL");
			returnMsg.setReturn_msg("");
			ServletInputStream in = request.getInputStream();
			String xmlMsg = XStreamFactory.inputStream2String(in);
			
			logger.info("收到微信退款回调内容："+xmlMsg);
			
			WxRefundNotifyResult result = (WxRefundNotifyResult)WXXMLUtil.xmlToBean(xmlMsg,WxRefundNotifyResult.class);
			if("SUCCESS".equals(result.getReturn_code())){
				String req_info = result.getReq_info();
				
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] decodeBuffer = decoder.decodeBuffer(req_info);

				String appid = result.getAppid();
				String mchKey = mchKey = Configuration.getPartnerKey();
	//			mchKey = MD5.encryptByMd5(mchKey);
				mchKey = MD5Util.encode(mchKey);
				String reqInfo = AES256Utils.Aes256Decode(decodeBuffer, mchKey.getBytes());
				logger.info("微信退款回调解密："+reqInfo);
				
//				WxRefundReqInfo info = getReqInfo(reqInfo);
				WxRefundReqInfo info = (WxRefundReqInfo)WXXMLUtil.xmlToBean(reqInfo,WxRefundReqInfo.class);

				synchronized (this) {
					String out_refund_no = info.getOut_refund_no();
	//				String transaction_id = info.getTransaction_id();
	//				String out_trade_no = info.getOut_trade_no();
	//				Integer total_fee = info.getTotal_fee();
					Refund refund = refundService.getBySn(out_refund_no);
					if(refund!=null){
						if(Refund.REFUNDING.equals(refund.getStatus())|| Refund.REFUNED.equals(refund.getStatus())){
							returnMsg.setReturn_code("SUCCESS");
						}
						if(Refund.REFUNDING.equals(refund.getStatus())){
							refundService.handleWxRefund(refund.getId(),info);
						}
					}else{
						returnMsg.setReturn_code("SUCCESS");
					}
				}
			}
			logger.info("收到微信退款回调 end");
			logger.info("返回给微信信息："+returnMsg.toXML());
			response.getWriter().print(returnMsg.toXML());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("微信退款回调异常：",e);
		}
	}


	private WxRefundReqInfo getReqInfo(String xmlMsg) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(WxRefundReqInfo.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		//结果
		WxRefundReqInfo result = (WxRefundReqInfo) unmarshaller.unmarshal(new StringReader(xmlMsg));
    	return result;
    }


	public static void main(String[] args) throws Exception {
/*
//		String xml = "<xml><return_code>SUCCESS</return_code><appid><![CDATA[wx1cd032053fd010bb]]></appid><mch_id><![CDATA[1488387082]]></mch_id><nonce_str><![CDATA[3145168c46bc396ac572e5119be55d8f]]></nonce_str><req_info><![CDATA[oBgojS8+Wtr0BvsU2YDE93nvfio7CPB2xcNZH1TXkfNKyEU05WPJuSq4juu4HWgu5lfLj8YhhmPoj+Ia/bkVOTd1FZArPDUS+b0iBQMqZN18usZ/6qCCfYGmnvhfqtDR6YQTgBaNdPgIpWNmVhReKIRPCql6tsuh7wKMT7rBFN0nGc7iEuGqKQcFM60O/5B26bRl/fus9+2BpINvAU369h5io/SyzCQ1bO8cn7ug+niNk6YMQvMHHdEOLuGdyhpaHBqo2muzDqV/+Aw4Qu5VhYjxceZAQrLCfJFNif1/OscQggjynDSUBaeYgWxm01Et7XvZgNPQ9K3/tpxrwo0Btm5XP47yky229Ch2ms4WejiFx3ULVLm77M8ZQjDzfxCIurN6l+zgsKIZ15mvEAKtXRelo6AyB+S3ZWzJ3Ip3qbEE+6YZLY1oAA+HSr8ZVLota7eMstJiHb6aAtTvjzDS1yl6i4bQBx+rn+ORH+4bt5Ha9FRt+Hl1bq8ZybzAQnFiA1CSajeSTY/Px77K8G1G0rGQxTmZlGCmF4kmbJ1iGoDhI7CRrEmQCwW04IPTfOpD+7pndsMbOzqaLOfjRgresOpR/SHdmVc3SYY1q9kj1KrpKS+S2S/lv/6l7wnJ6aFtECGakAzUEQgLN/xuKjdBj2D0ac/U/wWbW4geX5wKvm9RI0jq0ijKYMghmfSJ6cu1JFX4NSHRJIbkQm8zExfhhPRIS5fkfdpT9jBIOldm9wO5hV68d7QXtMthJrfp4n6JsqxE3qlVHpxDVTg/okqT7AHRh/rLa9Q73l6SFdvfnKxh8eoIrewmzu1+ewvapVrJ3qjEPL2zG0+FRkrMa5Cb2Y0mfMSKRyuczeyjIqMBvuyFwRYWPZJ2rXDktHmdEFluVVu0mBJ/y/FCygasbKYwugIw119EiaFff/+9seSloHj9/jnppHh+JX2bX4wNUc6+30rgTW+Fd+6brkG3mmm3xprcig4t7IKCLffYqVnlunJvlbVE4/C0+tXedRA3WIjUKxp+deQZP7Toi4JDGf6V+JPStuulobq0AyNohebmzMg=]]></req_info></xml>";
		String xml = "<xml><return_code>SUCCESS</return_code><appid><![CDATA[wxd7cf5169f036b230]]></appid><mch_id><![CDATA[1509017601]]></mch_id><nonce_str><![CDATA[7202adb0711e0dc86fa1e162be5678c6]]></nonce_str><req_info><![CDATA[y5agfHJtLwsBQulg6MypHSyV7QmdPtncTBaokzErGfd44NDZmsGGxLxvNJQkVXUyVJlMg2VwILWaYCC83AblzV8OavbGMgnh4/SogYZzqveDLywXWYos0JGDBiEETrqjKH4Vyd/7vhrpobwIpi464Gjyl0kfDcY8hYdt3xQ4kghtm3TtGZwa6E/peGVQjFkvB8TB7VlG0zITN9vf4QYa0Hz+Bn9hirXjk/2a+LXZJz7NkuWlwaSxc8FD4V2LHuolZnRi8C9wYUXaDFR0+lG4nAm/GwW5XTaPD80yp0RjwA/yFEX+2mzX6uW/DCyi7OisFGwIblwbKTACb0jCRaWKtSYABpgM4ww2zBQcNSNSIWb82cwyuCrBNpe7l6ZrsPEG+++jNYJJtU9PVuY1FHm5mO4BUVnGdpHBzynRWNeSClmS4jod9NAnVNvFKOtECC4XGrrK/NZFpguvrX4ZoBvKS/vtVNi9vI5/JPaF3Yzi9M8GIRnIujEbiZQilBe+/RfiJbfoUDR0Xz3bi0W2i6IoLDKv3htZTlu5wXcRX/C2HA2MmUxsKahQmCE3wG/9wgicLgg5cBbu8VC7/3m/O16GmJvgzX/NBbyvE0xdcYf1GqPlRl3JLRDUVUv3u7RWL5QwqRvlMWBjICFcxD3lFkAyeeQkawxjULBx/j7dBvGHYsbloQlQdtRGMl6owRiKbJ2B/C4hyeVs7MROIyGhfeENEi4Hk3A9LaXfYbJZ0V+xpCqydYatgS1Uj9QYQMvZg20SXN61eMbyAbIhf3J12YESVZ39tTQ5cOTtqy+/IbHDJP8sYFlM+flwor2SWa1Kw1nwQpA6sdi+VVCD7mar/DQUqDAlPi+uG+pljmNbw70XHuv6M6lxPc9mYCOLZHlCAz/6535FoDgwHOJDJa37cZcZco1KldYIirf65Y1L3eWc51tdYPTmCmePIG3BroGvpY5mTXDkgvotFRfxtigKc5Ay1RLKbuiLZNDI7xxeyy0fQRJ65qPU95fuSCWSxmOsPOXtT1NkD5ouuNo/zY51CIpTEdRGe4qh6RZs4EtSE6Pi+3Ofz+hbigMRDRx76dBywDNY]]></req_info></xml>";

		WxRefundNotifyResult result = (WxRefundNotifyResult)WXXMLUtil.xmlToBean(xml,WxRefundNotifyResult.class);
		System.out.println(result);

		*/

	}
}


