package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ArticleTag extends BaseEntity {

	/****/
	private Long articleId;

	/****/
	private Long tagId;



	public void setArticleId(Long articleId){
		this.articleId = articleId;
	}

	public Long getArticleId(){
		return this.articleId;
	}

	public void setTagId(Long tagId){
		this.tagId = tagId;
	}

	public Long getTagId(){
		return this.tagId;
	}

}
