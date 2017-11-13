package com.iotek.util;

import java.text.DateFormat;
import java.util.Date;

/**
 * 获取时间的工具类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class GetDate {
	private static DateFormat df = DateFormat.getDateTimeInstance();// 获取实例对象

	/**
	 * 获取当前系统时间的字符串
	 * 
	 * @return 日期的字符串
	 */
	public static String getNowTime() {// 2017-5-18 20:06:28
		Date date = new Date();
		return df.format(date);
	}

	
	public static void main(String[] args) {
		System.out.println(getNowTime());
	}
}
