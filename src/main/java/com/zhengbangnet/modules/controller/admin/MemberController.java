package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.Config;
import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.common.utils.excel.ExportExcel;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin4j.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类描述:会员控制类
 * 
 * @author: suijinchi
 * @date: 2018年6月19日
 *
 */
@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends AdminBaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;

	@Resource(name = "couponTypeServiceImpl")
	private CouponTypeService couponTypeService;

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;

	@Resource(name = "couponTaskServiceImpl")
	private CouponTaskService couponTaskService;

	@Resource(name = "couponTaskDetailServiceImpl")
	private CouponTaskDetailService couponTaskDetailService;

	@Resource(name="sysAdminServiceImpl")
	private SysAdminService sysAdminService;

	@Resource(name="sysMenuServiceImpl")
	private SysMenuService sysMenuService;

	@Resource(name="productServiceImpl")
	private ProductService productService;

	@Resource(name = "weixin")
	private Weixin weixin;

	@Resource(name="pointRecordServiceImpl")
	private PointRecordService pointRecordService;

	/**
	 * 会员列表展现
	 */
	@SysLog(module="会员管理",method="查询会员列表")
	@RequestMapping("/list")
	public String list(Long productId,String begRq, String endRq,String monthStart,String dayStart,String monthEnd,String dayEnd,
					   String nickname, String mobile, String type,Long memberRankId, String status,
					   BigDecimal historyBalanceLow, BigDecimal historyBalanceHigh,Pageable pageable, Model model) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 页码
		map.put("pageable", pageable);
		// 创建日期开始
		if (!(begRq == null || begRq.trim().equals("")))
			map.put("begRq", begRq);
		// 创建日期结束
		if (!(endRq == null || endRq.trim().equals("")))
			map.put("endRq", endRq);
		// 会员昵称
		if (!(nickname == null || nickname.trim().equals("")))
			map.put("nickname", nickname);
		// 会员级别
		map.put("memberRankId", memberRankId);
		map.put("type", type);
		// 会员状态
		if (!(status == null || status.trim().equals("")))
			map.put("status", status);
		map.put("mobile", mobile);
		map.put("historyBalanceLow", historyBalanceLow);
		map.put("historyBalanceHigh", historyBalanceHigh);

		map.put("currentMonth", DateUtils.dateToString(new Date(),"MM"));

		map.put("monthStart",StringUtils.isNotBlank(monthStart)?new Integer(monthStart):"");
		map.put("dayStart",StringUtils.isNotBlank(dayStart)?new Integer(dayStart):"");
		map.put("monthEnd",StringUtils.isNotBlank(monthEnd)?new Integer(monthEnd):"");
		map.put("dayEnd",StringUtils.isNotBlank(dayEnd)?new Integer(dayEnd):"");

		map.put("productId",productId);

		Product product = productService.getByPrimaryKey(productId);
		model.addAttribute("product",product);

		model.addAttribute("monthStart",StringUtils.isNotBlank(monthStart)?new Integer(monthStart):"");
		model.addAttribute("dayStart",StringUtils.isNotBlank(dayStart)?new Integer(dayStart):"");
		model.addAttribute("monthEnd",StringUtils.isNotBlank(monthEnd)?new Integer(monthEnd):"");
		model.addAttribute("dayEnd",StringUtils.isNotBlank(dayEnd)?new Integer(dayEnd):"");

		try {
			weixin.login(Configuration.getOAuthAppId(),Configuration.getOAuthSecret());
		} catch (WeixinException e) {
			logger.warn("后台会员列表异常：",e);
		}

		Page<Map<String, Object>> page = memberService.findPageByParams(map);
		if (page.getContent()!=null) {
			for (int i=0;i<page.getContent().size();i++) {
				Map<String, Object> data = page.getContent().get(i);
				Long id = (Long) data.get("id");
				String nickName = (String) data.get("nickname");
				String openid = (String) data.get("openid");
				String avatarUrl = (String) data.get("avatarUrl");
				if(StringUtils.isBlank(nickName)||StringUtils.isBlank(avatarUrl)){
					try {
						User userInfo = weixin.getUserInfo(openid);
						if(userInfo!=null){
							String hiu = userInfo.getHeadimgurl();
							String nn = userInfo.getNickname();
							if(StringUtils.isBlank(hiu)){
								hiu = SettingUtils.get().getDefaultHead();
							}
							if(StringUtils.isBlank(nn)){
								nn = "未知";
							}
							Member m = new Member();
							m.setAvatarUrl(hiu);
							m.setNickname(nn);
							m.setId(id);
							try {
								memberService.updateByPrimaryKeySelective(m);
							} catch (Exception e) {
								String name = (String) data.get("name");
								if(StringUtils.isBlank(name)){
									name = "未知";
								}
								m.setNickname(name);
								memberService.updateByPrimaryKeySelective(m);
							}
							data.put("nickname",userInfo.getNickname());
							data.put("avatarUrl",userInfo.getHeadimgurl());
						}
					} catch (Exception e) {
						logger.warn("后台会员列表获取用户信息异常：",e);
					}
				}
				if (nickName != null && nickName.length() > 5) {
					nickName = nickName.substring(0, 5);
				}
				page.getContent().get(i).put("shortNickName", nickName);
			}
		}

