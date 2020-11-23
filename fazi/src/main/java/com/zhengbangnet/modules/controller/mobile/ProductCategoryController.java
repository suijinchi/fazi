package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.RegexUtils;
import com.zhengbangnet.modules.entity.Area;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.ProductStock;
import com.zhengbangnet.modules.entity.Receiver;
import com.zhengbangnet.modules.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller("mobileProductCategoryController")
@RequestMapping("/mobile/product_category")
public class ProductCategoryController extends MobileBaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	
	@Resource(name = "productTagServiceImpl")
	private ProductTagService productTagService;

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

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

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;

	/**
	 * 商品详情
	 */
	@RequestMapping("/list")
	public String productDetail(Model model, HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("isDisplay",1);
		List<HashMap<String, Object>> productCategoryList = productCategoryService.findListByParams(params);
		model.addAttribute("productCategoryList",productCategoryList);
		return "/mobile/product_category/list";
	}

	@RequestMapping("/listData")
	public @ResponseBody
	Message listData(Long productCategoryId,HttpServletRequest request, Model model) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productCategoryId",productCategoryId);
		params.put("isMarketable",1);
		params.put("type",0);
		List<HashMap<String, Object>> list = productService.findListByParams(params);
		List<HashMap<String, Object>> tsList = new ArrayList<HashMap<String, Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> data = new HashMap<String,Object>();
			HashMap<String, Object> map = list.get(i);
			Long id = (Long)map.get("id");
			String name = (String)map.get("name");
			String showUrl = (String)map.get("showUrl");
			data.put("showUrl",showUrl);
			data.put("name",name);
			data.put("id",id);
			ProductStock ps = productStockService.getByProductId(id);
			data.put("productStock",ps);
			tsList.add(data);
		}

		Message success = Message.success("查询成功");
		success.putDatas("list",tsList);
		return success;
	}




}