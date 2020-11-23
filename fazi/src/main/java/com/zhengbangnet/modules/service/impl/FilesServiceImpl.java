package com.zhengbangnet.modules.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.ImageUtil;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.Files;
import com.zhengbangnet.modules.entity.Files.FileType;
import com.zhengbangnet.modules.service.FilesService;

import sun.misc.BASE64Decoder;

import com.zhengbangnet.modules.mapper.FilesMapper;
import com.zhengbangnet.modules.mapper.SysAdminMapper;

@Service("filesServiceImpl")
@Transactional(readOnly=true)
public class FilesServiceImpl extends BaseServiceImpl<Files,Long> implements FilesService{

	@Resource(name = "sysAdminMapper")
	private SysAdminMapper sysAdminMapper;
	
	@Resource(name = "filesMapper")
	private FilesMapper filesMapper;
	
	@Resource(name = "filesMapper")
	public void setBaseDao(FilesMapper filesMapper) {
		super.setBaseDao(filesMapper);
	}

	/** servletContext */
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		String[] extensions = null;
		if(fileType==FileType.doc){
			extensions = SettingUtils.get().getUploadDocExtensions();
		}else if(fileType==FileType.image){
			extensions = SettingUtils.get().getUploadImageExtensions();
		}else{
			return false;
		}
		return FilenameUtils.isExtension(multipartFile.getOriginalFilename().toLowerCase(), extensions);
	}
	
	/**
	 * base64解码上传
	 * @author suijinchi
	 * @param imgBase64
	 * @param compress
	 * @return
	 */
	@Override
	public Files uploadBase(String imgBase64, Integer compress) {
		
		if(compress==null){
			compress = 1;
		}
		
		if (imgBase64 == null || imgBase64.equals("")) {
			return null;
		}
		
		Files files = new Files();
		
		FileType fileType = FileType.image;
		String uploadPath = SettingUtils.get().getUploadImagePath();

		uploadPath += "/" + DateUtils.dateToString(new Date(), DateUtils.patternB);
		if(!new File(servletContext.getRealPath(uploadPath)).exists()){
			new File(servletContext.getRealPath(uploadPath)).mkdirs();
		}
		
		String excension = "jpeg";
		String destPath = uploadPath + "/" + UUID.randomUUID() + "."+excension;
		
		File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + "."+excension);
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdirs();
		}
		try {

			BASE64Decoder decoder = new BASE64Decoder();  
			byte[] decoderBytes = decoder.decodeBuffer(imgBase64.replace("data:image/jpeg;base64,",""));  
			FileOutputStream fis = new FileOutputStream(tempFile);
			fis.write(decoderBytes);
			fis.close();
			
			if(fileType==FileType.image&&compress==1){
				ImageUtil.compress(tempFile, tempFile, 350*1024);
			}

			String md5 = com.zhengbangnet.common.utils.FileUtils.fileMD5(tempFile.getAbsolutePath());
			
			Files f = filesMapper.getByMd5(md5);
			if(f!=null){
				if(!new File(servletContext.getRealPath(f.getRelative())).exists()){
					filesMapper.deleteByPrimaryKey(f.getId());
				}else{
					tempFile.delete();
					return f;
				}
			}
			
			files.setCreateDate(new Date());
			files.setModifyDate(new Date());
			files.setMd5(md5);
			files.setFileType(fileType);
			files.setName("");
			files.setSize(FileUtils.sizeOf(tempFile));
			files.setRelative(destPath);
			files.setAbsolute(SettingUtils.get().getDomain()+destPath);
//			filesMapper.insert(files);
			File destFile = new File(servletContext.getRealPath(destPath));
			FileUtils.moveFile(tempFile, destFile);
			
			return files;
		}catch (Exception e) {
			logger.warn("保存base编码文件异常：",e);
		}finally{
			FileUtils.deleteQuietly(tempFile);
		}
		return null;
	}
	
	/**
	 * 图片上传
	 *
	 * @author suijinchi
	 * @param fileType
	 * @param multipartFile
	 * @param compress
	 * @return
	 */
	@Transactional(readOnly=false)
	public Files upload(FileType fileType,MultipartFile multipartFile, Integer compress) {
		if(compress==null){
			compress = 1;
		}
		Files files = new Files();
		if (multipartFile == null) {
			return null;
		}
		
		String uploadPath = null;
		if(fileType==FileType.doc){
			uploadPath = SettingUtils.get().getUploadDocPath();
		}else if(fileType==FileType.image){
			uploadPath = SettingUtils.get().getUploadImagePath();
		}else{
			fileType = FileType.image;
			uploadPath = SettingUtils.get().getUploadImagePath();
		}
		
		uploadPath += "/" + DateUtils.dateToString(new Date(), DateUtils.patternB);
		if(!new File(servletContext.getRealPath(uploadPath)).exists()){
			new File(servletContext.getRealPath(uploadPath)).mkdirs();
		}
		
		String excension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

		String destPath = uploadPath + "/" + UUID.randomUUID() + "." + excension;
		
		File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + "."+excension);
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdirs();
		}
		try {
			
			multipartFile.transferTo(tempFile);
			if(fileType==FileType.image&&compress==1){
				ImageUtil.compress(tempFile, tempFile, 400*1024);
			}
			String md5 = com.zhengbangnet.common.utils.FileUtils.fileMD5(tempFile.getAbsolutePath());
			
			Files f = filesMapper.getByMd5(md5);
			if(f!=null){
				if(!new File(servletContext.getRealPath(f.getRelative())).exists()){
					filesMapper.deleteByPrimaryKey(f.getId());
				}else{
					tempFile.delete();
					return f;
				}
			}
			
			files.setCreateDate(new Date());
			files.setModifyDate(new Date());
			files.setMd5(md5);
			files.setFileType(fileType);
			files.setName(multipartFile.getOriginalFilename());
			files.setSize(FileUtils.sizeOf(tempFile));
			files.setRelative(destPath);
			files.setAbsolute(SettingUtils.get().getDomain()+destPath);
//			filesMapper.insert(files);
			File destFile = new File(servletContext.getRealPath(destPath));
			FileUtils.moveFile(tempFile, destFile);
			
			return files;
		}catch (Exception e) {
			logger.warn("保存上传文件异常：",e);
		}finally{
			FileUtils.deleteQuietly(tempFile);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		String md5;
		try {
			md5 = com.zhengbangnet.common.utils.FileUtils.fileMD5("D:\\java\\apache-tomcat-6.0.41\\webapps\\lianzhong\\index.jsp");
			System.out.println(md5);
			md5 = com.zhengbangnet.common.utils.FileUtils.fileMD5("D:\\java\\apache-tomcat-6.0.41\\webapps\\lianzhong\\2.jsp");
			System.out.println(md5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}