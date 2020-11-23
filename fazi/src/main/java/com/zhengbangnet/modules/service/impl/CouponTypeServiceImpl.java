package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;

import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.Coupon;
import com.zhengbangnet.modules.entity.CouponTypeProduct;
import com.zhengbangnet.modules.mapper.CouponMapper;
import com.zhengbangnet.modules.mapper.CouponTypeProductMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.CouponType;
import com.zhengbangnet.modules.service.CouponTypeService;
import com.zhengbangnet.modules.mapper.CouponTypeMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("couponTypeServiceImpl")
@Transactional(readOnly=true)
public class CouponTypeServiceImpl extends BaseServiceImpl<CouponType,Long> implements CouponTypeService{

	@Resource(name="couponTypeMapper")
	private CouponTypeMapper couponTypeMapper;

	@Resource(name="couponMapper")
	private CouponMapper couponMapper;

	@Resource(name="couponTypeProductMapper")
	private CouponTypeProductMapper couponTypeProductMapper;

	@Resource(name="couponTypeMapper")
	public void setBaseDao(BaseMapper<CouponType,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
    @Transactional
	public void save(CouponType couponType, Long[] ctp) {
		couponTypeMapper.insertSelective(couponType);
		if(ctp!=null&&ctp.length>0){
            for(Long t:ctp){
                CouponTypeProduct tctp = new CouponTypeProduct();
                tctp.setCouponTypeId(couponType.getId());
                tctp.setProductId(t);
                couponTypeProductMapper.insert(tctp);
            }
        }
	}

	@Override
	@Transactional
	public void update(CouponType ct, Long[] ctp) {
		couponTypeMapper.updateByPrimaryKeySelective(ct);
		couponTypeProductMapper.deleteByPrimaryKey(ct.getId());
		if(ctp!=null&&ctp.length>0){
			for(Long t:ctp){
				CouponTypeProduct tctp = new CouponTypeProduct();
				tctp.setCouponTypeId(ct.getId());
				tctp.setProductId(t);
				couponTypeProductMapper.insert(tctp);
			}
		}
	}

    @Override
    public List<CouponType> findValidListByParams(Map<String, Object> params) {
        return couponTypeMapper.findValidListByParams(params);
    }

    @Override
    @Transactional
    public void getCoupon(Long couponTypeId, Long memberId) {
        synchronized (this){
            CouponType couponType = couponTypeMapper.getByPrimaryKey(couponTypeId);
            couponType.setStock(couponType.getStock()-1);
            couponType.setGetNum(couponType.getGetNum()+1);
            couponTypeMapper.updateByPrimaryKeySelective(couponType);
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
            coupon.setScene(0);//自助领取
            coupon.setMemberId(memberId);
            coupon.setCouponTypeId(couponTypeId);
            couponMapper.insertSelective(coupon);
        }
    }

    @Override
    @Transactional
    public void deleteByPrimaryKeys(Long[] ids) {
        for(Long id : ids){
            CouponType ct = new CouponType();
            ct.setModifyDate(new Date());
            ct.setIsDeleted(1);
            ct.setId(id);
            couponTypeMapper.updateByPrimaryKeySelective(ct);
        }

    }

    @Override
    public Page<Map<String, Object>> findValidPageByParams(Map<String, Object> params) {
        Pageable pageable = (Pageable)params.get("pageable");
        Long count = couponTypeMapper.getValidCountByParams(params);
        int pn = (int) Math.ceil((double)count/(double)pageable.getPageSize());
        if(pn<pageable.getPageNo()){
            pageable.setPageNo(pn);
        }
        List<Map<String, Object>> list = couponTypeMapper.findValidPageByParams(params);
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(list,count, pageable);
        return page;
    }

}