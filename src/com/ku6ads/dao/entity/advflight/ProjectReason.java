package com.ku6ads.dao.entity.advflight;

import java.io.Serializable;
import java.util.Date;

/**
 * 执行单审核不通过原因
 * 
 * @author xuxianan
 *
 */
public class ProjectReason implements Serializable {

	private static final long serialVersionUID = 1355789787997927327L;

	private Integer id; // id
	private Integer projectId; // 执行单id
	private String reason; // 不通过原因
	private String creator; // 审核人
	private Date createTime; // 审核时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
