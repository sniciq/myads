package com.ku6ads.dao.impl.advflight;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.Material;
import com.ku6ads.dao.iface.advflight.AdvActiveDao;
import com.ku6ads.dao.iface.advflight.MaterialDao;
/**
 * 物料DAO
 * @author liujunshi
 *
 */
public class MaterialDaoImpl extends BaseAbstractDao implements MaterialDao {

	@Override
	public int insertMaterial(Material material) {
		return  Integer.parseInt(getSqlMapClientTemplate().insert(getMapping().getInsertMethod(), material).toString());
	}

}
