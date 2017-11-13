package com.iotek.bean;

import java.util.List;

import com.iotek.biz.ManageGoodsController;
import com.iotek.db.dao.impl.OrderDetailStatusDaoImpl;
import com.iotek.util.NumFormat;

/**
 * ������ϸ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDetail {
	private int id;// ������id
	private int gid;// ��Ʒid
	private int oid;// ����id
	private int num;// ��������
	private double totalPrice;// ���Ŷ�������ܼ�
	private int oStatusId;// ����״̬id

	public OrderDetail() {
		super();
	}

	/**
	 * ���ι��췽���������Զ����㶩�����ܼ�
	 * 
	 * @param gid
	 *            ��ƷID
	 * @param oid
	 *            ����ID
	 * @param num
	 *            ����
	 * @param oStatusId
	 *            ����״̬
	 */
	public OrderDetail(int gid, int oid, int num, int oStatusId) {
		super();
		this.gid = gid;
		this.oid = oid;
		this.num = num;
		this.oStatusId = oStatusId;
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

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
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

	public int getoStatusId() {
		return oStatusId;
	}

	public void setoStatusId(int oStatusId) {
		this.oStatusId = oStatusId;
	}

	/**
	 * �����Զ����ʽ���
	 * 
	 * @return ��������ϸ��Ϣ
	 */
	public String listInMyStyle() {
		List<OrderDetailStatus> list = new OrderDetailStatusDaoImpl()
				.selectAll();
		String str = null;
		// ���ݶ���״̬���ض���״̬���ַ���
		for (OrderDetailStatus ods : list) {
			if (ods.getId() == oStatusId) {
				str = ods.getStatus();
			}
		}
		Goods goods = ManageGoodsController.getGood(gid);
		return id + "\t" + goods.getId() + "\t" + goods.getName() + "\t"
				+ goods.getSalePrice() + "\t" + oid + "\t" + num + "\t"
				+ getTotalPrice() + "\t" + str;
	}

	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", gid=" + gid + ", oid=" + oid
				+ ", num=" + num + ", totalPrice=" + totalPrice
				+ ", oStatusId=" + oStatusId + "]";
	}
}
