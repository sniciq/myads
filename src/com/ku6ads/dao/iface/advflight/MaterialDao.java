package com.ku6ads.dao.iface.advflight;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.Material;

/**
 * 物料DAO
 * @author liujunshi
 *
 */
public interface MaterialDao extends BaseDao {

	public int insertMaterial(Material material);
	
	
}
