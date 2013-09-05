package com.ku6ads.dao.iface.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.basic.BaseData;

/**
 * 广告条dao
 * @author xuxianan
 *
 */
public interface AdvbarDao extends BaseDao {

	/**
	 * 查询广告条id,name
	 * @return List<Advbar>
	 */
	public List<Advbar> selectAdvbar();

	/**
	 * 查询广告条是否有外联使用
	 * @param id
	 * @return List<Advbar>
	 */
	public List<Advbar> selectAdvbarById(Advbar advbar);

	/**
	 * 查询广告条的广告容量
	 * @param advbarId
	 * @return
	 */
	public int selectBarContent(Integer advbarId);
	
	/**
	 * 得到广告条的页面类型
	 * @param advbarId
	 * @return
	 */
	public BaseData getAdvbarPageType(int advbarId);
	
	/**
	 * 插入记录,同时得到新增记录的id
	 * @param advbar
	 * @return
	 */
	public Integer insertAdvbarCallbackId(Advbar advbar);

	/**
	 * 查询频道下启用的广告条(广告位也是启用的)
	 * @param ety
	 * @return
	 */
	public List<Advbar> selectUsebleAdvbar(Advbar ety);
	
	/**
	 * 查询一个广告位下的广告条名称是否重复
	 */
	public Advbar selectAdvbarNameIsRepeated(Advbar advbar);
	
}
