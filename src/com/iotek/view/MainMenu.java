package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.MainMenuController;
import com.iotek.util.FilterInputMismatch;

/**
 * ��½����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class MainMenu {
	/**
	 * ��ʾ����
	 */
	public void show() {
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("==================");
			System.out.println("��ӭ���٣��칷�̳ǣ�");
			System.out.println("==================");
			System.out.println("******************");
			System.out.println("1��ע��");
			System.out.println("2����½");
			System.out.println("0���˳�");
			System.out.println("******************");
			System.out.println("======>���������ݣ�");

			int option = FilterInputMismatch.nextInt();
			new MainMenuController().doOption(option);
		}
	}
}
