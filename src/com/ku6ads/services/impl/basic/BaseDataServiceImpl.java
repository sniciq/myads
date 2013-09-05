package com.ku6ads.services.impl.basic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.baisc.BaseDataService;

public class BaseDataServiceImpl extends BaseAbstractService implements BaseDataService {

	public void deleteList(String[] idList) {
		for(int i = 0; i < idList.length; i++) {
			getBaseDao().deleteById(Integer.parseInt(idList[i]));
		}
	}

	public Map<String, Map<String, BaseData>> loadAllDictionary() {
		Map<String, Map<String, BaseData>> dictionaryMap = new TreeMap<String, Map<String, BaseData>>();
		BaseData baseDate = new BaseData();
		baseDate.setStatus(new Integer(0));
		List<BaseData> dictionarys = this.selectByLimit(baseDate);
		if (dictionarys.equals(null) || dictionarys.size()<=0) {
			return dictionaryMap;
		}
		Iterator dictionarysIte = dictionarys.iterator();
		while (dictionarysIte.hasNext()) {
			BaseData dictionary = (BaseData) dictionarysIte.next();
			Map<String, BaseData> valueMap = dictionaryMap.get(dictionary.getDataName());
			if (valueMap==null || valueMap.size() <=0) {
				Map<String, BaseData> sigleValue = new TreeMap<String, BaseData>();
				sigleValue.put(dictionary.getDataValue(), dictionary);
				dictionaryMap.put(dictionary.getDataType(), sigleValue);
			} else {
				valueMap.put(dictionary.getDataValue(), dictionary);
			}
		}
		return dictionaryMap;
	}

}
