package com.zhengbangnet.modules.controller.admin;

import javax.annotation.Resource;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.ArticleTagService;
import com.zhengbangnet.modules.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.modules.service.ProductTagService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("adminTagController")
@RequestMapping("/admin/tag")
public class TagController extends AdminBaseController {

	@Resource(name = "articleTagServiceImpl")
	private ArticleTagService articleTagService;

	@Resource(name = "productTagServiceImpl")
	private ProductTagService productTagService;

	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	/**
	 * 列表 create by suijinchi on 2018-06-02
	 * @param pageable
	 * @param model
	 * @return
	 */
	@SysLog(module="商品标签管理",method="查询商品标签列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, Model model, Integer type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		params.put("type", type);
		Page<Map<String, Object>> page = tagService.findPageByParams(params);
		model.addAttribute("page", page);
		model.addAttribute("type", type);
		return "/admin/tag/list";
	}

	/**
	 * 添加页面 create by suijinchi on 2018-06-25
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "/admin/tag/add";
	}

	/**
	 * 保存 create by suijinchi on 2018-06-25
	 */
	@SysLog(module="商品标签管理",method="添加商品标签")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(String name, String memo, String type) {
		Tag tag = new Tag();
		tag.setCreateDate(new Date());
		tag.setModifyDate(new Date());
		tag.setName(name);
		tag.setMemo(memo);
		tag.setType(Integer.parseInt(type));
		tagService.insert(tag);
		return Message.success("添加成功");
	}

	/**
	 * 编辑页面 create by suijinchi on 2018-06-25
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		Tag tag = tagService.getByPrimaryKey(id);
		model.addAttribute("tag", tag);
		return "/admin/tag/edit";
	}

	/**
	 * 更新 create by suijinchi on 2018-06-25
	 */
	@SysLog(module="商品标签管理",method="更新商品标签")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id, String name, String memo, String type) {
		Tag tag = tagService.getByPrimaryKey(id);
		tag.setModifyDate(new Date());
		tag.setName(name);
		tag.setMemo(memo);
		tag.setType(Integer.parseInt(type));
		tagService.updateByPrimaryKey(tag);
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除) create by suijinchi on 2018-06-25
	 */
	@SysLog(module="商品标签管理",method="删除商品标签")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		for(int i=0; i<ids.length; i++){
			List<ProductTag> productTagList = productTagService.getByTagId(ids[i]);
			if (productTagList.size() > 0) {
				return Message.error("标签下面存在商品");
			}
		}
		for(int i=0; i<ids.length; i++){
			List<ArticleTag> articleTagList = articleTagService.getByTagId(ids[i]);
			if (articleTagList.size() > 0) {
				return Message.error("标签下面存在文章");
			}
		}
		if (ids != null && ids.length > 0) {
			tagService.deleteByPrimaryKeys(ids);
			for(int i=0; i<ids.length; i++){
				tagService.deleteByPrimaryKeys(ids);
			}
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}