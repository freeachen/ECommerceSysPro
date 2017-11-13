package com.iotek.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 解决非字符串输入不匹配问题
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class FilterInputMismatch {
	/**
	 * 过滤整型输入不匹配问题
	 * 
	 * @return 安全的整型
	 */
	public static int nextInt() {
		Scanner in = null;
		int i = 0;
		while (true) {
			try {
				in = new Scanner(System.in);
				i = in.nextInt();
				if (i < 0) {
					System.out.println("输入内容不能为负！");
					continue;
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("输入不匹配！请重新输入：");
				in.next();
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return i;
	}

	/**
	 * 过滤浮点型输入不匹配问题
	 * 
	 * @return 安全的浮点型
	 */
	public static double nextDouble() {
		Scanner in = null;
		double d = 0;
		while (true) {
			try {
				in = new Scanner(System.in);
				d = in.nextDouble();
				if (d < 0) {
					System.out.println("输入内容不能为负！");
					continue;
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("输入不匹配！请重新输入：");
				in.next();
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return d;
	}
}
