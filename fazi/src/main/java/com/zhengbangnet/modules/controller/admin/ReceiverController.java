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
import com.zhengbangnet.modules.entity.Receiver;
import com.zhengbangnet.modules.service.ReceiverService;

@Controller("adminReceiverController")
@RequestMapping("/admin/receiver")
public class ReceiverController extends AdminBaseController {

	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = receiverService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/receiver/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/receiver/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String consignee,String contactNumber,String areaName,String address,String zipCode,Integer isDefault,Long memberId,Long areaId,
	HttpServletRequest request, HttpSession session) {
		Receiver receiver = new Receiver();
		receiver.setCreateDate(new Date());
		receiver.setModifyDate(new Date());
		receiver.setConsignee(consignee);
		receiver.setContactNumber(contactNumber);
		receiver.setAreaName(areaName);
		receiver.setAddress(address);
		receiver.setZipCode(zipCode);
		receiver.setIsDefault(isDefault);
		receiver.setMemberId(memberId);
		receiver.setAreaId(areaId);
		receiverService.insertSelective(receiver);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		Receiver receiver = receiverService.getByPrimaryKey(id);
		model.addAttribute("receiver", receiver);
		return "/admin/receiver/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String consignee,String contactNumber,String areaName,String address,String zipCode,Integer isDefault,Long memberId,Long areaId,
	Model model, HttpSession session, HttpServletRequest request) {
		Receiver receiver = receiverService.getByPrimaryKey(id);
		if (receiver == null) {
			return Message.error("编辑信息不存在");
		}
		receiver.setModifyDate(new Date());
		receiver.setId(id);
		receiver.setConsignee(consignee);
		receiver.setContactNumber(contactNumber);
		receiver.setAreaName(areaName);
		receiver.setAddress(address);
		receiver.setZipCode(zipCode);
		receiver.setIsDefault(isDefault);
		receiver.setMemberId(memberId);
		receiver.setAreaId(areaId);
		receiverService.updateByPrimaryKey(receiver);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			receiverService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}