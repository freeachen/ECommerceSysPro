package com.iotek.util;

import java.text.DateFormat;
import java.util.Date;

/**
 * ��ȡʱ��Ĺ�����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class GetDate {
	private static DateFormat df = DateFormat.getDateTimeInstance();// ��ȡʵ������

	/**
	 * ��ȡ��ǰϵͳʱ����ַ���
	 * 
	 * @return ���ڵ��ַ���
	 */
	public static String getNowTime() {// 2017-5-18 20:06:28
		Date date = new Date();
		return df.format(date);
	}

	
	public static void main(String[] args) {
		System.out.println(getNowTime());
	}
}
