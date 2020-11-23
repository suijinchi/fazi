package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.RandomUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.common.utils.SnUtils;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin4j.Configuration;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.pay.SignUtil;
import org.weixin4j.pay.UnifiedOrder;
import org.weixin4j.pay.UnifiedOrderResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Controller("apiRechargeController")
@RequestMapping("/mobile/recharge")
public class RechargeController extends MobileBaseController {
	
	@Resource(name="memberServiceImpl")
	private MemberService memberService;

	@Resource(name="agentServiceImpl")
	private AgentService agentService;

	@Resource(name = "messageLogServiceImpl")
	private MessageLogService messageLogService;

	@Resource(name="areaServiceImpl")
	private AreaService areaService;
	
	@Resource(name="rechargeTypeServiceImpl")
	private RechargeTypeService rechargeTypeService;
	
	@Resource(name="rechargeOrderServiceImpl")
	private RechargeOrderService rechargeOrderService;

	@Resource(name="payMethodServiceImpl")
	private PayMethodService payMethodService;
	
	@Resource(name="paymentServiceImpl")
	private PaymentService paymentService;
	
	@Resource(name="weixin")
	private Weixin weixin;

	/**
	 * 获取充值项
	 */
	@RequestMapping(value = "/index")
	public String sendMsgCode(Long agentId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isShow",1);
		List<HashMap<String, Object>> items = rechargeTypeService.findListByParams(params);
		model.addAttribute("rechargeTypeList",items);
		model.addAttribute("setting",SettingUtils.get());
		model.addAttribute("agentId",agentId);
		return "/mobile/recharge/index";
	}

	/**
	 * 充值提交
	 */
	@RequestMapping(value = "/submit")
	public @ResponseBody
	Message submit(BigDecimal amount,Long agentId,Long rechargeTypeId,HttpServletRequest request, HttpServletResponse response) {

		Member member = memberService.getCurrent(request);

		BigDecimal rechargeAmount = BigDecimal.ZERO;
		BigDecimal giveAmount = BigDecimal.ZERO;
		RechargeType rechargeType = rechargeTypeService.getByPrimaryKey(rechargeTypeId);
		if(rechargeType==null&&amount==null){
			return Message.error("请选择充值金额");
		}
		logger.info("充值金额："+amount+",充值方案："+rechargeTypeId+",会员："+member.getId()+"-"+member.getName());
		if(rechargeType!=null){
			rechargeAmount = rechargeType.getAmount();
			giveAmount = rechargeType.getGiveAmount();
		}else{
			rechargeAmount = amount;
			Setting setting = SettingUtils.get();
			if(rechargeAmount.compareTo(setting.getMinRechargeAmount())<0){
				return Message.error("最小充值金额为："+setting.getMinRechargeAmount());
			}
			giveAmount = BigDecimal.ZERO;
		}
        logger.info("实际充值金额："+rechargeAmount+",赠送金额："+giveAmount+",会员："+member.getId()+"-"+member.getName());

		String sn = SnUtils.getWechatSn(member.getId());
		RechargeOrder ro = new RechargeOrder();
		ro.setCreateDate(new Date());
		ro.setModifyDate(new Date());
		ro.setAmount(rechargeAmount);
		ro.setBenefitAmount(BigDecimal.ZERO);
		ro.setStatus(RechargeOrder.UNPAY);
		ro.setPayMethodId(1L);
		ro.setMemberId(member.getId());
		ro.setSn(sn);
		ro.setGiveAmount(giveAmount);
		ro.setTaxRatio(BigDecimal.ZERO);
		ro.setType(0);
		ro.setAgentId(agentId);
		if(agentId!=null){
			ro.setType(1);
		}
		ro.setTaxRatio(new BigDecimal(0.006));
		rechargeOrderService.insert(ro);

		Payment payment = new Payment();
		payment.setAmount(rechargeAmount);
		payment.setSn(sn);
		payment.setCreateDate(new Date());
		payment.setModifyDate(new Date());
		payment.setMemberId(member.getId());
		payment.setType(Payment.TYPE_RECHARGE);
		payment.setPayId(ro.getId().toString());
		payment.setStatus(Payment.WAIT);
		paymentService.insert(payment);

		String body = "法滋余额充值";

		// 微信
		UnifiedOrder order = new UnifiedOrder();
		order.setAppid(Configuration.getOAuthAppId());
		order.setBody(body);
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
//			data.put("appId", "wxbc7c4edce4bc5e2b");
			data.put("appId", Configuration.getOAuthAppId());
			data.put("timeStamp", String.valueOf(time));
			data.put("nonceStr", payUnifiedOrder.getNonce_str());
			data.put("package", "prepay_id="+payUnifiedOrder.getPrepay_id());
			data.put("signType", "MD5");
			String paySign = SignUtil.getSign(data, Configuration.getPartnerKey());
			data.put("paySign", paySign);
			Message success = Message.success("创建成功");
			logger.info("pay_info:" + data);
			success.getData().put("pay_info", data);
			return success;
		} else {
			logger.warn(payUnifiedOrder.getReturn_msg());
		}
		return Message.error("微信下单失败："+payUnifiedOrder.getReturn_msg());
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/mobile/recharge/list";
	}

	/**
	 * 列表数据
	 */
	@RequestMapping(value = "/listData")
	public @ResponseBody
	Object listPage(Integer pageNo,Integer pageSize,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Member member = memberService.getCurrent(request);

		Map<String,Object> params = new HashMap<String,Object>();
		Pageable pageable = new Pageable(pageNo,pageSize);
		params.put("pageable", pageable);
		params.put("status", RechargeOrder.PAID);
		params.put("memberId", member.getId());
		Page<Map<String, Object>> page = rechargeOrderService.findPageByParams(params);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> data:page.getContent()){
			BigDecimal amount = (BigDecimal)data.get("amount");
			BigDecimal giveAmount = (BigDecimal)data.get("giveAmount");
			Date payDate = (Date)data.get("payDate");
			Map<String, Object> temp = new HashMap<String,Object>();
			temp.put("amount", amount);
			temp.put("giveAmount", giveAmount);
			temp.put("sumAmount", giveAmount.add(amount));
			temp.put("payDate", DateUtils.dateToString(payDate, "MM月dd日 HH:mm"));
			list.add(temp);
		}

		Map<String,Object> data = new HashMap<String,Object>();
		data.put("pageNo", page.getPageNo());
		data.put("pageSize", page.getPageSize());
		data.put("totalPages", page.getTotalPages());
		data.put("total", page.getTotal());
		data.put("listData", list);
		return data;
	}

	@RequestMapping(value = "/agent/{id}", method = RequestMethod.GET)
	public String index(@PathVariable(value = "id") Long id, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws UnsupportedEncodingException {
		Agent agent = agentService.getByPrimaryKey(id);
		if (agent == null) {
			model.addAttribute("tips", "店铺不存在或已关闭");
			return RESULT_TIPS;
		}
		String action = URLEncoder.encode("/mobile/recharge/index?agentId=" + id, "utf-8");
		Member member = memberService.getCurrent(request);
		if (member == null) {
			return "redirect:/mobile/wechat/index?action=" + action;
		}
		return "redirect:/mobile/recharge/index?agentId=" + id;
	}

}


