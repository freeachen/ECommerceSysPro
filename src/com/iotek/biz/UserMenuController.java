package com.iotek.biz;

import com.iotek.view.BrowseGoodsMenu;
import com.iotek.view.CommentMenu;
import com.iotek.view.OrderMenu;
import com.iotek.view.ShoppingCartMenu;
import com.iotek.view.UserAccountMenu;

/**
 * 用户界面的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserMenuController {
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
			// 浏览商品
			i = 0;
			new BrowseGoodsMenu().show();
			break;
		case 2:
			// 购物车
			i = 0;
			while (true) {
				i = new ShoppingCartMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 3:
			// 用户订单
			i = 0;
			while (true) {
				i = new OrderMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 4:
			// 付款
			i = 0;
			new OrderController().payMoney();
			break;
		case 5:
			// 收货
			i = 0;
			new OrderController().receiveGoods();
			break;
		case 6:
			// 评论
			i = 0;
			while (true) {
				i = new CommentMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 7:
			// 充值
			i = 0;
			new UserAccountController().recharge();
			break;
		case 8:
			// 账户
			i = 0;
			while (true) {
				i = new UserAccountMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 0:
			// 退出
			return 2;
		default:
			System.out.println("无此选项，请重新输入！");
			break;
		}
		if (i == 2) {
			return 2;
		} else {
			return 0;
		}
	}
}
