package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.AdminMenuController;
import com.iotek.util.FilterInputMismatch;

/**
 * 管理员界面
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class AdminMenu {

	/**
	 * 显示界面
	 * 
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1、管理商品");
		System.out.println("2、管理订单");
		System.out.println("3、管理用户");
		System.out.println("4、开启抢红包活动");
		System.out.println("0、退出");
		System.out.println("******************");
		System.out.println("======>请输入内容：");

		int option = FilterInputMismatch.nextInt();
		return new AdminMenuController().doOption(option);
	}
}
