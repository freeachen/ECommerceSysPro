package com.iotek.view;

import java.util.Scanner;

import com.iotek.biz.BrowseGoodsController;
import com.iotek.util.FilterInputMismatch;

/**
 * 浏览商品界面
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class BrowseGoodsMenu {
	/**
	 * 显示界面
	 * 
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int show() {
		Scanner in = new Scanner(System.in);
		System.out.println("******************");
		System.out.println("1、查看全部商品");
		System.out.println("2、查看近期促销商品");
		System.out.println("3、按分类查询");
		System.out.println("4、按价格从低到高");
		System.out.println("5、按价格从高到低");
		System.out.println("9、返回上级菜单");
		System.out.println("0、退出");
		System.out.println("******************");
		System.out.println("======>请输入内容：");

		int option = FilterInputMismatch.nextInt();
		return new BrowseGoodsController().doOption(option);
	}
}
