package com.zhengbangnet.modules.controller.mobile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author suijinchi
 * 评价管理
 */
@Controller("mobileAssessController")
@RequestMapping("/mobile/assess")
public class AssessController extends AdminBaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "assessServiceImpl")
	private AssessService assessService;
	
	@Resource(name = "ordersItemServiceImpl")
	private OrdersItemService ordersItemService;
	
	@Resource(name = "ordersServiceImpl")
	private OrdersService ordersService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;

	@Resource(name = "pointRecordServiceImpl")
	private PointRecordService pointRecordService;
	
	/**
	 * 获取商品评价
	 */
	@RequestMapping(value = "/loadAssess", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> loadAssess(Long productId, Pageable pageable, Integer pageNo) {

        Map<String, Object> params = new HashMap<>();
        pageable.setPageSize(10);
        pageable.setPageNo(pageNo);
        params.put("pageable", pageable);
        params.put("productId", productId);
        params.put("status", 1);
        Page<Map<String, Object>> page = assessService.findPageByParams(params);

        List<Map<String, Object>> listData = new ArrayList<>();
        if (page.getContent() != null && page.getContent().size() > 0) {
            for (int i = 0; i < page.getContent().size(); i ++) {
                Map<String, Object> map = new HashMap<>();
                map.put("assess", page.getContent().get(i));
                Date date = (Date)page.getContent().get(i).get("createDate");
                map.put("createTime", DateUtils.dateToString(date, "yyyy-MM-dd HH:mm"));
                List<String> imgList = new ArrayList<>();
                String imgs = (String) page.getContent().get(i).get("img");
                if (imgs != null && !imgs.equals("")) {
                    for (String s : imgs.split(",")) {
                        imgList.add(s);
                    }
                }
                map.put("imgList", imgList);
                List<String> tagList = new ArrayList<>();
                String tags = (String) page.getContent().get(i).get("tags");
                if (tags != null && !tags.equals("")) {
                    for (String t : tags.split(",")) {
                        tagList.add(t);
                    }
                }
                map.put("tagList", tagList);
                listData.add(map);
            }
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("listData", listData);
        data.put("pageNo", page.getPageNo());
        data.put("totalPages", page.getTotalPages());
        data.put("pageSize", page.getPageSize());
        data.put("total", page.getTotal());
		
		return data;
	}

	/**
	 * 跳转添加商品评价界面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Long orderId, Model model) {

		//查询订单中商品
		List<ProductStock> productStockList = new ArrayList<ProductStock>();
		List<OrdersItem> ordersItemList = ordersItemService.getByOrdersId(orderId);
		for (int i=0;i<ordersItemList.size();i++) {
			OrdersItem ordersItem = ordersItemList.get(i);
			ProductStock productStock = productStockService.getByPrimaryKey(ordersItem.getProductStockId());
			productStockList.add(productStock);
		}
		model.addAttribute("productStockList", productStockList);
		model.addAttribute("orderId", orderId);

		return "mobile/assess/add";
	}

	/**
	 * 添加商品评价
	 */
	@RequestMapping(value = "/addAssess", method = RequestMethod.POST)
	public @ResponseBody Message addAssess(Long orderId, String contentStr, String imgStr, String tags, Integer productStarsCounts, Integer serviceStarsCounts, Integer logisticsStarsCounts, HttpServletRequest request) {
		
		//從cookie裡面取出當前登錄用戶
		Member member = memberService.getCurrent(request);

		if (orderId == null) {
            return Message.error("订单不存在！");
        }
		
		JSONArray contentArray = JSONObject.parseArray(contentStr);		
		JSONArray imgArray = JSONObject.parseArray(imgStr);

		if (!tags.equals("")) {
		    tags = tags.substring(0, tags.length() - 1);
        }
		
		List<OrdersItem> ordersItemList = ordersItemService.getByOrdersId(orderId);
		Integer isGivePoint = 0;
		for (int i=0;i<ordersItemList.size();i++) {
			OrdersItem ordersItem = ordersItemList.get(i);
			Assess assess = new Assess();
			assess.setProductId(ordersItem.getProductId());
			assess.setProductStockId(ordersItem.getProductStockId());
			assess.setMemberId(member.getId());
			assess.setCreateDate(new Date());
			assess.setModifyDate(new Date());
            String img = "";
            Integer index = 0;
            for(int h=0;h<imgArray.size();h++){
                JSONObject obj = imgArray.getJSONObject(h);
                String psid = obj.getString("psid");
                if (psid.equals(String.valueOf(ordersItem.getProductStockId()))) {
                    img += obj.getString("val") + ",";
                    index += 1;
                }
            }
            if (index > 6) {
                return Message.error("单个商品最多6张评论图片");
            }
            if (!img.equals("")) {
                img = img.substring(0,img.length()-1);
                assess.setImg(img);
            } else {
                assess.setImg(null);
            }
			for(int j=0;j<contentArray.size();j++){
				JSONObject obj = contentArray.getJSONObject(j);
				String psid = obj.getString("psid");
				if (psid.equals(String.valueOf(ordersItem.getProductStockId()))) {
					String content = obj.getString("val");
					assess.setContent(content);
					//评论30字以上赠送积分
					if (content.length() >= 15) {
						isGivePoint = 1;
					}
				}
			}
			assess.setProductAssess(productStarsCounts);
			assess.setServiceAssess(serviceStarsCounts);
			assess.setLogisticsAssess(logisticsStarsCounts);
			assess.setTags(tags);
			assess.setStatus(0);
			assessService.insert(assess);		
		}
		
		Orders orders = ordersService.getByPrimaryKey(orderId);
		orders.setAssessStatus(1);
		ordersService.updateByPrimaryKeySelective(orders);

		if (isGivePoint == 1) {
			Integer point = SettingUtils.get().getAssessPoint();
			member.setHistoryPoint(member.getHistoryPoint()+point);
			member.setPoint(member.getPoint() + point);
			memberService.updateByPrimaryKeySelective(member);
			PointRecord pr = new PointRecord();
			pr.setCreateDate(new Date());
			pr.setModifyDate(new Date());
			pr.setMemberId(member.getId());
			pr.setMemo("评论15字获得积分");
			pr.setPoint(point);
			pr.setSurplusAmount(member.getPoint());
			pointRecordService.insert(pr);
		}
		
		return Message.success("评论成功");
	}
	
}
