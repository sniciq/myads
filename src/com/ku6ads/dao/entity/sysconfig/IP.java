package com.ku6ads.dao.entity.sysconfig;

import java.util.Date;

public class IP {

	private String ipStart; // 开始ip
	private String ipEnd; // 结束ip
	private Integer cCode;
	private Integer pCode;
	private Integer dCode;
	private Integer iCode;
	private Integer type; // 类型
	private Date modifyDate; // 修改日期

	public String getIpStart() {
		return ipStart;
	}

	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}

	public String getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(String ipEnd) {
		this.ipEnd = ipEnd;
	}

	public Integer getcCode() {
		return cCode;
	}

	public void setcCode(Integer cCode) {
		this.cCode = cCode;
	}

	public Integer getpCode() {
		return pCode;
	}

	public void setpCode(Integer pCode) {
		this.pCode = pCode;
	}

	public Integer getdCode() {
		return dCode;
	}

	public void setdCode(Integer dCode) {
		this.dCode = dCode;
	}

	public Integer getiCode() {
		return iCode;
	}

	public void setiCode(Integer iCode) {
		this.iCode = iCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
