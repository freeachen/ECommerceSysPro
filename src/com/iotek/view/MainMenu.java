package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.MainMenuController;
import com.iotek.util.FilterInputMismatch;

/**
 * 登陆界面
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class MainMenu {
	/**
	 * 显示界面
	 */
	public void show() {
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("==================");
			System.out.println("欢迎光临，天狗商城！");
			System.out.println("==================");
			System.out.println("******************");
			System.out.println("1、注册");
			System.out.println("2、登陆");
			System.out.println("0、退出");
			System.out.println("******************");
			System.out.println("======>请输入内容：");

			int option = FilterInputMismatch.nextInt();
			new MainMenuController().doOption(option);
		}
	}
}
