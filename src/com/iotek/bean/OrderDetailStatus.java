package com.iotek.bean;

/**
 * 订单状态的类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDetailStatus {
	private int id;// 该类的id
	private String status;// 订单状态

	public OrderDetailStatus() {
		super();
	}

	public OrderDetailStatus(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderStatus [id=" + id + ", status=" + status + "]";
	}

}
