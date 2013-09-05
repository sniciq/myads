package com.ku6ads.dao.entity.webdata;

public class MetaProdName {
	private Integer id;
	private String prod_code;
	private String prod_name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProd_code() {
		return prod_code;
	}
	public void setProd_code(String prodCode) {
		prod_code = prodCode;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prodName) {
		prod_name = prodName;
	}
}
