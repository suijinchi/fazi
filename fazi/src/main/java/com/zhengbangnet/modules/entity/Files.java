package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Files extends BaseEntity {

	/**文件名称**/
	private String name;

	/**绝对地址**/
	private String absolute;

	/**相对地址**/
	private String relative;

	/****/
	private String md5;

	/**文件类型**/
	private FileType fileType;

	/**大小**/
	private Long size;

	public enum FileType {
		/**
		 * 图片
		 */
		image,
		/**
		 * 文档文件
		 */
		doc,
		/**
		 * 媒体类型
		 */
		media,
		/**
		 * 其它类型
		 */
		other
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setAbsolute(String absolute){
		this.absolute = absolute;
	}

	public String getAbsolute(){
		return this.absolute;
	}

	public void setRelative(String relative){
		this.relative = relative;
	}

	public String getRelative(){
		return this.relative;
	}

	public void setMd5(String md5){
		this.md5 = md5;
	}

	public String getMd5(){
		return this.md5;
	}

	public void setSize(Long size){
		this.size = size;
	}

	public Long getSize(){
		return this.size;
	}

}
