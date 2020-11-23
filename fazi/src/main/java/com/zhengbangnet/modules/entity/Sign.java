package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Sign extends BaseEntity {

	/****/
	private Long memberId;



	public void setMemberId(Long memberId){
		this.memberId = memberId;
	}

	public Long getMemberId(){
		return this.memberId;
	}

}
