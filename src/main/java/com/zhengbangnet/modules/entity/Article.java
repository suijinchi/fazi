package com.zhengbangnet.modules.entity;
import com.zhengbangnet.common.entity.BaseEntity;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class Article extends BaseEntity {

	/**文章内容**/
	private String content;

	/**点击次数**/
	private Integer hits;

	/**是否发布**/
	private Integer isPublication;

	/**是否置顶**/
	private Integer isTop;

	/**标题**/
	private String title;

	/**分类ID**/
	private Long articleCategoryId;

	/**作者**/
	private String author;

	/**文章来源**/
	private String source;

	/**展示图片**/
	private String showImageUrl;

	/**小标题**/
	private String subTitle;

	/**是否是首页**/
	private Integer isIndex;

    private Integer sort;

	public Integer getIsPublication() {
		return isPublication;
	}

	public void setIsPublication(Integer isPublication) {
		this.isPublication = isPublication;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getIsIndex() {
		return isIndex;
	}

	public void setIsIndex(Integer isIndex) {
		this.isIndex = isIndex;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setHits(Integer hits){
		this.hits = hits;
	}

	public Integer getHits(){
		return this.hits;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setArticleCategoryId(Long articleCategoryId){
		this.articleCategoryId = articleCategoryId;
	}

	public Long getArticleCategoryId(){
		return this.articleCategoryId;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return this.author;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return this.source;
	}

	public void setShowImageUrl(String showImageUrl){
		this.showImageUrl = showImageUrl;
	}

	public String getShowImageUrl(){
		return this.showImageUrl;
	}

	public void setSubTitle(String subTitle){
		this.subTitle = subTitle;
	}

	public String getSubTitle(){
		return this.subTitle;
	}

}
