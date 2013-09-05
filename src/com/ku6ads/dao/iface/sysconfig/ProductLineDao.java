package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.ProductLine;
/**
 * 产品线
 * @author liujunshi
 *
 */
public interface ProductLineDao{
	/**
	 * 新增产品线
	 * @param department
	 * @return
	 */
	public Object insertProductLine(ProductLine ProductLine);

	/**
	 * 按照产品线查询
	 * @param ProductLine
	 * @return
	 */
	public List<ProductLine> selectByProductLine(ProductLine ProductLine);

	
	/**
	 * 按照广告主Id产品线查询
	 * @param ProductLine
	 * @return
	 */
	public List<ProductLine> selectProductLineByAdvertiser(int advertiserId);

	
	/**
	 * 更新产品线信息
	 * @param ProductLine
	 * @return
	 */
	public Integer updateProductLine(ProductLine ProductLine);

	/**
	 * 按照id查询产品线信息
	 * @param ProductLineId
	 * @return
	 */
	public ProductLine selectById(int ProductLineId);

	/**
	 * 按照id删除产品线信息
	 * @param ProductLineId
	 */
	public void deleteById(int ProductLineId);
	
	/**
	 * 按照广告主id删除产品线信息
	 * @param ProductLineId
	 */
	public void deleteByAdvertiserId(int advertiserId);


	/**
	 * 查询产品线信息,返回集合
	 * @return
	 */
	public List<ProductLine> selectProductLine();

	public Integer selectBytProductLineCount(ProductLine productLine);
	
	public List<ProductLine> selectByProductLineLimit(ProductLine productLine);
	


}
