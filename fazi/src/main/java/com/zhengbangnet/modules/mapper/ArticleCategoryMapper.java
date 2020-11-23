package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ArticleCategory;
import org.springframework.stereotype.Repository;

/**
 * 
 * ArticleCategoryMapper数据库操作接口类
 * 
 **/

@Repository("articleCategoryMapper")
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory,Long>{



}