package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
public class ProductStock extends BaseEntity {

	/**价格**/
	private java.math.BigDecimal price;

	/**销量**/
	private Integer soldOut;

	/**库存**/
	private Integer stock;

	/**商品图片**/
	private String imgUrl;

	/**尺寸 如 18cm*18cm**/
	private String size;

	/**餐具 如 含10套餐具**/
	private String tableware;

	/**分享人数如 适合1人分享**/
	private String shareNum;

	/**商品id**/
	private Long productId;

	/**编码**/
	private String sn;

	/**会员价**/
	private java.math.BigDecimal memberPrice;

	/**市场价**/
	private java.math.BigDecimal marketPrice;

	/**积分价格**/
	private Integer point;

	/**赠送积分**/
	private Integer givePoint;

	/**商品名称**/
	private String name;

	/**规格属性名称**/
	private String specName;

	/**
	 * 商品最多抵扣金额
	 */
	private Integer maxOffsetPoint;

	/**
	 * 0未禁用 1已禁用
	 */
	private Integer isDisabled;

	/**
	 * 是否删除 0未删除 1已删除
	 */
	private Integer isVoid;

	public Integer getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Integer getIsVoid() {
		return isVoid;
	}

	public void setIsVoid(Integer isVoid) {
		this.isVoid = isVoid;
	}

	public Integer getMaxOffsetPoint() {
		return maxOffsetPoint;
	}

	public void setMaxOffsetPoint(Integer maxOffsetPoint) {
		this.maxOffsetPoint = maxOffsetPoint;
	}

	public void setPrice(java.math.BigDecimal price){
		this.price = price;
	}

	public java.math.BigDecimal getPrice(){
		return this.price;
	}

	public void setSoldOut(Integer soldOut){
		this.soldOut = soldOut;
	}

	public Integer getSoldOut(){
		return this.soldOut;
	}

	public void setStock(Integer stock){
		this.stock = stock;
	}

	public Integer getStock(){
		return this.stock;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}

	public String getImgUrl(){
		return this.imgUrl;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return this.size;
	}

	public void setTableware(String tableware){
		this.tableware = tableware;
	}

	public String getTableware(){
		return this.tableware;
	}

	public void setShareNum(String shareNum){
		this.shareNum = shareNum;
	}

	public String getShareNum(){
		return this.shareNum;
	}

	public void setProductId(Long productId){
		this.productId = productId;
	}

	public Long getProductId(){
		return this.productId;
	}

	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn(){
		return this.sn;
	}

	public void setMemberPrice(java.math.BigDecimal memberPrice){
		this.memberPrice = memberPrice;
	}

	public java.math.BigDecimal getMemberPrice(){
		return this.memberPrice;
	}

	public void setMarketPrice(java.math.BigDecimal marketPrice){
		this.marketPrice = marketPrice;
	}

	public java.math.BigDecimal getMarketPrice(){
		return this.marketPrice;
	}

	public void setPoint(Integer point){
		this.point = point;
	}

	public Integer getPoint(){
		return this.point;
	}

	public void setGivePoint(Integer givePoint){
		this.givePoint = givePoint;
	}

	public Integer getGivePoint(){
		return this.givePoint;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setSpecName(String specName){
		this.specName = specName;
	}

	public String getSpecName(){
		return this.specName;
	}

}
