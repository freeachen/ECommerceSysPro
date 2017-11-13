package com.iotek.util;

import java.util.Random;

/**
 * 随机验证码
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class RandomGen {
	/**
	 * 获取4位随机验证码
	 * 
	 * @return 4位随机验证码
	 */
	public static String codeGen() {
		int count = 0;
		String str = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		char[] charArray = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		while (true) {
			char c = charArray[random.nextInt(62)];
			if (sb.indexOf(c + "") == -1) {
				sb.append(c);
				count++;
				if (count == 4) {
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 随机产生两个整数的算术题
	 * 
	 * @return 算术结果
	 */
	public static int arithmeticGen() {
		// 随机获取1~20
		int i = (int) (Math.random() * 20 + 1);
		int j = (int) (Math.random() * 20 + 1);
		int res = 0;
		// 随机输出算术式
		switch ((int) (Math.random() * 3)) {
		case 0:
			System.out.println(i + " + " + j + " = ?");
			res = i + j;
			break;
		case 1:
			System.out.println(i + " * " + j + " = ?");
			res = i * j;
			break;
		case 2:
			if (i >= j) {
				System.out.println(i + " - " + j + " = ?");
				res = i - j;
			} else {
				System.out.println(j + " - " + i + " = ?");
				res = j - i;
			}
			break;

		default:
			break;
		}
		return res;
	}
}
