package com.iotek.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.iotek.bean.Comment;
import com.iotek.bean.Goods;
import com.iotek.bean.OrderDetail;
import com.iotek.db.dao.impl.CommentDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.util.GetDate;

/**
 * ���۵��߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class CommentController {
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
			// ��ʾδ���۵Ķ�����
			listNotCommentGoods();
			break;
		case 2:
			// ��ʾ��ʷ����
			listComment(new CommentDaoImpl().selectByUserName());
			break;
		case 3:
			// ��������
			updateMyComment();
			break;
		case 4:
			// ɾ������
			deleteMyComment();
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
	 * ɾ������
	 */
	public void deleteMyComment() {
		// ���ص�ǰ�û�����������
		List<Comment> list = new CommentDaoImpl().selectByUserName();
		listComment(list);
		if (list.size() == 0) {
			return;
		}
		System.out.println("����Ҫɾ�����۵���ţ�");
		int i = ShoppingCartController.checkIndex(list.size());
		Comment comm = list.get(i - 1);
		if (new CommentDaoImpl().delete(comm)) {
			System.out.println("ɾ���ɹ���");
			return;
		} else {
			System.out.println("ɾ��ʧ�ܣ�");
			return;
		}
	}

	/**
	 * �����ҵ�����
	 */
	public void updateMyComment() {
		Scanner in = new Scanner(System.in);
		// ���ص�ǰ�û�����������
		List<Comment> list = new CommentDaoImpl().selectByUserName();
		listComment(list);
		if (list.size() == 0) {
			return;
		}
		System.out.println("����Ҫ�޸Ķ��������ţ�");
		int i = ShoppingCartController.checkIndex(list.size());
		Comment comm = list.get(i - 1);
		System.out.println("�޸�������۰ɣ�");
		String content = in.nextLine();
		comm.setContent(content);
		if (new CommentDaoImpl().update(comm)) {
			System.out.println("�޸����۳ɹ���");
			return;
		} else {
			System.out.println("�޸�����ʧ�ܣ�");
			return;
		}
	}

	/**
	 * ��ʾδ���۵Ķ���
	 */
	public void listNotCommentGoods() {
		// ��ʾ�û���ɵĶ�����
		List<OrderDetail> od = new OrderController().getMyODByStatus(5);
		List<OrderDetail> od2 = new ArrayList<OrderDetail>();
		// �ӵ�od2����ȥ�����������Ϣʱ����
		od2.addAll(od);
		// �����û�������
		List<Comment> list = new CommentDaoImpl().selectByUserName();
		// �����û���ɵĶ����������б����˳�δ���۵Ķ�����
		for (OrderDetail odd : od) {
			int gid = odd.getGid();
			int oid = odd.getId();
			for (Comment comm : list) {
				if (comm.getGid() == gid && comm.getOid() == oid) {
					// һ�����ھʹ�od2���Ƴ�
					od2.remove(odd);
					break;
				}
			}
		}
		System.out.println("�һ�δ���۵Ķ�����");
		if (od2.size() != 0) {
			new ManageOrderController().listOrderDetail(od2);
			// ��ʼ������Ʒ
			commentGoods(od2);
		} else {
			System.out.println("���޿����۶�����");
		}
	}

	/**
	 * ������Ʒ
	 * 
	 * @param od
	 *            �����
	 */
	public void commentGoods(List<OrderDetail> od) {
		System.out.println("�����붩������Ž������ۣ�");
		int i = ShoppingCartController.checkIndex(od.size());
		OrderDetail odd = od.get(i - 1);
		comment(odd);
	}

	/**
	 * ��������
	 */
	public void insertComment(OrderDetail od) {
		System.out.println("��ȥ���۰�~");
		while (true) {
			System.out.println("��1-�������ۣ�0-�Ժ�������");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				System.out.println("�Ժ���ڡ���Ҫ���ۡ���׷�����ۣ�");
				return;
			} else if (i == 1) {
				comment(od);
				return;
			} else {
				System.out.println("�޴�ѡ�");
				continue;
			}
		}
	}

	/**
	 * ��ʼ����
	 * 
	 * @param od
	 *            ������
	 */
	private void comment(OrderDetail od) {
		Scanner in = new Scanner(System.in);
		System.out.println("��������Ը���Ʒ�����ۣ�");
		String content = in.nextLine();
		// �½����۶����û������Ϊ��ǰ��½�û����û���
		Comment c = new Comment(od.getGid(),
				MainMenuController.user.getUserName(), od.getId(), content,
				GetDate.getNowTime());
		boolean flag = new CommentDaoImpl().insert(c);
		if (flag) {
			System.out.println("��л�������ۣ�");
			return;
		} else {
			System.out.println("����ʧ�ܣ�");
			return;
		}
	}

	/**
	 * ��ʾ����
	 */
	public void listComment(List<Comment> c) {
		Goods good = null;
		int count = 0;
		System.out.println("�û����ۣ�");
		System.out.println("���\t������ID\t�û���\t��Ʒ��\t��������\t����ʱ��");
		if (c.size() != 0) {
			for (Comment comm : c) {
				good = ManageGoodsController.getGood(comm.getGid());
				System.out.println((++count) + "\t" + comm.getOid() + "\t"
						+ comm.getUserName() + "\t" + good.getName() + "\t"
						+ comm.getContent() + "\t" + comm.getDate());
			}
		} else {
			System.out.println("�������ۣ�");
		}
	}
}
