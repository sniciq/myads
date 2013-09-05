package com.ku6ads.services.iface.baisc;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.services.base.BaseServiceIface;

public interface BaseDataService extends BaseServiceIface {
	
	/**
	 * 删除多条数据，事务处理
	 * @param idList
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteList(String[] idList);
	
	/**
	 * 无条件获取字典表数据
	 * add by zhangyan 2010-11-22
	 * @return
	 */
	public Map<String, Map<String, BaseData>> loadAllDictionary();
}
