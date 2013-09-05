package com.ku6ads.dao.iface.sysconfig;

import java.util.List;


import com.ku6ads.dao.entity.sysconfig.Advertiser;
import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
import com.ku6ads.dao.entity.sysconfig.Department;
/**
 * 广告主
 * @author liujunshi
 *
 */
public interface AdvertiserDao {
	/**
	 * 新增广告主
	 * @param department
	 * @return
	 */
	public Object insertAdvertiser(Advertiser Advertiser);

	/**
	 * 按照广告主查询
	 * @param Advertiser
	 * @return
	 */
	public List<Advertiser> selectByAdvertiser(Advertiser Advertiser);

	/**
	 * 更新广告主信息
	 * @param Advertiser
	 * @return
	 */
	public Integer updateAdvertiser(Advertiser Advertiser);

	/**
	 * 按照id查询广告主信息
	 * @param AdvertiserId
	 * @return
	 */
	public Advertiser selectById(int AdvertiserId);

	/**
	 * 按照id删除广告主信息
	 * @param AdvertiserId
	 */
	public void deleteById(int AdvertiserId);

	/**
	 * 查询 ”启用“状态下的广告主信息,返回集合
	 * @return
	 */
	public List<Advertiser> selectAdvertiser();

	public Integer selectBytAdvertiserCount(Advertiser advertiser);
	
	public List<Advertiser> selectByAdvertiserLimit(Advertiser advertiser);
	
	/**
	 * 查询广告主类别列表
	 * @return
	 */
	public List<AdvertiserCategory> selectAdvertiserCategory();

}
