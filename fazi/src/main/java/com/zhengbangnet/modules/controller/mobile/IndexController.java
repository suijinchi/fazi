package com.zhengbangnet.modules.controller.mobile;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.ProductStock;
import com.zhengbangnet.modules.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.weixin4j.Weixin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("mobileIndexController")
@RequestMapping("/mobile")
public class IndexController extends AdminBaseController {

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "productCategoryServiceImpl")
    private ProductCategoryService productCategoryService;

    @Resource(name = "pointRecordServiceImpl")
    private PointRecordService pointRecordService;

    @Resource(name = "adServiceImpl")
    private AdService adService;

    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    @Resource(name = "productStockServiceImpl")
    private ProductStockService productStockService;

    @Resource(name = "weixin")
    private Weixin weixin;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {

        Member member = memberService.getCurrent(request);
        model.addAttribute("member", member);

        //首页banner
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("adLocationId",1);
        params.put("isShow",1);
        List<HashMap<String, Object>> ads = adService.findListByParams(params);
        model.addAttribute("ads",ads);

        //法滋头条
        params.clear();
        params.put("isPublication",1);
        params.put("articleCategoryId",1);
        List<HashMap<String, Object>> headline = articleService.findListByParams(params);
        model.addAttribute("headline",headline);

        //新品
        List<Long> tagIds = new ArrayList<Long>();
        tagIds.add(1L);//新品
        params.clear();
        params.put("tagIds",tagIds);
        params.put("isMarketable",1);
        params.put("type",0);
        params.put("isDisabled",0);
        params.put("isVoid",0);
        List<HashMap<String, Object>> xinpin = productService.findListByParams(params);
        for(HashMap<String,Object> map : xinpin){
            Long id = (Long)map.get("id");
            ProductStock ps = productStockService.getByProductId(id);
            map.put("productStock",ps);
        }

        model.addAttribute("xinpin",xinpin);
/*
        //全部商品
        params.clear();
        params.put("isMarketable",1);
        params.put("type",0);
        params.put("isDisabled",0);
        params.put("isVoid",0);
        List<HashMap<String, Object>> allProduct = productService.findListByParams(params);
        for(HashMap<String,Object> map : allProduct){
            Long id = (Long)map.get("id");
            ProductStock ps = productStockService.getByProductId(id);
            map.put("productStock",ps);
        }
        model.addAttribute("allProduct",allProduct);
        */

        List<HashMap<String, Object>> productCategoryList = productCategoryService.findListByParams(params);
        for(Map<String,Object> pc:productCategoryList){
            params.clear();
            params.put("productCategoryId",pc.get("id"));
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
            pc.put("productList",tsList);
        }
        model.addAttribute("productCategoryList",productCategoryList);

        //法滋头条
        params.clear();
        params.put("isPublication",1);
        params.put("articleCategoryId",2);
        params.put("count",5);
        List<HashMap<String, Object>> beautifulArticle = articleService.findListByParams(params);
        model.addAttribute("beautifulArticle",beautifulArticle);

        model.addAttribute("setting", SettingUtils.get());

        return "/mobile/index/index";
    }


    
}
