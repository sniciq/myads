package com.ku6ads.dao.entity.sysconfig;

public class Menu {

	private Integer id;
	private Integer nodeId;
	private Integer pnodeId;
	private String name;
	private String url;
	private String icon1;
	private String icon2;
	private Integer userId;
	private Integer sort;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getPnodeId() {
		return pnodeId;
	}

	public void setPnodeId(Integer pnodeId) {
		this.pnodeId = pnodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon1() {
		return icon1;
	}

	public void setIcon1(String icon1) {
		this.icon1 = icon1;
	}

	public String getIcon2() {
		return icon2;
	}

	public void setIcon2(String icon2) {
		this.icon2 = icon2;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
