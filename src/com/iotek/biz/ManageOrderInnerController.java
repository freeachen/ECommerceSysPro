package com.iotek.biz;

/**
 * �������ӽ�����߼���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageOrderInnerController {
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
			// �޸�ָ�����������ж�����״̬
			new ManageOrderController().updateOrder();
			break;
		case 2:
			// �޸�ָ��������״̬
			new ManageOrderController().updateOrderDetail();
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
}
