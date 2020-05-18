package com.zhengbangnet.modules.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.zhengbangnet.modules.entity.Payment;
import com.zhengbangnet.modules.service.PaymentService;

@Controller("adminPaymentController")
@RequestMapping("/admin/payment")
public class PaymentController extends AdminBaseController {

	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = paymentService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/payment/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/payment/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(java.math.BigDecimal amount,String sn,String paySn,java.util.Date payDate,Long memberId,String type,String payId,Integer status,
	HttpServletRequest request, HttpSession session) {
		Payment payment = new Payment();
		payment.setCreateDate(new Date());
		payment.setModifyDate(new Date());
		payment.setAmount(amount);
		payment.setSn(sn);
		payment.setPaySn(paySn);
		payment.setPayDate(payDate);
		payment.setMemberId(memberId);
		payment.setType(type);
		payment.setPayId(payId);
		payment.setStatus(status);
		paymentService.insertSelective(payment);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		Payment payment = paymentService.getByPrimaryKey(id);
		model.addAttribute("payment", payment);
		return "/admin/payment/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,java.math.BigDecimal amount,String sn,String paySn,java.util.Date payDate,Long memberId,String type,String payId,Integer status,
	Model model, HttpSession session, HttpServletRequest request) {
		Payment payment = paymentService.getByPrimaryKey(id);
		if (payment == null) {
			return Message.error("编辑信息不存在");
		}
		payment.setModifyDate(new Date());
		payment.setId(id);
		payment.setAmount(amount);
		payment.setSn(sn);
		payment.setPaySn(paySn);
		payment.setPayDate(payDate);
		payment.setMemberId(memberId);
		payment.setType(type);
		payment.setPayId(payId);
		payment.setStatus(status);
		paymentService.updateByPrimaryKey(payment);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			paymentService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}