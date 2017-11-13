package com.iotek.biz;

import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Goods;
import com.iotek.bean.Order;
import com.iotek.bean.OrderDetail;
import com.iotek.db.dao.impl.GoodsDaoImpl;
import com.iotek.db.dao.impl.OrderDaoImpl;
import com.iotek.db.dao.impl.OrderDetailDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.view.ManageOrderInnerMenu;

/**
 * ������������߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageOrderController {

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
			// Ҫ��˵Ķ���
			i = 0;
			while (true) {
				i = new ManageOrderInnerMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 2:
			// ��ʾ���ж���
			i = 0;
			listOrder(new OrderDaoImpl().selectAll());
			break;
		case 3:
			// ��ʾ���ж�����
			i = 0;
			listOrderDetail(new OrderDetailDaoImpl().selectAll());
			break;
		case 4:
			// ɾ��δ�������
			i = 0;
			deleteOrderDetail();
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
		if (i == 2) {
			return 2;
		} else {
			return 0;
		}
	}

	/**
	 * ɾ�������ֻ�л�δ����Ķ��������ɾ����
	 * 
	 * @return true or false
	 */
	public boolean deleteOrderDetail() {
		// ��������δ�������
		List<OrderDetail> list = new OrderDetailDaoImpl().selectByStatus(3);
		System.out.println("��ֻ��ɾ��δ����Ķ����");
		listOrderDetail(list);
		if (list.size() == 0) {
			return false;
		}
		System.out.println("������ɾ����������ţ�");
		int i = ShoppingCartController.checkIndex(list.size());
		OrderDetail od = list.get(i - 1);
		boolean flag = new OrderDetailDaoImpl().delete(od);
		if (flag) {
			// ɾ��������ָ����
			Goods goods = ManageGoodsController.getGood(od.getGid());
			goods.setStock(goods.getStock() + od.getNum());
			new GoodsDaoImpl().update(goods);
			System.out.println("������" + od.getId() + "ɾ���ɹ���");
			return true;
		} else {
			System.out.println("������" + od.getId() + "ɾ��ʧ�ܣ�");
			return false;
		}
	}

	/**
	 * �޸Ķ��������ж�����
	 * 
	 * @return true or false
	 */
	public boolean updateOrder() {
		// �������д���˵Ķ���
		List<Order> list = new OrderDaoImpl().selectByStatus(2);
		if (list.size() == 0) {
			System.out.println("��");
			return false;
		} else {
			listOrder(list);
		}
		System.out.println("�������޸Ķ�����ţ�");
		int i = ShoppingCartController.checkIndex(list.size());

		Order o = list.get(i - 1);
		// ���ݶ������ҵ����Ӧ�����ж�����
		List<OrderDetail> list2 = new OrderDetailDaoImpl().selectByOid(o
				.getId());
		for (OrderDetail od : list2) {
			// ������Щ������״̬Ϊδ����
			od.setoStatusId(3);
			boolean flag = new OrderDetailDaoImpl().update(od);
			if (flag) {
				System.out.println("������" + od.getId() + "��˳ɹ���");
			} else {
				System.out.println("������" + od.getId() + "���ʧ�ܣ�");
			}
		}

		// ������״̬һ���䶯�ͼ�鲢�����䶩��״̬
		int j = checkDetailStatus(list2, 3);
		o.setoStatusId(j);
		return new OrderDaoImpl().updateStatus(o);
	}

	/**
	 * �޸�ָ��������
	 * 
	 * @return true or false
	 */
	public boolean updateOrderDetail() {
		// �������д���˵Ķ�����
		List<OrderDetail> list = new OrderDetailDaoImpl().selectByStatus(2);
		if (list.size() == 0) {
			System.out.println("��");
			return false;
		} else {
			listOrderDetail(list);
		}
		System.out.println("�������޸Ķ�������ţ�");
		int i = ShoppingCartController.checkIndex(list.size());

		OrderDetail od = list.get(i - 1);
		// ���øö�����״̬Ϊδ����
		od.setoStatusId(3);
		boolean flag = new OrderDetailDaoImpl().update(od);
		if (flag) {
			// ��Ϊ���״̬�ķ�����Ҫ���ݼ���Ϊ�����������ڴ˶Զ������װһ��
			List<Order> list2 = new OrderDaoImpl().selectById(od.getOid());
			List<OrderDetail> list3 = new ArrayList<OrderDetail>();
			list3.add(od);
			// ������״̬һ���䶯�ͼ�鲢�����䶩��״̬
			int j = checkDetailStatus(list3, 3);
			list2.get(0).setoStatusId(j);
			new OrderDaoImpl().updateStatus(list2.get(0));
			System.out.println("������" + od.getId() + "��˳ɹ���");
			return true;
		} else {
			System.out.println("������" + od.getId() + "���ʧ�ܣ�");
			return false;
		}
	}

	/**
	 * ��ʾ����
	 * 
	 * @param list
	 *            ��������
	 * 
	 */
	public void listOrder(List<Order> list) {
		// ���������
		int count = 0;
		System.out.println("���\t����ID\t�û�ID\t�µ�ʱ��\t\t\t�����ܼ�\t����״̬");
		if (list.size() != 0) {
			for (Order order : list) {
				System.out.println((++count) + "\t" + order.listInMyStyle());
			}
		} else {
			System.out.println("��");
		}
	}

	/**
	 * ��ʾ������
	 * 
	 * @param list
	 *            �������
	 */
	public List<OrderDetail> listOrderDetail(List<OrderDetail> list) {
		// ���������
		int count = 0;
		System.out.println("���\t������ID\t��ƷID\t��Ʒ��\t��Ʒ����\t����ID"
				+ "\t��������\t�������ܼ�\t������״̬");
		if (list.size() != 0) {
			for (OrderDetail orderDetail : list) {
				System.out.println((++count) + "\t"
						+ orderDetail.listInMyStyle());
			}
		} else {
			System.out.println("��");
		}
		return list;
	}

	/**
	 * ��鶩�������ж������״̬��������͵�״̬���Ķ���״̬
	 * 
	 * @param od
	 *            �������
	 * @param i
	 *            ״̬
	 * @return ���ظ���״̬
	 */
	public int checkDetailStatus(List<OrderDetail> od, int i) {
		for (OrderDetail odd : od) {
			if (odd.getoStatusId() < i) {
				i = odd.getoStatusId();
			}
		}
		return i;
	}

	/**
	 * ������ķ���
	 * 
	 * @param gid
	 *            ��ƷID
	 * @param num
	 *            ��Ʒ����
	 * @return true or false
	 */
	public static boolean checkStock(int gid, int num) {
		Goods good = ManageGoodsController.getGood(gid);
		if (good.getStock() >= num) {
			return true;
		} else {
			System.out.println("��治����");
			return false;
		}
	}

}
