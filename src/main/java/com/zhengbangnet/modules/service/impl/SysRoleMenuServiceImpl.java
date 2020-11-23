package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.SysRoleMenu;
import com.zhengbangnet.modules.service.SysRoleMenuService;
import com.zhengbangnet.modules.mapper.SysRoleMenuMapper;

@Service("sysRoleMenuServiceImpl")
@Transactional(readOnly=false)
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu,Long> implements SysRoleMenuService{

	@Resource(name="sysRoleMenuMapper")
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Resource(name="sysRoleMenuMapper")
	public void setBaseDao(BaseMapper<SysRoleMenu,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
}