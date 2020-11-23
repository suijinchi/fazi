package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Assess;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * AssessMapper数据库操作接口类
 * 
 **/

@Repository("assessMapper")
public interface AssessMapper extends BaseMapper<Assess,Long>{

	/**
	 * 根据商品ID查询评价
	 * @author suijinchi
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getByProductId(Long id);



}