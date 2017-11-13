package com.iotek.biz;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.iotek.bean.Goods;
import com.iotek.db.dao.impl.GoodsDaoImpl;
import com.iotek.util.FilterInputMismatch;

/**
 * �����Ʒ���߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class BrowseGoodsController {
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
			// ��ʾ������Ʒ
			listAll(0);
			break;
		case 2:
			// ��ʾ������Ʒ
			listByStatus();
			break;
		case 3:
			// ������ʾ
			listByCategory();
			break;
		case 4:
			// �۸�����
			listAll(1);
			break;
		case 5:
			// �۸���
			listAll(2);
			break;
		case 9:
			// �����ϼ��˵�
			return 1;
		case 0:
			// �˳�
			return 2;
		default:
			break;
		}
		return 0;
	}

	/**
	 * ��ʾ������Ʒ
	 * 
	 * @param u
	 *            �û�����
	 * @param i
	 *            ָ����ʾ��ʽ
	 */
	public void listAll(int i) {
		List<Goods> list = new GoodsDaoImpl().selectAll();

		if (i != 0) {
			// �ü��ϵĹ��������Ʒ���۸���������
			Collections.sort(list, new Comparator<Goods>() {
				@Override
				public int compare(Goods o1, Goods o2) {
					int res = (int) (o1.getSalePrice() - o2.getSalePrice());
					return res == 0 ? o1.getName().compareTo(o2.getName()): res;
				}
			});
			// ��i=2ʱ������ļ��Ϸ�ת���
			if (i == 2) {
				Collections.reverse(list);
			}
		}
		ManageGoodsController.listForUser(list);
		browseAndBuy(list);
	}

	/**
	 * ���������ʾ��Ʒ
	 * 
	 * @param u
	 *            �û�����
	 */
	public void listByCategory() {
		System.out.println("���������");
		System.out.println("��1-��װ���Σ�2-ʳƷ��ʳ��3-����Ʒ��4-�����Ʒ��");
		int i = FilterInputMismatch.nextInt();

		Goods good = new Goods(0, null, 0, 0, null, i, 0);
		List<Goods> list = new GoodsDaoImpl().selectByCategory(good);
		if (list.size() == 0) {
			System.out.println("���޸�����Ʒ���ȴ�����Ա���~~");
		} else {
			ManageGoodsController.listForUser(list);
			browseAndBuy(list);
		}
	}

	/**
	 * ����״̬��ʾ��Ʒ���ô�ֻ��ʾ������Ʒ��
	 * 
	 */
	public void listByStatus() {
		Goods good = new Goods(0, null, 0, 0, null, 0, 3);
		List<Goods> list = new GoodsDaoImpl().selectByStatus(good);
		if (list.size() == 0) {
			System.out.println("���޴�����Ʒ���ȴ�����Ա���~~");
		} else {
			ManageGoodsController.listForUser(list);
			browseAndBuy(list);
		}
	}

	/**
	 * ������Ʒ��ϸ���鿴��ϸ��Ϣ���Ҳ鿴���ۺ͹���
	 */
	public void browseAndBuy(List<Goods> list) {
		if (list.size() != 0) {
			System.out.println("������Ž���鿴��ϸ��Ϣ��~");
			int i = ShoppingCartController.checkIndex(list.size());
			Goods goods = list.get(i - 1);
			ManageGoodsController.listDetail(goods);
			boolean flag = new ShoppingCartController().inertDetail(goods);
			if (flag) {
				System.out.println("�ɹ������Ʒ�����ﳵ��");
			} else {
				System.out.println("�����Ʒʧ�ܣ�");
			}
			return;
		}
	}

}
