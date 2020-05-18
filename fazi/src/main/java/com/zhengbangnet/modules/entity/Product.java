package com.zhengbangnet.modules.entity;

import com.zhengbangnet.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 * 
 */
public class Product extends BaseEntity {

	public static final Object lock = new Object();

	/**
	 * 产品名称
	 */
	private String name;

	/**
	 * 正方形展示图片
	 */
	private String showUrl;

	/**
	 * 产品价格
	 */
	@Deprecated
	private java.math.BigDecimal price;

	/**
	 * 会员价
	 */
	@Deprecated
	private java.math.BigDecimal memberPrice;

	/**
	 * 积分价格
	 */
	@Deprecated
	private Integer point;

	/**
	 * 赠送积分
	 */
	@Deprecated
	private Integer givePoint;

	/**
	 * 市场价
	 */
	@Deprecated
	private java.math.BigDecimal marketPrice;

	/**
	 * 商品介绍-特殊商品解读分析
	 */
	private String content;

	/**
	 * 简介
	 */
	private String introduce;

	/**
	 * 库存
	 */
	private Integer stock;

	/**
	 * 已售数量
	 */
	private Integer soldOut;

	/**
	 * 分类id
	 */
	private Long productCategoryId;

	/**
	 * 是否上架 0未上架 1已上架
	 */
	private Integer isMarketable;

	/**
	 * 商品排序
	 */
	private Integer orders;

	/**
	 * 是否置顶 0不置顶 1置顶
	 */
	private Integer isTop;

	/**
	 * 标签外键
	 */
	private Long tagId;

	/**
	 * 商品编号
	 */
	private String sn;

	/**
	 * 首页长方形展示图
	 */
	private String longShowUrl;
	
	/**
	 * 附图
	 */
	private String imageUrl;
	
	/**
	 * 商品备注
	 */
	private String memo;




	/**
	 * 0普通商品 1企业商品
	 */
	private Integer type;

	/**
	 * 商品副标题
	 */
	private String subname;

	/**
	 * 0无规格 1多规格
	 */
	private Integer spec;

	/**
	 * 甜度
	 */
	private Integer sweetness;

	/**
	 * 是否需要输入生日牌
	 */
	private Integer isBirthdayCard;

	private Date sellDate;

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public Integer getIsBirthdayCard() {
		return isBirthdayCard;
	}

	public void setIsBirthdayCard(Integer isBirthdayCard) {
		this.isBirthdayCard = isBirthdayCard;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}

	public Integer getSpec() {
		return spec;
	}

	public void setSpec(Integer spec) {
		this.spec = spec;
	}

	public Integer getSweetness() {
		return sweetness;
	}

	public void setSweetness(Integer sweetness) {
		this.sweetness = sweetness;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLongShowUrl() {
		return longShowUrl;
	}

	public void setLongShowUrl(String longShowUrl) {
		this.longShowUrl = longShowUrl;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setShowUrl(String showUrl){
		this.showUrl = showUrl;
	}

	public String getShowUrl(){
		return this.showUrl;
	}

	@Deprecated
	public void setPrice(java.math.BigDecimal price){
		this.price = price;
	}
	@Deprecated
	public java.math.BigDecimal getPrice(){
		return this.price;
	}
	@Deprecated
	public void setMemberPrice(java.math.BigDecimal memberPrice){
		this.memberPrice = memberPrice;
	}
	@Deprecated
	public java.math.BigDecimal getMemberPrice(){
		return this.memberPrice;
	}
	@Deprecated
	public void setPoint(Integer point){
		this.point = point;
	}
	@Deprecated
	public Integer getPoint(){
		return this.point;
	}
	@Deprecated
	public void setGivePoint(Integer givePoint){
		this.givePoint = givePoint;
	}
	@Deprecated
	public Integer getGivePoint(){
		return this.givePoint;
	}
	@Deprecated
	public void setMarketPrice(java.math.BigDecimal marketPrice){
		this.marketPrice = marketPrice;
	}
	@Deprecated
	public java.math.BigDecimal getMarketPrice(){
		return this.marketPrice;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setIntroduce(String introduce){
		this.introduce = introduce;
	}

	public String getIntroduce(){
		return this.introduce;
	}

	public void setStock(Integer stock){
		this.stock = stock;
	}

	public Integer getStock(){
		return this.stock;
	}

	public void setSoldOut(Integer soldOut){
		this.soldOut = soldOut;
	}

	public Integer getSoldOut(){
		return this.soldOut;
	}

	public void setProductCategoryId(Long productCategoryId){
		this.productCategoryId = productCategoryId;
	}

	public Long getProductCategoryId(){
		return this.productCategoryId;
	}

	public void setIsMarketable(Integer isMarketable){
		this.isMarketable = isMarketable;
	}

	public Integer getIsMarketable(){
		return this.isMarketable;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

	public void setIsTop(Integer isTop){
		this.isTop = isTop;
	}

	public Integer getIsTop(){
		return this.isTop;
	}

	public void setTagId(Long tagId){
		this.tagId = tagId;
	}

	public Long getTagId(){
		return this.tagId;
	}

	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn(){
		return this.sn;
	}

}
