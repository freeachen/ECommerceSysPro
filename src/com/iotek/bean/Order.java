package com.iotek.bean;

import java.util.List;

import com.iotek.db.dao.impl.OrderDetailStatusDaoImpl;
import com.iotek.util.NumFormat;

/**
 * 订单类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class Order {
	private int id;// 订单id
	private int uid;// 下单用户的id
	private List<OrderDetail> orderList = null;// 订单明细项集合
	private String date;// 下单的时间
	private double totalPrice;// 订单的总价
	private int oStatusId;// 订单状态0-未完成，1-完成

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
	 * 计算总价的方法，生成大订单的时候必须调用
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
	 * 附带浮点型格式化
	 * 
	 * @return 格式化后的浮点型
	 */
	public double getTotalPrice() {
		return NumFormat.formatDouble(totalPrice);
	}

	/**
	 * 附带浮点型格式化
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
	 * 根据自定义格式输出
	 * 
	 * @return 订单详细信息
	 */
	public String listInMyStyle() {
		List<OrderDetailStatus> list = new OrderDetailStatusDaoImpl()
				.selectAll();
		String str = null;
		// 根据订单状态表返回订单状态的字符串
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
