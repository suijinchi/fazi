package com.zhengbangnet.modules.controller.admin;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.zhengbangnet.Config;
import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.common.utils.excel.ExportExcel;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin4j.Configuration;
import org.weixin4j.WXTemplateMsg;
import org.weixin4j.Weixin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller("adminOrdersController")
@RequestMapping("/admin/orders")
public class OrdersController extends AdminBaseController {

	@Resource(name = "ordersServiceImpl")
	private OrdersService ordersService;

	@Resource(name = "ordersItemServiceImpl")
	private OrdersItemService ordersItemService;

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;

	@Resource(name = "sysAdminServiceImpl")
	private SysAdminService sysAdminService;

	@Resource(name = "refundServiceImpl")
	private RefundService refundService;

	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;

	@Resource(name = "weixin")
	private Weixin weixin;

	/**
	 * 订单列表
	 */	
	@SysLog(module="订单管理",method="查看订单列表")
	@RequestMapping("/list")
	public String platOrders(Integer serviceConfirmStatus,String orderBegDt, String orderEndDt, String memberNickName, String orderNumb,
			String productName,String ordererMobile,String mobile,Integer notShowRefund,
			String ordersStatus, String payStatus, String sendStatus, String shippingMethod, Pageable pageable, Model model) {
		if(notShowRefund==null){
			notShowRefund = 1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 页码
		map.put("pageable", pageable);
		// 订单开始日期
		if (orderBegDt != null && orderBegDt.length() > 0)
			map.put("orderBegDt", orderBegDt);
		// 订单结束日期
		if (orderEndDt != null && orderEndDt.length() > 0)
			map.put("orderEndDt", orderEndDt);
		// 会员昵称
		if (memberNickName != null && memberNickName.length() > 0)
			map.put("memberNickName", memberNickName);
		// 订单编号
		if (orderNumb != null && orderNumb.length() > 0)
			map.put("orderNumb", orderNumb);
		// 订单状态
		if (ordersStatus != null && ordersStatus.length() > 0)
			map.put("ordersStatus", ordersStatus);
		// 支付状态
		if (payStatus != null && payStatus.length() > 0)
			map.put("payStatus", payStatus);
		// 发送状态
		if (sendStatus != null && sendStatus.length() > 0)
			map.put("sendStatus", sendStatus);
		// 配送方式
		if (shippingMethod != null && shippingMethod.length() > 0)
			map.put("shippingMethod", shippingMethod);

		map.put("productName", productName);
		map.put("ordererMobile", ordererMobile);
		map.put("mobile", mobile);
		map.put("serviceConfirmStatus", serviceConfirmStatus);
		map.put("notShowRefund", notShowRefund);

		// 查询
		Page<Map<String, Object>> page = ordersService.findPageByParams(map);
		// 返回结果集
		model.addAttribute("page", page);
		if (page.getContent()!=null) {
			for (int i=0;i<page.getContent().size();i++) {

				Map<String, Object> data = page.getContent().get(i);

				Long id = (Long) data.get("id");
				List<OrdersItem> ordersItemList = ordersItemService.getByOrdersId(id);
				String ordersItemName = ordersItemList.get(0).getName();
				data.put("ordersItemName", ordersItemName);
				if (ordersItemName != null && ordersItemName.length() > 5) {
					ordersItemName = ordersItemName.substring(0, 5);
				}
				data.put("shortOrdersItemName", ordersItemName);

				//处理昵称
				String nickName = (String) data.get("nickname");
				if (nickName != null && nickName.length() > 5) {
					nickName = nickName.substring(0, 5);
				}
				data.put("shortNickName", nickName);

				String shippingTime = (String)data.get("shippingTime");
				if(StringUtils.isNotBlank(shippingTime)){
					Date st = DateUtils.stringToDate(shippingTime, "yyyy-MM-dd");
					int days = DateUtils.getDays(new Date(), st);
					if(days==0){
						shippingTime = shippingTime+"（今天）";
					}else if(days==1){
						shippingTime = shippingTime+"（明天）";
					}
					data.put("shippingTime",shippingTime);
				}

				String pickUpTime = (String)data.get("pickUpTime");
				if(StringUtils.isNotBlank(pickUpTime)){
					Date pt = DateUtils.stringToDate(pickUpTime, "yyyy-MM-dd");
					int ptDays = DateUtils.getDays(new Date(), pt);
					if(ptDays==0){
						pickUpTime = pickUpTime+"（今天）";
					}else if(ptDays==1){
						pickUpTime = pickUpTime+"（明天）";
					}
					data.put("pickUpTime",pickUpTime);
				}

			}
		}
		model.addAttribute("orderBegDt", orderBegDt == null ? "" : orderBegDt);
		model.addAttribute("orderEndDt", orderEndDt == null ? "" : orderEndDt);
		model.addAttribute("memberNickName", memberNickName == null ? "" : memberNickName);
		model.addAttribute("orderNumb", orderNumb == null ? "" : orderNumb);
		model.addAttribute("ordersStatus", ordersStatus == null ? "" : ordersStatus);
		model.addAttribute("payStatus", payStatus == null ? "" : payStatus);
		model.addAttribute("sendStatus", sendStatus == null ? "" : sendStatus);
		model.addAttribute("shippingMethod", shippingMethod == null ? "" : shippingMethod);
		model.addAttribute("productName", productName);
		model.addAttribute("ordererMobile", ordererMobile);
		model.addAttribute("mobile", mobile);
		model.addAttribute("serviceConfirmStatus", serviceConfirmStatus);
		model.addAttribute("notShowRefund", notShowRefund);
		return "/admin/orders/list";
	}

	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 订单-导出EXCEL
	 */
	@SysLog(module="订单管理",method="订单列表导出")
	@RequestMapping("/export")
	public void platExportXls(Long[] ids,String orderBegDt, String orderEndDt, String memberNickName, String orderNumb,
			String orderStatus, String payStatus, String sendStatus, HttpServletResponse response) {

		List<HashMap<String, Object>> ls = null;
		if(ids!=null&&ids.length>0){
			ls = ordersService.findListByIds(ids);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			// 订单开始日期
			if (orderBegDt != null && orderBegDt.length() > 0)
				map.put("orderBegDt", orderBegDt);
			// 订单结束日期
			if (orderEndDt != null && orderEndDt.length() > 0)
				map.put("orderEndDt", orderEndDt);
			// 会员昵称
			if (memberNickName != null && memberNickName.length() > 0)
				map.put("memberNickName", memberNickName);
			// 订单编号
			if (orderNumb != null && orderNumb.length() > 0)
				map.put("orderNumb", orderNumb);
			// 订单状态
			if (orderStatus != null && orderStatus.length() > 0)
				map.put("orderStatus", orderStatus);
			// 支付状态
			if (payStatus != null && payStatus.length() > 0)
				map.put("payStatus", payStatus);
			// 发送状态
			if (sendStatus != null && sendStatus.length() > 0)
				map.put("sendStatus", sendStatus);
			ls = ordersService.findListByParams(map);
		}

		Integer sumPointPay = 0;
		BigDecimal sumPointOffset = BigDecimal.ZERO;
		BigDecimal sumBalancePay = BigDecimal.ZERO;
		BigDecimal sumThirdPay = BigDecimal.ZERO;
		BigDecimal sumCouponPay = BigDecimal.ZERO;
		BigDecimal sumDiscount = BigDecimal.ZERO;
		BigDecimal sumAmount = BigDecimal.ZERO;
		BigDecimal sumSumAmount = BigDecimal.ZERO;

		String[] header = {
				"订单编号", "商品名称", "订单支付金额", "订单总金额","会员折扣",
				"积分支付额", "积分抵扣金额", "余额支付金额", "第三方支付金额", "卡券抵扣金额",
				"订单状态", "支付状态", "发货状态", "创建时间","配送方式",
				"会员昵称","收货人", "电话", "收货地址","微信支付单号",
				"微信支付商户单号"};
		ExportExcel exportXls = null;
		try {
			exportXls = new ExportExcel("平台订单统计表", header);
			for (HashMap m : ls) {

                Integer os = (Integer)m.get("ordersStatus");
                String shippingMethod = String.valueOf(m.get("shippingMethod"));
                String ost = "";
                if(1==os){
                    ost = "未确认";
                }else if(2==os){
                    ost = "已确认";
                }else if(3==os){
                    ost = "已完成";
                }else if(4==os){
                    ost = "申请退款";
                }else if(5==os){
                    ost = "退款中";
                }else if(6==os){
                    ost = "已退款";
                }

				sumBalancePay = sumBalancePay.add(new BigDecimal((m.get("balancePay")).toString()));

				sumPointOffset = sumPointOffset.add(new BigDecimal((m.get("pointOffset")).toString()));
				sumThirdPay = sumThirdPay.add(new BigDecimal((m.get("thirdPay")).toString()));
				sumPointPay = sumPointPay+(int)m.get("pointPay");
				sumAmount = sumAmount.add(new BigDecimal((m.get("amount")).toString()));
				sumSumAmount = sumSumAmount.add(new BigDecimal((m.get("sumAmount")).toString()));
				sumCouponPay = sumCouponPay.add(new BigDecimal((m.get("couponPay")).toString()));

				sumDiscount = sumDiscount.add((BigDecimal)m.get("discountPay"));

				String pname = "";
				Long id = (Long)m.get("id");
				List<Map<String, Object>> oiList = ordersItemService.findByOrdersId(id);
				for(Map<String,Object> oi:oiList){
					String n = (String)oi.get("name");
					pname = pname + "【" + n+"】";
				}

				Row row = exportXls.addRow();
				exportXls.addCell(row, 0, m.get("sn"));
				exportXls.addCell(row, 1, pname);
				exportXls.addCell(row, 2, ((BigDecimal)m.get("amount")).doubleValue());
				exportXls.addCell(row, 3, ((BigDecimal)m.get("sumAmount")).doubleValue());
				exportXls.addCell(row, 4, ((BigDecimal)m.get("discountPay")).doubleValue());
				exportXls.addCell(row, 5, m.get("pointPay"));
				exportXls.addCell(row, 6, ((BigDecimal)m.get("pointOffset")).doubleValue());
				exportXls.addCell(row, 7, ((BigDecimal)m.get("balancePay")).doubleValue());
				exportXls.addCell(row, 8, ((BigDecimal)m.get("thirdPay")).doubleValue());
				exportXls.addCell(row, 9, ((BigDecimal)m.get("couponPay")).doubleValue());
				exportXls.addCell(row, 10,ost);
				exportXls.addCell(row, 11,
						String.valueOf(m.get("payStatus")).equals("0") ? "未支付"
						: (String.valueOf(m.get("payStatus")).equals("1") ? "已支付" : ""));
				exportXls.addCell(row, 12,
						String.valueOf(m.get("shippingStatus")).equals("0") ? "未发货"
						: (String.valueOf(m.get("shippingStatus")).equals("1") ? "已发货"
						: (String.valueOf(m.get("shippingStatus")).equals("2") ? "已确认收货" : "")));
				exportXls.addCell(row, 13, m.get("createDate"));
                exportXls.addCell(row, 14, shippingMethod.equals("0") ? "自提" : "发货");
				exportXls.addCell(row, 15, m.get("nickname"));
				exportXls.addCell(row, 16, m.get("name"));
				exportXls.addCell(row, 17, m.get("mobile"));

                if("0".equals(shippingMethod)){
                    String pickUpAddress = (String)m.get("pickUpAddress");
                    String pickUpTime = (String)m.get("pickUpTime");
                    exportXls.addCell(row, 18, pickUpAddress+" "+pickUpTime);
                }else{
                    exportXls.addCell(row, 18, m.get("address"));
                }
                exportXls.addCell(row, 19, m.get("paymentSn"));
                exportXls.addCell(row, 20, m.get("paymentPaySn"));
			}

			Row row = exportXls.addRow();
			exportXls.addCell(row, 0, "总计");
			exportXls.addCell(row, 1, "");
			exportXls.addCell(row, 2, sumAmount.doubleValue());
			exportXls.addCell(row, 3, sumSumAmount.doubleValue());
			exportXls.addCell(row, 4, sumDiscount.doubleValue());
			exportXls.addCell(row, 5, sumPointPay);
			exportXls.addCell(row, 6, sumPointOffset.doubleValue());
			exportXls.addCell(row, 7, sumBalancePay.doubleValue());
			exportXls.addCell(row, 8, sumThirdPay.doubleValue());
			exportXls.addCell(row, 9, sumCouponPay.doubleValue());
			exportXls.addCell(row, 10, "");
			exportXls.addCell(row, 11, "");
			exportXls.addCell(row, 12, "");
			exportXls.addCell(row, 13, "");
			exportXls.addCell(row, 14, "");
			exportXls.addCell(row, 15, "");
			exportXls.addCell(row, 16, "");
			exportXls.addCell(row, 17, "");

			exportXls.write(response, "订单统计表.xlsx");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (exportXls != null)
				exportXls.dispose();
		}
	}

	/**
	 * 查看订单详情
	 */
	@SysLog(module="订单管理",method="查看订单详情")
	@RequestMapping("/detail")
	public String platDetail(String orderId, String orderStatus, String payStatus, String sendStatus, String shippingMethod, Model model) {
		// orderId
		long id = 0;
		try {
			id = Long.valueOf(orderId).longValue();
		} catch (Exception ex) {
			id = 0;
		}
		Orders orders = ordersService.getByPrimaryKey(id);
		boolean orderNull = orders == null;
		// 订单编号
		model.addAttribute("sn", orderNull ? "" : (orders.getSn() == null ? "" : orders.getSn()));
		// 金额
		model.addAttribute("amount",
				orderNull ? 0.00
						: (orders.getAmount() == null ? 0.00
								: orders.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
		// 订单状态
		model.addAttribute("ordersStatus",
				orderNull ? "" : (orders.getOrdersStatus() == null ? "" : orders.getOrdersStatus()));
		// 支付状态
		model.addAttribute("payStatus", orderNull ? "" : (orders.getPayStatus() == null ? "" : orders.getPayStatus()));
		// 发货状态
		model.addAttribute("shippingStatus",
				orderNull ? "" : (orders.getShippingStatus() == null ? "" : orders.getShippingStatus()));
		// 收货人
		model.addAttribute("name", orderNull ? "" : (orders.getName() == null ? "" : orders.getName()));
		// 地区
		long areaId = orderNull ? 0 : (orders.getAreaId() == null ? 0 : orders.getAreaId());
		Area area = areaService.getByPrimaryKey(areaId);
		model.addAttribute("area", area == null ? "" : (area.getFullName() == null ? "" : area.getFullName()));
		// 配送方式
		model.addAttribute("shippingMethod",
				orderNull ? "" : (orders.getShippingMethod() == null ? "" : orders.getShippingMethod()));
		// 创建日期
		model.addAttribute("createDate",
				orderNull ? ""
						: (orders.getCreateDate() == null ? ""
								: (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(orders.getCreateDate())));
		// 收货电话
		model.addAttribute("mobile", orderNull ? "" : (orders.getMobile() == null ? "" : orders.getMobile()));
		// 详细地址
		model.addAttribute("address", orderNull ? "" : (orders.getAddress() == null ? "" : orders.getAddress()));
		// 备注
		model.addAttribute("memo", orderNull ? "" : (orders.getMemo() == null ? "" : orders.getMemo()));

		// 查询商品信息
		List<Map<String, Object>> productMapList = new ArrayList<>();
		List<OrdersItem> ordersItemList = ordersItemService.getByOrdersId(id);
		if (ordersItemList != null && ordersItemList.size() > 0) {
			for (int i = 0; i < ordersItemList.size(); i ++) {
				Map<String, Object> productMap = new HashMap<>();
				OrdersItem ordersItem = ordersItemList.get(i);
				productMap.put("sn", ordersItem.getSn());
				productMap.put("name", ordersItem.getName());
				productMap.put("price", ordersItem.getPrice());
				productMap.put("quantity", ordersItem.getQuantity());
                productMap.put("birthdayCard", ordersItem.getBirthdayCard());

				Long productStockId = ordersItem.getProductStockId();
				List<Map<String, Object>> specMapList = productStockService.getSpecByProductStockId(productStockId);
				String spec = "";
				if (specMapList != null && specMapList.size() > 0) {
					for (int j = 0; j < specMapList.size(); j ++) {
						String specValue = (String)specMapList.get(j).get("specValue");
						spec += specValue + "， ";
					}
				}
				productMap.put("spec", spec);
				productMapList.add(productMap);
			}
		}

		//退款
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ordersId",orders.getId());
		List<HashMap<String, Object>> refundList = refundService.findListByParams(params);
		model.addAttribute("refundList",refundList);

		model.addAttribute("productMapList", productMapList);

		model.addAttribute("orderId", orderId);
		model.addAttribute("order", orders);

		Member member = memberService.getByPrimaryKey(orders.getMemberId());
		model.addAttribute("member", member);
		model.addAttribute("memberRank", memberRankService.getByPrimaryKey(member.getMemberRankId()));

		//三方支付
		Integer ps = orders.getPayStatus();
		if(ps==1){
			Payment payment = paymentService.getBySuccess(orders.getId(),0);
			model.addAttribute("payment",payment);
		}else{
			model.addAttribute("payment",null);
		}

		return "admin/orders/detail";
	}

	/**
	 * 调整支付金额
	 * @param orderId
	 * @param amount
	 * @return
	 */
	@RequestMapping("/set_amount")
	@ResponseBody
	public Message set_amount(Long orderId,BigDecimal amount) {

		if (amount == null) {
			return Message.error("调整金额不正确");
		}
		Orders orders = ordersService.getByPrimaryKey(orderId);
		if (orders == null) {
			return Message.error("订单信息不存在");
		}
		if (orders.getOrdersStatus() != 1) {
			return Message.error("订单状态不正确");
		}
		if (orders.getPayStatus() != 0) {
			return Message.error("订单为已支付状态");
		}

		//积分抵扣+余额抵扣+微信抵扣+优惠券抵扣+会员折扣=商品金额+配送费=sumAmount
		BigDecimal orderAmount = orders.getSumAmount()
				.subtract(orders.getPointOffset())//积分抵扣
				.subtract(orders.getCouponPay())//优惠券抵扣
				.subtract(orders.getDiscountPay())//会员折扣
				.subtract(orders.getBalancePay());//余额抵扣

		if (amount.compareTo(orders.getBalancePay()) <= 0) {
			return Message.error("调整后金额不能小于等于已支付金额");
		}
		BigDecimal rmbAmount = amount.subtract(orders.getBalancePay());

		BigDecimal orgRmbAmount = orders.getSumAmount()
				.subtract(orders.getPointOffset())//积分抵扣
				.subtract(orders.getCouponPay())//优惠券抵扣
				.subtract(orders.getDiscountPay());//会员折扣
		orders.setOffsetAmount(amount.subtract(orgRmbAmount));
		orders.setAmount(amount);
		ordersService.updateByPrimaryKeySelective(orders);
		return Message.success("调整成功");
	}

	/**
	 * 确认发货
	 */
	@SysLog(module="订单管理",method="订单确认发货")
	@RequestMapping("/confirm")
	@ResponseBody
	public Message confirm(Long orderId,Integer shippingMethod, String expressCompany, String expressSn, Model model) {
		Orders orders = new Orders();
		orders.setId(orderId);
		orders.setShippingMethod(shippingMethod);
		orders.setExpressCompany(expressCompany);
		orders.setExpressSn(expressSn);
		orders.setShippingStatus(1);
		orders.setShippingDate(new Date());
		this.sendMsg(ordersService.getByPrimaryKey(orderId));
		if (ordersService.updateByPrimaryKeySelective(orders) > 0) {
			return Message.success("操作成功");
		} else {
			return Message.error("操作失败");
		}
	}

	@SysLog(module="订单管理",method="客服确认")
	@RequestMapping("/serviceConfirm")
	@ResponseBody
	public Message serviceConfirm(Long id) {
		Orders orders = new Orders();
		orders.setId(id);
		orders.setModifyDate(new Date());
		orders.setServiceConfirmStatus(1);
		ordersService.updateByPrimaryKeySelective(orders);
		return Message.success("操作成功");
	}

	private void sendMsg(Orders order){
	    if(order.getShippingMethod()!=1){
            return;
        }
		Member member = memberService.getByPrimaryKey(order.getMemberId());
		if(member!=null){
             /*
             订单发货通知
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
			com.zhengbangnet.modules.entity.Orders orders = new com.zhengbangnet.modules.entity.Orders();
			orders.setModifyDate(new Date());
			orders.setShippingDate(new Date());
			orders.setIsShippingMsg(1);
			orders.setShippingStatus(1);
			orders.setId(order.getId());
			ordersService.updateByPrimaryKeySelective(orders);

			String pname = "";
			List<OrdersItem> ordersItems = ordersItemService.getByOrdersId(order.getId());
			for(OrdersItem oi : ordersItems){
				pname += oi.getName()+"*" + oi.getQuantity()+" ";
			}

			try{
				WXTemplateMsg msg = new WXTemplateMsg();
				msg.setTouser(member.getOpenid());
				msg.setTemplate_id(Config.getProperty("shippingMsgId"));
				msg.addItem("first", "亲，您购买的商品已经在配送当中，请耐心等候哦！");
				msg.addItem("keyword1", order.getSn(),"#173177");//
				msg.addItem("keyword2", order.getAmount().toString(),"#173177");
				msg.addItem("keyword3", order.getMobile(),"#173177");
				msg.addItem("keyword4", order.getName(),"#173177");
				msg.addItem("keyword5", order.getAddress(),"#173177");
				msg.addItem("remark","商品："+pname,"#173177");
				msg.setUrl(SettingUtils.get().getDomain()+"/mobile/wechat/index?action=/mobile/orders/list");
				weixin.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret());
				weixin.sendTemplateMsg(msg);
			}catch(Exception e){
				logger.error("发送发货通知模板异常",e);
			}

		}
	}

	/**
	 * 确认完成 add by suijinchi date 2018-06-21
	 */
	@SysLog(module="订单管理",method="订单确认完成")
	@RequestMapping("/complete")
	@ResponseBody
	public Message platComplete(String orderId) {
		long id = 0;
		try {
			id = Long.valueOf(orderId);
		} catch (Exception ex) {
			id = 0;
		}
		Orders orders = new Orders();
		orders.setId(id);
		orders.setOrdersStatus(2);
		if (ordersService.updateByPrimaryKeySelective(orders) > 0) {
			return Message.success("操作成功");
		} else {
			return Message.error("操作失败");
		}
	}

	@SysLog(module="订单管理",method="订单退货")
	@RequestMapping("/refund")
	@ResponseBody
	public Message refund(Long orderId) {
		Orders orders = ordersService.getByPrimaryKey(orderId);
		if(orders==null){
			return Message.error("订单不存在");
		}
		if(orders.getPayStatus()!=1){
			return Message.error("该订单未支付不可退款");
		}
		ordersService.refund(orderId,sysAdminService.getCurrent().getId());
		return Message.success("操作成功");
	}

	@SysLog(module="订单管理",method="退款驳回")
	@RequestMapping("/rejectRefund")
	@ResponseBody
	public Message rejectRefund(Long id) {
		Orders orders = ordersService.getByPrimaryKey(id);
		if(orders==null){
			return Message.error("订单信息不存在");
		}
		if(orders.getOrdersStatus()!=4){
			return Message.error("订单状态不正确");
		}
		if(orders.getPayStatus()!=1){
			return Message.error("订单为未支付状态");
		}
		orders.setOrdersStatus(1);
		orders.setPayStatus(1);
		ordersService.updateByPrimaryKeySelective(orders);
		return Message.success("驳回成功");
	}

	@SysLog(module="订单管理",method="标记")
	@RequestMapping("/change_flaginfo")
	@ResponseBody
	public Message change_flaginfo(Long orderId,String flaginfo,String flagicon,String flagname) {
		Orders orders = ordersService.getByPrimaryKey(orderId);
		if(orders==null){
			return Message.error("订单信息不存在");
		}
		orders.setFlagicon(flagicon);
		orders.setFlaginfo(flaginfo);
		orders.setFlagname(flagname);
		ordersService.updateByPrimaryKeySelective(orders);
		return Message.success("驳回成功");
	}

}