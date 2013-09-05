package com.ku6ads.dao.entity.sysconfig;

/**
 * 
 * 用户实体信息表(非广告本地实体用户表)
 * @author xuxianan
 *
 */
public class UserInfoEty {

	private String tokenId; // tokenId
	private Integer userId; // 用户编号
	private String username; // 用户名
	private String realname; // 真实姓名
	private String department; // 部门
	private String job; // 职业

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}
