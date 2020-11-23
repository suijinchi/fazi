package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.SysAdminRole;
import com.zhengbangnet.modules.service.SysAdminRoleService;
import com.zhengbangnet.modules.mapper.SysAdminRoleMapper;

@Service("sysAdminRoleServiceImpl")
@Transactional(readOnly=false)
public class SysAdminRoleServiceImpl extends BaseServiceImpl<SysAdminRole,Long> implements SysAdminRoleService{

	@Resource(name="sysAdminRoleMapper")
	private SysAdminRoleMapper sysAdminRoleMapper;

	@Resource(name="sysAdminRoleMapper")
	public void setBaseDao(BaseMapper<SysAdminRole,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}