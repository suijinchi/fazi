package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class SpecName extends BaseEntity {

	/**中文名称**/
	private String name;



	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

}
