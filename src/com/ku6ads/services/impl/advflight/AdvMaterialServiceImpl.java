package com.ku6ads.services.impl.advflight;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advflight.AdvMaterial;
import com.ku6ads.dao.entity.advflight.PlayerTemplate;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.AdvMaterialService;
/**
 * 广告活动ServiceImpl
 * @author liujunshi
 *
 */

public class AdvMaterialServiceImpl extends BaseAbstractService implements AdvMaterialService {

	/*
	 * (non-Javadoc)TODO 使用事物
	 * @see com.ku6ads.services.iface.advflight.AdvMaterialService#insert(java.util.List, int)
	 */
	public void insert(List<String> list, int advId) {
		
		if(list!=null&&advId!=0){
			AdvMaterial advMaterial = null;
			for (int i=0;i<list.size();i++)
			{
				advMaterial = new AdvMaterial();
				advMaterial.setAdvId(advId);
				advMaterial.setType(list.get(i));
				advMaterial.setStatus(0);
				//物料从第1个开始而不是第0个
				advMaterial.setsNumber(i+1);
				super.insert(advMaterial);
			}
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advflight.AdvMaterialService#insert(int, int)
	 */
	public void insert(int barTemType, int advId) {
		Map<Integer,String> map = PlayerTemplate.getPlayerMap();
		AdvMaterial advMaterial = null;
		for (int i=0;i<10;i++)
		{
			advMaterial = new AdvMaterial();
			advMaterial.setAdvId(advId);
			advMaterial.setMexplain(map.get(i+1));
			
			advMaterial.setStatus(0);
			//物料从第1个开始而不是第0个
			advMaterial.setsNumber(i+1);
			super.insert(advMaterial);
		}
		switch(barTemType){
			case 1:
		}
	}
	

	
}
