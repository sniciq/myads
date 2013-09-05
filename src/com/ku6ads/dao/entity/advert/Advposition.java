package com.ku6ads.dao.entity.advert;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 广告位
 * 
 * 广告位业务状态 在BussinessStatus类中定义
 * 
 * @author xuxianan
 *
 */
public class Advposition extends ExtEntity {

	private Integer id; // 编号
	private String name; // 名称
	private String nameEn; //	英文名称
	private Integer siteId; // 网站id
	private Integer channelId; // 频道id
	private Integer positionsizeId; //广告位规格id
	private Integer postemId; // 广告位模板id
	private Integer pageType; // 页面类型
	private String type; // 模板类型
	private Integer num; // 广告条数
	private String code; // 广告位代码
	private String note; // 备注
	private Integer sort; // 排序

	private String postemplateName; // 广告位模板名称
	private String positionsizeName; // 广告位规格名称
	private String siteName; // 网站名称
	private String channelName; // 频道名称

	private String dirCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Integer getPositionsizeId() {
		return positionsizeId;
	}

	public void setPositionsizeId(Integer positionsizeId) {
		this.positionsizeId = positionsizeId;
	}

	public Integer getPostemId() {
		return postemId;
	}

	public void setPostemId(Integer postemId) {
		this.postemId = postemId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPostemplateName() {
		return postemplateName;
	}

	public void setPostemplateName(String postemplateName) {
		this.postemplateName = postemplateName;
	}

	public String getPositionsizeName() {
		return positionsizeName;
	}

	public void setPositionsizeName(String positionsizeName) {
		this.positionsizeName = positionsizeName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getSiteName() {
		return siteName;
	}

	public String getChannelName() {
		return channelName;
	}

	public Integer getPageType() {
		return pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	//TODO 第三方监控代码
//	public String getDirCode() {
//		StringBuffer dirCodeSB = new StringBuffer();
//		dirCodeSB.append("<script type=\"text/javascript\">")
//		.append("var xmlhttp;")
//		.append(" if(window.XMLHttpRequest) {")
//		.append("xmlhttp = new XMLHttpRequest();")
//		.append("} else if (window.ActiveXObject) {")
//		.append("xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");}")
//		.append(" xmlhttp.open(\"POST\",path,false); ")
//		.append("xmlhttp.send();}")
//		.append("</script>");
//		
//		dirCode = dirCodeSB.toString();
//		
//		return dirCode;
//	}

	public void setDirCode(String dirCode) {
		this.dirCode = dirCode;
	}

	public String getDirCode() {
		return dirCode;
	}

	/**
	 * 排序
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * 排序
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}
