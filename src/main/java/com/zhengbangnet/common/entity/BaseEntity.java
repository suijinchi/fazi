package com.zhengbangnet.common.entity;

import java.util.Date;

public class BaseEntity extends SuperEntity{

	private static final long serialVersionUID = 8580028920441475889L;
	
	private Long id;
	
	/**
	 * 创建时间
	 */
	private Date createDate;//创建时间
	
	/**
	 * 修改时间
	 */
	private Date modifyDate;//修改时间
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
}
