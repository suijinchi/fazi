package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.PointRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin4j.Weixin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller("mobilePointController")
@RequestMapping("/mobile/point")
public class PointController extends MobileBaseController {
	
	@Resource(name="memberServiceImpl")
	private MemberService memberService;

	@Resource(name="pointRecordServiceImpl")
	private PointRecordService pointRecordService;

	@Resource(name="weixin")
	private Weixin weixin;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/mobile/point/list";
	}

	/**
	 * 列表数据
	 */
	@RequestMapping(value = "/listData")
	public @ResponseBody
    Object listPage(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Member member = memberService.getCurrent(request);

		Map<String,Object> params = new HashMap<String,Object>();
		Pageable pageable = new Pageable(pageNo,10);
		params.put("pageable", pageable);
		params.put("memberId", member.getId());
		Page<Map<String, Object>> page = pointRecordService.findPageByParams(params);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> data:page.getContent()){
			Integer point = (Integer)data.get("point");
			String memo = (String)data.get("memo");
			Date createDate = (Date)data.get("createDate");
			Map<String, Object> temp = new HashMap<String,Object>();
			temp.put("memo", memo);
			temp.put("point", point);
			temp.put("createDate", DateUtils.dateToString(createDate, "yyyy年MM月dd日 HH:mm"));
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



	
}


