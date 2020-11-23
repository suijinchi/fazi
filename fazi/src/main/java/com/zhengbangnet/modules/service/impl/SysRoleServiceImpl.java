package com.zhengbangnet.modules.service.impl;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.SysRole;
import com.zhengbangnet.modules.entity.SysRoleMenu;
import com.zhengbangnet.modules.service.SysRoleService;
import com.zhengbangnet.modules.mapper.SysAdminRoleMapper;
import com.zhengbangnet.modules.mapper.SysRoleMapper;
import com.zhengbangnet.modules.mapper.SysRoleMenuMapper;

@Service("sysRoleServiceImpl")
@Transactional(readOnly=false)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole,Long> implements SysRoleService{

	@Resource(name="sysRoleMapper")
	private SysRoleMapper sysRoleMapper;
	
	@Resource(name="sysRoleMenuMapper")
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Resource(name="sysAdminRoleMapper")
	private SysAdminRoleMapper sysAdminRoleMapper;
	
	@Resource(name="sysRoleMapper")
	public void setBaseDao(BaseMapper<SysRole,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
	
	@Override
	public List<SysRole> findBySysAdmin(Long sysAdminid) {
		return sysRoleMapper.findBySysAdmin(sysAdminid);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void save(String name, String description, Long[] menuIds) {
		SysRole role = new SysRole();
		role.setCreateDate(new Date());
		role.setModifyDate(null);
		role.setIsSystem("0");
		role.setName(name);
		role.setDescription(description);
		sysRoleMapper.insert(role);
		Long id = role.getId();
		if(menuIds!=null&&menuIds.length>0){
			for(Long mid:menuIds){
				SysRoleMenu srm = new SysRoleMenu();
				srm.setSysMenu(mid);
				srm.setSysRole(id);
				sysRoleMenuMapper.insert(srm);
			}
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void update(Long sysRoleId, String name, String description, Long[] menuIds) {
		SysRole sysRole = sysRoleMapper.getByPrimaryKey(sysRoleId);
		sysRole.setName(name);
		sysRole.setDescription(description);
		sysRole.setModifyDate(new Date());
		sysRoleMapper.updateByPrimaryKeySelective(sysRole);
		sysRoleMenuMapper.deleteBySysRoleId(sysRoleId);
		if(menuIds!=null&&menuIds.length>0){
			for(Long mid:menuIds){
				SysRoleMenu srm = new SysRoleMenu();
				srm.setSysMenu(mid);
				srm.setSysRole(sysRole.getId());
				sysRoleMenuMapper.insert(srm);
			}
		}
	}

	@Override
	public void deleteBySysRoleId(Long id) {
		// TODO Auto-generated method stub
		sysRoleMenuMapper.deleteBySysRoleId(id);
		sysAdminRoleMapper.deleteBySysRoleId(id);
	}
	
}