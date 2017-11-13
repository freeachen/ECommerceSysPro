package com.iotek.util;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * ��ʽ��һЩ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 *
 */
public class NumFormat {
	/**
	 * ��ʽ�������ͣ�ֻ����С�����2λ
	 * 
	 * @param d
	 *            ������
	 * @return ��ʽ����ĸ�����
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
	 * ��ʽ�������ͣ����ذٷֱȣ�������С�����2λ
	 * 
	 * @param d
	 *            ������
	 * @return ��ʽ����ĸ�����
	 */
	public static String formatPercent(Double d) {
		DecimalFormat df = new DecimalFormat(".00%");
		return df.format(d);
	}
}
