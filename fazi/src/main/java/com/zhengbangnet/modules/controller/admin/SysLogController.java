/**
 * 日志管理控制
 */
package com.zhengbangnet.modules.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.service.SysLogsService;

/**
 * 类描述:日志管理控制
 * 
 * @author: hawkbird 
 * @date: 2018年3月24日
 *
 */
@Controller
@RequestMapping("/admin/syslog")
public class SysLogController extends AdminBaseController {

	@Resource(name="sysLogsServiceImpl")
	private SysLogsService sysLogsService;
	/**
	 * 日志查看
	 */
	@RequestMapping("/list")
	@SysLog(module="日志管理",method="查询日志列表")
	public String list(String keyword,Pageable pageable,Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		// 页码
		map.put("pageable", pageable);
		// 创建日期开始
		if (!(keyword == null || keyword.trim().equals("")))
			map.put("keyword", keyword);
		Page<Map<String, Object>> page = sysLogsService.findPageByParams(map);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword == null ? "" : keyword);
		return "/admin/log/logs";
	}

}
