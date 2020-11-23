/**
 * 
 */
package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.RandomUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.common.utils.SnUtils;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.Orders;
import com.zhengbangnet.modules.entity.Payment;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.OrdersService;
import com.zhengbangnet.modules.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin4j.Configuration;
import org.weixin4j.WXXMLUtil;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.pay.*;
import org.weixin4j.util.XStreamFactory;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller("mobilePaymentController")
@RequestMapping("/mobile/payment")
public class PaymentController extends AdminBaseController {
	
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "ordersServiceImpl")
	private OrdersService ordersService;

	@Resource(name="weixin")
	private Weixin weixin;
	
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ResponseBody
	public Message pay(Long orderId, HttpSession session, HttpServletRequest request) {

		//支付id
		String payId = "";
		Member member = memberService.getCurrent(request);
		Orders orders = ordersService.getByPrimaryKey(orderId);
		if (orders == null || orders.getMemberId().longValue() != member.getId().longValue()) {
			return Message.error("订单不存在");
		}
		if (orders.getPayStatus() == 1) {
			return Message.error("订单已支付！");
		}
		if(orders.getOrdersStatus()!=1){
			return Message.error("订单状态不正确，请刷新界面！");
		}
		payId = orders.getId().toString();

		//支付金额
		BigDecimal amount1 = orders.getSumAmount().subtract(orders.getPointOffset()).subtract(orders.getCouponPay()).subtract(orders.getDiscountPay()).subtract(orders.getBalancePay()).add(orders.getOffsetAmount());
		BigDecimal amount = orders.getAmount().subtract(orders.getBalancePay());
		if(amount.compareTo(BigDecimal.ZERO)<=0){
			return Message.error("订单未支付金额不正确");
		}
		orders.setWxPayRecord(amount);
		ordersService.updateByPrimaryKey(orders);

		Payment payment = new Payment();
		payment.setAmount(amount);
		payment.setSn(SnUtils.getWechatSn(member.getId()));
		payment.setCreateDate(new Date());
		payment.setModifyDate(new Date());
		payment.setMemberId(member.getId());
		payment.setType(Payment.TYPE_ORDERS);
		payment.setPayId(payId);
		payment.setStatus(Payment.WAIT);
		paymentService.insert(payment);

		// 微信
		UnifiedOrder order = new UnifiedOrder();
		order.setAppid(Configuration.getOAuthAppId());
		order.setBody("法滋蛋糕");
		order.setMch_id(Configuration.getOAuthMchid());
		order.setNonce_str(RandomUtils.getRandomStringByLength(32));
		order.setNotify_url(SettingUtils.get().getDomain() + "/mobile/payment/notify");
		order.setOut_trade_no(payment.getSn());
		order.setSpbill_create_ip(request.getRemoteAddr());
		order.setTotal_fee(String.valueOf(payment.getAmount().multiply(new BigDecimal(100)).intValue()));
		order.setTrade_type("JSAPI");
		order.setOpenid(member.getOpenid());
		order.setSign(SignUtil.getSign(order.toMap(), Configuration.getPartnerKey()));

		UnifiedOrderResult payUnifiedOrder = null;
		try {
			payUnifiedOrder = weixin.payUnifiedOrder(order);
		} catch (WeixinException e) {
			logger.warn("APP微信支付失败：", e);
			return Message.error("微信下单异常");
		}

		if (payUnifiedOrder.getReturn_code().equals("SUCCESS")) {
			long time = System.currentTimeMillis();
			Map<String, String> data = new HashMap<String, String>();
			data.put("appId", Configuration.getOAuthAppId());
			data.put("timeStamp", String.valueOf(time));
			data.put("nonceStr", payUnifiedOrder.getNonce_str());
			data.put("package", "prepay_id="+payUnifiedOrder.getPrepay_id());
			data.put("signType", "MD5");
			String paySign = SignUtil.getSign(data, Configuration.getPartnerKey());
			data.put("paySign", paySign);
			logger.info("pay_info:" + data);
			Message success = Message.success("创建成功");
			success.getData().put("pay_info", data);
			return success;
		} else {
			logger.warn(payUnifiedOrder.getReturn_msg());
		}
		return Message.error("调起微信支付："+payUnifiedOrder.getReturn_msg());
	}
	
	
	
	@RequestMapping(value = "/notify")
	public synchronized void wechatNotify(HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			logger.info("收到微信支付回调 start");
			
			ReturnMsg returnMsg = new ReturnMsg();
			returnMsg.setReturn_code("FAIL");
			
			ServletInputStream in = request.getInputStream();
			String xmlMsg = XStreamFactory.inputStream2String(in);

//			PayNotifyResult payNotifyResult = PayUtil.getNotifyResult(xmlMsg);
			PayNotifyResult payNotifyResult = (PayNotifyResult) WXXMLUtil.xmlToBean(xmlMsg,PayNotifyResult.class);
			
	        //商户订单号
	        String out_trade_no = payNotifyResult.getOut_trade_no();
	        
	        //支付结果
	        String return_code = payNotifyResult.getReturn_code();
	        String result_code = payNotifyResult.getResult_code();
	        
	        logger.info("收到微信支付回调 return_code = " + return_code);
	        logger.info("收到微信支付回调 out_trade_no = " + out_trade_no);
	        logger.info("收到微信支付回调 内容 ：----\n" + xmlMsg);

	        synchronized (this){
				//判断签名及结果
				if ("SUCCESS".equals(return_code)) {
					Payment payment = paymentService.getBySn(out_trade_no);
					String key = Configuration.getPartnerKey();
					//验证签名
					boolean verify = PayUtil.verifySign(xmlMsg, key);
					if (verify&&"SUCCESS".equals(result_code)) {
						if(payment!=null&&payment.getStatus().equals(Payment.WAIT)){
							String total_fee = payNotifyResult.getTotal_fee();
							BigDecimal totalFee = new BigDecimal(total_fee).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
							String time_end = payNotifyResult.getTime_end();
							Date payDate = DateUtils.stringToDate(time_end, "yyyyMMddHHmmss");
							if(totalFee.compareTo(payment.getAmount())==0){
								paymentService.handle(payment.getId(),payNotifyResult.getTransaction_id(),payDate);
							}else{
								logger.info("微信支付回调金额不正确");
							}
						}
						returnMsg.setReturn_code("SUCCESS");
						returnMsg.setReturn_msg("处理成功");
					} else {
						returnMsg.setReturn_msg("签名失败");
					}
				} else {
					returnMsg.setReturn_msg("签名失败");
				}
			}

	        logger.info("商户返回信息："+returnMsg.toXML());
	        logger.info("收到微信支付回调 end");
	        response.getWriter().print(returnMsg.toXML());
		}catch(Exception e){
			logger.warn("微信支付回调：",e);
		}
	}
	
}
