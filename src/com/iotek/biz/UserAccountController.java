package com.iotek.biz;

import java.util.List;
import java.util.Scanner;

import com.iotek.bean.Order;
import com.iotek.bean.OrderDetail;
import com.iotek.bean.ShoppingDetail;
import com.iotek.bean.User;
import com.iotek.db.dao.impl.OrderDaoImpl;
import com.iotek.db.dao.impl.OrderDetailDaoImpl;
import com.iotek.db.dao.impl.ShoppingDetailDaoImpl;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.util.NumFormat;
import com.iotek.util.RandomGen;

/**
 * �û��˻�������߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserAccountController {
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
			// ��ʾ�û����˻�
			showMyAccount();
			break;
		case 2:
			// �����û���Ϣ
			updateMyInfo();
			break;
		case 3:
			// �޸�����
			updateMyPwd();
			break;
		case 4:
			// ע��
			return logout();
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
	 * �鿴������Ϣ
	 */
	public void showMyAccount() {
		// ����ָ����ʽ�������
		System.out.println("�û�����" + MainMenuController.user.getUserName());
		System.out.println("�ȼ���" + MainMenuController.user.showExp());
		double d = NumFormat.formatDouble((ManageUserController.checkUserLevel() * 10));
		System.out.println("�ȼ��ۿۣ�" + d + "��");
		System.out.println("�˻���" + NumFormat.formatDouble(MainMenuController.user.getBalance()) + "Ԫ");
		System.out.println("�ջ���ַ��" + MainMenuController.user.getAddress());
		System.out.println("���䣺" + MainMenuController.user.getEmail());
	}

	/**
	 * �޸ĸ�����Ϣ
	 */
	public void updateMyInfo() {
		// �޸��û����ջ���ַ������
		User u = new ManageUserController().updateUser(2,MainMenuController.user);
		if (u != null) {
			boolean flag = new UserDaoImpl().update(u);
			if (flag) {
				System.out.println("���³ɹ���");
			} else {
				System.out.println("����ʧ�ܣ�");
			}
		}
	}

	/**
	 * �޸��û�����
	 */
	public void updateMyPwd() {
		Scanner in = new Scanner(System.in);
		User u = null;
		while (true) {
			// ����������֤
			System.out.println("���������룺");
			String pwd = in.nextLine();
			if (!pwd.equals(MainMenuController.user.getPwd())) {
				System.out.println("�������");
				continue;
			} else {
				u = MainMenuController.user;
				u = new ManageUserController().updateUser(0, u);
				if (u != null) {
					boolean flag = new UserDaoImpl().update(u);
					if (flag) {
						System.out.println("���³ɹ���");
					} else {
						System.out.println("����ʧ�ܣ�");
					}
					break;
				}
			}
		}

	}

	/**
	 * �û�ע��
	 */
	public int logout() {
		Scanner in = new Scanner(System.in);
		User u = null;
		while (true) {
			// ����������֤
			System.out.println("���������룺");
			String pwd = in.nextLine();
			if (!pwd.equals(MainMenuController.user.getPwd())) {
				System.out.println("�������");
				continue;
			} else {
				u = MainMenuController.user;
				System.out.println("ȷ��Ҫע���˺ţ�");
				System.out.println("��һ��ע��������������Ϣ�������һأ������ز�������");
				System.out.println("��1-ȡ����0-ȷ�� ��");
				int i = FilterInputMismatch.nextInt();
				if (i == 1) {
					return 0;
				} else if (i == 0) {
					// ��֤����֤
					if (RandomGenCheck()) {
						return deleteUserInfo(u);
					} else {
						System.out.println("��֤ʧ�ܣ�");
						return 0;
					}
				} else {
					System.out.println("�޴�ѡ�");
					continue;
				}
			}
		}
	}

	/**
	 * ɾ���û��Ĳ���
	 * 
	 * @param u
	 *            �û�
	 */
	private int deleteUserInfo(User u) {
		try {
			System.out.println("���ڰ����˻����" + u.getBalance() + "Ԫ�����������п�������");
			Thread.sleep(1500);
			System.out.println("����ɾ�����Ķ���������");
			Thread.sleep(1000);
			System.out.println("����������Ĺ��ﳵ������");
			Thread.sleep(1000);
			// �����û�����
			List<Order> list = new OrderDaoImpl().selectByUid();
			for (Order o : list) {
				// ��ɾ�������Ŷ�Ӧ�Ķ�����
				List<OrderDetail> list2 = new OrderDetailDaoImpl().selectByOid(o.getId());
				for (OrderDetail od : list2) {
					new OrderDetailDaoImpl().delete(od);
				}
				new OrderDaoImpl().delete(o);
			}
			// ��ո��û��Ĺ�����
			List<ShoppingDetail> list3 = new ShoppingDetailDaoImpl().select();
			for (ShoppingDetail sd : list3) {
				new ShoppingDetailDaoImpl().delete(sd);
			}
			// ɾ�����û�
			new UserDaoImpl().delete();
			System.out.println("�ѳɹ�ע�������˻���");
			System.out.println("2��������Զ��˳�ϵͳ��");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("�޷��ɹ�ɾ����");
			return 0;
		}
		return 2;
	}

	/**
	 * ��֤�빦��
	 * 
	 * @return true or false
	 */
	public boolean RandomGenCheck() {
		Scanner in = new Scanner(System.in);
		int count = 0;
		while (count < 2) {
			System.out.println("������һ��4λ��֤�루�ɺ��Դ�Сд����");
			String code = RandomGen.codeGen();
			System.out.println("��֤�룺" + code);
			String str = in.nextLine();
			count++;
			if (str.equalsIgnoreCase(code)) {
				return true;
			} else {
				System.out.println("��֤�벻ƥ�䣡��ʣ" + (2 - count) + "�λ��ᣡ");
				continue;
			}
		}
		return false;
	}

	/**
	 * �û���ֵ
	 * 
	 * @return true or false
	 */
	public boolean recharge() {
		System.out.println("��ӭʹ�ÿ��֧����");
		System.out.println("������Ҫ��ֵ�Ľ�");
		System.out.println("��0-�˳���ֵ��");
		double i = FilterInputMismatch.nextDouble();
		if (i < 0) {
			System.out.println("���������Ŷ��");
			return false;
		} else if (i == 0) {
			System.out.println("�˳���ֵ��");
			return false;
		} else {
			// ��ֵ�������û����
			i += MainMenuController.user.getBalance();
			MainMenuController.user.setBalance(i);
			boolean flag = new UserDaoImpl().update(MainMenuController.user);
			if (flag) {
				System.out.println("֧���ɹ�����ȥ���򱦱���~~");
				return true;
			} else {
				System.out.println("֧��ʧ�ܣ�������~~");
				return false;
			}
		}
	}

	/**
	 * ��ȡ�û��ķ���
	 * 
	 * @param id
	 *            �û�id
	 * @return User����
	 */
	public static User getUser(int id) {
		List<User> list = new UserDaoImpl().selectAll();
		for (User user : list) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
}
