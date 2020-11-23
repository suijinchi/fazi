package com.zhengbangnet.modules.service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Files;
import com.zhengbangnet.modules.entity.Files.FileType;

public interface FilesService extends BaseService<Files, Long>,ServletContextAware {

	/**
	 * 判断文件类型是否正确
	 * @param fileType
	 * @param multipartFile
	 */
	public boolean isValid(FileType fileType, MultipartFile multipartFile);
	
	/**
	 * 文件上传
	 * @param fileType
	 * @param multipartFile
	 * @param compress 是否压缩 0不压缩 1压缩
	 * @return
	 */
	public Files upload(FileType fileType, MultipartFile multipartFile, Integer compress);

	/**
	 * base64解码上传
	 * @author suijinchi
	 * @param imgBase64
	 * @param compress
	 * @return
	 */
	public Files uploadBase(String imgBase64, Integer compress);
	
}