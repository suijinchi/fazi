package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.BeanUtil;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.CouponType;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.service.CouponService;
import com.zhengbangnet.modules.service.CouponTypeService;
import com.zhengbangnet.modules.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@Controller("mobileCouponTypeController")
@RequestMapping("/mobile/coupon_type")
public class CouponTypeController extends AdminBaseController {

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;

	@Resource(name = "couponTypeServiceImpl")
	private CouponTypeService couponTypeService;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {
		return "/mobile/coupon_type/list";
	}

	@RequestMapping("/listData")
	public @ResponseBody
    Object listData(Integer pageNo, HttpServletRequest request, Model model) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pageable",new Pageable(pageNo,20));
		params.put("now",new Date());
		Page<Map<String, Object>> page = couponTypeService.findValidPageByParams(params);
		List<Map<String, Object>> list = page.getContent();
		List<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> data = new HashMap<String,Object>();
			Map<String, Object> map = list.get(i);

			Long id = (Long)map.get("id");
			String name = (String)map.get("name");
			String subname = (String)map.get("subname");
			String shopName = (String)map.get("shopName");
			Integer useType = (Integer)map.get("useType");
			Integer getLimit = (Integer)map.get("getLimit");
			BigDecimal cutMoney = (BigDecimal)map.get("cutMoney");
			BigDecimal byFull = (BigDecimal)map.get("byFull");

			Date validStartDate = (Date)map.get("validStartDate");
			Date validEndDate = (Date)map.get("validEndDate");

			CouponType ct = (CouponType) BeanUtil.convertMap(CouponType.class,map);
			if(useType==1){
				data.put("useType", "无使用门槛");
			}else if(useType==2){
				data.put("useType", "满"+byFull+"元可用");
			}
			data.put("id",id);
			data.put("name",name);
			data.put("subname",subname);
			data.put("cutMoney",cutMoney);
			data.put("validDate",ct.getValidDate());
			data.put("shopName",shopName);
			data.put("getLimit",getLimit);
			data.put("validStartDate", DateUtils.dateToString(validStartDate, DateUtils.patternA));
			data.put("validEndDate", DateUtils.dateToString(validEndDate, DateUtils.patternA));

			listData.add(data);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("listData", listData);
		data.put("pageNo", page.getPageNo());
		data.put("totalPages", page.getTotalPages());
		data.put("pageSize", page.getPageSize());
		data.put("total", page.getTotal());
		return data;
	}

	@RequestMapping("/getCoupon")
	public @ResponseBody
    Message getCoupon(Long couponTypeId, HttpServletRequest request) {

		Member member = memberService.getCurrent(request);

		CouponType couponType = couponTypeService.getByPrimaryKey(couponTypeId);
		if(couponType==null){
			return Message.error("该优惠券不存在");
		}

		if(couponType.getStock()==0){
			return Message.error("该优惠券已领完");
		}
		if(couponType.getValidDateType()==1){
			if(new Date().after(couponType.getValidEndDate())){
				return Message.error("优惠券已过期");
			}
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("memberId",member.getId());
		params.put("couponTypeId",couponTypeId);
		Long count = couponService.getCountByParams(params);
		if(count>=couponType.getGetLimit()){
			return Message.error("您已经领取过了，每人限领"+couponType.getGetLimit()+"张");
		}
		couponTypeService.getCoupon(couponTypeId,member.getId());
		Message success = Message.success("领取成功");
		return success;
	}

}