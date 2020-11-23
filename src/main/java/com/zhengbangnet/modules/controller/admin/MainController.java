/*
 * 
 * 
 * 
 */
package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.Orders;
import com.zhengbangnet.modules.entity.SysAdmin;
import com.zhengbangnet.modules.entity.SysMenu;
import com.zhengbangnet.modules.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 共用
 * 
 * 
 * 
 */
@Controller("adminMainController")
@RequestMapping("/admin/main")
public class MainController extends AdminBaseController implements ServletContextAware {
	
	@Resource(name="sysAdminServiceImpl")
	private SysAdminService sysAdminService;
	
	@Resource(name="sysRoleServiceImpl")
	private SysRoleService sysRoleService;
	
	@Resource(name="sysMenuServiceImpl")
	private SysMenuService sysMenuService;

	@Resource(name="ordersServiceImpl")
	private OrdersService ordersService;
	
	@Resource(name="memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name="productServiceImpl")
	private ProductService productService;
	
	/** servletContext */
	@SuppressWarnings("unused")
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	/**
	 * 框架主页
	 */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request,Model model) {
    	SysAdmin admin = sysAdminService.getCurrent();
    	List<SysMenu> sysMenuList = sysMenuService.findBySysAdmin(admin.getId());
    	model.addAttribute("sysMenuList",sysMenuList);
    	model.addAttribute("admin",admin);

        return "/admin/main/index";
    }
    
    /**
     * 首页
     */
    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    public String welcome(Model model,Pageable pageable) {
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			String startTime = DateUtils.dateToString(DateUtils.getMonthFirstDay(new Date()), "yyyy-MM-dd HH:mm:ss");
			String endTime = DateUtils.dateToString(DateUtils.getMonthEndDay(new Date()), "yyyy-MM-dd HH:mm:ss");

			//全部收入
			BigDecimal allAmount = ordersService.findAmountByParams(params);
			if(allAmount==null){
				allAmount = BigDecimal.ZERO;
			}
			model.addAttribute("allAmount", allAmount);//全部收入额
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			//本月收入额
			BigDecimal amount = ordersService.findAmountByParams(params);
			if(amount==null){
				amount = BigDecimal.ZERO;
			}
			model.addAttribute("amount", amount);

			//订单
			params.clear();
			model.addAttribute("orderAllCounts", ordersService.findOrdersByParams(params) == null ? 0 : ordersService.findOrdersByParams(params));//全部已支付订单数量
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			model.addAttribute("orderCounts", ordersService.findOrdersByParams(params) == null ? 0 : ordersService.findOrdersByParams(params));//本月已支付订单数量

			//商品数量
			params.clear();
			model.addAttribute("allProductCounts", productService.findCountsByParams(params) == null ? 0 : productService.findCountsByParams(params));//全部商品数量
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			model.addAttribute("productCounts", productService.findCountsByParams(params) == null ? 0 : productService.findCountsByParams(params));//本月新增商品数量

			//活跃用户
			params.clear();
			model.addAttribute("memberAllCounts", memberService.findCountsByParams(params) == null ? 0 : memberService.findCountsByParams(params));//全部会员数量
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			model.addAttribute("memberCounts", memberService.findCountsByParams(params) == null ? 0 : memberService.findCountsByParams(params));//本月新增会员数量

			//今日订单
			params.clear();
			params.put("orderBegDt",DateUtils.getStartDateDay(new Date()));
			params.put("orderEndDt",DateUtils.getEndDateDay(new Date()));
			params.put("payStatus",1);
			Long todayOrders = ordersService.getCountByParams(params);
			model.addAttribute("todayOrders",todayOrders);

			//今日自提订单
			params.clear();
			params.put("orderBegDt",DateUtils.getStartDateDay(new Date()));
			params.put("orderEndDt",DateUtils.getEndDateDay(new Date()));
			params.put("shippingMethod",0);
			params.put("payStatus",1);
			Long todayPickUpOrders = ordersService.getCountByParams(params);
			model.addAttribute("todayPickUpOrders",todayPickUpOrders);

			//今日维权订单
			params.clear();
			params.put("ordersStatus",4);
			params.put("payStatus",1);
			Long todayRefundOrders = ordersService.getCountByParams(params);
			model.addAttribute("todayRefundOrders",todayRefundOrders);

			//订单
			params.clear();
			params.put("ordersStatus",1);
			params.put("shippingStatus",0);
			params.put("payStatus",1);
			Long unshippingOrders = ordersService.getCountByParams(params);
			model.addAttribute("unshippingOrders", unshippingOrders);


			//商品销售量排行榜
			model.addAttribute("productSale", productService.findSaleSort());

			//当年每月收益额统计
			model.addAttribute("orderAmountForMonth", ordersService.findAmountForMonth());

			//当月每日新增会员统计
			model.addAttribute("memberCountForDay", memberService.findCountForDay());

			//当月每日订单量统计
			model.addAttribute("orderCountForDay", ordersService.findCountForDay());

			//当年每月订单量统计
			model.addAttribute("orderCountForMonth", ordersService.findCountForMonth());

			//获取最新已支付未发货的订单（不大于10条）
			List<Orders> orderList = ordersService.findNearestOrders();
			model.addAttribute("orderList", orderList);
			if (orderList != null) {
				model.addAttribute("orderListSize", orderList.size());
			} else {
				model.addAttribute("orderListSize", 0);
			}

			model.addAttribute("orderBegDt",DateUtils.dateToString(DateUtils.getStartDateDay(new Date()),"yyyy-MM-dd HH:mm:ss"));
			model.addAttribute("orderEndDt",DateUtils.dateToString(DateUtils.getEndDateDay(new Date()),"yyyy-MM-dd HH:mm:ss"));

			return "/admin/main/welcome";
		} catch (Exception ex) {
			logger.warn("welcome:",ex);
			return "error/404";
		}
    }
    
    /**
     * 编辑密码
     */
    @RequestMapping(value = "/editPassword", method = RequestMethod.GET)
	public String editPassword(Long id, Model model) {
		model.addAttribute("admin", sysAdminService.getCurrent());
		return "/admin/main/editPassword";
	}
    
    /**
     * 更新密码
     */
    @SysLog(module="更新密码",method="")
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String password,String oldpassword, Model model) {
		SysAdmin admin = sysAdminService.getByPrimaryKey(id);
		if (admin == null) {
			return Message.error("该用户不存在");
		}
		if (StringUtils.isBlank(oldpassword)) {
			return Message.error("请输入旧密码");
		}
		if (StringUtils.isBlank(password)) {
			return Message.error("密码不能为空");
		}
		if(!DigestUtils.md5Hex(oldpassword).equals(admin.getPassword())){
			return Message.error("原密码错误");
		}
		admin.setPassword(DigestUtils.md5Hex(password));
		sysAdminService.updateByPrimaryKey(admin);
		return Message.success("修改成功");
	}
	
}