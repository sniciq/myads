package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
import com.ku6ads.dao.entity.sysconfig.BrandGroup;
/**
 * 品牌组
 * @author liujunshi
 *
 */
public interface BrandGroupDao {
	/**
	 * 新增品牌组
	 * @param department
	 * @return
	 */
	public Object insertBrandGroup(BrandGroup BrandGroup);

	/**
	 * 按照品牌组查询
	 * @param BrandGroup
	 * @return
	 */
	public List<BrandGroup> selectByBrandGroup(BrandGroup BrandGroup);

	/**
	 * 更新品牌组信息
	 * @param BrandGroup
	 * @return
	 */
	public Integer updateBrandGroup(BrandGroup BrandGroup);

	/**
	 * 按照id查询品牌组信息
	 * @param BrandGroupId
	 * @return
	 */
	public BrandGroup selectById(int BrandGroupId);

	/**
	 * 按照id删除品牌组信息
	 * @param BrandGroupId
	 */
	public void deleteById(int BrandGroupId);

	/**
	 * 查询品牌组信息,返回集合
	 * @return
	 */
	public List<BrandGroup> selectBrandGroup();
	

	/**
	 * 查询广告主类别列表
	 * @return
	 */
	public List<AdvertiserCategory> selectBrandCategory();
	
	
	public List<BrandGroup> selectByBrandGroupLimit(BrandGroup BrandGroup);

}
