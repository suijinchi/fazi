package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;

import com.zhengbangnet.modules.entity.ProductTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.ArticleTag;
import com.zhengbangnet.modules.service.ArticleTagService;
import com.zhengbangnet.modules.mapper.ArticleTagMapper;

import java.util.List;

@Service("articleTagServiceImpl")
@Transactional(readOnly=false)
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleTag,Long> implements ArticleTagService{

	@Resource(name="articleTagMapper")
	private ArticleTagMapper articleTagMapper;

	@Resource(name="articleTagMapper")
	public void setBaseDao(BaseMapper<ArticleTag,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public List<ArticleTag> getByTagId(Long tagId) {
		return articleTagMapper.getByTagId(tagId);
	}

	@Override
	public void save(Long[] tagIds, Long id) {
		for(int i=0; i<tagIds.length; i++){
			ArticleTag at = new ArticleTag();
			at.setArticleId(id);
			at.setTagId(tagIds[i]);
			articleTagMapper.insert(at);
		}
	}

	@Override
	public List<ArticleTag> getByArticleId(Long articleId) {
		return articleTagMapper.getByArticleId(articleId);
	}

	@Override
	public void deleteByArticleId(Long articleId) {
		articleTagMapper.deleteByArticleId(articleId);
	}
}