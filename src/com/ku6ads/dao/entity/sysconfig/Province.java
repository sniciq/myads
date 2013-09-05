package com.ku6ads.dao.entity.sysconfig;
public class Province {
	private Integer id;
	private Integer pcode;
	private Integer ncode;
	private String province;

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPcode() {
		return this.pcode;
	}
	public void setPcode(Integer pcode) {
		this.pcode = pcode;
	}

	public Integer getNcode() {
		return this.ncode;
	}
	public void setNcode(Integer ncode) {
		this.ncode = ncode;
	}

	public String getProvince() {
		return this.province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
}
