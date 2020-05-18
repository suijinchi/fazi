/**
 * 收货人地址及其它相关信息控制类
 */
package com.zhengbangnet.modules.controller.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.utils.RegexUtils;
import com.zhengbangnet.modules.entity.Area;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.Receiver;
import com.zhengbangnet.modules.service.AreaService;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.ReceiverService;

/**
 * 类描述:收货人地址及其它相关信息控制类
 * 
 * @author: hawkbird
 * @date: 2018年4月4日
 *
 */
@Controller("mobileReceiverController")
@RequestMapping("/mobile/receiver")
public class ReceiverController extends AdminBaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	/**
	 * 设置默认值
	 */
	@RequestMapping("/isDefault")
	@ResponseBody
	public Message isDefault(HttpServletRequest request, Long id) {
		// 先取消
		// 会员ID
		long memberId = memberService.getCurrentId(request);
		receiverService.updateNoDefaultByMemberId(memberId);
		// 再更新
		Receiver receiver = new Receiver();
		receiver.setId(id);
		receiver.setIsDefault(1);
		receiverService.updateByPrimaryKeySelective(receiver);
		return Message.success("成功");
	}

	/**
	 * 购买商品确认界面地址列表
	 */
	@RequestMapping("/receiverList")
	public @ResponseBody List<Map<String, Object>> receiverList(HttpServletRequest request, Model model) {
		// 获取会员ID
		Long memberId = memberService.getCurrentId(request);
		List<Map<String, Object>> ls = receiverService.queryAddressByMemberId(memberId);
		return ls;
	}

	/**
	 * 购买商品确认界面地址选择
	 */
	@RequestMapping("/chooseReceiver")
	public @ResponseBody Receiver chooseReceiver(Long id, HttpServletRequest request, Model model) {
		Receiver receiver = receiverService.getByPrimaryKey(id);
		return receiver;
	}

	/**
	 * 添加地址
	 */
	@RequestMapping("/addReceiver")
	public @ResponseBody Message addReceiver(String orderer,String ordererNumber,String zipCode, Long areaId, String newAddress, String contactNumber, String consignee,
			HttpServletRequest request, Model model) {

		// 获取会员ID
		Long memberId = memberService.getCurrentId(request);

		/*if (contactNumber.length() != 11 || !RegexUtils.isINTEGER_NEGATIVE(contactNumber) || !contactNumber.substring(0,1).equals("1")) {
			return Message.error("手机号格式不正确");
		}*/
		
		Receiver receiver = new Receiver();
		receiver.setAddress(newAddress);
		receiver.setConsignee(consignee);
		receiver.setZipCode(zipCode);
		receiver.setContactNumber(contactNumber);
		receiver.setCreateDate(new Date());
		receiver.setModifyDate(new Date());
		receiver.setAreaId(areaId);
		receiver.setMemberId(memberId);
		receiver.setIsDefault(0);
		receiver.setOrderer(orderer);
		receiver.setOrdererNumber(ordererNumber);
		// 查询地区名称
		Area area = areaService.getByPrimaryKey(areaId);
		receiver.setAreaName(area.getFullName());
		receiverService.insert(receiver);
		
		return Message.success("添加成功");
	}

	/**
	 * 删除地址
	 */
	@RequestMapping("/deleteReceiver")
	public void deleteReceiver(Long id, HttpServletRequest request, Model model) {
		receiverService.deleteByPrimaryKey(id);
	}

	/**
	 * 修改地址
	 */
	@RequestMapping("/editReceiver")
	public @ResponseBody Map<String, Object> edit(Long id, HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Receiver receiver = receiverService.getByPrimaryKey(id);
		Area area = areaService.getByPrimaryKey(receiver.getAreaId());
		map.put("receiver", receiver);
		map.put("treePath", area.getTreePath());
		return map;
	}

	/**
	 * 更新地址
	 */
	@RequestMapping("/updateReceiver")
	public @ResponseBody Message updateReceiver(String orderer,String ordererNumber,Long editReceiverId, String zipCode, Long areaId, String newAddress,
			String contactNumber, String consignee, HttpServletRequest request, Model model) {

	/*	if (contactNumber.length() != 11 || !RegexUtils.isINTEGER_NEGATIVE(contactNumber) || !contactNumber.substring(0,1).equals("1")) {
			return Message.error("手机号格式不正确");
		}*/

		Receiver receiver = receiverService.getByPrimaryKey(editReceiverId);
		receiver.setAddress(newAddress);
		receiver.setConsignee(consignee);
		receiver.setZipCode(zipCode);
		receiver.setContactNumber(contactNumber);
		receiver.setModifyDate(new Date());
		receiver.setAreaId(areaId);
		receiver.setOrderer(orderer);
		receiver.setOrdererNumber(ordererNumber);
		// 查询地区名称
		Area area = areaService.getByPrimaryKey(areaId);
		if (area != null) {
			receiver.setAreaName(area.getFullName());
		}
		receiverService.updateByPrimaryKeySelective(receiver);
		
		return Message.success("修改成功");
	}

	/**
	 * 依据会员ID选收货人及其地址信息页面
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		// 获取会员ID
		long memberId = memberService.getCurrentId(request);
		// 查询收货人列表信息
		List<Map<String, Object>> ls = receiverService.queryAddressByMemberId(memberId);
		if (ls != null && ls.size() > 0) {
			model.addAttribute("receivers", ls);
		}
		//
		return "/mobile/receiver/list";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/edit1")
	public String edit1(String id, HttpServletRequest request, Model model) {
		//
		long receiverId = 0;
		try {
			receiverId = Long.valueOf(id).longValue();
		} catch (Exception ex) {
			receiverId = 0;
		}
		Receiver receiver = receiverService.getByPrimaryKey(receiverId);
		model.addAttribute("consignee", "");
		model.addAttribute("contactNumber", "");
		model.addAttribute("address", "");
		model.addAttribute("zipCode", "");
		model.addAttribute("receiverId", 0);
		model.addAttribute("areaId", 0);
		model.addAttribute("treePath", "");
		if (receiver != null) {
			model.addAttribute("consignee", receiver.getConsignee());
			model.addAttribute("contactNumber", receiver.getContactNumber());
			model.addAttribute("address", receiver.getAddress());
			model.addAttribute("zipCode", receiver.getZipCode());
			model.addAttribute("receiverId", receiver.getId());
			model.addAttribute("orderer", receiver.getOrderer());
			model.addAttribute("ordererNumber", receiver.getOrdererNumber());
			model.addAttribute("areaId", receiver.getAreaId());
			// 查询area treepath
			Area area = areaService.getByPrimaryKey(receiver.getAreaId());
			model.addAttribute("treePath", area.getTreePath());
		}
		return "mobile/receiver/edit";
	}

	/**
	 * 修改提交
	 */
	@RequestMapping("/editsubmit")
	@ResponseBody
	public Message editSubmit(long id, String consignee, String contactNumber, long areaId, String address,
			String zipCode,String orderer,String ordererNumber, HttpServletRequest request, Model model) {

		if (contactNumber.length() != 11 || !RegexUtils.isINTEGER_NEGATIVE(contactNumber) || !contactNumber.substring(0,1).equals("1")) {
			return Message.error("手机号格式不正确");
		}
		
		Receiver receiver = new Receiver();
		// 查询area
		Area area = areaService.getByPrimaryKey(areaId);
		receiver.setAreaName(area == null ? "" : (area.getFullName() == null ? "" : area.getFullName()));
		receiver.setId(id);
		receiver.setConsignee(
				consignee == null ? "" : (consignee.length() <= 20 ? consignee : consignee.substring(0, 20)));
		receiver.setContactNumber(contactNumber == null ? ""
				: (contactNumber.length() <= 20 ? contactNumber : contactNumber.substring(0, 20)));
		receiver.setAreaId(areaId);
		receiver.setOrderer(orderer);
		receiver.setOrdererNumber(ordererNumber);
		receiver.setAddress(address == null ? "" : (address.length() <= 200 ? address : address.substring(0, 200)));
		receiver.setZipCode(zipCode == null ? "" : (zipCode.length() <= 10 ? zipCode : zipCode.substring(0, 10)));
		receiverService.updateByPrimaryKeySelective(receiver);
		return Message.success("操作成功");
	}

	/**
	 * 删除
	 */
	@RequestMapping("/del")
	public String del(String id, HttpServletRequest request, Model model) {
		// 删除
		long receiverId = 0;
		try {
			receiverId = Long.valueOf(id).longValue();
		} catch (Exception ex) {
			receiverId = 0;
		}
		receiverService.deleteByPrimaryKey(receiverId);
		//
		return list(request, model);
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model) {

		return "/mobile/receiver/add";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/edit")
	public String edit(Long id, Model model) {

		Receiver receiver = receiverService.getByPrimaryKey(id);
		Area area = areaService.getByPrimaryKey(receiver.getAreaId());
		model.addAttribute("receiver", receiver);
		model.addAttribute("treePath", area.getTreePath());

		return "/mobile/receiver/edit";
	}

	/**
	 * 新增提交
	 */
	@RequestMapping("/addsubmit")
	@ResponseBody
	public Message addSubmit(String consignee, String contactNumber, long areaId, String address, String zipCode,
			HttpServletRequest request, Model model) {

		if (contactNumber.length() != 11 || !RegexUtils.isINTEGER_NEGATIVE(contactNumber) || !contactNumber.substring(0,1).equals("1")) {
			return Message.error("手机号格式不正确");
		}
		
		long memberId = memberService.getCurrentId(request);
		Receiver receiver = new Receiver();
		receiver.setConsignee(
				consignee == null ? "" : (consignee.length() <= 20 ? consignee : consignee.substring(0, 20)));
		receiver.setContactNumber(contactNumber == null ? ""
				: (contactNumber.length() <= 20 ? contactNumber : contactNumber.substring(0, 20)));
		receiver.setAreaId(areaId);
		receiver.setAddress(address == null ? "" : (address.length() <= 200 ? address : address.substring(0, 200)));
		receiver.setZipCode(zipCode == null ? "" : (zipCode.length() <= 10 ? zipCode : zipCode.substring(0, 10)));
		receiver.setMemberId(memberId);
		receiver.setCreateDate(new Date());
		receiver.setModifyDate(new Date());
		// 查询地区名称
		Area area = areaService.getByPrimaryKey(areaId);
		receiver.setAreaName(area == null ? "" : (area.getFullName() == null ? "" : area.getFullName()));
		receiver.setIsDefault(0);
		receiverService.insert(receiver);
		
		return Message.success("操作成功");
	}

	/**
	 * 选择地址
	 */
	@RequestMapping("/choose")
	public String chooseAddress(HttpServletRequest request, Model model) {
		// 皮肤密码
		String receiverId = request.getParameter("id");
		long id = 0;
		try {
			id = Long.valueOf(receiverId);
		} catch (Exception ex) {
			ex.printStackTrace();
			id = 0;
		}
		Receiver receiver = receiverService.getByPrimaryKey(id);
		// 返回领取页面
		// 收货人信息ID
		model.addAttribute("id", "");
		// 收货人
		model.addAttribute("receiver", "");
		// 联系电话
		model.addAttribute("mobile", "");
		// 地区名称
		model.addAttribute("areaname", "");
		// 地址
		model.addAttribute("address", "");
		// 邮编
		model.addAttribute("zipcode", "");
		if (receiver != null) {
			model.addAttribute("id", receiver.getId());
			// 收货人
			model.addAttribute("receiver", receiver.getConsignee());
			// 联系电话
			model.addAttribute("mobile", receiver.getContactNumber());
			// 地区名称
			model.addAttribute("areaname", receiver.getAreaName());
			// 地址
			model.addAttribute("address", receiver.getAddress());
			// 邮编
			model.addAttribute("zipcode", receiver.getZipCode());
		}
		return "/mobile/skin_secret/onetouchcustomization";
	}

	/**
	 * 设置默认值
	 */
	@RequestMapping("/default")
	@ResponseBody
	public Map<String, Object> setDefaultAddress(HttpServletRequest request, long id) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("ret", 0);
		// 先取消
		// 会员ID
		long memberId = memberService.getCurrentId(request);
		receiverService.updateNoDefaultByMemberId(memberId);
		// 再更新
		Receiver receiver = new Receiver();
		receiver.setId(id);
		receiver.setIsDefault(1);
		receiverService.updateByPrimaryKeySelective(receiver);
		ret.put("ret", 1);
		//
		return ret;
	}
}
