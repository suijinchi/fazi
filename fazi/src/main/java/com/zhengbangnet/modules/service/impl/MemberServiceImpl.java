package com.zhengbangnet.modules.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhengbangnet.Config;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.common.utils.CookieUtils;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.JWT;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.service.MemberService;

import io.jsonwebtoken.Claims;
import org.weixin4j.Configuration;
import org.weixin4j.WXTemplateMsg;

@Service("memberServiceImpl")
@Transactional(readOnly=true)
public class MemberServiceImpl extends BaseServiceImpl<Member,Long> implements MemberService{

	@Resource(name="memberMapper")
	private MemberMapper memberMapper;

	@Resource(name="ordersMapper")
	private OrdersMapper ordersMapper;

	@Resource(name="couponTypeMapper")
	private CouponTypeMapper couponTypeMapper;

	@Resource(name="couponMapper")
	private CouponMapper couponMapper;

	@Resource(name="pointRecordMapper")
	private PointRecordMapper pointRecordMapper;

	@Resource(name="pointExpMapper")
	private PointExpMapper pointExpMapper;

	@Resource(name="couponTaskMapper")
	private CouponTaskMapper couponTaskMapper;

	@Resource(name="memberMapper")
	public void setBaseDao(BaseMapper<Member,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
	
	@Override
	public Member getCurrent(HttpServletRequest request) {
		if(SettingUtils.get().getIsDebug()){
			return memberMapper.getByPrimaryKey(1L);
		}
		String cookie = CookieUtils.getCookie(request, JWT.JWT_NAME);
		Claims parseJWT = JWT.parseJWT(cookie);
		if(parseJWT==null){
			return null;
		}
		String subject = parseJWT.getSubject();
		if(subject==null){
			return null;
		}
		JSONObject obj = JSONObject.parseObject(subject);
		if(obj==null){
			return null;
		}
		String uid = obj.getString(JWT.USER_ID);
		if(uid==null){
			return null;
		}
		Long memberId = new Long(uid);
		return memberMapper.getByPrimaryKey(memberId);
	}
	
	@Override
	public Long getCurrentId(HttpServletRequest request) {
		if(SettingUtils.get().getIsDebug()){
			return 1L;
		}
		String cookie = CookieUtils.getCookie(request, JWT.JWT_NAME);
		if(StringUtils.isBlank(cookie)){
			return null;
		}
		Claims parseJWT = JWT.parseJWT(cookie);
		if(parseJWT==null){
			return null;
		}
		String subject = parseJWT.getSubject();
		if(subject==null){
			return null;
		}
		JSONObject obj = JSONObject.parseObject(subject);
		if(obj==null){
			return null;
		}
		String uid = obj.getString(JWT.USER_ID);
		if(uid==null){
			return null;
		}
		return new Long(uid);
	}

	@Override
	public Member getByOpenid(String openid) {
		return memberMapper.getByOpenid(openid);
	}

	/**
	 * 当月每日新增会员统计
	 * @author suijinchi
	 * @return
	 */
	@Override
	public Map<String,Object> findCountForDay() {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		List<Long> countList = new ArrayList<Long>();
		List<Integer> dayList = new ArrayList<Integer>();
		Integer year = DateUtils.getDateYear(DateUtils.getDate());
		Integer month = DateUtils.getDateMonth(DateUtils.getDate());
		Integer days = DateUtils.getDayOfMonth(DateUtils.dateToString(new Date(),"yyyyMM"));
		for (int day = 1; day <= days; day ++) {
			String startTime = DateUtils.dateToString(DateUtils.getDate(year, month, day, 0, 0, 0), "yyyy-MM-dd HH:mm:ss");
			String endTime = DateUtils.dateToString(DateUtils.getDate(year, month, day, 23, 59, 59), "yyyy-MM-dd HH:mm:ss");
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			Long count = memberMapper.findCountsByParams(map);
			if (count == null) {
				count = 0L;
			}
			map.clear();
			dayList.add(day);
			countList.add(count);
		}
		params.put("dayList", dayList);
		params.put("countList", countList);
		return params;
	}

	@Override
	public List<Member> findByOpenid(String openid) {
		return memberMapper.findByOpenid(openid);
	}

	@Override
	public Member getOtherByOpenidAndId(String openid, Long id) {
		return memberMapper.getOtherByOpenidAndId(openid,id);
	}

	/**
	 * 根据条件查询会员数量
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	@Override
	public Long findCountsByParams(Map<String, Object> params) {
		return memberMapper.findCountsByParams(params);
	}

	@Override
	public Member getByMobile(String mobile) {
		return memberMapper.getByMobile(mobile);
	}

	@Override
	@Transactional
	public void changePoint(Long memberId, Integer point,String memo) {
		Member member = memberMapper.getByPrimaryKey(memberId);

		member.setPoint(member.getPoint()+point);
		member.setHistoryPoint(member.getPoint()+point);
		member.setModifyDate(new Date());
		memberMapper.updateByPrimaryKey(member);

		//积分记录
		PointRecord pr = new PointRecord();
		pr.setCreateDate(new Date());
		pr.setModifyDate(new Date());
		pr.setMemberId(member.getId());
		pr.setMemo(memo);
		pr.setPoint(point);
		pr.setSurplusAmount(member.getPoint());
		pointRecordMapper.insert(pr);

	}

	@Override
	public Page<Map<String, Object>> findTeamPage(Map<String, Object> params) {
		Pageable pageable = (Pageable)params.get("pageable");
		Long count = memberMapper.getTeamCount(params);
		int pn = (int) Math.ceil((double)count/(double)pageable.getPageSize());
		if(pn<pageable.getPageNo()){
			pageable.setPageNo(pn);
		}
		List<Map<String, Object>> list = memberMapper.findTeamPage(params);
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(list,count, pageable);
		return page;
	}

	@Override
	@Transactional
	public void giveCoupon(Member member) {

		Long registeCouponId = SettingUtils.get().getRegisteCouponId();
		if(registeCouponId==null){
			return;
		}
		CouponType couponType = couponTypeMapper.getByPrimaryKey(registeCouponId);
		if (couponType != null && couponType.getIsDeleted()==0 && couponType.getStock() > 0) {

			//更改库存和获取数量
			couponType.setStock(couponType.getStock() - 1);
			couponType.setGetNum(couponType.getGetNum() + 1);
			couponTypeMapper.updateByPrimaryKeySelective(couponType);

			Coupon coupon = new Coupon();
			BeanUtils.copyProperties(couponType, coupon);
			if (couponType.getValidDateType() == 2) {
				Integer validGetDay = couponType.getValidGetDay();
				Integer validDays = couponType.getValidDays();
				Date sd = DateUtils.getSomeDaysBeforeAfter(new Date(), validGetDay);
				Date ed = DateUtils.getSomeDaysBeforeAfter(sd, validDays);
				coupon.setValidStartDate(sd);
				coupon.setValidEndDate(ed);
			}
			coupon.setSourceMemberId(member.getId());
			coupon.setCreateDate(new Date());
			coupon.setModifyDate(new Date());
			coupon.setStatus(0);
			coupon.setMemberId(member.getId());
			coupon.setCouponTypeId(registeCouponId);
			couponMapper.insertSelective(coupon);
		}
	}

	@Override
	public List<HashMap<String, Object>> findListByIds(Long[] ids) {
		return memberMapper.findListByIds(ids);
	}

}