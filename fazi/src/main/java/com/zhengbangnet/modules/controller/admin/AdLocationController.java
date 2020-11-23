package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.AdLocation;
import com.zhengbangnet.modules.service.AdLocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller("adminAdLocationController")
@RequestMapping("/admin/ad_location")
public class AdLocationController extends AdminBaseController {

	@Resource(name = "adLocationServiceImpl")
	private AdLocationService adLocationService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = adLocationService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/ad_location/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/ad_location/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String name,Integer width,Integer height,
	HttpServletRequest request, HttpSession session) {
		AdLocation adLocation = new AdLocation();
		adLocation.setCreateDate(new Date());
		adLocation.setModifyDate(new Date());
		adLocation.setName(name);
		adLocation.setWidth(width);
		adLocation.setHeight(height);
		adLocationService.insertSelective(adLocation);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		AdLocation adLocation = adLocationService.getByPrimaryKey(id);
		model.addAttribute("adLocation", adLocation);
		return "/admin/ad_location/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String name,Integer width,Integer height,
	Model model, HttpSession session, HttpServletRequest request) {
		AdLocation adLocation = adLocationService.getByPrimaryKey(id);
		if (adLocation == null) {
			return Message.error("编辑信息不存在");
		}
		adLocation.setModifyDate(new Date());
		adLocation.setId(id);
		adLocation.setName(name);
		adLocation.setWidth(width);
		adLocation.setHeight(height);
		adLocationService.updateByPrimaryKey(adLocation);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			adLocationService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}