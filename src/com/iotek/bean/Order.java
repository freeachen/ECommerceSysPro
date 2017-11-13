package com.iotek.bean;

import java.util.List;

import com.iotek.db.dao.impl.OrderDetailStatusDaoImpl;
import com.iotek.util.NumFormat;

/**
 * ������
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class Order {
	private int id;// ����id
	private int uid;// �µ��û���id
	private List<OrderDetail> orderList = null;// ������ϸ���
	private String date;// �µ���ʱ��
	private double totalPrice;// �������ܼ�
	private int oStatusId;// ����״̬0-δ��ɣ�1-���

	public Order() {
		super();
	}

	public Order(int uid, String date, int oStatusId) {
		super();
		this.uid = uid;
		this.date = date;
		this.oStatusId = oStatusId;
	}

	/**
	 * �����ܼ۵ķ��������ɴ󶩵���ʱ��������
	 */
	public void culTotalPrice() {
		for (OrderDetail od : orderList) {
			totalPrice += od.getTotalPrice();
		}
		totalPrice = NumFormat.formatDouble(totalPrice);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public List<OrderDetail> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDetail> orderList) {
		this.orderList = orderList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
	 * @return ������ϸ��Ϣ
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
		return id + "\t" + uid + "\t" + date + "\t" + getTotalPrice() + "\t"
				+ str;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", uid=" + uid + ", orderList=" + orderList
				+ ", date=" + date + ", totalPrice=" + totalPrice
				+ ", oStatusId=" + oStatusId + "]";
	}

}
