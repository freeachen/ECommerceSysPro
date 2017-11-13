package com.iotek.biz;

import java.util.List;
import java.util.Scanner;

import com.iotek.bean.User;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.util.CheckEmail;
import com.iotek.util.FilterInputMismatch;

/**
 * �����û����߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageUserController {
	/**
	 * ִ��ѡ��ķ���
	 * 
	 * @param option
	 *            ѡ��
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int doOption(int option) {
		switch (option) {
		case 1:
			// ��ʾ�����û���Ϣ
			list();
			break;
		case 2:
			// �޸��û�����
			update(0);
			break;
		case 3:
			// �޸��û��ȼ�
			update(1);
			break;
		case 9:
			// �����ϼ��˵�
			return 1;
		case 0:
			// �˳�
			return 2;
		default:
			System.out.println("�޴�ѡ����������룡");
			break;
		}
		return 0;
	}

	/**
	 * ��ʾ�û���Ϣ�ķ���
	 */
	private void list() {
		List<User> list = new UserDaoImpl().selectAll();
		System.out.println("�û�ID\t�û���\t�û�����\t�ջ���ַ\t����\t\t����ֵ\t�ȼ�\t���");
		for (User user : list) {
			System.out.println(user);
		}
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param i
	 *            �ɴ˾��������ĸ��ֶΣ�0-�޸����룬1-�޸ĵȼ���2-��ַ������
	 * @return true or false
	 */
	public void update(int i) {
		User user = null;
		Scanner in = new Scanner(System.in);

		// ����Աֻ��Ҫͨ���û������ܻ�ȡ���û���Ϣ
		System.out.println("�������û�����");
		String userName = in.nextLine();
		user = new User(userName, null, null, null, 0, 0, 0);
		user = new UserDaoImpl().selectByUserName(user);
		if (user == null) {
			System.err.println("���û������ڣ�");
		} else {
			user = updateUser(i, user);
		}
		if (user != null) {
			boolean flag = new UserDaoImpl().update(user);
			if (flag) {
				// System.out.println("���³ɹ���");
			} else {
				System.out.println("����ʧ�ܣ�");
			}
		}
	}

	/**
	 * �����û���Ϣ��ʵ�ʲ�������
	 * 
	 * @param i
	 *            �ɴ˾��������ĸ��ֶΣ�0-�޸����룬1-�޸ĵȼ���2-��ַ������
	 * @param user
	 *            Ҫ���µ��û�����
	 * @return User ���º���û�����
	 */
	public User updateUser(int i, User user) {
		Scanner in = new Scanner(System.in);
		switch (i) {
		case 0:
			// ����ע��ʱ��������
			user = checkPwd(user);
			break;
		case 1:
			// �����û�����ֵ���ﵽ�����û��ȼ���Ŀ��
			System.out.println("�������û�����ֵ��");
			System.out.println("��1-1000��2-5000��3-20000��4-50000,5-100000��");
			int exp = FilterInputMismatch.nextInt();
			user.setExp(exp);
			user.calcLevel();
			break;
		case 2:
			// �û������Լ���ַ������
			System.out.println("�������µ��ջ���ַ��");
			String address = in.nextLine();
			user.setAddress(address);
			user = checkEmailFormat(user);
			break;
		default:
			break;
		}
		return user;
	}

	/**
	 * �ж������ʽ
	 * 
	 * @param user
	 * @return user
	 */
	public User checkEmailFormat(User user) {
		Scanner in = new Scanner(System.in);
		// �����ʽ�ж�
		while (true) {
			System.out.println("��������ȷ�������ʽ��");
			System.out.println("���磺123@123.com.cn��123@123.com");
			String email = in.nextLine();
			// ���ù���������ķ���
			if (CheckEmail.checkEmail(email)) {
				user.setEmail(email);
				return user;
			} else {
				continue;
			}
		}
	}

	/**
	 * ����Ķ���������֤
	 * 
	 * @param user
	 * @return user
	 */
	public User checkPwd(User user) {
		Scanner in = new Scanner(System.in);
		// �������ȷ��
		while (true) {
			System.out.println("�����������룺");
			String pwd = in.nextLine();
			System.out.println("���ٴ�ȷ�����룺");
			String pwd2 = in.nextLine();
			if (pwd2.equals(pwd)) {
				user.setPwd(pwd);
				return user;
			} else {
				System.out.println("���벻ƥ�䣡���������룡");
				continue;
			}
		}
	}

	/**
	 * ����û�����
	 * 
	 * @param user
	 * @return user
	 */
	public User checkUserName(User user) {
		Scanner in = new Scanner(System.in);
		// �û��������ж�
		while (true) {
			String userName;
			while (true) {
				System.out.println("�������û�����");
				userName = in.nextLine();
				// �û������벻�ܴ��ո�
				if (checkBlank(userName)) {
					break;
				} else {
					System.out.println("ע���û������ܴ��ո�");
					continue;
				}
			}
			user.setUserName(userName);
			// ��ѯ���ݿ��ж��û����Ƿ��Ѿ�����
			if (new UserDaoImpl().selectByUserName(user) != null) {
				System.out.println("���û����Ѵ������������룡");
				continue;
			} else {
				return user;
			}
		}
	}

	/**
	 * ���������ַ������ݴ��ո�
	 * 
	 * @param str
	 *            ԭ�ַ���
	 * @return true or false
	 */
	public boolean checkBlank(String str) {
		// �����ַ������Ƿ���ո�
		char[] c = str.toCharArray();
		for (char d : c) {
			if (d == ' ') {
				return false;
			}
		}
		return true;
	}

	/**
	 * ���ݵ�ǰ�û��ȼ������ۿ�ϵ��
	 * 
	 * @return �ۿ�ϵ��
	 */
	public static double checkUserLevel() {
		// �����û��ȼ������ۿ�ϵ��
		switch (MainMenuController.user.getLevel()) {
		case 1:
			return 1;
		case 2:
			return 0.95;
		case 3:
			return 0.9;
		case 4:
			return 0.85;
		case 5:
			return 0.8;
		default:
			return 1;
		}
	}
}
