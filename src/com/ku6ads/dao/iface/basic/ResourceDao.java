package com.ku6ads.dao.iface.basic;

import java.util.List;

import com.ku6ads.dao.entity.basic.Resource;

/**
 * ��ԴDAO
 *
 */
public interface ResourceDao {
	
	/**
	 * ����Id�������ڵ���Ϣ
	 * @param id
	 * @return
	 */
	public Resource selectByResourceId(Integer id);
	
	/**
	 * ���սڵ�Idɾ��ڵ���Ϣ
	 * @param id
	 * @return
	 */
	public Integer deleteByResourceId(Integer id);
	
	/**
	 * �޸����ڵ���Ϣ
	 * @param resource
	 * @return
	 */
	public Integer updateResource(Resource resource);
	
	/**
	 * �������ڵ���Ϣ
	 * @param menu
	 * @return
	 */
	public Object insertResource(Resource menu);
	
	/**
	 * ���Resource��ѯ
	 * @param res
	 * @return Resource
	 */
	public List<Resource> selectByResource(Resource res);
	
	public Integer selectByResourceCount(Resource res);
	
	public List<Resource> selectByResourceLimit(Resource res);
	
	/**
	 * ��ѯ��Դ�����б�
	 * @param res
	 * @return
	 */
	public List<Resource> selectNavigateList(Resource res);
}
