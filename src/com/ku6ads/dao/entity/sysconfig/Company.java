package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 所属公司
 * @author liujunshi
 *
 */
public class Company extends ExtEntity{

	private int companyId;
	private String companyName;
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
