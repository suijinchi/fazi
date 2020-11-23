package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.MemberRank;
import com.zhengbangnet.modules.service.MemberRankService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller("adminMemberRankController")
@RequestMapping("/admin/member_rank")
public class MemberRankController extends AdminBaseController {

	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;

	@SysLog(module="会员等级",method="列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String keyword, Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		params.put("keyword", keyword);
		Page< Map<String, Object>> page = memberRankService.findPageByParams(params);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		return "/admin/member_rank/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/member_rank/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="会员等级",method="保存")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
    Message save(String name, BigDecimal scale, Integer isDefault, Integer highAmount, Integer lowAmount,
				 HttpServletRequest request, HttpSession session) {
		MemberRank memberRank = new MemberRank();
		memberRank.setCreateDate(new Date());
		memberRank.setModifyDate(new Date());
		memberRank.setName(name);
		memberRank.setIsDefault(0);
		memberRank.setScale(scale);
		memberRank.setHighAmount(highAmount);
		memberRank.setLowAmount(lowAmount);
		memberRankService.insertSelective(memberRank);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		MemberRank memberRank = memberRankService.getByPrimaryKey(id);
		model.addAttribute("memberRank", memberRank);
		return "/admin/member_rank/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="会员等级",method="更新编辑")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
    Message update(Long id,BigDecimal scale, String name, Integer isDefault,
				   Integer highAmount,Integer lowAmount,
                   Model model, HttpSession session, HttpServletRequest request) {
		MemberRank memberRank = memberRankService.getByPrimaryKey(id);
		if (memberRank == null) {
			return Message.error("编辑信息不存在");
		}
		memberRank.setScale(scale);
		memberRank.setModifyDate(new Date());
		memberRank.setId(id);
		memberRank.setName(name);
		memberRank.setHighAmount(highAmount);
		memberRank.setLowAmount(lowAmount);
		memberRankService.updateByPrimaryKey(memberRank);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="会员等级",method="删除")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
    Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			memberRankService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}