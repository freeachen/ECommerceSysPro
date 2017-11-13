package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.ManageUserController;
import com.iotek.util.FilterInputMismatch;

/**
 * �����û�����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageUserMenu {
	/**
	 * ��ʾ����
	 * 
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1���鿴�û���Ϣ");
		System.out.println("2���޸��û�����");
		System.out.println("3���޸��û��ȼ�");
		System.out.println("9�������ϼ��˵�");
		System.out.println("0���˳�");
		System.out.println("******************");
		System.out.println("======>���������ݣ�");

		int option = FilterInputMismatch.nextInt();
		return new ManageUserController().doOption(option);
	}
}
