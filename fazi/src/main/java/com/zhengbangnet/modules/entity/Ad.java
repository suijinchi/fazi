package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Ad extends BaseEntity {

	/**图片地址**/
	private String imgUrl;

	/**广告名称**/
	private String name;

	/**连接地址**/
	private String link;

	/**是否显示 0不显示 1显示**/
	private Integer isShow;

	/**广告位置外键**/
	private Long adLocationId;

	/**店铺id外键**/
	private Long shopId;

	/**排序**/
	private Integer orders;



	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}

	public String getImgUrl(){
		return this.imgUrl;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return this.link;
	}

	public void setIsShow(Integer isShow){
		this.isShow = isShow;
	}

	public Integer getIsShow(){
		return this.isShow;
	}

	public void setAdLocationId(Long adLocationId){
		this.adLocationId = adLocationId;
	}

	public Long getAdLocationId(){
		return this.adLocationId;
	}

	public void setShopId(Long shopId){
		this.shopId = shopId;
	}

	public Long getShopId(){
		return this.shopId;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

}
