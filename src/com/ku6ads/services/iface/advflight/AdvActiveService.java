package com.ku6ads.services.iface.advflight;

import java.util.List;

import com.ku6ads.dao.entity.advflight.AdvActive;
import com.ku6ads.services.base.BaseServiceIface;
/**
 * 广告活动
 * @author liujunshi
 *
 */
public interface AdvActiveService extends BaseServiceIface{
	
	public List<AdvActive> selectAdvactive();
	
	public List<AdvActive> selectByName(AdvActive advActive);
	
//	public void insert(AdvActive advActive);

}
