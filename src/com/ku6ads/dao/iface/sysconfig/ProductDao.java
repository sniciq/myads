package com.ku6ads.dao.iface.sysconfig;

import java.util.List;
import com.ku6ads.dao.entity.sysconfig.Product;
/**
 * 产品
 * @author liujunshi
 *
 */
public interface ProductDao {
	/**
	 * 新增产品
	 * @param department
	 * @return
	 */
	public Object insertProduct(Product Product);

	/**
	 * 按照产品查询
	 * @param Product
	 * @return
	 */
	public List<Product> selectByProduct(Product Product);

	/**
	 * 更新产品信息
	 * @param Product
	 * @return
	 */
	public Integer updateProduct(Product Product);

	/**
	 * 按照id查询产品信息
	 * @param ProductId
	 * @return
	 */
	public Product selectById(int ProductId);

	/**
	 * 按照id删除产品信息
	 * @param ProductId
	 */
	public void deleteById(int ProductId);
	
	/**
	 * 按照产品线id删除产品信息
	 * @param ProductId
	 */
	public void deleteByLineId(int ProductLineId);

	/**
	 * 查询产品信息,返回集合
	 * @return
	 */
	public List<Product> selectProduct();
	
	
	/**
	 * 返回总数
	 * @return
	 */
	public int selectProductCount(Product product);
	
	

	public List<Product> selectByProductLimit(Product Product);
	
	/**
	 * 根据产品线id查询对应的产品
	 * @return
	 */
	public List<Product> selectByLineId(Integer productLineId);

}
