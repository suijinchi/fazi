package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.modules.entity.RechargeType;
import com.zhengbangnet.modules.service.RechargeTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("adminRechargeTypeController")
@RequestMapping("/admin/recharge_type")
public class RechargeTypeController extends AdminBaseController {

	@Resource(name = "rechargeTypeServiceImpl")
	private RechargeTypeService rechargeTypeService;

	@SysLog(module="充值管理",method="充值列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		Map<String,Object> params = new HashMap<String,Object>();
		List<HashMap<String, Object>> typeList = rechargeTypeService.findListByParams(params);
		model.addAttribute("typeList",typeList);
		return "/admin/recharge_type/list";
	}

	@SysLog(module="充值管理",method="保存充值项")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "/admin/recharge_type/add";
	}

	@SysLog(module="充值管理",method="充值项添加页面")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
    Message save(String name, BigDecimal amount, BigDecimal giveAmount, Integer isShow, Integer orders) {
		RechargeType rt = new RechargeType();
		rt.setName(name);
		rt.setAmount(amount);
		rt.setGiveAmount(giveAmount);
		rt.setCreateDate(new Date());
		rt.setModifyDate(new Date());
		rt.setIsShow(isShow);
		rt.setOrders(orders);
		rechargeTypeService.insertSelective(rt);
		return Message.success("添加成功");
	}

	@SysLog(module="充值管理",method="充值项编辑页面")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		RechargeType rt = rechargeTypeService.getByPrimaryKey(id);
		model.addAttribute("rechargeType",rt);
		return "/admin/recharge_type/edit";
	}
	@SysLog(module="充值管理",method="充值项更新")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
    Message update(Long id, String name, BigDecimal amount, BigDecimal giveAmount, Integer isShow, Integer orders) {
		RechargeType rt = rechargeTypeService.getByPrimaryKey(id);
		rt.setName(name);
		rt.setAmount(amount);
		rt.setGiveAmount(giveAmount);
		rt.setCreateDate(new Date());
		rt.setModifyDate(new Date());
		rt.setIsShow(isShow);
		rt.setOrders(orders);
		rechargeTypeService.updateByPrimaryKeySelective(rt);
		return Message.success("修改成功");
	}

	@SysLog(module="充值管理",method="充值项删除")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
    Message delete(Long[] ids) {
		rechargeTypeService.deleteByPrimaryKeys(ids);
		return SUCCESS_MESSAGE;
	}
}