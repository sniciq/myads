package com.ku6ads.dao.iface.advproduct;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advproduct.AdvproductEty;

public interface AdvproductDao extends BaseDao {

	List selectByAdvproductName(AdvproductEty ety);
}