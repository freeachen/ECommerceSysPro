package com.iotek.biz;

import java.util.Scanner;

import com.iotek.bean.User;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.util.GrabRadEnvelope;
import com.iotek.view.AdminMenu;
import com.iotek.view.UserMenu;

/**
 * ��½������߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class MainMenuController {
	public static User user = null;

	/**
	 * ִ��ѡ��ķ���
	 * 
	 * @param option
	 *            ѡ��
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public void doOption(int option) {
		switch (option) {
		case 1:
			// ע��
			register();
			break;
		case 2:
			// ��½
			checkLogin(login());
			break;
		case 0:
			// �˳�ϵͳ
			MainMenuController.exit();
			break;
		default:
			System.out.println("����������������룡");
			break;
		}
	}

	/**
	 * ע��
	 * 
	 * @return �û�����
	 */
	public void register() {
		Scanner in = new Scanner(System.in);
		User user = new User();
		// �û����������
		user = new ManageUserController().checkUserName(user);
		// ����ȷ����֤
		user = new ManageUserController().checkPwd(user);
		System.out.println("�������ջ���ַ��");
		String address = in.nextLine();
		user.setAddress(address);
		// �����ʽ�޶�
		user = new ManageUserController().checkEmailFormat(user);
		// �û���ʼ�ȼ�1
		user.setLevel(1);
		boolean falg = new UserDaoImpl().insert(user);
		if (falg) {
			System.out.println("ע��ɹ���");
		} else {
			System.err.println("ע��ʧ�ܣ�");
		}
	}

	/**
	 * ��½
	 */
	public User login() {
		Scanner in = new Scanner(System.in);
		System.out.println("�������û�����");
		String userName = in.nextLine();
		System.out.println("���������룺");
		String pwd = in.nextLine();

		User u = new User(userName, pwd, null, null, 0, 1, 0);
		return u;
	}

	/**
	 * ��½��֤
	 */
	private void checkLogin(User u) {
		int i = 0;
		// �жϵ�½�û������
		user = new UserDaoImpl().selectByUserNameAndPwd(u);
		if (user != null) {
			// ����Ա��½
			if (user.getUserName().equals("admin") && user.getPwd().equals("admin")) {
				System.out.println("����Ա��½�ɹ���");
				while (true) {
					i = new AdminMenu().show();
					if (i != 0) {
						break;
					}
				}
			} else {
				// ��鲢�����������Ϸ
				GrabRadEnvelope.showRadEnv();
				// �û���½
				System.out.println(user.getUserName() + "����ӭ���٣�");
				while (true) {
					if (new UserMenu().show() != 0) {
						break;
					}
				}
			}
		} else {
			System.err.println("��½ʧ�ܣ�");
		}
	}

	/**
	 * �˳�ϵͳ
	 */
	public static void exit() {
		System.out.println("��ӭ�´�������");
		System.exit(0);
	}
}
