package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ProductTag extends BaseEntity {

	/****/
	private Long tagId;

	/****/
	private Long productId;



	public void setTagId(Long tagId){
		this.tagId = tagId;
	}

	public Long getTagId(){
		return this.tagId;
	}

	public void setProductId(Long productId){
		this.productId = productId;
	}

	public Long getProductId(){
		return this.productId;
	}

}
