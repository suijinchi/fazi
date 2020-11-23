package com.zhengbangnet.modules.controller.mobile;

import com.alibaba.fastjson.JSONObject;
import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author suijinchi
 * 訂單管理
 */
@Controller("mobileOrdersController")
@RequestMapping("/mobile/orders")
public class OrdersController extends MobileBaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "assessServiceImpl")
	private AssessService assessService;

	@Resource(name = "ordersItemServiceImpl")
	private OrdersItemService ordersItemService;

	@Resource(name = "ordersServiceImpl")
	private OrdersService ordersService;

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;

	@Resource(name = "cartServiceImpl")
	private CartService cartService;

	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;

	@Resource(name = "shippingTimeServiceImpl")
	private ShippingTimeService shippingTimeService;

	@Resource(name = "pickUpTimeServiceImpl")
	private PickUpTimeService pickUpTimeService;

	@Resource(name = "pickUpAddressServiceImpl")
	private PickUpAddressService pickUpAddressService;

	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;

	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(HttpServletRequest request,Model model) {

		Member member = memberService.getCurrent(request);
		//清空下架商品
		cartService.clearProduct(member.getId());
		Cart cart = cartService.getByMemberId(member.getId());
		if(cart==null){
			model.addAttribute("tips","购物车为空");
			return RESULT_TIPS;
		}

		//购物车列表
		CartItem c = new CartItem();
		c.setCartId(cart.getId());
		List<Map<String, Object>> cartItemList = cartItemService.getListByCartItem(c);
		if(cartItemList==null||cartItemList.size()==0){
			model.addAttribute("tips","购物车为空");
			return RESULT_TIPS;
		}
		model.addAttribute("cartItemList",cartItemList);

		//默认收货人
		Receiver receiver = receiverService.getDefault(member.getId());
		model.addAttribute("receiver",receiver);
/*
		//自提日期时间
		model.addAttribute("pickUpDate", JSONObject.toJSON(getDate()));
		List<String> put = new ArrayList<String>();
		List<PickUpTime> pickUpTime = pickUpTimeService.findAll();
		for(PickUpTime pu:pickUpTime){
			put.add(pu.getTime());
		}
		model.addAttribute("pickUpTime",JSONObject.toJSON(put));

		*/
		//自提地点
		List<String> pua = new ArrayList<String>();
		List<PickUpAddress> pickUpAddress = pickUpAddressService.findAll();
		for(PickUpAddress pu:pickUpAddress){
			pua.add(pu.getAddress());
		}
		model.addAttribute("pickUpAddress",JSONObject.toJSON(pua));

		//根据商品计算最大抵扣积分
		Integer maxOffsetPoint = 0;
		BigDecimal productPrice = BigDecimal.ZERO;
		for(Map<String,Object> map:cartItemList){
			Integer mop = (Integer)map.get("maxOffsetPoint");
			BigDecimal price = (BigDecimal)map.get("price");
			Integer quantity = (Integer)map.get("quantity");
			maxOffsetPoint+=mop*quantity;
			productPrice = productPrice.add(price.multiply(new BigDecimal(quantity)));
		}


		//会员等级优惠
		BigDecimal memberRankDiscount = BigDecimal.ZERO;
		MemberRank memberRank = memberRankService.getByPrimaryKey(member.getMemberRankId());
		memberRankDiscount = productPrice.multiply(BigDecimal.ONE.subtract(memberRank.getScale())).setScale(2,BigDecimal.ROUND_HALF_UP);

		model.addAttribute("maxOffsetPoint",maxOffsetPoint);
		model.addAttribute("setting", SettingUtils.get());
		model.addAttribute("member",member);

		model.addAttribute("productPrice",productPrice);
		model.addAttribute("memberRankDiscount",memberRankDiscount);
		model.addAttribute("finalProductPrice", productPrice.subtract(memberRankDiscount));

		//查询购物车商品可以使用的优惠券
		List<Coupon> couponList = couponService.findListByCart(member.getId(),new Date());
		model.addAttribute("couponList",couponList);

		return "mobile/orders/info";
	}

	@RequestMapping("/getCartCoupon")
	@ResponseBody
	public Object getCartCoupon(HttpServletRequest request){
		Member member = memberService.getCurrent(request);
		List<Coupon> couponList = couponService.findListByCart(member.getId(),new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("couponList",couponList);
		return map;
	}

	/**
	 * 获取配送日期
	 */
	@RequestMapping("/getPsDate")
	@ResponseBody
	public Object getPsDate(){
		//配送日期时间
		List<String> date = getDate();
		date.add("闪电配送");
		List<String> displayDate = getDisplayDate();
		displayDate.add("闪电配送");

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date",date);
		map.put("displayDate",displayDate);
		return map;
	}

	/**
	 * 根据配送日期查询配送时间
	 */
	@RequestMapping("/getPsTimeByPsDate")
	@ResponseBody
	public Object getPsDate(String date){
		List<String> stl = new ArrayList<String>();
		if(StringUtils.isNotBlank(date)){
			List<ShippingTime> shippingTimeList = shippingTimeService.findAll();
			for(ShippingTime st:shippingTimeList){
				String time = st.getEndTime();
				if(DateUtils.dateToString(new Date(),"yyyy-MM-dd").equals(date)){
					String t = DateUtils.dateToString(new Date(), "HH:mm");
					if(DateUtils.compareTime(time,t)>0){
						stl.add(st.getTime());
					}
				}else{
					stl.add(st.getTime());
				}

			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("time",stl);
		return map;
	}

	private List<String> getDate() {
		List<String> timeList = new ArrayList<String>();
		int days = 30;
		Date now = new Date();
		for (int i = 0; i < days; i++) {
			Date date = DateUtils.getSomeDaysBeforeAfter(now, i);
			String day = DateUtils.dateToString(date, "yyyy-MM-dd");
			if(i==0){
				boolean flag = false;
				List<ShippingTime> shippingTimeList = shippingTimeService.findAll();
				for(ShippingTime st:shippingTimeList){
					String t = DateUtils.dateToString(new Date(), "HH:mm");
					if(DateUtils.compareTime(st.getEndTime(),t)>0){
						flag = flag||true;
						if(flag){
							break;
						}
					}
				}
				if(flag){
					timeList.add(day);
				}
			}else{
				timeList.add(day);
			}
		}
		return timeList;
	}
	private List<String> getDisplayDate() {
		List<String> timeList = new ArrayList<String>();
		int days = 30;
		Date now = new Date();
		for (int i = 0; i < days; i++) {
			Date date = DateUtils.getSomeDaysBeforeAfter(now, i);
			String day = DateUtils.dateToString(date, "MM月dd日");
			if(i==0){
				day = day+"(今天)";
			}else if(i==1){
				day = day+"(明天)";
			}

			String week = getWeekOfDate(date);
			if(i==0){
				boolean flag = false;
				List<ShippingTime> shippingTimeList = shippingTimeService.findAll();
				for(ShippingTime st:shippingTimeList){
					String t = DateUtils.dateToString(new Date(), "HH:mm");
					if(DateUtils.compareTime(st.getEndTime(),t)>0){
						flag = flag||true;
						if(flag){
							break;
						}
					}
				}
				if(flag){
					timeList.add(day+" "+week);
				}
			}else{
				timeList.add(day+" "+week);
			}
		}
		return timeList;
	}
    public String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }





	/**
	 * 获取自提日期
	 */
	@RequestMapping("/getPickUpDate")
	@ResponseBody
	public Object getPickUpDate(){
		//配送日期时间
		List<String> date = getPUDate();
		List<String> displayDate = getPUDisplayDate();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date",date);
		map.put("displayDate",displayDate);
		return map;
	}

	/**
	 * 根据自提日期查询自提时间
	 */
	@RequestMapping("/getPickUpTimeByPickUpDate")
	@ResponseBody
	public Object getPickUpTimeByPickUpDate(String date){
		List<String> stl = new ArrayList<String>();
		if(StringUtils.isNotBlank(date)){
			List<PickUpTime> pickUpTimeList = pickUpTimeService.findAll();
			String nowDate = DateUtils.dateToString(new Date(),"yyyy-MM-dd");
			for(PickUpTime st:pickUpTimeList){
				String time = st.getEndTime();
				if(nowDate.equals(date)){
					String t = DateUtils.dateToString(new Date(), "HH:mm");
					if(DateUtils.compareTime(time,t)>0){
						stl.add(st.getTime());
					}
				}else{
					stl.add(st.getTime());
				}
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("time",stl);
		return map;
	}
	private List<String> getPUDate() {
		List<String> timeList = new ArrayList<String>();
		int days = 30;
		Date now = new Date();
		for (int i = 0; i < days; i++) {
			Date date = DateUtils.getSomeDaysBeforeAfter(now, i);
			String day = DateUtils.dateToString(date, "yyyy-MM-dd");
			if(i==0){
				boolean flag = false;
				List<PickUpTime> pickUpTimeList = pickUpTimeService.findAll();
				for(PickUpTime put:pickUpTimeList){
					String t = DateUtils.dateToString(new Date(), "HH:mm");
					if(DateUtils.compareTime(put.getEndTime(),t)>0){
						flag = flag||true;
						if(flag){
							break;
						}
					}
				}
				if(flag){
					timeList.add(day);
				}
			}else{
				timeList.add(day);
			}
		}
		return timeList;
	}
	private List<String> getPUDisplayDate() {
		List<String> timeList = new ArrayList<String>();
		int days = 30;
		Date now = new Date();
		for (int i = 0; i < days; i++) {
			Date date = DateUtils.getSomeDaysBeforeAfter(now, i);
			String day = DateUtils.dateToString(date, "MM月dd日");
			if(i==0){
				day = day+"(今天)";
			}else if(i==1){
				day = day+"(明天)";
			}
			String week = getWeekOfDate(date);
			if(i==0){
				boolean flag = false;
				List<PickUpTime> pickUpTimeList = pickUpTimeService.findAll();
				for(PickUpTime put:pickUpTimeList){
					String t = DateUtils.dateToString(new Date(), "HH:mm");
					if(DateUtils.compareTime(put.getEndTime(),t)>0){
						flag = flag||true;
						if(flag){
							break;
						}
					}
				}
				if(flag){
					timeList.add(day+" "+week);
				}
			}else{
				timeList.add(day+" "+week);
			}
		}
		return timeList;
	}




















	/**
	 * 创建订单
	 */
	@RequestMapping("/create")
	@ResponseBody
	public Message create(HttpServletRequest request,String memo,
			Integer offsetPoint,BigDecimal offsetPointAmount,BigDecimal offsetBalance,
			BigDecimal shippingFee,BigDecimal thirdAmount,
			Long couponId,BigDecimal offsetCouponAmount,
			Integer shippingMethod,
			String pickUpDate,String pickUpTime,String pickUpAddress,String mobile,String name,
			String psdate,String pstime,Long receiverId){

        String shippingTime = psdate + " " + pstime;
        String pickUpDateTime = pickUpDate + " " +pickUpTime;
		shippingTime = shippingTime.replace("(今天)","").replace("(明天)","");
		pickUpDateTime = pickUpDateTime.replace("(今天)","").replace("(明天)","");
        if(offsetPointAmount==null){
            offsetPointAmount = BigDecimal.ZERO;
        }
        if(offsetBalance==null){
            offsetBalance = BigDecimal.ZERO;
        }
        if(offsetCouponAmount==null){
            offsetCouponAmount = BigDecimal.ZERO;
        }
        if(shippingFee==null){
            shippingFee = BigDecimal.ZERO;
        }

        Setting setting = SettingUtils.get();
		Member member = memberService.getCurrent(request);
		Cart cart = cartService.getByMemberId(member.getId());
		if(cart==null){
			return Message.error("购物车为空");
		}

		//购物车列表
		CartItem c = new CartItem();
		c.setCartId(cart.getId());
		List<Map<String, Object>> cartItemList = cartItemService.getListByCartItem(c);

		//根据商品计算最大抵扣积分
		Integer maxOffsetPoint = 0;
		BigDecimal productPrice = BigDecimal.ZERO;
		for(Map<String,Object> map:cartItemList){
			Integer mop = (Integer)map.get("maxOffsetPoint");
			BigDecimal price = (BigDecimal)map.get("price");
			Date sellDate = (Date)map.get("sellDate");
			Integer quantity = (Integer)map.get("quantity");
			String pname = (String)map.get("name");
			maxOffsetPoint+=mop*quantity;
			productPrice = productPrice.add(price.multiply(new BigDecimal(quantity)));
			if(sellDate!=null&&sellDate.after(new Date())){
				return Message.error(pname+",未到销售时间："+DateUtils.dateToString(sellDate,"yyyy-MM-dd HH:mm"));
			}
		}

		if(shippingMethod==0){
			if(StringUtils.isBlank(pickUpAddress)){
				return Message.error("请选择自提点");
			}
			if(StringUtils.isBlank(pickUpTime)){
				return Message.error("请选择自提时间");
			}
		}else if(shippingMethod==1){
			if(StringUtils.isBlank(psdate)){
				return Message.error("请选择配送日期");
			}
            if(StringUtils.isBlank(pstime)){
                return Message.error("请选择配送时间");
            }

            Receiver receiver = receiverService.getByPrimaryKey(receiverId);
			if(receiver==null){
				return Message.error("请填写收货地址");
			}
		}

		if(couponId!=null){
			//检测 积分 优惠券 余额
			//检测优惠券
			Coupon coupon = couponService.getByPrimaryKey(couponId);
			if(coupon==null||coupon.getMemberId().longValue()!=member.getId().longValue()){
				return Message.error("选择的优惠券不存在，请刷新页面后重新选择");
			}
			if(coupon.getCutMoney().compareTo(offsetCouponAmount)<0){
				return Message.error("优惠券抵扣金额错误，请刷新页面后重新选择");
			}
			//结算商品与优惠券检测
			Boolean check = couponService.checkCoupon(coupon.getId());
			if(!check){
				throw new RuntimeException("优惠券使用错误");
			}
			if(coupon.getIsWithPointShare()!=null&&coupon.getIsWithPointShare()==0&&offsetPoint>0){
				return Message.error("您选择的优惠券不可与积分同享");
			}

		}

		//检测积分
		if(offsetPoint>member.getPoint()){
			return Message.error("积分余额不足，请刷新页面后重新下单");
		}
		if(offsetPoint>maxOffsetPoint){
			return Message.error("抵扣积分不可大于商品的最大抵扣数量");
		}
		if(new BigDecimal(offsetPoint/setting.getPointRatio()).compareTo(offsetPointAmount)!=0){
			return Message.error("积分抵扣金额错误");
		}
		//检测余额
		if(offsetBalance.compareTo(member.getBalance())>0){
			return Message.error("抵扣余额不可大于用户余额");
		}


		//会员等级优惠
		BigDecimal memberRankDiscount = BigDecimal.ZERO;
		MemberRank memberRank = memberRankService.getByPrimaryKey(member.getMemberRankId());
		memberRankDiscount = productPrice.multiply(BigDecimal.ONE.subtract(memberRank.getScale())).setScale(2,BigDecimal.ROUND_HALF_UP);

		//最终价格
		BigDecimal finalPrice = productPrice.subtract(memberRankDiscount);
		//总体检测
		if(offsetPointAmount.add(offsetBalance).add(thirdAmount).add(offsetCouponAmount).compareTo(finalPrice.add(shippingFee))!=0){
			return Message.error("金额计算错误，请刷新页面后重新下单");
		}

		Date msgDate = null;
		if(shippingMethod==1){
			ShippingTime st = shippingTimeService.getByTime(pstime);
			String day = psdate;
			msgDate = DateUtils.stringToDate(day + " " + st.getMsgTime(),"yyyy-MM-dd HH:mm:ss");
		}

		/*
		Integer offsetPoint,
		BigDecimal offsetPointAmount,
		BigDecimal offsetBalance,
		BigDecimal shippingFee,
		BigDecimal thirdAmount,
		Integer shippingMethod,
		String pickUpTime,String pickUpAddress,
		String shippingTime,Long receiverId
		*/
		//不需要第三方支付
		if(thirdAmount.compareTo(BigDecimal.ZERO)==0){
			ordersService.payByBalance(member,shippingMethod,
                    offsetPoint,offsetPointAmount,offsetBalance,couponId,offsetCouponAmount,
                    shippingFee,thirdAmount,
                    pickUpDateTime,pickUpAddress,name,mobile,shippingTime,receiverId,msgDate,memo);
			Message success = Message.success("支付成功");
			success.putDatas("thirdPay",0);//不需要第三方支付
			return success;
		}
		Orders order = ordersService.payByThird(member,shippingMethod,
                offsetPoint,offsetPointAmount,offsetBalance,couponId,offsetCouponAmount,
                shippingFee,thirdAmount,
                pickUpDateTime,pickUpAddress,name,mobile,shippingTime,receiverId,msgDate,memo);
		Message success = Message.success("下单成功");
		success.putDatas("thirdPay",1);//需要第三方支付
		success.putDatas("sn",order.getSn());
		success.putDatas("orderId",order.getId());
		return success;
	}



	/**
	 * 跳转我的订单界面
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String type, Model model) {
		model.addAttribute("type", type);
		return "mobile/orders/list";
	}

	/**
	 * 订单信息加载 create by suijinchi 2018-06-29
	 */
	@RequestMapping("/load")
	@ResponseBody
	public Map<String, Object> load(Pageable pageable, String ordersType, Integer pageNo, HttpServletRequest request) {

		Map<String, Object> params = new HashMap<>();

		pageable.setPageSize(10);
		pageable.setPageNo(pageNo);
		params.put("pageable", pageable);

		Member member = memberService.getCurrent(request);
		params.put("memberId", member.getId());

		switch (ordersType) {
			// 待付款
			case "nopay":
				params.put("ordersStatus", 1);
				params.put("payStatus", 0);
				break;
			// 待配送
			case "noship":
				params.put("ordersStatus", 1);
				params.put("payStatus", 1);
				params.put("sendStatus", 0);
				break;
			// 待收货
			case "noaccept":
				params.put("ordersStatus", 1);
				params.put("payStatus", 1);
				params.put("sendStatus", 1);
				break;
			// 已完成
			case "iscomplete":
				params.put("ordersStatus", 2);
				params.put("payStatus", 1);
				params.put("sendStatus", 2);
				break;
			//全部
			default:
				break;
		}

		Page<Map<String, Object>> page = ordersService.findPageByParams(params);

		List<Map<String, Object>> listData = new ArrayList<>();
		if (page.getContent() != null && page.getContent().size() > 0) {
			for (int i = 0; i < page.getContent().size(); i ++) {
				Map<String, Object> map = new HashMap<>();
				map.put("createTime", page.getContent().get(i).get("createDate"));
				String sn = (String)page.getContent().get(i).get("sn");
				map.put("sn", sn);
				BigDecimal amount = (BigDecimal)page.getContent().get(i).get("amount");
				map.put("amount", amount);
				Integer ordersStatus = (Integer)page.getContent().get(i).get("ordersStatus");
				Integer payStatus = (Integer)page.getContent().get(i).get("payStatus");
				Integer shippingStatus = (Integer)page.getContent().get(i).get("shippingStatus");
				Integer shippingMethod = (Integer)page.getContent().get(i).get("shippingMethod");
				Integer assessStatus = (Integer)page.getContent().get(i).get("assessStatus");
				map.put("ordersStatus", ordersStatus);
				map.put("payStatus", payStatus);
				map.put("shippingStatus", shippingStatus);
				map.put("shippingMethod", shippingMethod);
				map.put("assessStatus", assessStatus);
				Long orderId = (Long)page.getContent().get(i).get("id");
				map.put("orderId", orderId);
				List<Map<String, Object>> ordersItemList = ordersItemService.findByOrdersId(orderId);
				map.put("ordersItemList", ordersItemList);
				/*for (int j = 0; j < ordersItemList.size(); j ++) {
					Long productStockId = (Long)ordersItemList.get(j).get("productStockId");
					if (productStockId != null) {
						List<Map<String, Object>> specMapList = productStockService.getSpecByProductStockId(productStockId);
						String specValues = "";
						if (specMapList != null && specMapList.size() > 0) {
							for (int g = 0; g < specMapList.size(); g ++) {
								String specValue = (String)specMapList.get(g).get("specValue");
								specValues += specValue + " ";
							}
							ordersItemList.get(j).put("specValues", specValues);
						}
					}
				}*/
				listData.add(map);
			}
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("listData", listData);
		data.put("pageNo", page.getPageNo());
		data.put("totalPages", page.getTotalPages());
		data.put("pageSize", page.getPageSize());
		data.put("total", page.getTotal());

		return data;
	}

	/**
	 * 跳转订单详情页
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Long orderId, Model model) {

		Orders order = ordersService.getByPrimaryKey(orderId);
		List<OrdersItem> ordersItemList = ordersItemService.getByOrdersId(orderId);
		BigDecimal productAmount = BigDecimal.ZERO;
		for (int i = 0; i < ordersItemList.size(); i ++) {
			OrdersItem ordersItem = ordersItemList.get(i);
			productAmount = productAmount.add(ordersItem.getPrice().multiply(new BigDecimal(ordersItem.getQuantity())));
		}
		model.addAttribute("order", order);
		model.addAttribute("ordersItemList", ordersItemList);
		model.addAttribute("productAmount", productAmount);
		
		Date cd = order.getCreateDate();
		int minutes = DateUtils.getMinutes(cd,new Date());
		model.addAttribute("minutes", minutes);

		return "mobile/orders/detail";
	}

	/**
	 * 订单完成
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public @ResponseBody
	Message confirm(Long orderId) {
		Orders order = ordersService.getByPrimaryKey(orderId);
		if(order==null){
			return Message.error("订单不存在");
		}
		ordersService.confirmReceive(orderId);
		return Message.success("收货成功");
	}

	/**
	 * 订单取消
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public @ResponseBody
	Message cancel(Long orderId) {
		Orders order = ordersService.getByPrimaryKey(orderId);
		if(order==null){
			return Message.error("订单不存在");
		}
		if(order.getOrdersStatus()!=1){
			return Message.error("订单状态非已确认状态不可取消");
		}
		if(order.getPayStatus()==1){
			return Message.error("订单已支付不可取消");
		}
		ordersService.cancel(orderId);
		return Message.success("操作成功");
	}

	/**
	 * 退款
	 */
	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	public @ResponseBody
	Message refund(Long orderId,String reason) {
		Orders order = ordersService.getByPrimaryKey(orderId);
		if(order==null){
			return Message.error("订单不存在");
		}
		if(order.getPayStatus()!=1){
			return Message.error("订单未支付");
		}
		ordersService.applyRefund(orderId,reason);
		return Message.success("操作成功");
	}

}


