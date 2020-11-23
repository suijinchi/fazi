package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.QRCodeUtil;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.Agent;
import com.zhengbangnet.modules.service.AgentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller("adminAgentController")
@RequestMapping("/admin/agent")
public class AgentController extends AdminBaseController {

	@Resource(name = "agentServiceImpl")
	private AgentService agentService;

	@SysLog(module="代理商管理",method="列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String adminMobile,String name,Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		params.put("adminMobile", adminMobile);
		params.put("name", name);
		Page< Map<String, Object>> page = agentService.findPageByParams(params);
		model.addAttribute("page", page);
		model.addAttribute("adminMobile", adminMobile);
		model.addAttribute("name", name);
		return "/admin/agent/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/agent/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="代理商管理",method="保存")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String name,String username,String password,String adminName,String adminMobile,
	HttpServletRequest request, HttpSession session) {
		Agent agent = new Agent();
		agent.setCreateDate(new Date());
		agent.setModifyDate(new Date());
		agent.setName(name);
		agent.setUsername(username);
		agent.setPassword(password);
		agent.setAdminName(adminName);
		agent.setAdminMobile(adminMobile);
		agentService.insertSelective(agent);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		Agent agent = agentService.getByPrimaryKey(id);
		model.addAttribute("agent", agent);
		return "/admin/agent/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="代理商管理",method="更新")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String name,String username,String password,String adminName,String adminMobile,
	Model model, HttpSession session, HttpServletRequest request) {
		Agent agent = agentService.getByPrimaryKey(id);
		if (agent == null) {
			return Message.error("编辑信息不存在");
		}
		agent.setModifyDate(new Date());
		agent.setId(id);
		agent.setName(name);
		agent.setUsername(username);
		agent.setPassword(password);
		agent.setAdminName(adminName);
		agent.setAdminMobile(adminMobile);
		agentService.updateByPrimaryKey(agent);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="代理商管理",method="删除")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			agentService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

	@SysLog(module="经销商管理",method="二维码")
	@RequestMapping(value = "/recharge_qrcode")
	@ResponseBody
	public Message qrcode(String id,HttpSession session) {
		Agent shop = agentService.getByPrimaryKey(Long.parseLong(id));
		Long folderId = Long.parseLong(id)/10000;
		Setting setting = SettingUtils.get();
		String url = SettingUtils.get().getDomain()+"/mobile/recharge/agent/"+id;
		String qrcodeRelativePath = "/resources/upload/agent/recharge/"+folderId+"/"+id+".jpg";
		String qrcodeRealPath = session.getServletContext().getRealPath(qrcodeRelativePath);
		String qrcodeAbsolutePath = setting.getDomain()+qrcodeRelativePath;
		if(!new File(qrcodeRealPath).exists()){
			try {
				QRCodeUtil.encode(url, qrcodeRealPath);
			} catch (Exception e) {
				logger.warn("二维码异常",e);
			}
		}
		Message success = Message.success("");
		success.putDatas("name",shop.getName());
		success.putDatas("path",qrcodeAbsolutePath);
		success.putDatas("link",url);
		return success;
	}
}