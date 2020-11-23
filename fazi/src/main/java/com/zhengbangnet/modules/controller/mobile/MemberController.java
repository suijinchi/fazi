package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.utils.*;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.MemberRank;
import com.zhengbangnet.modules.entity.MessageLog;
import com.zhengbangnet.modules.entity.PointRecord;
import com.zhengbangnet.modules.service.MemberRankService;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.MessageLogService;
import com.zhengbangnet.modules.service.PointRecordService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin4j.Weixin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller("mobileMemberController")
@RequestMapping("/mobile/member")
public class MemberController extends MobileBaseController{

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "memberRankServiceImpl")
    private MemberRankService memberRankService;

    @Resource(name = "pointRecordServiceImpl")
    private PointRecordService pointRecordService;

    @Resource(name = "messageLogServiceImpl")
    private MessageLogService messageLogService;

    @Resource(name = "weixin")
    private Weixin weixin;

    /**
     * 跳转我的中心页面
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

        Member member = memberService.getCurrent(request);
        model.addAttribute("member",member);

        MemberRank memberRank = memberRankService.getByPrimaryKey(member.getMemberRankId());
        model.addAttribute("memberRank",memberRank);

        model.addAttribute("setting",SettingUtils.get());
        return "/mobile/member/index";
    }

    /**
     * 完善信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(Model model, HttpServletRequest request, HttpServletResponse response) {
        Member member = memberService.getCurrent(request);
        model.addAttribute("member",member);
        return "/mobile/member/info";
    }

    /**
     * 提交信息
     */
    @RequestMapping("/submitInfo")
    @ResponseBody
    public Message submitInfo(String mobile,String msgCode,String name,String birthday,HttpServletRequest request) {

        if(StringUtils.isBlank(name)){
            return Message.error("请填写姓名");
        }
        if(StringUtils.isBlank(mobile)){
            return Message.error("请填写手机号");
        }
        if(!RegexUtils.isMobile(mobile)){
            return Message.error("请填写正确的手机号");
        }
        if(StringUtils.isBlank(birthday)){
            return Message.error("请填写生日");
        }

        Member member = memberService.getCurrent(request);



        //手机号不一样则验证验证码
        if(StringUtils.isBlank(member.getMobile())||!mobile.equals(member.getMobile())){

            if(StringUtils.isBlank(msgCode)){
                return Message.error("请填写验证码");
            }
            MessageLog messageLog = messageLogService.getByMobile(mobile,MessageLog.COMPLETE);
            if(messageLog==null){
                return Message.error("验证码不正确");
            }
            if(messageLog.getIsUsed()==1){
                return Message.error("验证码已过期");
            }
            if(messageLog.isExpire()){
                return Message.error("验证码已过期");
            }
            String mc = messageLog.getMsgCode();
            if(!mc.equals(msgCode)){
                return Message.error("验证码错误");
            }
            messageLog.setIsUsed(1);
            messageLogService.updateByPrimaryKey(messageLog);
            member.setMobile(mobile);
        }

        if (member.getRecommendId() != null && (member.getType() == null || member.getType()==0)) {
            Integer point = SettingUtils.get().getRegistePoint();
            Long memberId = member.getRecommendId();
            Member recommendMember = memberService.getByPrimaryKey(memberId);
            recommendMember.setPoint(recommendMember.getPoint() + point);
            recommendMember.setHistoryPoint(recommendMember.getHistoryPoint()+point);
            memberService.updateByPrimaryKeySelective(recommendMember);
            PointRecord pr = new PointRecord();
            pr.setCreateDate(new Date());
            pr.setModifyDate(new Date());
            pr.setMemberId(memberId);
            pr.setMemo("推荐新会员("+name+")注册送积分");
            pr.setPoint(point);
            pr.setSurplusAmount(member.getPoint());
            pointRecordService.insert(pr);
        }

        member.setName(name);
        member.setBirthday(birthday);
        if(StringUtils.isNotBlank(birthday)){
            member.setBirthdayYear(new Integer(birthday.split("-")[0]));
            member.setBirthdayMonth(new Integer(birthday.split("-")[1]));
            member.setBirthdayDay(new Integer(birthday.split("-")[2]));
        }
        member.setMemberNo(mobile);
        member.setType(1);

        memberService.updateByPrimaryKey(member);

        return Message.success("提交成功");
    }


    /**
     *  发送短信验证码
     */
    @RequestMapping(value = "/sendMsgCode", method = RequestMethod.POST)
    public @ResponseBody
    Message sendMsgCode(String mobile,HttpServletRequest request, HttpServletResponse response, Model model) {
        if(StringUtils.isBlank(mobile)){
            return Message.error("请填写手机号");
        }
        if(mobile.length()!=11||!StringUtils.isNumeric(mobile)){
            return Message.error("手机号格式错误");
        }
        Setting setting = SettingUtils.get();
        String ip = request.getRemoteHost();

        Long count = messageLogService.findCountByIp(ip);
        if(count>setting.getIpMaxMsgPerDay()){
            return Message.error("短信发送量达到每日上限，请明天再进行操作！");
        }
        //校验：24小时内大于短信数
        Long times = messageLogService.findCountByMobile(mobile);
        Integer maxMsg = setting.getMobileMaxMsgPerDay();
        if(times>=maxMsg){
            return Message.error("短信发送量达到每日上限，请明天再进行操作！");
        }
//        Member member = memberService.getByMobile(mobile);
        //检测是否过期或者已经发送
        MessageLog messageLog = messageLogService.getByMobile(mobile,MessageLog.COMPLETE);
        if(messageLog!=null){
            //校验：发送间隔小于60秒
            if(!messageLog.isOutInterval()){
                return Message.error("短信发送间隔小于60秒！");
            }
        }
        String num = RandomStringUtils.randomNumeric(6);
        String content = setting.getMsgContent().replace(SMS.CODE, num).replace(SMS.MINUTE, setting.getMsgExpireTime().toString());
        String sign = setting.getMsgSign();
        boolean isSend = SMS.sendMsg(SettingUtils.get().getMsgUid(),SettingUtils.get().getMsgKey(),SettingUtils.get().getMsgTemplate(),sign,mobile,content);
        if(!isSend){
            return Message.error("短信发送失败！");
        }
        MessageLog ml = new MessageLog();
        ml.setCreateDate(new Date());
        ml.setModifyDate(new Date());
        ml.setIp(ip);
        ml.setMobile(mobile);
        ml.setSendDate(new Date());
        ml.setMsgCode(num);
        ml.setType(MessageLog.COMPLETE);
        ml.setIsUsed(0);
        messageLogService.insert(ml);
        Message success = Message.success("发送成功");
        return success;
    }

    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public String qrCode(HttpSession session, HttpServletRequest request, Model model) {

        Member member = memberService.getCurrent(request);

        Long id = member.getId();
        Long folderId = id/10000;
        Setting setting = SettingUtils.get();
        String url = setting.getDomain() + "/mobile/qrcode/jump?id="+id;
        String qrcodeRelativePath = "/resources/upload/qrcode/"+folderId+"/"+id+".jpg";
        String qrcodeRealPath = session.getServletContext().getRealPath(qrcodeRelativePath);
        String qrcodeAbsolutePath = setting.getDomain()+qrcodeRelativePath;
        if(!new File(qrcodeRealPath).exists()){
            try {
                QRCodeUtil.encode(url, qrcodeRealPath);
                member.setQrCodeUrl(qrcodeAbsolutePath);
                memberService.updateByPrimaryKeySelective(member);
            } catch (Exception e) {
                logger.warn("二维码异常",e);
            }
        }
        model.addAttribute("member", member);
        model.addAttribute("qrcodeUrl", qrcodeAbsolutePath);

        model.addAttribute("link", setting.getDomain() + "/mobile/qrcode/index?id="+id);
        model.addAttribute("setting", SettingUtils.get());
        return "/mobile/member/qrcode";
    }


    @RequestMapping(value = "/get_card", method = RequestMethod.GET)
    public String get_card(Model model, HttpServletRequest request, HttpServletResponse response) {
        Member member = memberService.getCurrent(request);
        model.addAttribute("member",member);
        if(StringUtils.isBlank(member.getMemberNo())){
            return "/mobile/member/get_card";
        }else{
            return "redirect:/mobile/member/index";
        }
    }

}
