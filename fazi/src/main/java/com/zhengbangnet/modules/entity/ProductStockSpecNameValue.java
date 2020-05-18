package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ProductStockSpecNameValue extends BaseEntity {

	/****/
	private Long productStockId;

	/****/
	private Long specNameId;

	/****/
	private Long specValueId;



	public void setProductStockId(Long productStockId){
		this.productStockId = productStockId;
	}

	public Long getProductStockId(){
		return this.productStockId;
	}

	public void setSpecNameId(Long specNameId){
		this.specNameId = specNameId;
	}

	public Long getSpecNameId(){
		return this.specNameId;
	}

	public void setSpecValueId(Long specValueId){
		this.specValueId = specValueId;
	}

	public Long getSpecValueId(){
		return this.specValueId;
	}

}
