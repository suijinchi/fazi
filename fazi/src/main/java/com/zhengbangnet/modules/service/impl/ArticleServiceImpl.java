package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.Article;
import com.zhengbangnet.modules.service.ArticleService;
import com.zhengbangnet.modules.mapper.ArticleMapper;

@Service("articleServiceImpl")
@Transactional(readOnly=true)
public class ArticleServiceImpl extends BaseServiceImpl<Article,Long> implements ArticleService{

	@Resource(name="articleMapper")
	private ArticleMapper articleMapper;

	@Resource(name="articleMapper")
	public void setBaseDao(BaseMapper<Article,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}