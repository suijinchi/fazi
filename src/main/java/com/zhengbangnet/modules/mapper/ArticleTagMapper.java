package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.ArticleTag;
import com.zhengbangnet.modules.entity.ProductTag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * ArticleTagMapper数据库操作接口类
 * 
 **/

@Repository("articleTagMapper")
public interface ArticleTagMapper extends BaseMapper<ArticleTag,Long>{

    /**
     * 根据标签ID查询
     * @param tagId
     * @return
     */
    List<ArticleTag> getByTagId(Long tagId);

    /**
     * 通过tagId查询
     * @param articleId
     * @return
     */
    List<ArticleTag> getByArticleId(Long articleId);

    /**
     * 通过tagId删除
     * @param articleId
     */
    void deleteByArticleId(Long articleId);
}