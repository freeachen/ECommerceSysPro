package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.ManageOrderInnerController;
import com.iotek.util.FilterInputMismatch;

/**
 * �������ӽ���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageOrderInnerMenu {
	/**
	 * ��ʾ����
	 * 
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1���޸Ķ��������ж�����״̬");
		System.out.println("2���޸�ָ��������״̬");
		System.out.println("9�������ϼ��˵�");
		System.out.println("0���˳�");
		System.out.println("******************");
		System.out.println("======>���������ݣ�");

		int option = FilterInputMismatch.nextInt();
		return new ManageOrderInnerController().doOption(option);
	}
}
