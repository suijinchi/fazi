package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class PickUpTime extends BaseEntity {

	/**自提时间**/
	private String time;

	/**排序**/
	private Integer orders;

	/***
	 * 自提截止时间
	 */
	private String endTime;


	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return this.time;
	}

	public void setOrders(Integer orders){
		this.orders = orders;
	}

	public Integer getOrders(){
		return this.orders;
	}

}
