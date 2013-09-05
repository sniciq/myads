package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
/**
 * 广告主类别
 * @author liujunshi
 *
 */
public interface AdvertiserCategoryDao {
	/**
	 * 新增广告主类别
	 * @param department
	 * @return
	 */
	public Object insertAdvertiserCategory(AdvertiserCategory AdvertiserCategory);

	/**
	 * 按照广告主类别查询
	 * @param AdvertiserCategory
	 * @return
	 */
	public List<AdvertiserCategory> selectByAdvertiserCategory(AdvertiserCategory AdvertiserCategory);

	/**
	 * 更新广告主类别信息
	 * @param AdvertiserCategory
	 * @return
	 */
	public Integer updateAdvertiserCategory(AdvertiserCategory AdvertiserCategory);

	/**
	 * 按照id查询广告主类别信息
	 * @param AdvertiserCategoryId
	 * @return
	 */
	public AdvertiserCategory selectById(int AdvertiserCategoryId);

	/**
	 * 按照id删除广告主类别信息
	 * @param AdvertiserCategoryId
	 */
	public void deleteById(int AdvertiserCategoryId);

	/**
	 * 查询广告主类别信息,返回集合
	 * @return
	 */
	public List<AdvertiserCategory> selectAdvertiserCategory();

	public List<AdvertiserCategory> selectByAdvertiserCategoryLimit(AdvertiserCategory AdvertiserCategory);

}
