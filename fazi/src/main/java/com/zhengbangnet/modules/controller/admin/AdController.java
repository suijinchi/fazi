package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.Ad;
import com.zhengbangnet.modules.entity.AdLocation;
import com.zhengbangnet.modules.service.AdLocationService;
import com.zhengbangnet.modules.service.AdService;
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
import java.util.List;
import java.util.Map;

@Controller("adminAdController")
@RequestMapping("/admin/ad")
public class AdController extends AdminBaseController {

	@Resource(name = "adServiceImpl")
	private AdService adService;
	
	@Resource(name = "adLocationServiceImpl")
	private AdLocationService adLocationService;

	@SysLog(module="广告管理",method="查询广告列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = adService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/ad/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("setting", SettingUtils.get());
		List<AdLocation> adlocationList = adLocationService.findAll();
		model.addAttribute("adlocationList", adlocationList);
		return "/admin/ad/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="广告管理",method="添加广告")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String imgUrl,String name,String link,Integer isShow, Long adlocationId,Integer orders,
	HttpServletRequest request, HttpSession session) {
		Ad ad = new Ad();
		ad.setCreateDate(new Date());
		ad.setModifyDate(new Date());
		ad.setImgUrl(imgUrl);
		ad.setName(name);
		ad.setLink(link);
		ad.setIsShow(isShow);
		ad.setAdLocationId(adlocationId);	
		ad.setOrders(orders);
		adService.insertSelective(ad);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("setting", SettingUtils.get());
		List<AdLocation> adlocationList = adLocationService.findAll();
		model.addAttribute("adlocationList", adlocationList);
		Ad ad = adService.getByPrimaryKey(id);
		model.addAttribute("ad", ad);
		return "/admin/ad/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="广告管理",method="更新广告")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String imgUrl,String name,String link,Integer isShow, Long adlocationId,Integer orders,
			HttpServletRequest request, HttpSession session) {
		Ad ad = adService.getByPrimaryKey(id);
		if (ad == null) {
			return Message.error("编辑信息不存在");
		}
		ad.setModifyDate(new Date());
		ad.setImgUrl(imgUrl);
		ad.setName(name);
		ad.setLink(link);
		ad.setIsShow(isShow);
		ad.setAdLocationId(adlocationId);	
		ad.setOrders(orders);
		adService.updateByPrimaryKey(ad);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="广告管理",method="删除广告")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			adService.deleteByPrimaryKeys(ids);		
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}