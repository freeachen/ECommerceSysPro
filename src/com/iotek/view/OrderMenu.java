package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.OrderController;
import com.iotek.util.FilterInputMismatch;

/**
 * 用户订单界面
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderMenu {
	/**
	 * 显示界面
	 * 
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1、查看我的订单项信息");
		System.out.println("2、查看历史消费订单项");
		System.out.println("9、返回上级菜单");
		System.out.println("0、退出");
		System.out.println("******************");
		System.out.println("======>请输入内容：");

		int option = FilterInputMismatch.nextInt();
		return new OrderController().doOption(option);
	}
}
