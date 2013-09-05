/**
 * 
 */
package com.ku6ads.struts.basic.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.exception.DictionaryAppendException;
import com.ku6ads.exception.DictionaryNotFoundException;
import com.ku6ads.services.iface.baisc.BaseDataService;

/**
 * 
 * HISTORY @ Copyright: Copyright (c) 2010 @ Company:KU6
 * 
 * @version:1.0
 * @date 2010-11-22
 * @author zhangyan
 * @description:
 */
public class DictionaryFactory {

	// 数据字典map
	private static Map dictoinaryInfo;

	// 字典工厂对象
	private static DictionaryFactory instance;

	private static BaseDataService baseDataService;

	/**
	 * 获得所有字典
	 * 
	 * @return
	 */
	public static Map getDictionary() {
		if (dictoinaryInfo == null) {
			dictoinaryInfo = loadAll();
		}
		return dictoinaryInfo;
	}

	public static void flush() {
		dictoinaryInfo = loadAll();
	}

	/**
	 * 字典工厂单例方法
	 * 
	 * @return
	 */
	public static DictionaryFactory getInstance() {
		if (instance == null) {
			instance = new DictionaryFactory();
		}
		return instance;
	}

	/**
	 * 加载所有数据字典
	 * 
	 * @return
	 */
	private static Map loadAll() {
		return baseDataService.loadAllDictionary();
	}

	/**
	 * 添加一个数据字典
	 * 
	 * @param key
	 * @param valueMap
	 * @throws DictionaryAppendException
	 */
	public void append(String key, Map valueMap) throws DictionaryAppendException {
		if ((key == null) && key.equals("") || (valueMap == null && valueMap.equals(""))) {
			throw new DictionaryAppendException("数据字典参数不完整");
		}
		dictoinaryInfo.put(key, valueMap);
	}

	/**
	 * 根据英文标识获得某一个字典Map
	 * 
	 * @param enName
	 * @return
	 * @throws DictionaryNotFoundException
	 */
	public Map getValus(String enName) throws DictionaryNotFoundException {
		Map value = (Map) dictoinaryInfo.get(enName);
		if (value == null && value.equals("")) {
			throw new DictionaryNotFoundException("没有找到英文标识为" + enName + "的数据字典");
		}
		Map valueMap = new TreeMap();
		Iterator values = value.values().iterator();
		while (values.hasNext()) {
			BaseData dictionary = (BaseData) values.next();
			valueMap.put(dictionary.getDataValue(), dictionary.getDataName());
		}
		return valueMap;
	}

	/**
	 * 通过英文标示和字典值获得数据字典对象
	 * 
	 * @param enName
	 * @param dictionaryValue
	 * @return
	 * @throws DictionaryNotFoundException
	 */
	public BaseData getDictionaryByValue(String enName, String dictionaryValue) throws DictionaryNotFoundException {
		Map value = (Map) dictoinaryInfo.get(enName);
		if (value == null || value.equals("")) {
			throw new DictionaryNotFoundException("没有找到英文标识为" + enName + "的数据字典");
		}
		Iterator values = value.values().iterator();
		while (values.hasNext()) {
			BaseData dictionary = (BaseData) values.next();
			if (dictionary.getDataValue().equals(dictionaryValue)) {
				return dictionary;
			}
		}
		return null;
	}

	/**
	 * 通过英文标示和中文名称获得数据字典对象
	 * 
	 * @param enName
	 * @param cnname
	 * @return
	 * @throws DictionaryNotFoundException
	 */
	public BaseData getDictionaryByCNNAME(String enName, String cnname) throws DictionaryNotFoundException {
		Map value = (Map) dictoinaryInfo.get(enName);
		if (value == null || value.equals("")) {
			throw new DictionaryNotFoundException("没有找到英文标识为" + enName + "的数据字典");
		}
		Iterator values = value.values().iterator();
		while (values.hasNext()) {
			BaseData dictionary = (BaseData) values.next();
			if (dictionary.getDataName().equals(cnname)) {
				return dictionary;
			}
		}
		return null;
	}

	/**
	 * 修改一个字典
	 * 
	 * @param key
	 * @param valueMap
	 * @throws DictionaryAppendException
	 */
	public void modify(String key, Map valueMap) throws DictionaryAppendException {
		if ((key == null && key.equals("")) || valueMap.equals(null)) {
			throw new DictionaryAppendException("修改数据字典时参数不完整");
		}
		dictoinaryInfo.remove(key);
		dictoinaryInfo.put(key, valueMap);
	}

	/**
	 * 修改一个字典中的值
	 * 
	 * @param key
	 * @param value
	 * @param cnName
	 * @throws DictionaryAppendException
	 * @throws DictionaryNotFoundException
	 */
	public void modifyValue(String key, String value, String cnName) throws DictionaryAppendException, DictionaryNotFoundException {
		if ((key == null && key.equals("")) || (value.equals(null) && value.equals("")) || (cnName.equals(null) && cnName.equals(""))) {
			throw new DictionaryAppendException("修改数据字典" + key + "的" + value + "时参数不完整�");
		}
		Map values = getValus(key);
		if (!values.containsKey(value)) {
			throw new DictionaryNotFoundException("修改数据字典值时没有找到原有字典");
		}
		values.remove(value);
		values.put(value, cnName);
	}

	public synchronized void refreshDictionary() {
		dictoinaryInfo.clear();
		loadAll();
	}

	public BaseDataService getBaseDataService() {
		return baseDataService;
	}

	public void setBaseDataService(BaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}

}
