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
import com.zhengbangnet.modules.entity.MessageLog;
import com.zhengbangnet.modules.service.MessageLogService;

@Controller("adminMessageLogController")
@RequestMapping("/admin/message_log")
public class MessageLogController extends AdminBaseController {

	@Resource(name = "messageLogServiceImpl")
	private MessageLogService messageLogService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = messageLogService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/message_log/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/message_log/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String ip,String mobile,String msgCode,java.util.Date sendDate,Integer type,Integer isUsed,
	HttpServletRequest request, HttpSession session) {
		MessageLog messageLog = new MessageLog();
		messageLog.setCreateDate(new Date());
		messageLog.setModifyDate(new Date());
		messageLog.setIp(ip);
		messageLog.setMobile(mobile);
		messageLog.setMsgCode(msgCode);
		messageLog.setSendDate(sendDate);
		messageLog.setType(type);
		messageLog.setIsUsed(isUsed);
		messageLogService.insertSelective(messageLog);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		MessageLog messageLog = messageLogService.getByPrimaryKey(id);
		model.addAttribute("messageLog", messageLog);
		return "/admin/message_log/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String ip,String mobile,String msgCode,java.util.Date sendDate,Integer type,Integer isUsed,
	Model model, HttpSession session, HttpServletRequest request) {
		MessageLog messageLog = messageLogService.getByPrimaryKey(id);
		if (messageLog == null) {
			return Message.error("编辑信息不存在");
		}
		messageLog.setModifyDate(new Date());
		messageLog.setId(id);
		messageLog.setIp(ip);
		messageLog.setMobile(mobile);
		messageLog.setMsgCode(msgCode);
		messageLog.setSendDate(sendDate);
		messageLog.setType(type);
		messageLog.setIsUsed(isUsed);
		messageLogService.updateByPrimaryKey(messageLog);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			messageLogService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}