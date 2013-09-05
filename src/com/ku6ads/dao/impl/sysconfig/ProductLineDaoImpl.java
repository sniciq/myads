package com.ku6ads.dao.impl.sysconfig;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ku6ads.dao.entity.sysconfig.ProductLine;
import com.ku6ads.dao.iface.sysconfig.ProductLineDao;
/**
 * 产品线
 * @author liujunshi
 *
 */
public class ProductLineDaoImpl  extends SqlMapClientDaoSupport implements ProductLineDao {

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#deleteById(int)
	 */
	public void deleteById(int ProductLineId) {
		getSqlMapClientTemplate().delete("sysconfig.ProductLine.deleteById", ProductLineId);

	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#insertProductLine(com.ku6ads.dao.entity.sysconfig.ProductLine)
	 */
	public Object insertProductLine(ProductLine ProductLine) {
		return getSqlMapClientTemplate().insert("sysconfig.ProductLine.insertProductLine", ProductLine);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#selectById(int)
	 */
	public ProductLine selectById(int ProductLineId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#selectByProductLine(com.ku6ads.dao.entity.sysconfig.ProductLine)
	 */
	public List<ProductLine> selectByProductLine(ProductLine ProductLine) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#selectByProductLineLimit(com.ku6ads.dao.entity.sysconfig.ProductLine)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductLine> selectByProductLineLimit(ProductLine productLine) {
		
		return getSqlMapClientTemplate().queryForList("sysconfig.ProductLine.selectByProductLineLimit", productLine);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#selectBytProductLineCount(com.ku6ads.dao.entity.sysconfig.ProductLine)
	 */
	@Override
	public Integer selectBytProductLineCount(ProductLine productLine) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject("sysconfig.ProductLine.selectByProductLineCount",productLine);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#selectProductLine()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductLine> selectProductLine() {
		return getSqlMapClientTemplate().queryForList("sysconfig.ProductLine.selectByEntity");
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#updateProductLine(com.ku6ads.dao.entity.sysconfig.ProductLine)
	 */
	@Override
	public Integer updateProductLine(ProductLine ProductLine) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#selectProductLineByAdvertiser(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductLine> selectProductLineByAdvertiser(int advertiserId) {
		return getSqlMapClientTemplate().queryForList("sysconfig.ProductLine.selectByAdvertiser",advertiserId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ProductLineDao#deleteByAdvertiserId(int)
	 */
	@Override
	public void deleteByAdvertiserId(int advertiserId) {
		getSqlMapClientTemplate().delete("sysconfig.ProductLine.deleteByAdvertiserId", advertiserId);
	}

}
