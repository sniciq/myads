package com.ku6ads.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author xuxianan
 *
 */
public class DateUtils {

	/**
	 * 格式化日期,返回字符串
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String getDateToStr(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}

}
