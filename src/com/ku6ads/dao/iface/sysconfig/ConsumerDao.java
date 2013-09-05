package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Advertiser;
import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
import com.ku6ads.dao.entity.sysconfig.Company;
import com.ku6ads.dao.entity.sysconfig.Consumer;
/**
 * 客户（下单公司）
 * @author liujunshi
 *
 */
public interface ConsumerDao {
	/**
	 * 新增客户
	 * @param department
	 * @return
	 */
	public Object insertConsumer(Consumer Consumer);

	/**
	 * 按照客户查询
	 * @param Consumer
	 * @return
	 */
	public List<Consumer> selectByConsumer(Consumer Consumer);

	/**
	 * 更新客户信息
	 * @param Consumer
	 * @return
	 */
	public Integer updateConsumer(Consumer Consumer);

	/**
	 * 按照id查询客户信息
	 * @param ConsumerId
	 * @return
	 */
	public Consumer selectById(int ConsumerId);

	/**
	 * 按照id删除客户信息
	 * @param ConsumerId
	 */
	public void deleteById(int ConsumerId);

	/**
	 * 查询 启用 状态下的客户信息,返回集合
	 * @return
	 */
	public List<Consumer> selectConsumer();
	/**
	 * 分页查询
	 * @param Consumer
	 * @return
	 */
	public List<Consumer> selectByConsumerLimit(Consumer Consumer);
	/**
	 * 取得总数
	 * @param consumer
	 * @return
	 */
	public Integer selectByConsumerCount(Consumer consumer);
	
	/**
	 * 查询类别列表
	 * @return
	 */
	public List<AdvertiserCategory> selectConsumerCategory();
	
	/**
	 * 查询公司列表
	 * @return
	 */
	public List<Company> selectCompanyList();
	
	

}
