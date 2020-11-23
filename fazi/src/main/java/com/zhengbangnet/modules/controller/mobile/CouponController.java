package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.service.CouponService;
import com.zhengbangnet.modules.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller("mobileCouponController")
@RequestMapping("/mobile/coupon")
public class CouponController extends AdminBaseController {

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@SysLog(module="优惠券",method="领取详情")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String nickname,Long couponTypeId,Pageable pageable, HttpServletRequest request, Model model) {
		return "/mobile/coupon/list";
	}

	@RequestMapping("/listData")
	public @ResponseBody
	Object listData(Integer pageNo,HttpServletRequest request, Model model) {
		Long currentId = memberService.getCurrentId(request);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pageable",new Pageable(pageNo,20));
		params.put("memberId",currentId);
		params.put("status",0);
		params.put("now",new Date());
		Page<Map<String, Object>> page = couponService.findPageByParams(params);
		List<Map<String, Object>> list = page.getContent();
		List<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> data = new HashMap<String,Object>();
			Map<String, Object> map = list.get(i);

			Long id = (Long)map.get("id");
			String name = (String)map.get("name");
			String subname = (String)map.get("subname");
			Integer useType = (Integer)map.get("useType");
			BigDecimal cutMoney = (BigDecimal)map.get("cutMoney");
			BigDecimal byFull = (BigDecimal)map.get("byFull");

			Date validStartDate = (Date)map.get("validStartDate");
			Date validEndDate = (Date)map.get("validEndDate");

			if(useType==1){
				data.put("useType", "无使用门槛");
			}else if(useType==2){
				data.put("useType", "满"+byFull+"元可用");
			}
			data.put("id",id);
			data.put("name",name);
			data.put("subname",subname);
			data.put("cutMoney",cutMoney);
			data.put("validStartDate",DateUtils.dateToString(validStartDate,DateUtils.patternA));
			data.put("validEndDate",DateUtils.dateToString(validEndDate,DateUtils.patternA));
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

	

}