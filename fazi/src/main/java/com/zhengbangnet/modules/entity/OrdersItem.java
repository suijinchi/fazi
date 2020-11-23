package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class OrdersItem extends BaseEntity {

	/**产品名称**/
	private String name;

	private String subname;

	/**价格**/
	private java.math.BigDecimal price;

	/**数量**/
	private Integer quantity;

	/**编号**/
	private String sn;

	/**缩略图**/
	private String thumbnail;

	/**订单外键**/
	private Long ordersId;

	/**产品外键**/
	private Long productId;

	/**积分价格**/
	private Integer point;
	
	/**
	 * 商品规格
	 */
	private String productSpec;

	private Long productStockId;

	private String birthdayCard;

	public String getBirthdayCard() {
		return birthdayCard;
	}

	public void setBirthdayCard(String birthdayCard) {
		this.birthdayCard = birthdayCard;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}

	public Long getProductStockId() {
		return productStockId;
	}

	public void setProductStockId(Long productStockId) {
		this.productStockId = productStockId;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public void setPrice(java.math.BigDecimal price){
		this.price = price;
	}

	public java.math.BigDecimal getPrice(){
		return this.price;
	}

	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}

	public Integer getQuantity(){
		return this.quantity;
	}

	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn(){
		return this.sn;
	}

	public void setThumbnail(String thumbnail){
		this.thumbnail = thumbnail;
	}

	public String getThumbnail(){
		return this.thumbnail;
	}

	public void setOrdersId(Long ordersId){
		this.ordersId = ordersId;
	}

	public Long getOrdersId(){
		return this.ordersId;
	}

	public void setProductId(Long productId){
		this.productId = productId;
	}

	public Long getProductId(){
		return this.productId;
	}

}
