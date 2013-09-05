package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Area;
import com.ku6ads.dao.entity.sysconfig.Industry;
/**
 * 地域
 * @author liujunshi
 *
 */
public interface IndustryDao {
	/**
	 * 新增行业
	 * @param Industry
	 * @return
	 */
	public Object insertIndustry(Industry Industry);

	/**
	 * 按照行业查询
	 * @param Industry
	 * @return
	 */
	public List<Industry> selectByIndustry(Industry Industry);

	/**
	 * 更新行业信息
	 * @param Industry
	 * @return
	 */
	public Integer updateIndustry(Industry Industry);

	/**
	 * 按照id查询行业信息
	 * @param IndustryId
	 * @return
	 */
	public Industry selectById(int IndustryId);

	/**
	 * 按照id删除行业信息
	 * @param IndustryId
	 */
	public void deleteById(int IndustryId);

	/**
	 * 查询行业信息,返回集合
	 * @return
	 */
	public List<Industry> selectIndustry();
	


	public List<Industry> selectByIndustryLimit(Industry Industry);

}
