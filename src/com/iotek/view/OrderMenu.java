package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.OrderController;
import com.iotek.util.FilterInputMismatch;

/**
 * �û���������
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderMenu {
	/**
	 * ��ʾ����
	 * 
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1���鿴�ҵĶ�������Ϣ");
		System.out.println("2���鿴��ʷ���Ѷ�����");
		System.out.println("9�������ϼ��˵�");
		System.out.println("0���˳�");
		System.out.println("******************");
		System.out.println("======>���������ݣ�");

		int option = FilterInputMismatch.nextInt();
		return new OrderController().doOption(option);
	}
}
