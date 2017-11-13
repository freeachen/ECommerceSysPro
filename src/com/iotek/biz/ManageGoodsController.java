package com.iotek.biz;

import java.util.List;
import java.util.Scanner;

import com.iotek.bean.Goods;
import com.iotek.db.dao.impl.CommentDaoImpl;
import com.iotek.db.dao.impl.GoodsDaoImpl;
import com.iotek.util.FilterInputMismatch;

/**
 * ������Ʒ���߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageGoodsController {
	/**
	 * �Բ˵�ѡ������ķ���
	 * 
	 * @param option
	 *            �û������ѡ�
	 * @return 0-����ѭ����1-�˳���ǰѭ����2-�˵���¼����
	 */
	public int doOption(int option) {
		switch (option) {
		case 1:
			// ��ʾ������Ʒ�������¼���Ʒ��
			listAll();
			break;
		case 2:
			// ������Ʒ
			insert();
			break;
		case 3:
			// �޸���Ʒ���
			listAll();
			updateStock();
			break;
		case 4:
			// �޸���Ʒ״̬
			listAll();
			updateStatus();
			break;
		case 5:
			// �޸���Ʒ��Ϣ
			listAll();
			updateGoodsInfo();
			break;
		case 9:
			// �����ϼ��˵�
			return 1;
		case 0:
			// �˳�
			return 2;
		default:
			System.out.println("����������������룡");
			break;
		}
		return 0;
	}

	/**
	 * ��ʾ������Ʒ�������¼���Ʒ����������Աʹ�ã�
	 */
	public void listAll() {
		List<Goods> list = new GoodsDaoImpl().queryAll();
		listForAdmin(list);
	}

	/**
	 * ��ʾ��Ʒ��ϸ��Ϣ
	 * 
	 * @param g
	 *            ��Ʒ����
	 */
	public static void listDetail(Goods g) {
		System.out.println("��Ʒ����" + g.getName());
		System.out.println("���ۣ�" + g.getSalePrice());
		System.out.println("��棺" + g.getStock());
		System.out.println("������" + g.getDescribe());
		System.out.println("���" + g.getCategory());
		System.out.println("״̬��" + g.getStatus());
		new CommentController()
				.listComment(new CommentDaoImpl().selectByGid(g));
	}

	/**
	 * ��ʾ��Ʒ��Ϣ������ƷID��ר������Աʹ�ã�
	 * 
	 * @param list
	 *            ��Ʒ����
	 * @return ��Ʒ����
	 */
	private static List<Goods> listForAdmin(List<Goods> list) {
		System.out.println("��ƷID\t��Ʒ��\t���ۣ�Ԫ��\t���\t����\t���\t״̬");
		for (Goods goods : list) {
			System.out.println(goods.getId() + "\t" + goods);
		}
		return list;
	}

	/**
	 * ��ʾ��Ʒ��Ϣ��������ƷID���������ʾ��ר���û�ʹ�ã�
	 * 
	 * @param list
	 *            ��Ʒ����
	 * @return ��Ʒ����
	 */
	public static List<Goods> listForUser(List<Goods> list) {
		int count = 0;
		System.out.println("���\t��Ʒ��\t���ۣ�Ԫ��\t���\t����\t���\t״̬");
		for (Goods goods : list) {
			System.out.println((++count) + "\t" + goods);
		}
		return list;
	}

	/**
	 * ��ȡָ����Ʒ����
	 * 
	 * @param id
	 *            ��ƷID
	 * @return Goods����
	 */
	public static Goods getGood(int id) {
		List<Goods> list = new GoodsDaoImpl().queryAll();
		for (Goods goods : list) {
			if (goods.getId() == id) {
				return goods;
			}
		}
		return null;
	}

	/**
	 * ������Ʒ
	 */
	public void insert() {
		// ������Ʒ��Ϣ
		Goods good = inputGoodsInfo();
		boolean flag = new GoodsDaoImpl().insert(good);
		if (flag) {
			System.out.println("�����Ʒ�ɹ���");
		} else {
			System.out.println("�����Ʒʧ�ܣ�");
		}
	}

	/**
	 * �޸���Ʒ���
	 */
	public void updateStock() {
		Goods good = getUpdateGoods();
		if (good != null) {
			System.out.println("��������Ʒ��棺");
			int stock = FilterInputMismatch.nextInt();
			good.setStock(stock);
			boolean flag = new GoodsDaoImpl().update(good);
			if (flag) {
				System.out.println("�޸Ŀ��ɹ���");
			} else {
				System.out.println("�޸Ŀ��ʧ�ܣ�");
			}
		} else {
			System.out.println("����Ʒ�����ڣ�");
			return;
		}
	}

	/**
	 * �޸���Ʒ״̬�ķ���
	 * 
	 */
	public void updateStatus() {
		Goods good = getUpdateGoods();
		if (good != null) {
			System.out.println("��������Ʒ״̬��");
			System.out.println("��1-���¼ܣ�2-�����У�3-�����С�");
			int status = FilterInputMismatch.nextInt();
			good.setgStatusId(status);
			boolean flag = new GoodsDaoImpl().update(good);
			if (flag) {
				System.out.println("�޸�״̬�ɹ���");
			} else {
				System.out.println("�޸�״̬ʧ�ܣ�");
			}
		} else {
			System.out.println("����Ʒ�����ڣ�");
			return;
		}
	}

	/**
	 * �޸���Ʒ��ϸ��Ϣ
	 */
	public void updateGoodsInfo() {
		Goods good = getUpdateGoods();
		if (good != null) {
			// ������Ʒ��Ϣ
			Goods inputGoods = inputGoodsInfo();
			inputGoods.setId(good.getId());
			boolean flag = new GoodsDaoImpl().update(inputGoods);
			if (flag) {
				System.out.println("�޸�״̬�ɹ���");
			} else {
				System.out.println("�޸�״̬ʧ�ܣ�");
			}
		} else {
			System.out.println("����Ʒ�����ڣ�");
			return;
		}
	}

	/**
	 * ��ȡҪ�޸ĵ���Ʒ
	 * 
	 * @return ��Ʒ
	 */
	private Goods getUpdateGoods() {
		System.out.println("�������޸���Ʒ��ID��");
		int id = FilterInputMismatch.nextInt();
		Goods good = ManageGoodsController.getGood(id);
		return good;
	}

	/**
	 * ������Ʒ��Ϣ
	 * 
	 * @return ��Ʒ
	 */
	private Goods inputGoodsInfo() {
		Scanner in = new Scanner(System.in);
		String name = null;
		while (true) {
			System.out.println("��������Ʒ���ƣ�");
			name = in.nextLine();
			// ��Ʒ��Ϣ���ܴ��ո�
			if (new ManageUserController().checkBlank(name)) {
				if (checkGoodsName(name)) {
					break;
				} else {
					continue;
				}
			} else {
				System.out.println("��Ʒ�����ܴ��ո�");
				continue;
			}
		}
		double price;
		while (true) {
			System.out.println("��������Ʒ�۸�");
			price = FilterInputMismatch.nextDouble();
			if (price <= 0) {
				System.out.println("��Ʒ�۸���Ϊ0��");
				continue;
			} else {
				break;
			}
		}
		System.out.println("��������Ʒ��棺");
		int stock = FilterInputMismatch.nextInt();
		System.out.println("��������Ʒ������");
		String describe = in.nextLine();
		System.out.println("��������Ʒ���");
		System.out.println("��1-��װ���Σ�2-ʳƷ��ʳ��3-����Ʒ��4-�����Ʒ��");
		int categoryId = FilterInputMismatch.nextInt();
		System.out.println("��������Ʒ״̬��");
		System.out.println("��1-���¼ܣ�2-�����У�3-�����С�");
		int statusId = FilterInputMismatch.nextInt();

		Goods good = new Goods(0, name, price, stock, describe, categoryId,
				statusId);
		return good;
	}

	/**
	 * �����Ʒ������
	 * 
	 * @param name
	 *            ��Ʒ��
	 * @return true or false
	 */
	private boolean checkGoodsName(String name) {
		List<Goods> list = new GoodsDaoImpl().queryAll();
		for (Goods goods : list) {
			if (goods.getName().equals(name)) {
				System.out.println("������Ʒ���Ѵ��ڣ�");
				return false;
			}
		}
		return true;
	}

	/**
	 * �����Ʒ״̬�������ۿ�ϵ����������Ʒ��9�ۣ�
	 * 
	 * @param g
	 *            ��Ʒ
	 * @return ϵ��ֵ
	 */
	public static double checkGoodStatus(Goods g) {
		// ��Ʒ״̬3��ʱ�򷵻�һ���ۿ�ϵ��
		if (g.getgStatusId() == 3) {
			return 0.9;
		}
		return 1;
	}
}
