package com.ku6ads.dao.iface.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.Advposition;
import com.ku6ads.dao.entity.advert.Postemplate;
import com.ku6ads.dao.entity.basic.BaseData;

/**
 * 广告位dao
 * @author xuxianan
 *
 */
public interface AdvpositionDao extends BaseDao {

	/**
	 * 查询广告位id,name
	 * @return List<Advposition>
	 */
	public List<Advposition> selectAdvposition();

	/**
	 * 查询广告条是否有被外联使用
	 * @param advpositionId
	 * @return List<Advbar>
	 */
	public List<Advposition> selectAdvpositionById(Advposition advposition);

	/**
	 * 验证广告位名称是否唯一
	 * @param name
	 * @return List<Advposition>
	 */
	public List<Advposition> selectAdvpositionByName(Advposition advposition);

	/**
	 * 广告条中针对广告位的定制查询
	 * @param advpositionId
	 * @return Advposition
	 */
	public Advposition selectAdvpositionInAdvbar(int advpositionId);

	/**
	 * 根据页面类型查询出对应的模板类型
	 * @param typeValue
	 * @return List<BaseData>
	 */
	public List<BaseData> selectPostemTypeByTypeValue(String typeValue);

	
	/**
	 * 根据频道Id获取该频道下未删除的广告位
	 * add by zhangyan
	 * @param channelId
	 * @return
	 */
	public List<Advposition> loadByChannelId(Integer channelId);


	/**
	 * 三级联动 根据模板类型查询对应的模板
	 * @param type
	 * @return
	 */
	public List<Postemplate> selectPostemplateByType(Postemplate postemplate);
	
	/**
	 * 查询广告位下面对应的所有广告条
	 * @param advpositionId
	 * @return
	 */
	public List<Advbar> selectAdvbarByAdvpositionId(int advpositionId);
	
	/**
	 * 查询一个频道下的广告位名称是否重复
	 */
	public Advposition selectAdvNameIsRepeated(Advposition advposition);

}
