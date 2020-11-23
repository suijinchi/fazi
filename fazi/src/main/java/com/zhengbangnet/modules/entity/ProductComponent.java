package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ProductComponent extends BaseEntity {

	/**商品外键**/
	private Long productId;

	/**成分外键**/
	private Long componentId;



	public void setProductId(Long productId){
		this.productId = productId;
	}

	public Long getProductId(){
		return this.productId;
	}

	public void setComponentId(Long componentId){
		this.componentId = componentId;
	}

	public Long getComponentId(){
		return this.componentId;
	}

}
