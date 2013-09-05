package com.ku6ads.dao.iface.advflight;

import java.util.Date;
import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.Advertisement;

/**
 * 广告DAO
 * @author liyonghui
 *
 */
public interface AdvertisementDao extends BaseDao {

	int insertAdv(Object obj);
	
	public List<Advertisement> selectMailNotify(Date date);
	
	public List<Advertisement> selectAll(Integer advactiveId);
	
	/**
	 * 根据广告Id 获得对应广告条模板类型（普通页面 、播放器）
	 * @param advId
	 * @return
	 */
	public Integer getBarTemType(int advId);
}
