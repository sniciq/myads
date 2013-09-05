package com.ku6ads.services.iface.advert;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * 广告条service
 * @author xuxianan
 *
 */
public interface AdvbarService extends BaseServiceIface {

	/**
	 * 查询广告条id,name
	 * @return List<Advbar>
	 */
	public List<Advbar> selectAdvbar();

	/**
	 * 根据广告位id查询
	 * @param id
	 * @return List<Advbar>
	 */
	public List<Advbar> selectAdvbarByPosId(int id);

	/**
	 * 根据频道id查询
	 * @param id
	 * @return List<Advbar>
	 */
	public List<Advbar> selectAdvbarByChannelId(int id);

	/**
	 * 根据网站id查询
	 * @param id
	 * @return List<Advbar>
	 */
	public List<Advbar> selectAdvbarBySiteBarsizeId(int id);

	/**
	 * 根据关联广告条id查询
	 * @param id
	 * @return List<Advbar>
	 */
	public List<Advbar> selectAdvbarBySrcposId(int id);

	/**
	 * 根据广告条模板id查询
	 * @param id
	 * @return List<Advbar>
	 */
	public List<Advbar> selectAdvbarByBartemId(int id);
	
	/**
	 * 得到广告条的页面类型
	 * @param advbraId
	 * @return
	 */
	public BaseData getAdvbarPageType(int advbraId);
	
	/**
	 * 插入记录,同时得到新增记录的id
	 * @param advbar
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int insertAdvbarCallbackId(Advbar advbar);
	
	/**
	 * 插入记录,同时得到新增记录的id
	 * @param advbar
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int insertAdvbarCallbackIdMemory(Advbar advbar) throws Exception;
	
	/**
	 * 修改数据 并推入内存，保持事务
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateAndMemory(Advbar advbar)throws Exception;
	

	/**
	 * 删除数据 并推入内存，保持事务
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAndMemory(Integer advbarId)throws Exception;
	
	/**
	 * 查询一个广告位下的广告条名称是否重复
	 * @param advpositionId
	 * @param advbarName
	 * @return Integer
	 */
	public Advbar selectAdvbarNameIsRepeated(int advpositionId, String advbarName);

}
