package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class AdLocation extends BaseEntity {

	/**广告位置名称**/
	private String name;

	/**宽**/
	private Integer width;

	/**高**/
	private Integer height;



	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setWidth(Integer width){
		this.width = width;
	}

	public Integer getWidth(){
		return this.width;
	}

	public void setHeight(Integer height){
		this.height = height;
	}

	public Integer getHeight(){
		return this.height;
	}

}
