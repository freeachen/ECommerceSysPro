package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.ShoppingCartController;
import com.iotek.util.FilterInputMismatch;

/**
 * ���ﳵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ShoppingCartMenu {
	/**
	 * ��ʾ����
	 * 
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1���鿴���ﳵ");
		System.out.println("2�������Ʒ�����ﳵ");
		System.out.println("3���޸���Ʒ����");
		System.out.println("4��ɾ����Ʒ");
		System.out.println("5��ȷ���µ�");
		System.out.println("9�������ϼ��˵�");
		System.out.println("0���˳�");
		System.out.println("******************");
		System.out.println("======>���������ݣ�");

		int option = FilterInputMismatch.nextInt();
		return new ShoppingCartController().doOption(option);
	}

}
