package com.ku6ads.dao.impl.advflight;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.AdvActive;
import com.ku6ads.dao.iface.advflight.AdvActiveDao;
/**
 * 广告活动DAO
 * @author liujunshi
 *
 */
public class AdvActiveDaoImpl extends BaseAbstractDao implements AdvActiveDao {
	public void insert(AdvActive advActive)
	{
		getSqlMapClientTemplate().insert("advflight.AdvActive.insert",advActive);
	}
	
	public List<AdvActive> selectAll(){
		return getSqlMapClientTemplate().queryForList("advflight.AdvActive.selectAll");
	}
	
	public List<AdvActive> selectByName(AdvActive advActive){
		return getSqlMapClientTemplate().queryForList("advflight.AdvActive.selectByName",advActive);
	}
}
