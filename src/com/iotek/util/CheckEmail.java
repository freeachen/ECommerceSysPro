package com.iotek.util;

/**
 * 判断邮箱格式
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 *
 */
public class CheckEmail {
	/**
	 * 通过正则表达式检验邮箱格式
	 * 
	 * @param str
	 *            邮箱字符串
	 * @return true or false
	 */
	public static boolean checkEmail(String str) {
		String regex = "\\w+@\\w+(\\.\\w{2,3}){1,2}";
		if (str.matches(regex)) {
			return true;
		} else {
			System.out.println("输入格式错误，请重新输入！");
			return false;
		}
	}
}
