package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class SpecValue extends BaseEntity {

	/**规格名称外键**/
	private Long specNameId;

	/**规格值 如 1磅 2磅，甜 苦 辣**/
	private String value;



	public void setSpecNameId(Long specNameId){
		this.specNameId = specNameId;
	}

	public Long getSpecNameId(){
		return this.specNameId;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}

}
