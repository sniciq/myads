package com.ku6ads.services.iface.advflight;

import java.util.Date;
import java.util.List;

import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.services.base.BaseServiceIface;
/**
 * 广告
 * @author liujunshi
 *
 */
public interface AdvertisementService extends BaseServiceIface{

	public int insertAdv(Object obj);
	
	public List<Advertisement> selectMailNotify(Date date);
	
	public List<Advertisement> selectAdvertisement(Integer advactiveId);
	
	/**
	 * 根据广告Id 获得对应广告条模板类型（普通页面 、播放器）
	 * @param advId
	 * @return
	 */
	public int getBarTemType(int advId);
}
