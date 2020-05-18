package com.zhengbangnet.common.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.service.BaseService;

@Transactional(readOnly = true)
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID>{
	
	/**
	 * http://blog.csdn.net/blueheart20/article/details/44654007/
	 * http://blog.csdn.net/blueheart20/article/details/44685161
	 * @Transactional(readOnly = true)放在类级别上等同于该类的每个方法都放上了@Transactional(readOnly = true)
	 * 
	 * 增删改设置注解
	 * @Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	 * 可简写为@Transactional(readOnly = false)
	 * Propagation.REQUIRED ：有事务就处于当前事务中，没事务就创建一个事务
	 * isolation=Isolation.DEFAULT：事务数据库的默认隔离级别
	 * readOnly=false：可写 针对 增删改操作
	 * 方法的@Transactional会覆盖类上面声明的事务
	 * https://www.cnblogs.com/tianyuchen/p/6678084.html
	 * Spring的事务管理默认是针对unchecked exception回滚，也就是默认对Error异常和RuntimeException异常以及其子类进行事务回滚，且必须对抛出异常，若使用try-catch对其异常捕获则不会进行回滚！（Error异常和RuntimeException异常抛出时不需要方法调用throws或try-catch语句）；
	 */
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private BaseMapper<T, ID> baseMapper;
	
/*	
	protected Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
    }
*/
	
	public void setBaseDao(BaseMapper<T, ID> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void insert(T entity) {
		baseMapper.insert(entity);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void insertSelective(T entity) {
		baseMapper.insertSelective(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void deleteByPrimaryKey(ID id) {
		baseMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void deleteByPrimaryKeys(ID[] ids) {
		for(ID id : ids){
			baseMapper.deleteByPrimaryKey(id);
		}
	}
	
	@Override
	public T getByPrimaryKey(ID id) {
		return baseMapper.getByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public int updateByPrimaryKeySelective(T entity) {
		return baseMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public int updateByPrimaryKey(T entity) {
		return baseMapper.updateByPrimaryKey(entity);
	}
	
	@Override
	public Page<Map<String, Object>> findPageByParams(Map<String, Object> params) {
		Pageable pageable = (Pageable)params.get("pageable");
		Long count = baseMapper.getCountByParams(params);
//		int pn = (int) Math.ceil((double)count/(double)pageable.getPageSize());
//    	if(pn<pageable.getPageNo()){
//    		pageable.setPageNo(pn);
//    	}
		List<Map<String, Object>> list = baseMapper.findPageByParams(params);
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(list,count, pageable);
		return page;
	}
	
	/**
	 * 查询所有记录
	 * @return
	 */
	@Override
	public List<T> findAll(){
		return baseMapper.findAll();
	}
	
	@Override
	public List<HashMap<String,Object>> findListByParams(Map<String, Object> params){
		return baseMapper.findListByParams(params);
	}
	
	@Override
	public Long getCountByParams(Map<String, Object> params) {
		return baseMapper.getCountByParams(params);
	}
}
