package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.BrowseGoodsController;
import com.iotek.util.FilterInputMismatch;

/**
 * �����Ʒ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class BrowseGoodsMenu {
	/**
	 * ��ʾ����
	 * 
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1���鿴ȫ����Ʒ");
		System.out.println("2���鿴���ڴ�����Ʒ");
		System.out.println("3���������ѯ");
		System.out.println("4�����۸�ӵ͵���");
		System.out.println("5�����۸�Ӹߵ���");
		System.out.println("9�������ϼ��˵�");
		System.out.println("0���˳�");
		System.out.println("******************");
		System.out.println("======>���������ݣ�");

		int option = FilterInputMismatch.nextInt();
		return new BrowseGoodsController().doOption(option);
	}
}
