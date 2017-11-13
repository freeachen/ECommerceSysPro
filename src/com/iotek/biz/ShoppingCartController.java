package com.iotek.biz;

import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Goods;
import com.iotek.bean.ShoppingCart;
import com.iotek.bean.ShoppingDetail;
import com.iotek.db.dao.impl.ShoppingDetailDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.view.BrowseGoodsMenu;

/**
 * ���ﳵ������߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ShoppingCartController {
	/**
	 * ִ��ѡ��ķ���
	 * 
	 * @param option
	 *            ѡ��
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int doOption(int option) {
		boolean flag = false;
		switch (option) {
		case 1:
			// ��ʾ���ﳵ
			showDetail(checkCart());
			break;
		case 2:
			// ������Ʒ
			new BrowseGoodsMenu().show();
			break;
		case 3:
			// �޸�����
			flag = updateDetail();
			if (flag) {
				System.out.println("�޸ĳɹ���");
			} else {
				System.out.println("�޸�ʧ�ܣ�");
			}
			break;
		case 4:
			// ɾ��������
			flag = deleteDetail();
			if (flag) {
				System.out.println("ɾ���ɹ���");
			} else {
				System.out.println("ɾ��ʧ�ܣ�");
			}
			break;
		case 5:
			// ȷ���µ�
			placeOrder();
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
	 * �û�ȷ���µ�
	 */
	public void placeOrder() {
		List<ShoppingDetail> list = checkCart();
		if (list.size() == 0) {
			System.out.println("���ﳵ�տգ���ȥ�����Ʒ�ɣ�");
			return;
		} else {
			showDetail(list);
			while (true) {
				System.out.println("��1-ѡ���µ���2-ȫ���µ���0-�˳���");
				int i = FilterInputMismatch.nextInt();
				switch (i) {
				case 0:
					// �˳��µ�
					return;
				case 1:
					// ѡ���µ�
					placeOrderSingle(list);
					return;
				case 2:
					// ȫ���µ�
					new OrderController().createOrder(list);
					return;
				default:
					System.out.println("�޴�ѡ�");
					continue;
				}
			}
		}
	}

	/**
	 * ѡ���µ�
	 */
	public void placeOrderSingle(List<ShoppingDetail> list) {
		List<ShoppingDetail> list2 = new ArrayList<ShoppingDetail>();
		while (true) {
			System.out.println("��1-������0-�˳���");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				boolean flag = quitOrSure();
				if (flag) {
					if (list2.size() != 0) {
						// �˳�������ȷ�ϲ���������ɶ���
						new OrderController().createOrder(list2);
					}
					return;
				} else {
					return;
				}
			} else if (i == 1) {
				System.out.println("������Ҫ�µ�����ţ�");
				int j = checkIndex(list.size());
				ShoppingDetail sd = list.get(j - 1);
				// �����Ʒ�Ƿ��Ѿ��ظ���ӽ�list2����֤������ظ����
				int g = checkCart(list2, sd.getGid());
				if (g == -1) {
					// list2���޸���Ʒ�����
					list2.add(sd);
					System.out.println("��ӳɹ���");
				} else {
					System.out.println("����Ʒ���µ���");
				}
			} else {
				System.out.println("�޴�ѡ�");
				continue;
			}
		}
	}

	/**
	 * �˳�����ȷ��
	 * 
	 * @return true or false ȷ�� �� ����
	 */
	private boolean quitOrSure() {
		System.out.println("ȷ���µ����Ƿ����µ���");
		while (true) {
			System.out.println("��1-ȷ�ϣ�0-������");
			int j = FilterInputMismatch.nextInt();
			if (j == 1) {
				return true;
			} else if (j == 0) {
				System.out.println("�ѷ����µ���");
				return false;
			} else {
				System.out.println("�޴�ѡ�");
				continue;
			}
		}
	}

	/**
	 * �鿴���ﳵ
	 * 
	 * @return �������
	 */
	public List<ShoppingDetail> checkCart() {
		List<ShoppingDetail> list = new ShoppingDetailDaoImpl().select();
		return list;
	}

	/**
	 * ���¹��ﳵ����
	 * 
	 * @return true or false
	 */
	public boolean updateDetail() {
		List<ShoppingDetail> list = checkCart();
		showDetail(list);
		if (list.size() == 0) {
			return false;
		}
		System.out.println("�����޸���Ʒ�����:");
		int i = checkIndex(list.size());
		// ���ض�Ӧ��ŵĹ�����
		ShoppingDetail sd = list.get(i - 1);
		int g = checkCart(list, sd.getGid());

		if (g != -1) {
			int num;
			while (true) {
				System.out.println("������Ʒ������");
				num = FilterInputMismatch.nextInt();
				if (num <= 0) {
					System.out.println("������Ʒ��������Ϊ0��");
					continue;
				} else {
					break;
				}
			}
			// �޸�����ʱҲҪ�����
			if (!ManageOrderController.checkStock(sd.getGid(), num)) {
				return false;
			}
			sd.setNum(num);
			return new ShoppingDetailDaoImpl().update(sd);
		} else {
			System.out.println("�޸���Ʒ��");
			return false;
		}
	}

	/**
	 * ɾ�����ﳵ����
	 * 
	 * @return true or false
	 */
	public boolean deleteDetail() {
		List<ShoppingDetail> list = checkCart();
		showDetail(list);
		if (list.size() == 0) {
			return false;
		}
		System.out.println("����Ҫɾ������Ʒ���:");
		int i = checkIndex(list.size());
		// ���ض�Ӧ��ŵĹ�����
		ShoppingDetail sd = list.get(i - 1);
		int g = checkCart(list, sd.getGid());

		if (g != -1) {
			return new ShoppingDetailDaoImpl().delete(sd);
		} else {
			System.out.println("�޸���Ʒ��");
			return false;
		}
	}

	/**
	 * ����������
	 * 
	 * @param ��ǰ��Ʒ
	 * 
	 * @return true or false
	 */
	public boolean inertDetail(Goods good) {
		List<ShoppingDetail> list = checkCart();
		ShoppingDetail shoppingDetail = null;
		System.out.println("�����Ʒ�����ﳵ�ɣ�����");
		while (true) {
			System.out.println("��1-ȷ����ӣ�0-��ϲ������Ʒ��");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				return false;
			} else if (i == 1) {
				int num;
				while (true) {
					System.out.println("������Ʒ������");
					num = FilterInputMismatch.nextInt();
					if (num <= 0) {
						System.out.println("������Ʒ��������Ϊ0��");
						continue;
					} else {
						break;
					}
				}
				if (!ManageOrderController.checkStock(good.getId(), num)) {
					return false;
				}

				int g = checkCart(list, good.getId());

				// �жϹ��ﳵ���Ƿ����и���Ʒ
				if (list.size() == 0 || g == -1) {
					// �±�Ϊ-1��ʱ���������Ʒδ�����ڹ��ﳵ�У�ֱ�����
					shoppingDetail = new ShoppingDetail(0, good.getId(),
							MainMenuController.user.getId(), num);
					return new ShoppingDetailDaoImpl().insert(shoppingDetail);
				} else {
					// ������ﳵ�����и���Ʒ����ֻ�޸�����
					num += list.get(g).getNum();
					shoppingDetail = new ShoppingDetail(0, good.getId(),
							MainMenuController.user.getId(), num);
					return new ShoppingDetailDaoImpl().update(shoppingDetail);
				}
			} else {
				System.out.println("�޴�ѡ�");
				continue;
			}
		}
	}

	/**
	 * ��ʾ������
	 * 
	 * @param list
	 *            �������
	 * @param sc
	 *            ���ﳵ
	 */
	private void showDetail(List<ShoppingDetail> list) {
		ShoppingCart sc = new ShoppingCart(list);
		System.out.println("���\t��ƷID\t��Ʒ��\t��Ʒ����\t����\t�ܼ�");
		int count = 0;
		if (list.size() != 0) {
			for (ShoppingDetail shoppingDetail : list) {
				System.out.println((++count) + "\t"
						+ shoppingDetail.listInMyStyle());
			}
		} else {
			System.out.println("���ﳵ�տգ���ȥ�����Ʒ�ɣ�");
		}
		System.out.println("������Ʒ�ܼ�Ϊ��" + sc.getTotalPrice() + "Ԫ");
	}

	/**
	 * �������Ƿ��ڼ�����
	 * 
	 * @param size
	 *            ���ϴ�С
	 * @return ���ϼ�����
	 */
	public static int checkIndex(int size) {
		int i = 0;
		while (true) {
			i = FilterInputMismatch.nextInt();
			// �ж�����Ƿ��ڼ����У����ش��ڵ����
			if ((i - 1) < 0 || i > size) {
				System.out.println("�޸������������");
				continue;
			} else {
				break;
			}
		}
		return i;
	}

	/**
	 * ��鹺�ﳵ�Ƿ�����ͬ����Ʒ
	 * 
	 * @param list
	 *            �������
	 * @param gid
	 *            ��ƷID
	 * @return ��������ֵ��±꣬δ���ַ���-1
	 */
	private int checkCart(List<ShoppingDetail> list, int gid) {
		// ���ڷ�����ƷID��ȵ��±꣬�����Ʒ���ڹ������з���-1
		int g = -1;
		for (ShoppingDetail sDetail : list) {
			if (sDetail.getGid() == gid) {
				g = list.indexOf(sDetail);
				break;
			}
		}
		return g;
	}
}
