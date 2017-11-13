package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.UserMenuController;
import com.iotek.util.FilterInputMismatch;

/**
 * �û�����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserMenu {
	/**
	 * ��ʾ����
	 * 
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1�������Ʒ");
		System.out.println("2���ҵĹ��ﳵ");
		System.out.println("3���ҵĶ���");
		System.out.println("4����Ҫ����");
		System.out.println("5��ȷ���ջ�");
		System.out.println("6����Ҫ����");
		System.out.println("7����Ҫ��ֵ");
		System.out.println("8���ҵ��˻�");
		System.out.println("0���˳�");
		System.out.println("******************");
		System.out.println("======>���������ݣ�");

		int option = FilterInputMismatch.nextInt();
		return new UserMenuController().doOption(option);
	}

}
