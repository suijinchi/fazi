package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.SysAdmin;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * SysAdminMapper数据库操作接口类
 * 
 **/

@Repository("sysAdminMapper")
public interface SysAdminMapper extends BaseMapper<SysAdmin,Long>{

	public SysAdmin getByUsername(String username);
	
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	public SysAdmin getByMobile(@Param("phone")String phone);

}