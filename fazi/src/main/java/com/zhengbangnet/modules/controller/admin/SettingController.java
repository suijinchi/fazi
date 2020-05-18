package com.zhengbangnet.modules.controller.admin;

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
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.utils.SettingUtils;

@Controller
@RequestMapping("/admin/setting")
public class SettingController extends AdminBaseController {
	
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model) {
    	model.addAttribute("setting", SettingUtils.get());
        return "/admin/setting/edit";
    }
    
    @SysLog(module="系统管理",method="更新系统设置")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Message update(Setting setting,Model model,HttpServletRequest request,HttpSession session){
    	if(setting==null){
			Message.success("参数错误");
		}
    	SettingUtils.set(setting);
    	return Message.success("修改成功");
    }
    
}
