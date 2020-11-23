package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ProductImage extends BaseEntity {

	/**产品外键**/
	private Long productId;

	/**排序**/
	private Integer orders;

	/**图片地址**/
	private String imgUrl;



	public void setProductId(Long productId){
		this.productId = productId;
	}

	public Long getProductId(){
		return this.productId;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}

	public String getImgUrl(){
		return this.imgUrl;
	}

}
