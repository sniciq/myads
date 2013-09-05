package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.sysconfig.Product;
import com.ku6ads.dao.iface.sysconfig.ProductDao;
/**
 * 产品
 * @author liujunshi
 *
 */
public class ProductDaoImpl extends  SqlMapClientDaoSupport implements ProductDao {

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductDao#deleteById(int)
	 */
	public void deleteById(int ProductId) {
		getSqlMapClientTemplate().delete("sysconfig.Product.deleteById", ProductId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductDao#insertProduct(com.ku6ads.dao.entity.sysconfig.Product)
	 */
	public Object insertProduct(Product Product) {
		return getSqlMapClientTemplate().insert("sysconfig.Product.insertProduct", Product);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductDao#selectById(int)
	 */
	public Product selectById(int ProductId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductDao#selectByProduct(com.ku6ads.dao.entity.sysconfig.Product)
	 */
	public List<Product> selectByProduct(Product Product) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductDao#selectByProductLimit(com.ku6ads.dao.entity.sysconfig.Product)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> selectByProductLimit(Product Product) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Product.selectByProductLimit", Product);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductDao#selectProduct()
	 */
	@SuppressWarnings("unchecked")
	public List<Product> selectProduct() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Product.selectByEntity");
	}

	@Override
	public Integer updateProduct(Product Product) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductDao#selectProductCount(com.ku6ads.dao.entity.sysconfig.Product)
	 */
	@Override
	public int selectProductCount(Product product) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sysconfig.Product.selectByProductCount",product);
		}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductDao#deleteByLineId(int)
	 */
	@Override
	public void deleteByLineId(int ProductLineId) {
		getSqlMapClientTemplate().delete("sysconfig.Product.deleteByLineId", ProductLineId);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> selectByLineId(Integer productLineId) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Product.selectByLineId", productLineId);
	}

}
