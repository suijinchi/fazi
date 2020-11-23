package com.zhengbangnet.modules.controller.mobile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.ArticleCategory;
import com.zhengbangnet.modules.service.ArticleCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.modules.entity.Article;
import com.zhengbangnet.modules.service.ArticleService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

@Controller("mobileArticleController")
@RequestMapping("/mobile/article")
public class ArticleController extends MobileBaseController{
	
	@Resource(name="articleServiceImpl")
	private ArticleService articelService;
	@Resource(name="articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	@RequestMapping(value = "/detail")
	public String detail(Long id,HttpServletRequest request, HttpServletResponse response, Model model) {
		Article article = articelService.getByPrimaryKey(id);
		model.addAttribute("article", article);
		return "/mobile/article/detail";
	}

	/**
	 * 商品列表
	 */
	@RequestMapping("/list")
	public String list(Model model,Long articleCategoryId,Long[] aid, HttpServletRequest request, HttpServletResponse response){

		model.addAttribute("articleCategoryId",articleCategoryId);
		ArticleCategory articleCategory = articleCategoryService.getByPrimaryKey(articleCategoryId);
		model.addAttribute("articleCategory",articleCategory);
		model.addAttribute("aid", aid);
		return "/mobile/article/list";
	}
	
	/**
	 *
	 */
	@RequestMapping("/listData")
	public @ResponseBody
	Object listData(Long articleCategoryId,Long[] aid,Integer pageNo,HttpServletRequest request, Model model) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pageable",new Pageable(pageNo,20));
		params.put("cid",articleCategoryId);
		params.put("isPublication",1);
        params.put("aids",aid);
		Page<Map<String, Object>> page = articelService.findPageByParams(params);
		List<Map<String, Object>> list = page.getContent();
		List<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> data = new HashMap<String,Object>();
			Map<String, Object> map = list.get(i);
			Long id = (Long)map.get("id");
			String title = (String)map.get("title");
			String showImageUrl = (String)map.get("showImageUrl");
			String subTitle = (String)map.get("subTitle");
			Date createDate = (Date)map.get("createDate");
			data.put("showImageUrl",showImageUrl);
			data.put("subTitle",subTitle);
			data.put("title",title);
			data.put("createDate", DateUtils.dateToString(createDate,DateUtils.patternA));
			data.put("id",id);
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

}


