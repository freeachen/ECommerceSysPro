package com.iotek.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * ������ַ������벻ƥ������
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class FilterInputMismatch {
	/**
	 * �����������벻ƥ������
	 * 
	 * @return ��ȫ������
	 */
	public static int nextInt() {
		Scanner in = null;
		int i = 0;
		while (true) {
			try {
				in = new Scanner(System.in);
				i = in.nextInt();
				if (i < 0) {
					System.out.println("�������ݲ���Ϊ����");
					continue;
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("���벻ƥ�䣡���������룺");
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
	 * ���˸��������벻ƥ������
	 * 
	 * @return ��ȫ�ĸ�����
	 */
	public static double nextDouble() {
		Scanner in = null;
		double d = 0;
		while (true) {
			try {
				in = new Scanner(System.in);
				d = in.nextDouble();
				if (d < 0) {
					System.out.println("�������ݲ���Ϊ����");
					continue;
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("���벻ƥ�䣡���������룺");
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
