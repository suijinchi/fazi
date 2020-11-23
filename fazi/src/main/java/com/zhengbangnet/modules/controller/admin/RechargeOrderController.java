package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.excel.ExportExcel;
import com.zhengbangnet.modules.entity.Agent;
import com.zhengbangnet.modules.service.AgentService;
import com.zhengbangnet.modules.service.RechargeOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("adminRechargeOrderController")
@RequestMapping("/admin/recharge_order")
public class RechargeOrderController extends AdminBaseController {

	@Resource(name = "rechargeOrderServiceImpl")
	private RechargeOrderService rechargeOrderService;

	@Resource(name = "agentServiceImpl")
	private AgentService agentService;

	@SysLog(module="充值管理",method="充值明细")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, Long agentId,Integer type, String startDate, String endDate, String nickname, Model model) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status",1);
		params.put("type",type);
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		params.put("nickname",nickname);
		params.put("agentId",agentId);
		params.put("pageable",pageable);
		List<Agent> agentList = agentService.findAll();
		Page<Map<String, Object>> page = rechargeOrderService.findPageByParams(params);
		model.addAttribute("page",page);
		model.addAttribute("type",type);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("nickname",nickname);
		model.addAttribute("agentList",agentList);
		model.addAttribute("agentId",agentId);
		return "/admin/recharge_order/list";
	}

	@SysLog(module="会员管理",method="导出会员列表")
	@RequestMapping("/exportExcel")
	public void exportExcel(Long agentId,Integer type, String startDate, String endDate, String nickname,HttpServletResponse response, Model model){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status",1);
		params.put("type", type);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("nickname", nickname);
		params.put("agentId", agentId);
		params.put("pageable",new Pageable(1,10000));
		Page<Map<String, Object>> page = rechargeOrderService.findPageByParams(params);
		List<Map<String, Object>> ls = page.getContent();
		if(ls==null||ls.isEmpty())
			return;
		String[] header = {"ID","昵称","姓名","手机号","当前余额","余额消费","平台充值单号","微信充值单号","充值金额","赠送金额","充值时间","到账时间","充值类型","所属分销商"};
		ExportExcel exportXls = null;
		try {
			exportXls = new ExportExcel("会员充值统计表", header);
			for(Map m:ls){
				BigDecimal b = (BigDecimal) m.get("balance");
				BigDecimal hb = (BigDecimal) m.get("historyBalance");
				Row row = exportXls.addRow();
				exportXls.addCell(row, 0, m.get("id"));
				exportXls.addCell(row, 1, m.get("nickname"));
				exportXls.addCell(row, 2, m.get("name"));
				exportXls.addCell(row, 3, m.get("mobile"));
				exportXls.addCell(row, 4, b);
				exportXls.addCell(row, 5, hb.subtract(b));
				exportXls.addCell(row, 6, m.get("sn"));
				exportXls.addCell(row, 7, m.get("paySn"));
				exportXls.addCell(row, 8, m.get("amount"));
				exportXls.addCell(row, 9, m.get("giveAmount"));
				exportXls.addCell(row, 10, DateUtils.dateToString((Date)m.get("createDate"),"yyyy-MM-dd HH:mm:ss"));
				exportXls.addCell(row, 11, DateUtils.dateToString((Date)m.get("payDate"),"yyyy-MM-dd HH:mm:ss"));
				exportXls.addCell(row, 12, ((Integer)m.get("type"))==0?"平台充值":"分销商充值");
				String agentName = (String)m.get("agentName");
				exportXls.addCell(row, 13, agentName);
			}
			exportXls.write(response, "会员充值统计表.xlsx");
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(exportXls!=null)
				exportXls.dispose();
		}
	}

}