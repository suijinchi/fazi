package com.zhengbangnet.common.utils;

public class StringUtils {
	
	/**
	 * 从 带后缀名的文件名称 获取文件名称
	 * @param name 带后缀名的文件名称
	 * @return 文件名称
	 */
	public static String getFileName(String name,String separator){
		if(name==null||"".equals(name.trim())){
			return null;
		}
		if(separator==null||"".equals(separator.trim())){
			return name;
		}
		String[] split = name.split(separator);
		if(split!=null&&split.length>0){
			return split[0];
		}
		return name;
	}
	
	
}
