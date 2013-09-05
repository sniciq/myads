package com.ku6ads.dao.iface.advflight;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.AdvActive;

/**
 * 广告活动DAO
 * @author liujunshi
 *
 */
public interface AdvActiveDao extends BaseDao {
	public void insert(AdvActive advActive);
	
	public List<AdvActive> selectAll();
	
	public List<AdvActive> selectByName(AdvActive advActive);

}
