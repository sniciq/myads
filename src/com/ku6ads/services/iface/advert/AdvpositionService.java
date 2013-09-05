package com.ku6ads.services.iface.advert;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.Advposition;
import com.ku6ads.dao.entity.advert.Postemplate;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * @author xuxianan
 *
 */
public interface AdvpositionService extends BaseServiceIface {

	/**
	 * 查询广告位
	 * @return List<Advposition>
	 */
	public List<Advposition> selectAdvposition();

	/**
	 * 根据广告位规格id查询
	 * @param id
	 * @return List<Advposition>
	 */
	public List<Advposition> selectAdvpositionByPositionsizeId(int id);

	/**
	 * 根据广告位模板id查询
	 * @param id
	 * @return List<Advposition>
	 */
	public List<Advposition> selectAdvpositionByPostemId(int id);

	/**
	 * 验证广告位名称是否唯一
	 * @param name
	 * @return List<Advposition>
	 */
	public List<Advposition> selectAdvpositionByName(String name);

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
	 * 插入数据 并推入内存，保持事务
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void insertAndMemory(Advposition advposition)throws Exception;
	

	/**
	 * 修改数据 并推入内存，保持事务
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateAndMemory(Advposition advposition)throws Exception;
	

	/**
	 * 删除数据 并推入内存，保持事务
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAndMemory(Integer advposId)throws Exception;
	
	/**
	 * 根据播放器广告模板种类插入配置文件中对应的value
	 * @param type
	 * @param fileName
	 * @return
	 */
	public int getAdvpositionOrder(Integer type, String fileName);
	
	/**
	 * 查询一个频道下的广告位名称是否重复
	 * @param channelId
	 * @param advName
	 * @return Integer
	 */
	public Advposition selectAdvNameIsRepeated(int channelId, String advName);
}
