package com.ku6ads.util;

/**
 * 各种类型转换类
 * @author liujunshi
 *
 */
public class TypeConverterUtil {

	/**
	 * String 转成 int 如果是null是NUll返回0
	 * @param s
	 * @return
	 */
	public static int parseInt(String s) {

		int res = 0;
		if (s != null && !s.trim().equals("")) {
			try {
				res = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/**
	 * String 转成 double 如果是null是NUll返回0
	 * @return
	 */
	public static double parseDouble(String param) {
		double params = 0;
		if (param != null && !param.trim().equals("")) {
			try {
				params = Double.parseDouble(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return params;
	}
}
