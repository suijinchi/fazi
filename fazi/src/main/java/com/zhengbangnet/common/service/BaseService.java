package com.zhengbangnet.common.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.page.Page;

public interface BaseService<T, ID extends Serializable> {
	
	 /**  
     * 插入一个实体  
     */  
    void insert(T entity);
    
    void insertSelective(T entity);
      
    /**  
     * 根据实体主键删除一个实体  
     * @param id  
     */  
    void deleteByPrimaryKey(ID id);  
    
    void deleteByPrimaryKeys(ID[] ids);
    
    /**
     * 选择更新
     * @param entity
     * @return
     */
    int updateByPrimaryKeySelective(T entity);

    /**
     * 根据主键更新所有
     * @param entity
     * @return
     */
    int updateByPrimaryKey(T entity);

	/**  
	 * 根据主键获取一个实体  
	 * @param primaryKey  
	 * @return  
	 */  
	T getByPrimaryKey(ID id);
	
	/**
	 * 根据条件查询
	 * @param params
	 * @return
	 */
	Page<Map<String, Object>> findPageByParams(Map<String, Object> params);
	
	/**
	 * 查询所有记录
	 * @return
	 */
	List<T> findAll();
	
	List<HashMap<String,Object>> findListByParams(Map<String, Object> params);
	
	/**
	 * 获取数量
	 * @param params
	 */
	Long getCountByParams(Map<String, Object> params);
}
