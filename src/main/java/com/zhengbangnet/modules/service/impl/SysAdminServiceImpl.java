package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.SysAdmin;
import com.zhengbangnet.modules.entity.SysAdminRole;
import com.zhengbangnet.modules.service.SysAdminService;
import com.zhengbangnet.modules.mapper.SysAdminMapper;
import com.zhengbangnet.modules.mapper.SysAdminRoleMapper;

@Service("sysAdminServiceImpl")
@Transactional(readOnly=false)
public class SysAdminServiceImpl extends BaseServiceImpl<SysAdmin,Long> implements SysAdminService{

	@Resource(name="sysAdminMapper")
	private SysAdminMapper sysAdminMapper;

	@Resource(name="sysAdminMapper")
	public void setBaseDao(BaseMapper<SysAdmin,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}
	
	@Resource(name="sysAdminRoleMapper")
	private SysAdminRoleMapper sysAdminRoleMapper;
	
	@Override
	public SysAdmin getByUsername(String username) {
		return sysAdminMapper.getByUsername(username);
	}
	
	@Override
	public SysAdmin getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Long id = (Long) request.getSession().getAttribute(SysAdmin.CURRENT_LOGIN_ADMIN);
			if (id != null) {
				return sysAdminMapper.getByPrimaryKey(id);
			}
		}
		return null;
	}
	
	@Override
	public SysAdmin getByMobile(String phone) {
		return sysAdminMapper.getByMobile(phone);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void save(SysAdmin admin, Long[] roleIds) {
		sysAdminMapper.insert(admin);
		if(roleIds!=null&&roleIds.length>0){
			for(Long rid:roleIds){
				SysAdminRole sar = new SysAdminRole();
				sar.setSysAdmin(admin.getId());
				sar.setSysRole(rid);
				sysAdminRoleMapper.insert(sar);
			}
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void update(SysAdmin admin, Long[] roleIds) {
		sysAdminMapper.updateByPrimaryKey(admin);
		sysAdminRoleMapper.deleteBySysAdminId(admin.getId());
		if(roleIds!=null&&roleIds.length>0){
			for(Long rid:roleIds){
				SysAdminRole sar = new SysAdminRole();
				sar.setSysAdmin(admin.getId());
				sar.setSysRole(rid);
				sysAdminRoleMapper.insert(sar);
			}
		}
	}

	@Override
	public void deleteBySysAdminId(Long id) {
		// TODO Auto-generated method stub
		sysAdminRoleMapper.deleteBySysAdminId(id);
	}
	
}