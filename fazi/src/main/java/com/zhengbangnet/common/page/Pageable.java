package com.zhengbangnet.common.page;

import java.io.Serializable;



/**
 * 分页信息
 * 
 */
public class Pageable implements Serializable {

	private static final long serialVersionUID = -5286584711455474233L;

	/** 默认页码 */
	private static final int DEFAULT_PAGE_NUMBER = 1;

	/** 默认每页记录数 */
	private static final int DEFAULT_PAGE_SIZE = 20;

	/** 最大每页记录数 */
	private static final int MAX_PAGE_SIZE = 10000;

	/** 页码 */
	private int pageNo = DEFAULT_PAGE_NUMBER;

	/** 每页记录数 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 初始化一个新创建的Pageable对象
	 */
	public Pageable() {
	}

	public Pageable(Integer pageNo) {
		if (pageNo != null && pageNo >= 1) {
			this.pageNo = pageNo;
		}
		this.pageSize = MAX_PAGE_SIZE;
	}
	
	/**
	 * 初始化一个新创建的Pageable对象
	 * 
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 */
	public Pageable(Integer pageNo, Integer pageSize) {
		if (pageNo != null && pageNo >= 1) {
			this.pageNo = pageNo;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNumber
	 *            页码
	 */
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			pageNo = DEFAULT_PAGE_NUMBER;
		}
		this.pageNo = pageNo;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	/**
	 * 设置excel导出最大页
	 * @param pageSize
	 */
	public void setExcelPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public Integer getFirstResult(){
		return (getPageNo()-1)*getPageSize();
	}

}