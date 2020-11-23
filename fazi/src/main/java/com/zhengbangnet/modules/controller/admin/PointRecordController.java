package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.PointRecord;
import com.zhengbangnet.modules.service.PointRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller("adminPointRecordController")
@RequestMapping("/admin/point_record")
public class PointRecordController extends AdminBaseController {

	@Resource(name = "pointRecordServiceImpl")
	private PointRecordService pointRecordService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = pointRecordService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/point_record/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/point_record/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
    Message save(Integer point, String memo, Long memberId,
                 HttpServletRequest request, HttpSession session) {
		PointRecord pointRecord = new PointRecord();
		pointRecord.setCreateDate(new Date());
		pointRecord.setModifyDate(new Date());
		pointRecord.setPoint(point);
		pointRecord.setMemo(memo);
		pointRecord.setMemberId(memberId);
		pointRecordService.insertSelective(pointRecord);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		PointRecord pointRecord = pointRecordService.getByPrimaryKey(id);
		model.addAttribute("pointRecord", pointRecord);
		return "/admin/point_record/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
    Message update(Long id, Integer point, String memo, Long memberId,
                   Model model, HttpSession session, HttpServletRequest request) {
		PointRecord pointRecord = pointRecordService.getByPrimaryKey(id);
		if (pointRecord == null) {
			return Message.error("编辑信息不存在");
		}
		pointRecord.setModifyDate(new Date());
		pointRecord.setId(id);
		pointRecord.setPoint(point);
		pointRecord.setMemo(memo);
		pointRecord.setMemberId(memberId);
		pointRecordService.updateByPrimaryKey(pointRecord);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
    Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			pointRecordService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}