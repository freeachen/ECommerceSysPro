package com.iotek.bean;

import com.iotek.biz.ManageGoodsController;
import com.iotek.util.NumFormat;

/**
 * ���ﳵ��ϸ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ShoppingDetail {
	private int id;// ���ﳵ��ϸ��id
	private int gid;// ��Ʒid
	private int uid;// �û�id
	private int num;// ��������
	private double totalPrice;// �������ܼ�

	public ShoppingDetail() {
		super();
	}

	/**
	 * ���ι��췽��
	 * 
	 * @param id
	 *            ������ID
	 * @param gid
	 *            ��ƷID
	 * @param uid
	 *            �û�ID
	 * @param num
	 *            ����
	 */
	public ShoppingDetail(int id, int gid, int uid, int num) {
		super();
		this.id = id;
		this.gid = gid;
		this.uid = uid;
		this.num = num;
		// ʹ���вι��췽����������ʱ�ͼ���õ����ܼ�
		Goods goods = ManageGoodsController.getGood(gid);
		totalPrice = goods.getSalePrice() * num;
		totalPrice = NumFormat.formatDouble(totalPrice);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * ���������͸�ʽ��
	 * 
	 * @return ��ʽ����ĸ�����
	 */
	public double getTotalPrice() {
		return NumFormat.formatDouble(totalPrice);
	}

	/**
	 * ���������͸�ʽ��
	 * 
	 * @param totalPrice
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = NumFormat.formatDouble(totalPrice);
	}

	/**
	 * �����Զ����ʽ���
	 */
	public String listInMyStyle() {
		Goods goods = ManageGoodsController.getGood(gid);
		return gid + "\t" + goods.getName() + "\t" + goods.getSalePrice()
				+ "\t" + num + "\t" + getTotalPrice();
	}

	@Override
	public String toString() {
		return "ShoppingDetail [id=" + id + ", gid=" + gid + ", uid=" + uid
				+ ", num=" + num + ", totalPrice=" + totalPrice + "]";
	}

}
