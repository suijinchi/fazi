package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.BaseController;
import com.zhengbangnet.modules.entity.Area;
import com.zhengbangnet.modules.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员管理
 */
@Controller("mobileAreaController")
@RequestMapping("/mobile/area")
public class AreaController extends BaseController {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	/**
	 * 地区
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> list(Long parentId) {
		List<Area> areas = new ArrayList<Area>();
		Area parent = areaService.getByPrimaryKey(parentId);
		if (parent != null) {
			areas = areaService.findChildren(parentId);
		} else {
			areas = areaService.findRoots();
		}
		Map<String, String> options = new LinkedHashMap<String, String>();
		for (Area area : areas) {
			options.put(area.getId().toString(), area.getName());
		}
		if(parent==null){
			options.put("area", "p");//省
		}else if(parent.getTreePath().split(",").length==0){
			options.put("area", "c");//市
		}else if(parent.getTreePath().split(",").length==2){
			options.put("area", "d");//区
		}

		if(parent!=null){
			String name = parent.getName();
			if(name.equals("北京市")||name.equals("天津市")||name.equals("重庆市")||name.equals("上海市")){
				options.put("area", "d");//区
			}
		}

		return options;
	}
}
