package com.iotek.util;

import java.util.Random;

/**
 * �����֤��
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class RandomGen {
	/**
	 * ��ȡ4λ�����֤��
	 * 
	 * @return 4λ�����֤��
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
	 * �����������������������
	 * 
	 * @return �������
	 */
	public static int arithmeticGen() {
		// �����ȡ1~20
		int i = (int) (Math.random() * 20 + 1);
		int j = (int) (Math.random() * 20 + 1);
		int res = 0;
		// ����������ʽ
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
