package com.ku6ads.dao.entity.basic;

import com.ku6ads.dao.entity.ExtEntity;


/**
 * �����ʵ��
 * @author chenhb
 *
 */
public class Resource extends ExtEntity {

	private Integer resourceId;//��ID
	
	private String id = "";//���ڵ�Id

	private String menuName = "";//���ڵ����

	private String parantId = "";//���ڵ�ĸ��ڵ�Id

	private String icon = "";//���ڵ�ͼ��

	private String openIcon = "";//���ڵ�չ��ʱ��ͼ��

	private String actionPath = "";//���ڵ㴥���Ķ���

	private int menuOrder = 0;//���ڵ�������
	
	private String isValidate = "";//�ڵ��Ƿ����
	
	private String description = "";//�ڵ������
	
	/**
	 * �ڵ��Ƿ����
	 * @return
	 */
	public String getIsValidate() {
		return isValidate;
	}

	/**
	 * �ڵ��Ƿ����
	 * @param isValidate
	 */
	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}

	/**
	 * ���ڵ�Id
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * ���ڵ�Id
	 * @return
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * ���ڵ����
	 * @return
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * ���ڵ����
	 * @param menuName
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * ���ڵ�ĸ��ڵ�Id
	 * @return
	 */
	public String getParantId() {
		return parantId;
	}

	/**
	 * ���ڵ�ĸ��ڵ�Id
	 * @return
	 */
	public void setParantId(String parantId) {
		this.parantId = parantId;
	}

	/**
	 * ���ڵ�ͼ��
	 * @return
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * ���ڵ�ͼ��
	 * @return
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * ���ڵ�չ��ʱ��ͼ��
	 * @return
	 */
	public String getOpenIcon() {
		return openIcon;
	}

	/**
	 * ���ڵ�չ��ʱ��ͼ��
	 * @return
	 */
	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}

	/**
	 * ���ڵ�������
	 * @return
	 */
	public int getMenuOrder() {
		return menuOrder;
	}

	/**
	 * ���ڵ�������
	 * @return
	 */
	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	/**
	 * ��ID
	 * @return
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * ��ID
	 * @param resourceId
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	

	/**
	 * �ڵ������
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * �ڵ������
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
}
