package com.ku6ads.services.iface.advflight;

import java.util.List;

import com.ku6ads.services.base.BaseServiceIface;
/**
 * 广告物料
 * @author liujunshi
 *
 */
public interface AdvMaterialService extends BaseServiceIface{

	/**
	 * 插入广告与多天物料的关系
	 * @param list
	 */
	public void insert(List<String> list,int advId);
	
	/**
	 * 按模板类型田间关系，前贴片后铁片
	 * @param barTemType
	 * @param advId
	 */
	public void insert(int barTemType,int advId);
}
