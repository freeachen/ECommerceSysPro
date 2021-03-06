package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.UserMenuController;
import com.iotek.util.FilterInputMismatch;

/**
 * 用户界面
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserMenu {
	/**
	 * 显示界面
	 * 
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1、浏览商品");
		System.out.println("2、我的购物车");
		System.out.println("3、我的订单");
		System.out.println("4、我要付款");
		System.out.println("5、确认收货");
		System.out.println("6、我要评论");
		System.out.println("7、我要充值");
		System.out.println("8、我的账户");
		System.out.println("0、退出");
		System.out.println("******************");
		System.out.println("======>请输入内容：");

		int option = FilterInputMismatch.nextInt();
		return new UserMenuController().doOption(option);
	}

}
