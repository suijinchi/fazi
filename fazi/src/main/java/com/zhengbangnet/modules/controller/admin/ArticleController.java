package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.Article;
import com.zhengbangnet.modules.entity.ArticleCategory;
import com.zhengbangnet.modules.entity.ArticleTag;
import com.zhengbangnet.modules.entity.Tag;
import com.zhengbangnet.modules.service.ArticleCategoryService;
import com.zhengbangnet.modules.service.ArticleService;
import com.zhengbangnet.modules.service.ArticleTagService;
import com.zhengbangnet.modules.service.TagService;
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
import java.util.List;
import java.util.Map;

@Controller("adminArticleController")
@RequestMapping("/admin/article")
public class ArticleController extends AdminBaseController {

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;

    @Resource(name = "articleCategoryServiceImpl")
    private ArticleCategoryService articleCategoryService;

    @Resource(name = "tagServiceImpl")
    private TagService tagService;

    @Resource(name = "articleTagServiceImpl")
    private ArticleTagService articleTagService;

	@SysLog(module="文章设置",method="文章列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, HttpServletRequest request, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page< Map<String, Object>> page = articleService.findPageByParams(params);
		model.addAttribute("page", page);
		return "/admin/article/list";
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request, HttpSession session) {
        List<Tag> tagList = tagService.findByType(1);
        model.addAttribute("tagList", tagList);
        List<ArticleCategory> articleCategoryList = articleCategoryService.findAll();
        model.addAttribute("articleCategoryList", articleCategoryList);
		return "/admin/article/add";
	}

	/**
	 * 保存
	 */
	@SysLog(module="文章设置",method="新增文章")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(Long[] tagIds, String content, Integer isPublication, Integer isTop,String subTitle, String title,Long articleCategoryId, String showImageUrl, Integer sort, HttpServletRequest request, HttpSession session) {
		Article article = new Article();
		article.setCreateDate(new Date());
		article.setModifyDate(new Date());
		article.setContent(content);
		article.setIsPublication(isPublication);
		article.setIsTop(isTop);
		article.setTitle(title);
		article.setSubTitle(subTitle);
		article.setArticleCategoryId(articleCategoryId);
		article.setShowImageUrl(showImageUrl);
		article.setSort(sort);
		articleService.insertSelective(article);
        Long id = article.getId();
        if(tagIds!=null&&tagIds.length>0){
            articleTagService.save(tagIds,id);
        }
		return Message.success("添加成功");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model, HttpServletRequest request, HttpSession session) {
        List<Tag> tagList = tagService.findByType(1);
        model.addAttribute("tagList", tagList);
        List<ArticleCategory> articleCategoryList = articleCategoryService.findAll();
        model.addAttribute("articleCategoryList", articleCategoryList);
        List<ArticleTag> articleTagList = articleTagService.getByArticleId(id);
        model.addAttribute("articleTagList", articleTagList);
		Article article = articleService.getByPrimaryKey(id);
		model.addAttribute("article", article);
		return "/admin/article/edit";
	}

	/**
	 * 更新编辑
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Long id, Long[] tagIds, String content, Integer isPublication, Integer isTop,String subTitle, String title,Long articleCategoryId, String showImageUrl, Integer sort, HttpServletRequest request, HttpSession session) {
		Article article = articleService.getByPrimaryKey(id);
		if (article == null) {
			return Message.error("编辑信息不存在");
		}
        article.setModifyDate(new Date());
        article.setContent(content);
        article.setIsPublication(isPublication);
        article.setIsTop(isTop);
        article.setTitle(title);
        article.setSubTitle(subTitle);
        article.setArticleCategoryId(articleCategoryId);
        article.setShowImageUrl(showImageUrl);
        article.setSort(sort);
		articleService.updateByPrimaryKeySelective(article);
        articleTagService.deleteByArticleId(id);
        if(tagIds!=null&&tagIds.length>0){
            articleTagService.save(tagIds,id);
        }
		return Message.success("修改成功");
	}

	/**
	 * 删除(含批量删除)
	 */
	@SysLog(module="",method="")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids, Model model, HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			articleService.deleteByPrimaryKeys(ids);
            for(int i=0; i<ids.length; i++){
                articleTagService.deleteByArticleId(ids[i]);
            }
            return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

}