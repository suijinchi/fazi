package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.utils.CookieUtils;
import com.zhengbangnet.common.utils.EmojiFilter;
import com.zhengbangnet.common.utils.JWT;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.MemberRank;
import com.zhengbangnet.modules.service.MemberRankService;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.OrdersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.weixin4j.*;
import org.weixin4j.http.OAuth2Token;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 微信跳转
 */
@Controller("mobileWechatController")
@RequestMapping("/mobile/wechat")
public class WechatController extends MobileBaseController {

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "ordersServiceImpl")
    private OrdersService ordersService;

    @Resource(name = "weixin")
    private Weixin weixin;

    @Resource(name = "memberRankServiceImpl")
    private MemberRankService memberRankService;

    @RequestMapping(value = "index")
    public String index(String invite,HttpServletRequest request, HttpServletResponse response, HttpSession session, String type, String action, Model model) {
        OAuth2 oauth2 = new OAuth2();
        String url = "";
        action = action == null ? "" : action;
        if(invite==null){
            invite = "";
        }
        if ("base".equals(type)) {
            url = oauth2.getOAuth2CodeBaseUrl(Configuration.getOAuthAppId(), SettingUtils.get().getDomain() + "/mobile/wechat/code?action=" + action+"&invite="+invite);
        } else {
            url = oauth2.getOAuth2CodeUserInfoUrl(Configuration.getOAuthAppId(), SettingUtils.get().getDomain() + "/mobile/wechat/code?action=" + action+"&invite="+invite);
        }
        return "redirect:" + url;
    }

    @RequestMapping(value = "code")
    public String code(String code, String action, String invite,HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

        if (StringUtils.isBlank(code)) {
            model.addAttribute("tips", "无法获取code");
            return ERROR_VIEW;
        }

        OAuth2 oauth2 = new OAuth2();
        OAuth2Token token = null;
        try {
            token = oauth2.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret(), code);
        } catch (WeixinException e) {
            logger.warn("oauth2.login错误：", e);
        }
        if (token == null) {
            model.addAttribute("tips", "无法获取code");
            return ERROR_VIEW;
        }
        String openid = token.getOpenid();
        if (StringUtils.isBlank(openid)) {
            model.addAttribute("tips", "获取openid错误");
            return ERROR_VIEW;
        }
        try {

            OAuth2User oauth2User = oauth2.getUserInfo();

            Member member = memberService.getByOpenid(openid);
            if (member == null) {
                member = new Member();
                member.setCreateDate(new Date());
                member.setModifyDate(new Date());
                member.setAvatarUrl(oauth2User.getHeadimgurl());
                member.setBalance(BigDecimal.ZERO);
                if (oauth2User.getSex() == 1) {//男
                    member.setGender("1");
                } else if (oauth2User.getSex() == 2) {//女
                    member.setGender("0");
                } else {
                    member.setGender("2");
                }
                member.setBalance(new BigDecimal(0));
                member.setHistoryBalance(BigDecimal.ZERO);
                member.setPoint(0);
                member.setHistoryPoint(0);
                member.setOpenid(oauth2User.getOpenid());
                String nickname = EmojiFilter.filterEmoji(oauth2User.getNickname());
                member.setNickname(nickname);
                member.setStatus("0");
                member.setType(0);
                member.setSignSerialTimes(0);
                MemberRank mr = memberRankService.getDefault();
                member.setMemberRankId(mr.getId());
                if(StringUtils.isNotBlank(invite)){
                    Member inviteMember = memberService.getByPrimaryKey(Long.parseLong(invite));
                    if(inviteMember!=null){
                        member.setRecommendId(inviteMember.getId());
                        member.setRecommendId2(inviteMember.getRecommendId());
                        member.setRecommendId3(inviteMember.getRecommendId2());
                        member.setTreePath(inviteMember.getTreePath()+invite+Member.TREEPATH_SPRATOR);
                    }
                }

                synchronized (this) {
                    Member tm = memberService.getByOpenid(openid);
                    if (tm == null) {
                        try {
                            memberService.insert(member);
                        } catch (Exception e) {
                            logger.warn("保存会员异常，重新保存：", e);
                            member.setNickname("未知");
                            memberService.insert(member);
                        }
                        memberService.giveCoupon(member);
                    }
                }
            }
            //登录即重新生成头像
//            member.processAvatar(oauth2User.getHeadimgurl(), session);
            member.setAvatarUrl(oauth2User.getHeadimgurl());
            memberService.updateByPrimaryKeySelective(member);

            String tokens = JWT.createJWT(member.getId().toString(), member.getNickname(), SettingUtils.get().getDomain(), 1000 * 60 * 60 * 24 * 30);
            CookieUtils.addCookie(request, response, JWT.JWT_NAME, tokens);
            if(StringUtils.isBlank(action)){
                if (member.getStatus().equals("0")) {
                    action = "/mobile/index";
                } else if (member.getStatus().equals("1")) {
                    action = "/mobile/error";
                }
            }
        } catch (Exception e) {
            logger.info("微信登录异常", e);
            model.addAttribute("tips", "链接跳转错误");
            return ERROR_VIEW;
        }
        String url = action.replace("@", "&");
        return "redirect:" + url;
    }

}
