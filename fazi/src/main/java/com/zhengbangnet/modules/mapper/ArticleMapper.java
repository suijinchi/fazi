package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Article;
import org.springframework.stereotype.Repository;

/**
 * 
 * ArticleMapper数据库操作接口类
 * 
 **/

@Repository("articleMapper")
public interface ArticleMapper extends BaseMapper<Article,Long>{

}