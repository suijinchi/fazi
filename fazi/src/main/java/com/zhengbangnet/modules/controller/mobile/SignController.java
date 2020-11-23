package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.BaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.SignService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller("mobileSignController")
@RequestMapping("/mobile/sign")
public class SignController extends BaseController {

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "signServiceImpl")
    private SignService signService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpSession session, Model model) {
        Member member = memberService.getCurrent(request);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("nowDate", DateUtils.dateToString(new Date(),"yyyy-MM-dd"));
        params.put("memberId", member.getId());
        List<Map<String, Object>> list = signService.findDateByMember(params);
        if(list!=null&&list.size()>0){
            model.addAttribute("sign", true);
        }else{
            model.addAttribute("sign", false);

            Date yestoday = DateUtils.getSomeDaysBeforeAfter(new Date(), -1);
            params.put("nowDate", DateUtils.dateToString(yestoday,"yyyy-MM-dd"));
            params.put("memberId", member.getId());
            List<Map<String, Object>> yestodayList = signService.findDateByMember(params);
            if(yestodayList==null||yestodayList.size()==0){//没签到
                member.setSignSerialTimes(0);
                memberService.updateByPrimaryKeySelective(member);
            }
        }
        model.addAttribute("member", member);
        return "/mobile/sign/index";
    }

    /**
     * 获取日期
     **/
    @RequestMapping("/date")
    public @ResponseBody
    Message getDate(Integer month, HttpSession session, HttpServletRequest request) throws ParseException {
        Date date = DateUtils.addMonth(new Date(), month);
        Member member = memberService.getCurrent(request);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", member.getId());
        params.put("monthStartDate", DateUtils.getMinMonthDate(date));
        params.put("monthEndDate", DateUtils.getMaxMonthDate(date));
        Message success = Message.success("");
        success.putDatas("signList", signService.findDateByMember(params));
        success.putDatas("date", DateUtils.dateToString(new Date(), DateUtils.patternA));
        return success;
    }

    @RequestMapping("/sign")
    public @ResponseBody
    Message sign(Integer month, HttpSession session, HttpServletRequest request) throws ParseException {
        Long memberId = memberService.getCurrentId(request);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("nowDate", DateUtils.dateToString(new Date(),"yyyy-MM-dd"));
        params.put("memberId", memberId);
        List<Map<String, Object>> list = signService.findDateByMember(params);
        if(list!=null&&list.size()>0){
            return Message.error("您今天已经签过到了哦~");
        }
        signService.sign(memberId);
        return Message.success("签到成功");
    }

}
