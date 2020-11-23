package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Files;
import org.springframework.stereotype.Repository;

/**
 * 
 * FilesMapper数据库操作接口类
 * 
 **/

@Repository("filesMapper")
public interface FilesMapper extends BaseMapper<Files,Long>{

	/**
	 * 通过MD5查询资源文件
	 */
	Files getByMd5(String md5);


}