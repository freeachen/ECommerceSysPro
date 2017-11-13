package com.iotek.biz;

import com.iotek.util.GrabRadEnvelope;
import com.iotek.view.ManageGoodsMenu;
import com.iotek.view.ManageOrderMenu;
import com.iotek.view.ManageUserMenu;

/**
 * 管理员界面的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class AdminMenuController {

	/**
	 * 执行选项的方法
	 * 
	 * @param option
	 *            选项
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int doOption(int option) {
		int i = 0;
		switch (option) {
		case 1:
			// 管理商品
			i = 0;
			while (true) {
				i = new ManageGoodsMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 2:
			// 管理订单
			i = 0;
			while (true) {
				i = new ManageOrderMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 3:
			// 管理用户
			i = 0;
			while (true) {
				i = new ManageUserMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 4:
			// 开启抢红包活动
			new GrabRadEnvelope().rechargeRadEnv();
			break;
		case 0:
			// 返回主菜单
			return 2;
		default:
			System.out.println("输入错误，请重新输入！");
			break;
		}
		if (i == 2) {
			return 2;
		} else {
			return 0;
		}
	}
}
