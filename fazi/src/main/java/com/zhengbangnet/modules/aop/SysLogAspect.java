package com.zhengbangnet.modules.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.utils.HttpContextUtils;
import com.zhengbangnet.common.utils.WebUtils;
import com.zhengbangnet.modules.entity.SysAdmin;
import com.zhengbangnet.modules.entity.SysLogs;
import com.zhengbangnet.modules.service.SysAdminService;
import com.zhengbangnet.modules.service.SysLogsService;

/**
 * 系统日志，切面处理类
 * 
 */
@Aspect
@Component
public class SysLogAspect {

	@Resource(name = "sysLogsServiceImpl")
	private SysLogsService sysLogsService;

	@Resource(name = "sysAdminServiceImpl")
	private SysAdminService sysAdminService;

	@Pointcut("@annotation(com.zhengbangnet.common.annotation.SysLog)")
	public void controllerPointCut() {

	}

	@Before("controllerPointCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			SysLogs sysLogs = new SysLogs();
			SysLog syslog = method.getAnnotation(SysLog.class);
			if (syslog != null) {
				// 注解上的描述
				sysLogs.setOperation(syslog.method());
				sysLogs.setType(syslog.module());
			}
			// 请求的方法名
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = signature.getName();
			sysLogs.setMethod(className + "." + methodName + "()");
			String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
			StringBuilder sb = new StringBuilder();
			if (parameterNames != null && parameterNames.length > 0) {
				for (int i = 0; i < parameterNames.length; i++) {
					if (joinPoint.getArgs()[i] != null) {
						if (!(joinPoint.getArgs()[i] instanceof Object)) {
							String value = joinPoint.getArgs()[i].toString();
							if (value != null && !value.trim().equals("")) {
								sb.append(parameterNames[i] + ":" + value + "; ");
							}
						} else {

						}
					}
				}
			}
			sysLogs.setParams(sb.toString());
			// 获取request
			HttpServletRequest httpRequest = HttpContextUtils.getHttpServletRequest();
			// 设置IP地址
			sysLogs.setIp(WebUtils.getRemoteHost(httpRequest));
			// 用户名
			SysAdmin current = sysAdminService.getCurrent();
			sysLogs.setUsername(current.getUsername());
			sysLogs.setSysAdminId(current.getId());
			sysLogs.setCreateDate(new Date());
			sysLogs.setModifyDate(new Date());
			// 保存系统日志
			sysLogsService.insertSelective(sysLogs);
		} catch (Exception e) {
			
		}
		
	}

}
