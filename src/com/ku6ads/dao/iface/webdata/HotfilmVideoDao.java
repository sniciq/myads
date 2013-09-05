package com.ku6ads.dao.iface.webdata;

import java.util.Date;
import java.util.List;

import com.ku6ads.dao.base.BaseDao;

public interface HotfilmVideoDao extends BaseDao {

	public Date selectSysDate();
	
	/**
	 * 得到需要统计的视频的VID
	 * @return
	 */
	public List<String> selectStateVideos();
	
	/**
	 * 根据filmID删除
	 * @param filmId
	 * @return
	 */
	public Integer deleteByFilmId(Integer filmId);
}
