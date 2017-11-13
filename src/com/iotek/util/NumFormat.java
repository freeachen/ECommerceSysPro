package com.iotek.util;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 格式化一些数据
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 *
 */
public class NumFormat {
	/**
	 * 格式化浮点型，只保留小数点后2位
	 * 
	 * @param d
	 *            浮点型
	 * @return 格式化后的浮点型
	 */
	public static double formatDouble(Double d) {
		DecimalFormat df = new DecimalFormat(".00");
		double dd = 0;
		Number parse = null;
		try {
			parse = df.parse(df.format(d));
			dd = parse.doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dd;

	}

	/**
	 * 格式化浮点型，返回百分比，并保留小数点后2位
	 * 
	 * @param d
	 *            浮点型
	 * @return 格式化后的浮点型
	 */
	public static String formatPercent(Double d) {
		DecimalFormat df = new DecimalFormat(".00%");
		return df.format(d);
	}
}
