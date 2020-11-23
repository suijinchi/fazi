package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class SysMenu extends BaseEntity {

	/**菜单名称**/
	private String name;

	/**链接**/
	private String url;

	/**菜单级别**/
	private Integer grade;

	/****/
	private String treePath;

	/**是否显示**/
	private String isShow;

	/**排序**/
	private Integer orders;

	/**父ID**/
	private Long parentId;

	/**描述**/
	private String description;

	/**图标**/
	private String icon;

	/****/
	private String perms;



	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return this.url;
	}

	public void setGrade(Integer grade){
		this.grade = grade;
	}

	public Integer getGrade(){
		return this.grade;
	}

	public void setTreePath(String treePath){
		this.treePath = treePath;
	}

	public String getTreePath(){
		return this.treePath;
	}



	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

	public void setParentId(Long parentId){
		this.parentId = parentId;
	}

	public Long getParentId(){
		return this.parentId;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return this.icon;
	}

	public void setPerms(String perms){
		this.perms = perms;
	}

	public String getPerms(){
		return this.perms;
	}

}
