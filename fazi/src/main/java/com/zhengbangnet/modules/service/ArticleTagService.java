package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.ArticleTag;
import com.zhengbangnet.modules.entity.ProductTag;

import java.util.List;

public interface ArticleTagService extends BaseService<ArticleTag,Long> {

    /**
     * 根据标签ID查询
     * @param tagId 标签ID
     * @return
     */
    List<ArticleTag> getByTagId(Long tagId);

    void save(Long[] tagIds, Long id);

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