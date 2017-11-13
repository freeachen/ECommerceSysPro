package com.iotek.biz;

import com.iotek.view.BrowseGoodsMenu;
import com.iotek.view.CommentMenu;
import com.iotek.view.OrderMenu;
import com.iotek.view.ShoppingCartMenu;
import com.iotek.view.UserAccountMenu;

/**
 * �û�������߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserMenuController {
	/**
	 * ִ��ѡ��ķ���
	 * 
	 * @param option
	 *            ѡ��
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int doOption(int option) {
		int i = 0;
		switch (option) {
		case 1:
			// �����Ʒ
			i = 0;
			new BrowseGoodsMenu().show();
			break;
		case 2:
			// ���ﳵ
			i = 0;
			while (true) {
				i = new ShoppingCartMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 3:
			// �û�����
			i = 0;
			while (true) {
				i = new OrderMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 4:
			// ����
			i = 0;
			new OrderController().payMoney();
			break;
		case 5:
			// �ջ�
			i = 0;
			new OrderController().receiveGoods();
			break;
		case 6:
			// ����
			i = 0;
			while (true) {
				i = new CommentMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 7:
			// ��ֵ
			i = 0;
			new UserAccountController().recharge();
			break;
		case 8:
			// �˻�
			i = 0;
			while (true) {
				i = new UserAccountMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 0:
			// �˳�
			return 2;
		default:
			System.out.println("�޴�ѡ����������룡");
			break;
		}
		if (i == 2) {
			return 2;
		} else {
			return 0;
		}
	}
}
