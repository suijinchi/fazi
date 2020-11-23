package com.zhengbangnet.common.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T,ID extends Serializable> {
	
	/**
	 * 查询多条记录
	 */
	List<HashMap<String, Object>> superSelectList(String value);
	
	/**
	 * 查询一条记录
	 */
	HashMap<String, Object> superSelect(String value);
	
	 /**  
     * 插入一个实体  
     */  
	Long insert(T entity) ;  
	
	/**
	 * 动态插入
	 */
	Long insertSelective(T entity) ;  
      
    /**  
     * 根据实体主键删除一个实体  
     * @param id  
     */  
    void deleteByPrimaryKey(ID id);  
      
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
	 * 查询所有记录
	 * @return
	 */
	List<T> findAll();
    
	/**
	 * 根据条件查询数量
	 * @param params
	 * @return
	 */
	public Long getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据条件分页查询数据
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findPageByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询
	 * @param params
	 * @return
	 */
	List<HashMap<String,Object>> findListByParams(Map<String, Object> params);
	
}
