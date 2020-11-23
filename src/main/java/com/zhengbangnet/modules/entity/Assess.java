package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Assess extends BaseEntity {

	/****/
	private Long productId;

	/****/
	private Long memberId;

	/****/
	private String content;

	/****/
	private String img;

	/**商品评价**/
	private Integer productAssess;

	/**服务评价**/
	private Integer serviceAssess;

	/**物流评价**/
	private Integer logisticsAssess;

	/**标签**/
	private String tags;

	/**审核状态（0未审核 1已通过 2不通过）**/
	private Integer status;

	private Long productStockId;

	public Long getProductStockId() {
		return productStockId;
	}

	public void setProductStockId(Long productStockId) {
		this.productStockId = productStockId;
	}

	public void setProductId(Long productId){
		this.productId = productId;
	}

	public Long getProductId(){
		return this.productId;
	}

	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return this.img;
	}

	public void setProductAssess(Integer productAssess){
		this.productAssess = productAssess;
	}

	public Integer getProductAssess(){
		return this.productAssess;
	}

	public void setServiceAssess(Integer serviceAssess){
		this.serviceAssess = serviceAssess;
	}

	public Integer getServiceAssess(){
		return this.serviceAssess;
	}

	public void setLogisticsAssess(Integer logisticsAssess){
		this.logisticsAssess = logisticsAssess;
	}

	public Integer getLogisticsAssess(){
		return this.logisticsAssess;
	}

	public void setTags(String tags){
		this.tags = tags;
	}

	public String getTags(){
		return this.tags;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

}
