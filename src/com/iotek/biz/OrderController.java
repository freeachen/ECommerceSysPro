package com.iotek.biz;

import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Goods;
import com.iotek.bean.Order;
import com.iotek.bean.OrderDetail;
import com.iotek.bean.ShoppingDetail;
import com.iotek.bean.User;
import com.iotek.db.dao.impl.GoodsDaoImpl;
import com.iotek.db.dao.impl.OrderDaoImpl;
import com.iotek.db.dao.impl.OrderDetailDaoImpl;
import com.iotek.db.dao.impl.ShoppingDetailDaoImpl;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.util.GetDate;
import com.iotek.util.NumFormat;

/**
 * ����������߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderController {
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
			// ��ʾ�û�δ��ɶ�����
			listODUnfinished();
			break;
		case 2:
			// ��ʾ�û�����ɶ�����
			listODFinished();
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
	 * ��ʾ�û�δ��ɶ�����
	 * 
	 * @return �������
	 */
	public List<OrderDetail> listODUnfinished() {
		List<OrderDetail> od = new ArrayList<OrderDetail>();
		// ��������δ��ɵĶ����״̬1��4�Ķ�����
		for (int i = 1; i < 5; i++) {
			List<OrderDetail> odd = getMyODByStatus(i);
			if (odd != null) {
				od.addAll(odd);
			}
		}
		System.out.println("��δ��ɵĶ�����");
		new ManageOrderController().listOrderDetail(od);
		// �ý�������ʾ��������ܼ�
		double totalPrice = 0;
		for (OrderDetail orderDetail : od) {
			totalPrice += orderDetail.getTotalPrice();
		}
		System.out
				.println("�������ܼ�Ϊ��" + NumFormat.formatDouble(totalPrice) + "Ԫ");
		return od;
	}

	/**
	 * ��ʾ�û���ɶ�����
	 * 
	 * @return �������
	 */
	public List<OrderDetail> listODFinished() {
		List<OrderDetail> od = getMyODByStatus(5);
		System.out.println("������ɵĶ�����");
		new ManageOrderController().listOrderDetail(od);
		return od;
	}

	/**
	 * ���ص�ǰ�û�ָ��״̬�Ķ�����
	 * 
	 * @param i
	 *            ״̬
	 * @return �������
	 */
	public List<OrderDetail> getMyODByStatus(int i) {
		// �����û��Ķ���
		List<Order> o = new OrderDaoImpl().selectByUid();
		List<OrderDetail> od = new ArrayList<OrderDetail>();
		for (Order order : o) {
			// ���ݶ����ŷ��ض�Ӧ�����ж�����
			List<OrderDetail> odd = new OrderDetailDaoImpl().selectByOid(order
					.getId());
			for (OrderDetail orderDetail : odd) {
				// �ж���Щ�������״̬����ӽ�od
				int j = orderDetail.getoStatusId();
				if (j == i) {
					od.add(orderDetail);
				}
			}
		}
		return od;
	}

	/**
	 * ���ɶ������
	 * 
	 * @param o
	 *            ����
	 * @param s
	 *            �������
	 * @return �������
	 */
	public List<OrderDetail> createOrderDetail(Order o, List<ShoppingDetail> s) {
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		OrderDetail od = null;
		boolean flag = false;
		for (ShoppingDetail sd : s) {
			// ���ɶ������ʱ�������Ʒ��ʱ�Ŀ�棬�����Ļ��޷����ɶ�����
			if (!ManageOrderController.checkStock(sd.getGid(), sd.getNum())) {
				Goods good = ManageGoodsController.getGood(sd.getGid());
				System.out.println("��Ʒ��" + good.getName() + "���޷����ɶ����");
				continue;
			} else {
				// ���ɶ����Ĭ��״̬�����
				od = new OrderDetail(sd.getGid(), o.getId(), sd.getNum(), 2);
				flag = new OrderDetailDaoImpl().insert(od);
				if (flag) {
					// ������ɶ�����ɹ����޸���Ʒ���
					Goods good = ManageGoodsController.getGood(sd.getGid());
					int stock = good.getStock() - sd.getNum();
					good.setStock(stock);
					new GoodsDaoImpl().update(good);
					// ���ﻹҪɾ����Ӧ�Ĺ�����
					new ShoppingDetailDaoImpl().delete(sd);
					list.add(od);
				} else {
					System.out.println("����������ʧ�ܣ�");
				}
			}
		}
		return list;
	}

	/**
	 * ���ɶ���
	 * 
	 * @param u
	 *            �û�����
	 * @param s
	 *            �������
	 */
	public void createOrder(List<ShoppingDetail> s) {
		// ���˹��ﳵ����ӵ����¼���Ʒ
		List<ShoppingDetail> sc = checkShoppingDetail(s);
		// ��ȡ��ǰϵͳʱ��
		String date = GetDate.getNowTime();
		// ���ɳ�ʼ�ն���
		Order order = new Order(MainMenuController.user.getId(), date, 2);

		boolean flag = new OrderDaoImpl().insert(order);
		if (flag) {
			// ���������û�ID��ʱ�����ɣ�Ȼ���ٻ�ȡ�ö�����ID��������������
			Order newOrder = new OrderDaoImpl().getNewOrder(date);
			List<OrderDetail> od = createOrderDetail(newOrder, sc);
			// ����һ������©���Դ󶩵��ܼ۵ļ��㲢����
			newOrder.setOrderList(od);
			newOrder.culTotalPrice();
			new OrderDaoImpl().updateTotalPrice(newOrder);
			System.out.println("���ɶ����ɹ���");
		} else {
			System.out.println("���ɶ���ʧ�ܣ�");
		}
	}

	/**
	 * ����
	 * 
	 * @return true or false
	 */
	public boolean payMoney() {
		// ��������δ����Ķ�����
		List<OrderDetail> od = getMyODByStatus(3);
		System.out.println("δ������");
		if (od.size() == 0) {
			System.out.println("��");
			System.out.println("�˻�������" + MainMenuController.user.getBalance()
					+ "Ԫ");
			return false;
		} else {
			new ManageOrderController().listOrderDetail(od);
			double totalPrice = 0;
			// ��ǰ�û����ۿ�ϵ������һ���ĸ�ʽ��
			double d = NumFormat.formatDouble((ManageUserController
					.checkUserLevel() * 10));
			for (OrderDetail odd : od) {
				totalPrice += odd.getTotalPrice();
			}
			System.out.println("�ϼƸ��" + totalPrice);
			System.out.println("�û��ȼ��ۿۣ�" + d + "��");
			// ����ʵ��Ӧ���Ҫ�����û��ĵȼ��ۿ�
			totalPrice *= ManageUserController.checkUserLevel();
			System.out.println("ʵ��Ӧ���" + NumFormat.formatDouble(totalPrice));
			System.out.println("�˻�������" + MainMenuController.user.getBalance()
					+ "Ԫ");
		}

		while (true) {
			System.out.println("ȷ�ϸ��");
			System.out.println("��1-�ǣ�0-��");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				System.out.println("�˳����");
				return false;
			} else if (i == 1) {
				if (payNow(od)) {
					return true;
				} else {
					return false;
				}
			} else {
				System.out.println("�޴�ѡ�");
				continue;
			}
		}
	}

	/**
	 * ȷ�ϸ���
	 * 
	 * @param od
	 *            �������
	 * @return true or false
	 */
	private boolean payNow(List<OrderDetail> od) {
		// ��ѯ�û��������µ�
		boolean flag = checkBalance(od);
		if (!flag) {
			return false;
		} else {
			// ��ȡ��ǰ�û������;���ֵ
			double balance = MainMenuController.user.getBalance();
			int exp = MainMenuController.user.getExp();
			double totalPrice = 0;
			for (OrderDetail orderDetail : od) {
				orderDetail.setoStatusId(4);
				boolean flag2 = new OrderDetailDaoImpl().update(orderDetail);
				if (flag2) {
					// ������״̬һ���䶯�ͼ�鲢�����䶩��״̬
					List<Order> list2 = new OrderDaoImpl()
							.selectById(orderDetail.getOid());
					List<OrderDetail> list3 = new ArrayList<OrderDetail>();
					list3.add(orderDetail);
					int j = new ManageOrderController().checkDetailStatus(
							list3, 4);
					list2.get(0).setoStatusId(j);
					new OrderDaoImpl().updateStatus(list2.get(0));
					// ���㶩�����ܼ۵ĺͣ�����ֵ������Щ�ܺ�
					totalPrice += orderDetail.getTotalPrice();
					exp += (int) (orderDetail.getTotalPrice());
					System.out.println("������" + orderDetail.getId() + "����ɹ���");
				} else {
					System.out.println("������" + orderDetail.getId() + "����ʧ�ܣ�");
				}
			}
			// �����û��ۿ�ϵ��������ۺ�ļ۸�
			totalPrice *= ManageUserController.checkUserLevel();
			// �û�����ȥ�ۿۺ�ļ۸�
			balance -= totalPrice;
			MainMenuController.user.setBalance(balance);
			MainMenuController.user.setExp(exp);
			// ����ֵһ���䶯�ͼ���һ�µȼ�
			MainMenuController.user.calcLevel();
			new UserDaoImpl().update(MainMenuController.user);

			// ���û�����Ǯת�Ƶ�����Ա�˻���ȥ
			User u = new User("admin", "admin", null, null, 0, 0, 0);
			u = new UserDaoImpl().selectByUserNameAndPwd(u);
			balance = u.getBalance() + totalPrice;
			u.setBalance(balance);
			if (u != null) {
				new UserDaoImpl().update(u);
			}
			return true;
		}
	}

	/**
	 * ȷ���ջ�
	 * 
	 * @return true or false
	 */
	public boolean receiveGoods() {
		// ���ط���״̬�Ķ�����
		List<OrderDetail> od = getMyODByStatus(4);
		if (od.size() == 0) {
			System.out.println("��û�п��Ŷ~����ȥ������Ʒ�ɣ�");
			return false;
		} else {
			for (OrderDetail orderDetail : od) {
				try {
					System.out.println("��������С�����");
					Thread.sleep(1500);
					Goods goods = ManageGoodsController.getGood(orderDetail
							.getGid());
					System.out.println("�������" + goods.getName() + "���͵�����ǩ�գ�");
					Thread.sleep(500);
					orderDetail.setoStatusId(5);
					boolean flag = new OrderDetailDaoImpl().update(orderDetail);
					if (flag) {
						// ������״̬һ���䶯�ͼ�鲢�����䶩��״̬
						List<Order> list2 = new OrderDaoImpl()
								.selectById(orderDetail.getOid());
						List<OrderDetail> list3 = new ArrayList<OrderDetail>();
						list3.add(orderDetail);
						int j = new ManageOrderController().checkDetailStatus(
								list3, 5);
						list2.get(0).setoStatusId(j);
						new OrderDaoImpl().updateStatus(list2.get(0));
						System.out.println(goods.getName() + "ǩ�ճɹ���");
						// �ջ��ɹ��͵����������۵ķ���
						new CommentController().insertComment(orderDetail);
						return true;
					} else {
						System.out.println(goods.getName() + "ǩ��ʧ�ܣ�");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * ����û����
	 * 
	 * @param s
	 *            �������
	 * @return true or false
	 */
	public boolean checkBalance(List<OrderDetail> od) {
		// ���ﴫ��������δ����Ķ������
		double totalPrice = 0;
		for (OrderDetail o : od) {
			totalPrice += o.getTotalPrice();
		}
		// �鿴�û�����ܷ�֧���ۿۼ�
		totalPrice *= ManageUserController.checkUserLevel();
		double balance = MainMenuController.user.getBalance();
		if (balance < totalPrice) {
			System.out.println("���㣬��ȥ��ֵ�ɣ�");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * �����¼���Ʒ
	 * 
	 * @param s
	 *            �������
	 * @return ������ϣ��ѹ��ˣ�
	 */
	public List<ShoppingDetail> checkShoppingDetail(List<ShoppingDetail> s) {
		List<ShoppingDetail> list = new ArrayList<ShoppingDetail>();
		// �������ﳵ����������¼���Ʒ�ͺ��Ե�
		for (ShoppingDetail sd : s) {
			Goods good = ManageGoodsController.getGood(sd.getGid());
			if (good.getgStatusId() != 1) {
				list.add(sd);
			} else {
				System.out.println(good.getName() + "���¼ܣ��޷����ɶ�����");
			}
		}
		return list;
	}

}