/*
		Date lastDate = DateUtils.getSomeDaysBeforeAfter(new Date(),-30);
		if(StringUtils.isBlank(monthStart)){
			monthStart = DateUtils.dateToString(lastDate,"MM");
		}
		if(StringUtils.isBlank(dayStart)){
			dayStart = DateUtils.dateToString(lastDate,"dd");
		}
		model.addAttribute("monthStart",monthStart);
		model.addAttribute("dayStart",dayStart);

		if(StringUtils.isBlank(monthEnd)){
			monthEnd = DateUtils.dateToString(new Date(),"MM");
		}
		if(StringUtils.isBlank(dayEnd)){
			dayEnd = DateUtils.dateToString(new Date(),"dd");
		}
		model.addAttribute("monthEnd",monthEnd);
		model.addAttribute("dayEnd",dayEnd);
		*/

		SysAdmin admin = sysAdminService.getCurrent();
		List<SysMenu> sysMenuList = sysMenuService.findBySysAdmin(admin.getId());
		model.addAttribute("sysMenuList",sysMenuList);

		List<MemberRank> memberRankList = memberRankService.findAll();
		model.addAttribute("memberRankList", memberRankList);

		model.addAttribute("page", page);
		model.addAttribute("mobile", mobile);
		model.addAttribute("historyBalanceLow", historyBalanceLow);
		model.addAttribute("historyBalanceHigh", historyBalanceHigh);
		model.addAttribute("begRq", begRq == null ? "" : begRq);
		model.addAttribute("endRq", endRq == null ? "" : endRq);
		model.addAttribute("nickname", nickname == null ? "" : nickname);
		model.addAttribute("memberRankId", memberRankId);
		model.addAttribute("type", type == null ? "" : type);
		model.addAttribute("status", status == null ? "" : status);
		return "/admin/member/list";
	}

	@SysLog(module="会员管理",method="更新用户信息")
	@RequestMapping(value = "/update_member", method = RequestMethod.POST)
	@ResponseBody
	public Message update_member(Long memberId,String memberName,String memberMobile,String memberBirthday) {
		Member member = memberService.getByPrimaryKey(memberId);
		member.setName(memberName);
		member.setMobile(memberMobile);
		member.setBirthday(memberBirthday);
		if(StringUtils.isNotBlank(memberBirthday)){
			member.setBirthdayYear(new Integer(memberBirthday.split("-")[0]));
			member.setBirthdayMonth(new Integer(memberBirthday.split("-")[1]));
			member.setBirthdayDay(new Integer(memberBirthday.split("-")[2]));
		}
		memberService.updateByPrimaryKeySelective(member);
		return Message.success("修改成功");
	}


	/**
	 * 启用会员
	 */
	@SysLog(module="会员管理",method="修改积分")
	@RequestMapping(value = "/change_point", method = RequestMethod.POST)
	@ResponseBody
	public Message change_point(Long memberId,Integer point,String memo) {
		Member member = memberService.getByPrimaryKey(memberId);
		if(point<0){
			if(member.getPoint()+point<0){
				return Message.error("扣除积分不能小于会员当前积分");
			}
		}
		memberService.changePoint(memberId,point,memo);
		member = memberService.getByPrimaryKey(memberId);
		return Message.success("调整成功，会员当前积分："+member.getPoint());
	}


	/**
	 * 启用会员
	 */
	@SysLog(module="会员管理",method="启用会员")
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public Message start(String id) {
		Member member = new Member();
		try {
			member.setId(Long.valueOf(id));
			member.setStatus("0");
			if (memberService.updateByPrimaryKeySelective(member) > 0)
				return Message.success("启用成功！");
			else
				return Message.error("启用失败！");
		} catch (Exception ex) {
			return Message.error("启用失败！");
		}
	}

	@SysLog(module="会员管理",method="查询商品")
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@ResponseBody
	public Object product(String productName) {
		List<Object> dataList = new ArrayList<>();
		Map<String,Object> params = new HashMap<>();
		params.put("count",10);
		params.put("keyword",productName);
		List<HashMap<String, Object>> productList = productService.findListByParams(params);
		for(Map<String,Object> data:productList){
			Map<String,Object> map = new HashMap<>();
			map.put("productName",data.get("name"));
			map.put("productId",data.get("id"));
			dataList.add(map);
		}
		return dataList;
	}

	/**
	 * 停用会员
	 */
	@SysLog(module="会员管理",method="停用会员")
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public Message stop(String id) {
		Member member = new Member();
		try {
			member.setId(Long.valueOf(id));
			member.setStatus("1");
			if (memberService.updateByPrimaryKeySelective(member) > 0)
				return Message.success("冻结成功！");
			else
				return Message.error("冻结失败！");
		} catch (Exception ex) {
			return Message.error("冻结失败！");
		}
	}

	/**
	 * 依据查询条件导出EXCEL
	 */
	@SysLog(module="会员管理",method="导出会员列表")
	@RequestMapping("/exportXls")	
	public void exportExcel(Long[] ids,Long productId,Long memberRankId,String begRq, String endRq, String nickname, String type, String status,
			String monthStart,String dayStart,String monthEnd,String dayEnd, String mobile,BigDecimal historyBalanceLow, BigDecimal historyBalanceHigh,
							Model model,HttpServletResponse response){

		List<HashMap<String,Object>> ls = null;
		if(ids!=null&&ids.length>0){
			ls = memberService.findListByIds(ids);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			// 创建日期开始
			if (!(begRq == null || begRq.trim().equals("")))
				map.put("begRq", begRq);
			// 创建日期结束
			if (!(endRq == null || endRq.trim().equals("")))
				map.put("endRq", endRq);
			// 会员昵称
			if (!(nickname == null || nickname.trim().equals("")))
				map.put("nickname", nickname);
			// 会员身份
			if (!(type == null || type.trim().equals("")))
				map.put("type", type);
			// 会员状态
			if (!(status == null || status.trim().equals("")))
				map.put("status", status);
			map.put("memberRankId", memberRankId);
			map.put("type", type);
			// 会员状态
			if (!(status == null || status.trim().equals("")))
				map.put("status", status);
			map.put("mobile", mobile);
			map.put("historyBalanceLow", historyBalanceLow);
			map.put("historyBalanceHigh", historyBalanceHigh);
			map.put("currentMonth", DateUtils.dateToString(new Date(),"MM"));
			map.put("monthStart",StringUtils.isNotBlank(monthStart)?new Integer(monthStart):"");
			map.put("monthEnd",StringUtils.isNotBlank(monthEnd)?new Integer(monthEnd):"");
            map.put("dayStart",StringUtils.isNotBlank(dayStart)?new Integer(dayStart):"");
			map.put("dayEnd",StringUtils.isNotBlank(dayEnd)?new Integer(dayEnd):"");
			map.put("productId",productId);
			ls = memberService.findListByParams(map);

		}

		if(ls==null||ls.isEmpty())
			return;
		String[] header = {"ID","昵称","性别","级别","积分","余额","创建时间","状态","姓名","手机号","生日"};
		ExportExcel exportXls = null;
		try {
			exportXls = new ExportExcel("会员统计表", header);
			for(HashMap m:ls){
				Row row = exportXls.addRow();
				exportXls.addCell(row, 0, m.get("id"));
				exportXls.addCell(row, 1, m.get("nickname"));
				exportXls.addCell(row, 2, m.get("gender").equals("1")?"男":"女");
				exportXls.addCell(row, 3, m.get("memberRankName"));
				exportXls.addCell(row, 4, m.get("point"));
				exportXls.addCell(row, 5, m.get("balance"));
				exportXls.addCell(row, 6, m.get("cjrq"));
				exportXls.addCell(row, 7, m.get("status").equals("0")?"正常":"冻结");
				exportXls.addCell(row, 8, m.get("name"));
				exportXls.addCell(row, 9, m.get("mobile"));
				exportXls.addCell(row, 10, m.get("birthday"));
			}			
			exportXls.write(response, "会员统计表.xlsx");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{			
			if(exportXls!=null)
				exportXls.dispose();
		}
	}

	@SysLog(module="会员管理",method="领取优惠券")
	@RequestMapping(value = "/select_coupon", method = RequestMethod.GET)
	public String list(String name,Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
        params.put("stock", 0);
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = couponTypeService.findPageByParams(params);
		model.addAttribute("page", page);
		model.addAttribute("name", name);
		model.addAttribute("now", new Date());
		return "/admin/member/select_coupon";
	}

    @SysLog(module="会员管理",method="赠送优惠券")
    @RequestMapping(value = "/send_coupon")
    @ResponseBody
    public Message send_coupon(Long[] memberIds,Long[] couponTypeIds) {

		SysAdmin current = sysAdminService.getCurrent();

		CouponTask task = new CouponTask();
		task.setCreateDate(new Date());
		task.setModifyDate(new Date());
		task.setCouponTypeIds(StringUtils.join(couponTypeIds,","));
		task.setMemberIds(StringUtils.join(memberIds,","));
		task.setCurrentRecord(0);
		task.setIsCompleted(0);
		task.setSysAdminId(current.getId());
		task.setType(0);
		couponTaskService.insert(task);

		List<CouponType> couponTypeList = new ArrayList<>();
		for(Long id:couponTypeIds){
			CouponType couponType = couponTypeService.getByPrimaryKey(id);
			couponTypeList.add(couponType);
		}
		
		for(Long memberId:memberIds){

			Member member = memberService.getByPrimaryKey(memberId);
			for(CouponType couponType:couponTypeList){
				try{
					Coupon coupon = new Coupon();
					BeanUtils.copyProperties(couponType,coupon);
					if(couponType.getValidDateType()==2){
						Integer validGetDay = couponType.getValidGetDay();
						Integer validDays = couponType.getValidDays();
						Date sd = DateUtils.getSomeDaysBeforeAfter(new Date(), validGetDay);
						Date ed = DateUtils.getSomeDaysBeforeAfter(sd, validDays);
						coupon.setValidStartDate(sd);
						coupon.setValidEndDate(ed);
					}
					coupon.setCreateDate(new Date());
					coupon.setModifyDate(new Date());
					coupon.setStatus(0);
					coupon.setMemberId(memberId);
					coupon.setCouponTypeId(couponType.getId());
					coupon.setScene(2);//后台发送
					coupon.setSysAdminId(current.getId());

					couponService.insertSelective(coupon);

					CouponTaskDetail detail = new CouponTaskDetail();
					detail.setCreateDate(new Date());
					detail.setModifyDate(new Date());
					detail.setCouponId(coupon.getId());
					detail.setCouponTaskId(task.getId());
					detail.setCouponTypeId(couponType.getId());
					detail.setMemberId(memberId);
					couponTaskDetailService.insert(detail);
					/**
					 3Pbk-t1NJFO9I3VkApOAAzedHJQA8RtdQ9L9hgr9qa4
					 开发者调用模版消息接口时需提供模版ID
					 标题购买成功通知
					 行业IT科技 - 互联网|电子商务
					 详细内容
					 {{first.DATA}}
					 产品名称：{{keyword1.DATA}}
					 支付金额：{{keyword2.DATA}}
					 使用时间：{{keyword3.DATA}}
					 使用商家：{{keyword4.DATA}}
					 验券码：{{keyword5.DATA}}
					 {{remark.DATA}}
					 在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
					 内容示例
					 购买成功通知
					 产品名称：机器洗车
					 支付金额：30元
					 使用时间：2017-2020
					 使用商家：不限
					 验券码：Xdfg3f
					 消费时请向商家出示验券码，验券成功后便可享受对应服务。部分参与活动的特价产品仅可在指定商家或指定时间内使用。如有任何疑问，请拨打客服电话400
					 */
					try{
						String tips  = DateUtils.dateToString(coupon.getValidStartDate(),"yyyy-MM-dd HH:mm")
									+"至"
									+DateUtils.dateToString(coupon.getValidEndDate(),"yyyy-MM-dd HH:mm");
						WXTemplateMsg msg = new WXTemplateMsg();
						msg.setTouser(member.getOpenid());
						msg.setTemplate_id(Config.getProperty("couponMsgId"));
						msg.setUrl(SettingUtils.get().getDomain()+"/mobile/wechat/index?action=/mobile/index");
						msg.addItem("first", "系统赠送了您一张优惠券，还不快去使用","#173177");
						msg.addItem("keyword1", coupon.getName(),"#173177");
						msg.addItem("keyword2", "赠送","#173177");
						msg.addItem("keyword3", tips,"#173177");
						msg.addItem("keyword4", "法滋","#173177");
						msg.addItem("keyword5", "微信商城内使用","#173177");
						msg.addItem("remark","用券更优惠，省钱还省心","#173177");
						weixin.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret());
						weixin.sendTemplateMsg(msg);
					}catch(Exception e){
						logger.error("后台批量发送优惠券通知模板异常",e);
					}
				}catch (Exception e){
					logger.error("后台批量发送优惠券异常",e);
				}
			}
		}
		task.setIsCompleted(1);
		couponTaskService.updateByPrimaryKey(task);
	    return Message.success("发送成功");
    }

	@SysLog(module="会员管理",method="查询积分列表")
	@RequestMapping("/point_list")
	public String list(Long memberId,Pageable pageable, Model model) {
		Map<String,Object> params = new HashMap<String,Object>();
		pageable.setPageSize(20);
		params.put("pageable", pageable);
		params.put("memberId", memberId);
		Page<Map<String, Object>> page = pointRecordService.findPageByParams(params);
		model.addAttribute("page",page);
		return "/admin/member/point_list";
	}

}
