package com.iotek.util;

/**
 * �ж������ʽ
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 *
 */
public class CheckEmail {
	/**
	 * ͨ��������ʽ���������ʽ
	 * 
	 * @param str
	 *            �����ַ���
	 * @return true or false
	 */
	public static boolean checkEmail(String str) {
		String regex = "\\w+@\\w+(\\.\\w{2,3}){1,2}";
		if (str.matches(regex)) {
			return true;
		} else {
			System.out.println("�����ʽ�������������룡");
			return false;
		}
	}
}
