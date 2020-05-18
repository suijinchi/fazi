package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.Assess;
import com.zhengbangnet.modules.service.AssessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author suijinchi
 * 评价管理
 */
@Controller("adminAssessController")
@RequestMapping("/admin/assess")
public class AssessController extends AdminBaseController {

	@Resource(name = "assessServiceImpl")
	private AssessService assessService;

	/**
	 * 列表 create by suijinchi on 2018-06-28
	 * @param pageable
	 * @param model
	 * @return
	 */
	@SysLog(module="评价管理",method="查询商品评价列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, Model model, Integer status, String productName, String memberNickName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		params.put("status", status);
		params.put("productName", productName);
		params.put("memberNickName", memberNickName);
		Page<Map<String, Object>> page = assessService.findPageByParams(params);
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute("productName", productName);
		model.addAttribute("memberNickName", memberNickName);
		return "/admin/assess/list";
	}

	/**
	 * 详情 create by suijinchi on 2018-06-28
	 * @param pageable
	 * @param model
	 * @return
	 */
	@SysLog(module="评价管理",method="查询商品评价列表")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Pageable pageable, Model model, Long id) {
		Assess assess = assessService.getByPrimaryKey(id);
		model.addAttribute("assess", assess);

		List<String> imgList = new ArrayList<String>();
		String img = assess.getImg();
		if (img!=null&&!img.equals("")) {
			for(String s:img.split(",")){
				imgList.add(s);
			}
		}
		model.addAttribute("imgList", imgList);

		return "/admin/assess/view";
	}

	/**
	 * 删除商品审核
	 * @param assessId
	 * @return
	 */
	@SysLog(module="评价管理",method="审核评价")
	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public @ResponseBody Message review(Long assessId, Integer status) {
		Assess assess = new Assess();
		assess.setId(assessId);
		assess.setStatus(status);
		assessService.updateByPrimaryKeySelective(assess);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除商品评价
	 * @param assessId
	 * @return
	 */
	@SysLog(module="评价管理",method="删除评价")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long assessId) {
		assessService.deleteByPrimaryKey(assessId);
		return SUCCESS_MESSAGE;
	}
	
}
