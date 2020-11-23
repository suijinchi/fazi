package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;

import java.math.BigDecimal;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class MemberRank extends BaseEntity {

	/**等级名称**/
	private String name;

	/**等级最小积分值**/
	private Integer lowAmount;

	/**等级最大积分值**/
	private Integer highAmount;

	/**是否默认 0否 1是**/
	private Integer isDefault;

	/**优惠比例**/
	private java.math.BigDecimal scale;

	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setLowAmount(Integer lowAmount){
		this.lowAmount = lowAmount;
	}

	public Integer getLowAmount(){
		return this.lowAmount;
	}

	public Integer getHighAmount() {
		return highAmount;
	}

	public void setHighAmount(Integer highAmount) {
		this.highAmount = highAmount;
	}

	public void setIsDefault(Integer isDefault){
		this.isDefault = isDefault;
	}

	public Integer getIsDefault(){
		return this.isDefault;
	}

}
