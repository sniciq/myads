package com.ku6ads.dao.entity;

import java.util.Date;

import com.ku6ads.util.ExtLimit;

public class ExtEntity {
	private ExtLimit extLimit;
	
	private Integer status; //状态
	
	private String creator; //创建人
	
	private Date createTime;//创建时间
	
	private String modifier;//修改人
	
	private Date modifyTime;//修改时间
	
	/**
	 * 用于EXT的分页对象
	 * @return
	 */
	public ExtLimit getExtLimit() {
		return extLimit;
	}

	/**
	 * 用于EXT的分页对象
	 * @param extLimit
	 */
	public void setExtLimit(ExtLimit extLimit) {
		this.extLimit = extLimit;
	}

	/**
	 * 状态<br>
	 * 0:新建<br>
	 * 1:删除<br>
	 * 2：停用<br>
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态<br>
	 * 0:新建<br>
	 * 1:删除<br>
	 * 2：停用<br>
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 创建人
	 * @return
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 创建人
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 创建时间
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 修改人
	 * @return
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * 修改人
	 * @param modifier
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * 修改时间
	 * @return
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 修改时间
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
