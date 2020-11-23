package com.zhengbangnet.modules.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.Coupon;
import com.zhengbangnet.modules.service.CouponService;

@Controller("adminCouponController")
@RequestMapping("/admin/coupon")
public class CouponController extends AdminBaseController {

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;

	@SysLog(module="优惠券",method="领取详情")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer scene,String nickname,Long couponTypeId,Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		params.put("nickname", nickname);
		params.put("couponTypeId", couponTypeId);
		params.put("scene", scene);
		Page< Map<String, Object>> page = couponService.findPageByParams(params);
		model.addAttribute("page", page);
		model.addAttribute("nickname", nickname);
		model.addAttribute("couponTypeId", couponTypeId);
		model.addAttribute("scene", scene);
		return "/admin/coupon/list";
	}




}