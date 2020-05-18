package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class CartItem extends BaseEntity {

	/****/
	private java.util.Date modifeDate;

	/**购物车id**/
	private Long cartId;

	/**产品id**/
	private Long productId;

	/**商品字表id**/
	private Long productStockId;

	/**数量**/
	private Integer quantity;

	/**店铺id**/
	private Long shopId;

	/**商品规格（零度用）**/
	private String productSpec;

	/**生日牌**/
	private String birthdayCard;



	public void setModifeDate(java.util.Date modifeDate){
		this.modifeDate = modifeDate;
	}

	public java.util.Date getModifeDate(){
		return this.modifeDate;
	}

	public void setCartId(Long cartId){
		this.cartId = cartId;
	}

	public Long getCartId(){
		return this.cartId;
	}

	public void setProductId(Long productId){
		this.productId = productId;
	}

	public Long getProductId(){
		return this.productId;
	}

	public void setProductStockId(Long productStockId){
		this.productStockId = productStockId;
	}

	public Long getProductStockId(){
		return this.productStockId;
	}

	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}

	public Integer getQuantity(){
		return this.quantity;
	}

	public void setShopId(Long shopId){
		this.shopId = shopId;
	}

	public Long getShopId(){
		return this.shopId;
	}

	public void setProductSpec(String productSpec){
		this.productSpec = productSpec;
	}

	public String getProductSpec(){
		return this.productSpec;
	}

	public void setBirthdayCard(String birthdayCard){
		this.birthdayCard = birthdayCard;
	}

	public String getBirthdayCard(){
		return this.birthdayCard;
	}

}
