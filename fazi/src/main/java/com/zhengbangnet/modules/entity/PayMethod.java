package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class PayMethod extends BaseEntity {

	/****/
	private String name;

	/****/
	private String type;

	/****/
	private String icon;

	/****/
	private java.math.BigDecimal taxRatio;



	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return this.icon;
	}

	public void setTaxRatio(java.math.BigDecimal taxRatio){
		this.taxRatio = taxRatio;
	}

	public java.math.BigDecimal getTaxRatio(){
		return this.taxRatio;
	}

}
