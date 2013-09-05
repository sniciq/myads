package com.ku6ads.dao.entity.sys;

public class SysSequenceEty {
	private String id;
	private Integer step;
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public String getId() {
		return this.id;
	};
	public void setId(String id) {
		this.id = id;
	};

	private String tableName;
	public String getTableName() {
		return this.tableName;
	};
	public void setTableName(String tableName) {
		this.tableName = tableName;
	};

	private int nextSequenceValue;
	public int getNextSequenceValue() {
		return this.nextSequenceValue;
	};
	public void setNextSequenceValue(int nextSequenceValue) {
		this.nextSequenceValue = nextSequenceValue;
	};

}