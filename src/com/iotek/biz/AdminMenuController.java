package com.iotek.biz;

import com.iotek.util.GrabRadEnvelope;
import com.iotek.view.ManageGoodsMenu;
import com.iotek.view.ManageOrderMenu;
import com.iotek.view.ManageUserMenu;

/**
 * ����Ա������߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class AdminMenuController {

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
			// ������Ʒ
			i = 0;
			while (true) {
				i = new ManageGoodsMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 2:
			// ������
			i = 0;
			while (true) {
				i = new ManageOrderMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 3:
			// �����û�
			i = 0;
			while (true) {
				i = new ManageUserMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 4:
			// ����������
			new GrabRadEnvelope().rechargeRadEnv();
			break;
		case 0:
			// �������˵�
			return 2;
		default:
			System.out.println("����������������룡");
			break;
		}
		if (i == 2) {
			return 2;
		} else {
			return 0;
		}
	}
}
