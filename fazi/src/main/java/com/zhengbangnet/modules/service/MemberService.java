package com.zhengbangnet.modules.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Member;

public interface MemberService extends BaseService<Member,Long> {

	/**
	 * 获取当前登录用户
	 * @param request
	 * @return
	 */
	Member getCurrent(HttpServletRequest request);

	/***
	 * 获取当前登录用户id
	 */
	Long getCurrentId(HttpServletRequest request);

	/**
	 * 根据openid查询
	 * @param openid
	 * @return
	 */
	Member getByOpenid(String openid);

	/**
	 * 当月每日新增会员统计
	 * @author suijinchi
	 * @return
	 */
	Map<String,Object> findCountForDay();

	/**
	 * 根据openid查询
	 */
	List<Member> findByOpenid(String openid);

	/**
	 * 根据openid和id查询
	 * @param openid
	 * @param id
	 * @return
	 */
	Member getOtherByOpenidAndId(String openid,Long id);

	/**
	 * 根据条件查询会员数量
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	Long findCountsByParams(Map<String, Object> params);

	/**
	 * 根据手机号查找用户
	 * @param mobile
	 * @return
	 */
	Member getByMobile(String mobile);

	/**
	 * 修改会员积分
	 * @param memberId 会员id
	 * @param point 积分
	 */
    void changePoint(Long memberId, Integer point, String memo);

	Page<Map<String,Object>> findTeamPage(Map<String, Object> params);

    void giveCoupon(Member member);

    List<HashMap<String,Object>> findListByIds(Long[] ids);
}