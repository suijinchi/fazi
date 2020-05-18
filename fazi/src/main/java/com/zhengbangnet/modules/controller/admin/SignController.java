package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.SignService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员模块的控制层
 */
@Controller
@RequestMapping("/admin/sign")
public class SignController extends AdminBaseController{

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "signServiceImpl")
	private SignService signService;
	
	/**
	 * 签到列表
	 */
	@RequestMapping(value = "/list")
	public String list(String date, Pageable pageable, Model model) {
		if(StringUtils.isBlank(date)){
			date = DateUtils.dateToString(new Date(), DateUtils.patternA);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("date", date);
		params.put("pageable", pageable);
		Page<Map<String,Object>> page = signService.findPageByParams(params);
		model.addAttribute("page", page);
		model.addAttribute("date", date);
		return "/admin/sign/list";
	}
	
}
