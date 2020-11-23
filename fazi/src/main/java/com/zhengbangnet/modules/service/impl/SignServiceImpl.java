package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;

import com.zhengbangnet.Config;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.service.SignService;
import org.weixin4j.Configuration;
import org.weixin4j.WXTemplateMsg;
import org.weixin4j.Weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("signServiceImpl")
@Transactional(readOnly=true)
public class SignServiceImpl extends BaseServiceImpl<Sign,Long> implements SignService{

	@Resource(name="signMapper")
	private SignMapper signMapper;
	@Resource(name="memberMapper")
	private MemberMapper memberMapper;
	@Resource(name="pointRecordMapper")
	private PointRecordMapper pointRecordMapper;
	@Resource(name="couponTypeMapper")
	private CouponTypeMapper couponTypeMapper;
	@Resource(name="couponMapper")
	private CouponMapper couponMapper;
	@Resource(name = "weixin")
	private Weixin weixin;

	@Override
	@Resource(name="signMapper")
	public void setBaseDao(BaseMapper<Sign,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public List<Map<String,Object>> findDateByMember(Map<String, Object> params) {
		return signMapper.findDateByMember(params);
	}

	@Override
	@Transactional
	public void sign(Long memberId) {
		Member member = memberMapper.getByPrimaryKey(memberId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.clear();
		Date yestoday = DateUtils.getSomeDaysBeforeAfter(new Date(), -1);
		params.put("nowDate", DateUtils.dateToString(yestoday,"yyyy-MM-dd"));
		params.put("memberId", memberId);
		List<Map<String, Object>> yestodayList = signMapper.findDateByMember(params);
		if(yestodayList!=null&&yestodayList.size()>0){//昨天签到了
			member.setSignSerialTimes(member.getSignSerialTimes()+1);
		}else{//没签到
			member.setSignSerialTimes(1);
		}
		memberMapper.updateByPrimaryKeySelective(member);

		Sign s = new Sign();
		s.setModifyDate(new Date());
		s.setCreateDate(new Date());
		s.setMemberId(memberId);
		signMapper.insert(s);

		Integer signPoint = SettingUtils.get().getSignPoint();

		member.setHistoryPoint(member.getHistoryPoint()+signPoint);
		member.setPoint(member.getPoint()+signPoint);
		memberMapper.updateByPrimaryKeySelective(member);
		
		PointRecord pr = new PointRecord();
		pr.setCreateDate(new Date());
		pr.setModifyDate(new Date());
		pr.setSurplusAmount(member.getPoint());
		pr.setPoint(signPoint);
		pr.setMemberId(memberId);
		pr.setMemo("签到送积分");
		pointRecordMapper.insertSelective(pr);

		//签到每满30天送券
		if(member.getSignSerialTimes()!=0&&member.getSignSerialTimes()%30==0){

			Long signCouponId = SettingUtils.get().getSignCouponId();
			if(signCouponId==null){
				return;
			}
			CouponType couponType = couponTypeMapper.getByPrimaryKey(signCouponId);
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
				coupon.setCouponTypeId(signCouponId);
				coupon.setScene(3);
				couponMapper.insertSelective(coupon);


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
					msg.addItem("first", "签到满30天，赠送您一张优惠券，还不快去使用","#173177");
					msg.addItem("keyword1", coupon.getName(),"#173177");
					msg.addItem("keyword2", "签到满30天赠送","#173177");
					msg.addItem("keyword3", tips,"#173177");
					msg.addItem("keyword4", "法滋","#173177");
					msg.addItem("keyword5", "微信商城内使用","#173177");
					msg.addItem("remark","用券更优惠，省钱还省心","#173177");
					weixin.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret());
					weixin.sendTemplateMsg(msg);
				}catch(Exception e){
					logger.error("签到满30天发送优惠券通知模板异常",e);
				}

			}

		}

	}
}