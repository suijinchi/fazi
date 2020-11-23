package com.zhengbangnet.modules.controller.mobile;

import com.alibaba.fastjson.JSONObject;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.common.utils.URLUtil;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin4j.Configuration;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.jssdk.JSPermission;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller("mobileProductController")
@RequestMapping("/mobile/product")
public class ProductController extends MobileBaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;

	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	@Resource(name = "couponTypeServiceImpl")
	private CouponTypeService couponTypeService;

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;

	@Resource(name = "productTagServiceImpl")
	private ProductTagService productTagService;
	
	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;

	@Resource(name = "assessServiceImpl")
	private AssessService assessService;

	@Resource(name = "componentServiceImpl")
	private ComponentService componentService;

	/**
	 * 会员福利（前台产品）详情
	 */
	@RequestMapping("/detail")
	public String productDetail(Long skuId,String sn,Model model, HttpServletRequest request, HttpServletResponse response) {

		ProductStock ps = productStockService.getByPrimaryKey(skuId);
		if(ps==null){
			model.addAttribute("tips", "查询的商品不存在");
			return RESULT_TIPS;
		}

		Product product = productService.getByPrimaryKey(ps.getProductId());
		if (product == null) {
			model.addAttribute("tips", "查询的商品不存在");
			return RESULT_TIPS;
		}

		if (product.getIsMarketable() != 1) {
			model.addAttribute("tips", "商品已下架");
			return RESULT_TIPS;
		}

		//商品
		model.addAttribute("product", product);

		//商品库存等信息
		model.addAttribute("productStock", ps);

		Map<String,Object> params = new HashMap<String,Object>();

		//查询该商品的优惠券
		params.put("now",new Date());
		params.put("productId",product.getId());
		List<CouponType> couponTypeList = couponTypeService.findValidListByParams(params);
		model.addAttribute("couponTypeList",couponTypeList);

		//新品
		List<Long> tagIds = new ArrayList<Long>();
		tagIds.add(2L);//商品详情推荐
		params.clear();
		params.put("tagIds",tagIds);
		params.put("isMarketable",1);
		List<HashMap<String, Object>> recommendList = productService.findListByParams(params);
		for(HashMap<String,Object> map : recommendList){
			Long id = (Long)map.get("id");
			ProductStock st = productStockService.getByProductId(id);
			map.put("productStock",st);
		}
		model.addAttribute("recommendList",recommendList);


		//检索方位图
		params.clear();
		params.put("productid", product.getId());
		List<HashMap<String, Object>> ls = productImageService.findListByParams(params);
		if (ls != null && ls.size() > 0) {
			model.addAttribute("swiperpics", ls);
		}

		Member member = memberService.getCurrent(request);

		//分享授权
		Setting setting = SettingUtils.get();
//		String link = setting.getDomain() + "/mobile/qrcode?invite=" + member.getId();
		String link = setting.getDomain() + "/mobile/product/detail?skuId=" + ps.getId();
		String cl = URLUtil.getRequestUrl(request);
		JSPermission jsPermission = null;
		try {
			weixin.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret());
			jsPermission = weixin.getJSPermission(cl);
			model.addAttribute("jsPermission", jsPermission);
		} catch (WeixinException e) {
			logger.warn("jsPermission异常", e);
		}
		String imgUrl = SettingUtils.get().getDomain()+"/resources/mobile/images/logo.png";
		if (ls != null && ls.size() > 0) {
			imgUrl = (String)ls.get(0).get("imgUrl");
		}
		model.addAttribute("imgUrl", imgUrl);
		model.addAttribute("link", link);
		model.addAttribute("setting", setting);

		boolean isSell = false;
		if(product.getSellDate()!=null&&product.getSellDate().before(new Date())) {
			isSell = true;
		}else if(product.getSellDate()==null){
			isSell = true;
		}else{
			isSell = false;
		}
		model.addAttribute("isSell", isSell);//是否售卖
		model.addAttribute("sellDate", DateUtils.dateToString(product.getSellDate(),"yyyy-MM-dd HH:mm"));

		if(product.getSpec()==0){//无规格
			return "/mobile/product/detail_nospec";
		}else{
			List<Long> specValueList = productStockService.getProductStockSpecValue(ps.getId());
			String specValueJsonStr = JSONObject.toJSON(specValueList).toString();
			model.addAttribute("specValueJsonStr", specValueJsonStr);

			//查询成分
			List<Component> componentList = componentService.findListByProductId(product.getId());
			model.addAttribute("componentList", componentList);
			//查询规格属性值
			List<Map<String,Object>> specNameValue = productService.findSpecNameAndValue(product.getId());
			model.addAttribute("specNameValue",specNameValue);
			return "/mobile/product/detail_spec";
		}

	}

    /**
     * 加载规格商品
     */
    @RequestMapping("/loadSpecProducts")
    public @ResponseBody
    Message loadSpecProducts(Long productId,HttpServletRequest request) {
        Product product = productService.getByPrimaryKey(productId);
        if(product==null){
            return Message.error("商品不存在");
        }
        List<HashMap<String,Object>> products = productService.findSpecProduct(productId);
        Message success = Message.success("加载成功");
        success.putDatas("products",products);
        return success;
    }

    /**
     * 加载指定商品信息
     */
    @RequestMapping("/loadSpecProduct")
    public @ResponseBody
    Message loadSpecProduct(Long productId,Long skuId,HttpServletRequest request) {

        Product product = productService.getByPrimaryKey(productId);
        if(product==null){
            return Message.error("商品不存在");
        }
        ProductStock ps = productStockService.getByPrimaryKey(skuId);
        Message success = Message.success("加载成功");
        success.putDatas("product",product);
        success.putDatas("productStock",ps);
        return success;
    }


    /**
     * 加载规格值
     */
	/*
	@RequestMapping("/loadSpecValue")
	public @ResponseBody
	Message loadSpec(Long productId,Long[] specValues, HttpServletRequest request) {

		Product product = productService.getByPrimaryKey(productId);
		if(product==null){
			return Message.error("商品不存在");
		}

		List<Map<String,Object>> specName = productService.findSpecNameAndValue(productId,specValues);

		return Message.success("加载成功");
	}
    */










	/**
	 * 商品列表
	 */
	@RequestMapping("/list")
	public String list(String keyword,Long productCategoryId,Long tagId,Model model, HttpServletRequest request, HttpServletResponse response){
		model.addAttribute("name",keyword);
		model.addAttribute("productCategoryId",productCategoryId);
		model.addAttribute("tagId",tagId);
		return "/mobile/product/list";
	}

	/**
	 * 商品列表数据
	 * //排序方式：
	 * //xinpinSort 按时间降序排列
	 * //priceAsc 按价格升序排列
	 * //priceDesc 按价格降序排列
	 * //salesAsc 按销量升序排列
	 * //salesDesc 按销量降序排列
	 * //zongheSort 按后台商品排序字段排序，时间降序排列
	 */
	@RequestMapping("/listData")
	public @ResponseBody
	Object listData(Long tagId,String name,Long productCategoryId,Integer pageNo,String sort, HttpServletRequest request, Model model) {

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productCategoryId",productCategoryId);
		params.put("pageable",new Pageable(pageNo,20));
		params.put("isMarketable",1);
		params.put("name",name);
		params.put("type",0);
		if(tagId!=null){
			List<Long> tagIds = new ArrayList<Long>();
			tagIds.add(tagId);
			params.put("tagIds",tagIds);
		}
		params.put("sort",sort);

		Page<Map<String, Object>> page = productService.findPageByParams(params);
		List<Map<String, Object>> list = page.getContent();
		List<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> data = new HashMap<String,Object>();
			Map<String, Object> map = list.get(i);
			Long id = (Long)map.get("id");
			String pname = (String)map.get("name");
			String showUrl = (String)map.get("showUrl");
			BigDecimal price = (BigDecimal)map.get("price");
			data.put("showUrl",showUrl);
			data.put("name",pname);
			data.put("id",id);
			ProductStock ps = productStockService.getByProductId(id);
			data.put("productStock",ps);
			data.put("price",ps!=null?ps.getPrice().setScale(2,BigDecimal.ROUND_HALF_UP):price);
			listData.add(data);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("listData", listData);
		data.put("pageNo", page.getPageNo());
		data.put("totalPages", page.getTotalPages());
		data.put("pageSize", page.getPageSize());
		data.put("total", page.getTotal());
		return data;
	}


    @RequestMapping("/getCoupon")
    public @ResponseBody
    Message getCoupon(Long couponTypeId,HttpServletRequest request) {

	    Member member = memberService.getCurrent(request);

	    CouponType couponType = couponTypeService.getByPrimaryKey(couponTypeId);
	    if(couponType==null){
	        return Message.error("该优惠券不存在");
        }

        if(couponType.getStock()==0){
	        return Message.error("该优惠券已领完");
        }
        if(couponType.getIsShow()==0){
			return Message.error("优惠券状态不正确");
		}
		if(couponType.getIsDeleted()==1){
			return Message.error("该优惠券不存在");
		}
        if(couponType.getValidDateType()==1){
	        if(new Date().after(couponType.getValidEndDate())){
	            return Message.error("优惠券已过期");
            }
        }
        Map<String,Object> params = new HashMap<String,Object>();
	    params.put("memberId",member.getId());
        params.put("couponTypeId",couponTypeId);
        Long count = couponService.getCountByParams(params);
        if(count>=couponType.getGetLimit()){
            return Message.error("您已经领取过了，每人限领"+couponType.getGetLimit()+"张");
        }
        couponTypeService.getCoupon(couponTypeId,member.getId());
        Message success = Message.success("领取成功");
        return success;
    }


	/**
	 * 企业专区
	 */
	@RequestMapping("/spec")
	public String spec(Model model, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> params = new HashMap<String, Object>();
		params.clear();
		params.put("isMarketable",1);
		params.put("type",1);//企业专区商品
		List<HashMap<String, Object>> list = productService.findListByParams(params);
		for(HashMap<String,Object> map : list){
			Long id = (Long)map.get("id");
			ProductStock ps = productStockService.getByProductId(id);
			map.put("productStock",ps);
		}
		model.addAttribute("list",list);
		return "/mobile/product/spec";
	}

}