package com.zhengbangnet.modules.controller.admin;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.ProductStock;
import com.zhengbangnet.modules.service.CouponTypeProductService;
import com.zhengbangnet.modules.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.CouponType;
import com.zhengbangnet.modules.service.CouponTypeService;

@Controller("adminCouponTypeController")
@RequestMapping("/admin/coupon_type")
public class CouponTypeController extends AdminBaseController {

	@Resource(name = "couponTypeServiceImpl")
	private CouponTypeService couponTypeService;

	@Resource(name = "couponTypeProductServiceImpl")
	private CouponTypeProductService couponTypeProductService;

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@SysLog(module="营销管理",method="优惠券列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String name,Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = couponTypeService.findPageByParams(params);
		model.addAttribute("page", page);
		model.addAttribute("name", name);
		model.addAttribute("now", new Date());
		return "/admin/coupon_type/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/coupon_type/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(
			String name,String subname,Integer type,Integer isShow,
			Integer useType,BigDecimal cutMoney,BigDecimal byFull,
			Integer validDateType,String validStartDate,String validEndDate,Integer validGetDay,Integer validDays,
			Integer stock,Integer getLimit,String useMemo,String contact,Integer useScope,Long[] ctp,Integer isWithPointShare,
			HttpServletRequest request, HttpSession session) {

		CouponType couponType = new CouponType();
		couponType.setCreateDate(new Date());
		couponType.setModifyDate(new Date());
		couponType.setName(name);
		couponType.setSubname(subname);
		couponType.setType(type);
		couponType.setUseType(useType);
		couponType.setCutMoney(cutMoney);
		couponType.setIsShow(isShow);
		couponType.setByFull(byFull);
		couponType.setValidDateType(validDateType);
		couponType.setValidStartDate(DateUtils.stringToDate(validStartDate,"yyyy-MM-dd HH:mm:ss"));
		couponType.setValidEndDate(DateUtils.stringToDate(validEndDate,"yyyy-MM-dd HH:mm:ss"));
		couponType.setValidGetDay(validGetDay);
		couponType.setValidDays(validDays);
		couponType.setStock(stock);
		couponType.setGetLimit(getLimit);
		couponType.setUseMemo(useMemo);
		couponType.setContact(contact);
		couponType.setUseScope(useScope);
		couponType.setIsReg(0);
		couponType.setGetNum(0);
		couponType.setIsDeleted(0);
		couponType.setIsWithPointShare(isWithPointShare);
		couponTypeService.save(couponType,ctp);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {

		CouponType couponType = couponTypeService.getByPrimaryKey(id);
		model.addAttribute("couponType", couponType);

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("couponTypeId",couponType.getId());
		List<HashMap<String, Object>> couponTypeProductList = couponTypeProductService.findListByParams(params);
		model.addAttribute("couponTypeProductList", couponTypeProductList);
		return "/admin/coupon_type/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,Integer isShow,String contact,Integer stock,Long[] ctp, HttpSession session, HttpServletRequest request) {
		CouponType couponType = couponTypeService.getByPrimaryKey(id);
		if (couponType == null) {
			return Message.error("编辑信息不存在");
		}
		CouponType ct = new CouponType();
		ct.setModifyDate(new Date());
		ct.setId(id);
		ct.setIsShow(isShow);
		ct.setContact(contact);
		ct.setStock(stock);
		couponTypeService.update(ct,ctp);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="影像管理",method="批量删除")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			couponTypeService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Long id, Model model, HttpServletRequest request, HttpSession session) {
		CouponType couponType = couponTypeService.getByPrimaryKey(id);
		model.addAttribute("couponType", couponType);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("couponTypeId",couponType.getId());
		List<HashMap<String, Object>> couponTypeProductList = couponTypeProductService.findListByParams(params);
		model.addAttribute("couponTypeProductList", couponTypeProductList);
		return "/admin/coupon_type/detail";
	}



	@RequestMapping("/findProduct")
	public @ResponseBody
	Object findProduct(String keyword, HttpServletRequest request, Model model) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isBlank(keyword)){
			keyword = "";
		}
		params.put("keyword",keyword.trim());
		params.put("count",10);
		List<HashMap<String, Object>> productList = productService.findListByParams(params);
		List<HashMap<String, Object>> list = productList;
		List<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> data = new HashMap<String,Object>();
			Map<String, Object> map = list.get(i);
			Long id = (Long)map.get("id");
			String pname = (String)map.get("name");
			String showUrl = (String)map.get("showUrl");
			BigDecimal lowPrice = (BigDecimal)map.get("lowPrice");
			BigDecimal highPrice = (BigDecimal)map.get("highPrice");
			data.put("highPrice",highPrice);
			data.put("lowPrice",lowPrice);
			if(lowPrice.compareTo(highPrice)!=0){
				data.put("priceRange","￥"+lowPrice+"-"+highPrice);
			}else{
				data.put("priceRange","￥"+lowPrice);
			}
			data.put("showUrl",showUrl);
			data.put("name",pname);
			data.put("id",id);
			listData.add(data);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("value", listData);
		return data;
	}
}