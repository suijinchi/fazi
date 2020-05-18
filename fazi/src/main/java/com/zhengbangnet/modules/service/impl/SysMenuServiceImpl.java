package com.zhengbangnet.modules.service.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.SysMenu;
import com.zhengbangnet.modules.service.SysMenuService;
import com.zhengbangnet.modules.mapper.SysMenuMapper;

@Service("sysMenuServiceImpl")
@Transactional(readOnly=false)
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu,Long> implements SysMenuService{

	@Resource(name="sysMenuMapper")
	private SysMenuMapper sysMenuMapper;

	@Resource(name="sysMenuMapper")
	public void setBaseDao(BaseMapper<SysMenu,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
	
	@Override
	public List<SysMenu> findBySysAdmin(Long sysAdminId) {
		return sysMenuMapper.findBySysAdmin(sysAdminId);
	}
	
	@Override
	public List<Map<String, Object>> findBySysRole(Long sysRoleId) {
		return sysMenuMapper.findBySysRole(sysRoleId);
	}
	
}