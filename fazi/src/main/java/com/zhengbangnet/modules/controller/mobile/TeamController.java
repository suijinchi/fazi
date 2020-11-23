package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.BaseController;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.OrdersService;
import com.zhengbangnet.modules.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 会员管理
 */
@Controller("mobileTeamController")
@RequestMapping("/mobile/team")
public class TeamController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "ordersServiceImpl")
	private OrdersService ordersService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpSession session, Model model) {
		Member member = memberService.getCurrent(request);
		model.addAttribute("member", member);
		return "/mobile/team/list";
	}
	
	@RequestMapping(value = "/listJson", method = RequestMethod.POST)
	public @ResponseBody
    Map<String, Object> listJson(HttpServletRequest request, Integer level, Pageable pageable) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Member current = memberService.getCurrent(request);
		params.put("pageable", pageable);
		params.put("recommendId",current.getId().toString());
		params.put("level", level.toString());
		params.put("tp", current.getTreePath());
		Page<Map<String, Object>> page = memberService.findTeamPage(params);
		List<Object> listData = new ArrayList<Object>();
		for (Map<String, Object> tempData : page.getContent()) {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			Long id = (Long) tempData.get("id");
			Integer type = (Integer) tempData.get("type");
			Date createDate = (Date) tempData.get("createDate");
			String mobile = (String) tempData.get("mobile");
			String name = (String) tempData.get("name");
			String memberRankName = (String) tempData.get("memberRankName");
			String nickname = (String) tempData.get("nickname");
			String avatarUrl = (String) tempData.get("avatarUrl");
			tempMap.put("type", memberRankName);
			tempMap.put("date", DateUtils.dateToString(createDate, DateUtils.patternA));
			tempMap.put("name", name);
			tempMap.put("phone", mobile);
			tempMap.put("nickname", nickname);
			tempMap.put("avatarUrl", avatarUrl);
			listData.add(tempMap);
		}
		data.put("listData", listData);
		data.put("pageNo", page.getPageNo());
		data.put("totalPages", page.getTotalPages());
		data.put("pageSize", page.getPageSize());
		data.put("total", page.getTotal());
		return data;
	}
	
}
