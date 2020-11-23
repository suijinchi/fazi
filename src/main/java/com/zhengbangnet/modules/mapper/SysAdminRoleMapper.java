package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.SysAdminRole;
import org.springframework.stereotype.Repository;

/**
 * 
 * SysAdminRoleMapper数据库操作接口类
 * 
 **/

@Repository("sysAdminRoleMapper")
public interface SysAdminRoleMapper extends BaseMapper<SysAdminRole,Long>{

	 /**  
     * 插入一个实体  
     */  
	Long insert(SysAdminRole entity) ;  
	
	Long deleteBySysAdminId(Long sysAdminId) ;

	void deleteBySysRoleId(Long id);

}