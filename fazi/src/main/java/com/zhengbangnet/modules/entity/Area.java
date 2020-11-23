package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Area extends BaseEntity {

	/**地区全称**/
	private String fullName;

	/**地区简称**/
	private String name;

	/****/
	private String treePath;

	/**父ID**/
	private Long parentId;

	/**排序**/
	private Integer orders;



	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return this.fullName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setTreePath(String treePath){
		this.treePath = treePath;
	}

	public String getTreePath(){
		return this.treePath;
	}

	public void setParentId(Long parentId){
		this.parentId = parentId;
	}

	public Long getParentId(){
		return this.parentId;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

}
