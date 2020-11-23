package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.SysRoleMenu;
import org.springframework.stereotype.Repository;

/**
 * 
 * SysRoleMenuMapper数据库操作接口类
 * 
 **/

@Repository("sysRoleMenuMapper")
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu,Long>{

	Long deleteBySysRoleId(Long sysRoleId) ;

}