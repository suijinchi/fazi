package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.utils.CookieUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.common.utils.URLUtil;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.weixin4j.Configuration;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.jssdk.JSPermission;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * 微信跳转
 */
@Controller("mobileQrCodeController")
@RequestMapping("/mobile/qrcode")
public class QrCodeController extends MobileBaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name="weixin")
	private Weixin weixin;

	@RequestMapping(value = "/jump", method = RequestMethod.GET)
	public String jumb(Long id) {
		Member member = memberService.getByPrimaryKey(id);
		if(member==null){
			return ERROR_VIEW;
		}
		return "redirect:/mobile/wechat/index?invite="+id;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String qrCode(Long id,HttpSession session, HttpServletRequest request, Model model) {
		Member member = memberService.getByPrimaryKey(id);
		if(member==null){
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		String url = setting.getDomain()+"/mobile/qrcode?invite="+member.getId();
		model.addAttribute("link", url);
		model.addAttribute("member", member);
		model.addAttribute("qrcodeUrl", member.getQrCodeUrl());

		model.addAttribute("link", setting.getDomain() + "/mobile/qrcode/index?id="+member.getId());
		model.addAttribute("setting", SettingUtils.get());
		return "/mobile/qrcode/index";
	}

}
