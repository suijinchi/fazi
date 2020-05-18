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
import com.zhengbangnet.modules.entity.Files;
import com.zhengbangnet.modules.service.FilesService;

@Controller("adminFilesController")
@RequestMapping("/admin/files")
public class FilesController extends AdminBaseController {

	@Resource(name = "filesServiceImpl")
	private FilesService filesService;

	@SysLog(module="",method="")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = filesService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/files/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		return "/admin/files/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String name,String absolute,String relative,String md5,String fileType,Long size,
	HttpServletRequest request, HttpSession session) {
		Files files = new Files();
		files.setCreateDate(new Date());
		files.setModifyDate(new Date());
		files.setName(name);
		files.setAbsolute(absolute);
		files.setRelative(relative);
		files.setMd5(md5);
		files.setFileType(null);
		files.setSize(size);
		filesService.insertSelective(files);
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
		Files files = filesService.getByPrimaryKey(id);
		model.addAttribute("files", files);
		return "/admin/files/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id,String name,String absolute,String relative,String md5,String fileType,Long size,
	Model model, HttpSession session, HttpServletRequest request) {
		Files files = filesService.getByPrimaryKey(id);
		if (files == null) {
			return Message.error("编辑信息不存在");
		}
		files.setModifyDate(new Date());
		files.setId(id);
		files.setName(name);
		files.setAbsolute(absolute);
		files.setRelative(relative);
		files.setMd5(md5);
		files.setFileType(null);
		files.setSize(size);
		filesService.updateByPrimaryKey(files);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			filesService.deleteByPrimaryKeys(ids);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}