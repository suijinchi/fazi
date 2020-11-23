package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ArticleCategory;
import com.zhengbangnet.modules.service.ArticleCategoryService;
import com.zhengbangnet.modules.mapper.ArticleCategoryMapper;

@Service("articleCategoryServiceImpl")
@Transactional(readOnly=true)
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory,Long> implements ArticleCategoryService{

	@Resource(name="articleCategoryMapper")
	private ArticleCategoryMapper articleCategoryMapper;

	@Resource(name="articleCategoryMapper")
	public void setBaseDao(BaseMapper<ArticleCategory,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}