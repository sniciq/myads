package com.ku6ads.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.Resource;

/**
 * 
 * properties工具类
 * @author xuxianan
 *
 */
public class PropertiesUtils {
	private static Properties propertie;

	/**
	 * 从文件中初始化配置文件
	 * @param file
	 */
	public static void load(File file){
		propertie = new Properties();
		try {
			FileInputStream is = new FileInputStream(file);
			propertie.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * spring的配置文件初始化方式
	 * @param resource
	 */
	public static void load(Resource resource) {
		try {
			load(resource.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过key得到value
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		if (propertie != null) {
			String result = propertie.getProperty(key);
			if (result != null)
				return result.trim();

		}
		return null;
	}

}
