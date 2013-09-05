package com.ku6ads.dao.entity.advflight;

import java.util.HashMap;
import java.util.Map;

public class PlayerTemplate {

	private static Map<Integer,String> PlayerMap;

	public static Map<Integer,String> getPlayerMap() {
		PlayerMap = new HashMap();
		PlayerMap.put(1, "前贴片广告物料地址，jpg/gif/flv");
		PlayerMap.put(2, "配合前贴片一起出现的其他物料地址,jpg,swf");
		PlayerMap.put(3, "前贴片广告前贴片的曝光统计（内部系统统计）料地址");
		PlayerMap.put(4, "前贴片的广告点击地址");
		PlayerMap.put(5, "形第三方曝光监测地址");
	
		
		return PlayerMap;
	}

	
}
