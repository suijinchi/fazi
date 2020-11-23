package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ProductCategory extends BaseEntity {

	/**分类名称**/
	private String name;

	/**排序**/
	private Integer orders;

	private Integer isDisplay;

	private String indexShowUrl;

	public String getIndexShowUrl() {
		return indexShowUrl;
	}

	public void setIndexShowUrl(String indexShowUrl) {
		this.indexShowUrl = indexShowUrl;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

}
