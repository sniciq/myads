package com.ku6ads.dao.iface.sysconfig;

import java.util.List;
import com.ku6ads.dao.entity.sysconfig.ConsumerCategory;
/**
 * 客户类别
 * @author liujunshi
 *
 */
public interface ConsumerCategoryDao {
	/**
	 * 新增客户类别
	 * @param department
	 * @return
	 */
	public Object insertConsumerCategory(ConsumerCategory ConsumerCategory);

	/**
	 * 按照客户类别查询
	 * @param ConsumerCategory
	 * @return
	 */
	public List<ConsumerCategory> selectByConsumerCategory(ConsumerCategory ConsumerCategory);

	/**
	 * 更新客户类别信息
	 * @param ConsumerCategory
	 * @return
	 */
	public Integer updateConsumerCategory(ConsumerCategory ConsumerCategory);

	/**
	 * 按照id查询客户类别信息
	 * @param ConsumerCategoryId
	 * @return
	 */
	public ConsumerCategory selectById(int ConsumerCategoryId);

	/**
	 * 按照id删除客户类别信息
	 * @param ConsumerCategoryId
	 */
	public void deleteById(int ConsumerCategoryId);

	/**
	 * 查询客户类别信息,返回集合
	 * @return
	 */
	public List<ConsumerCategory> selectConsumerCategory();

	public List<ConsumerCategory> selectByConsumerCategoryLimit(ConsumerCategory ConsumerCategory);

}
