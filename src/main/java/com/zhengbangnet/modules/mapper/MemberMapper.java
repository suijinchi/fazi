package com.zhengbangnet.modules.mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Member;

/**
 * 
 * MemberMapper数据库操作接口类
 * 
 **/

@Repository("memberMapper")
public interface MemberMapper extends BaseMapper<Member,Long>{

	Member getByOpenid(String openid);
	
	List<Member> findByOpenid(String openid);
	
	/**
	 * 依据会员ID查询积分
	 * @author hawkbird 2018-04-10
	 */
	List<Map<String,Object>> queryPointByMemberId(Map<String,Object> params);
	
	/**
	 * 依据订单状态查询订单数量
	 * @author hawkbird date 2018-04-10
	 */
	int countByOrdersStatus(Map<String,Object> params);

	/**
	 * 根据openid和id查询
	 * @param openid
	 * @param id
	 * @return
	 */
	Member getOtherByOpenidAndId(@Param("openid")String openid, @Param("id")Long id);

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
	 * 查询团队数量
	 * @param params
	 * @return
	 */
	Long getTeamCount(Map<String, Object> params);
	/**
	 * 查询团队分页数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> findTeamPage(Map<String, Object> params);

	List<HashMap<String,Object>> findListByIds(@Param("ids") Long[] ids);

}