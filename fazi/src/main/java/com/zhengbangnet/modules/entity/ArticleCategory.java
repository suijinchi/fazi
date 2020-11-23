package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ArticleCategory extends BaseEntity {

	/**分类名称**/
	private String name;

	/****/
	private String treePath;

	/****/
	private Long parent;

	/****/
	private Integer orders;

	/****/
	private Integer grade;



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

	public void setParent(Long parent){
		this.parent = parent;
	}

	public Long getParent(){
		return this.parent;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

	public void setGrade(Integer grade){
		this.grade = grade;
	}

	public Integer getGrade(){
		return this.grade;
	}

}
