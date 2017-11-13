package com.iotek.bean;

/**
 * 商品状态的类
 * 
 * @author kd
 * @version 1.0
 * @since　JDK 1.7
 * 
 */
public class GoodStatus {
	private int id;// 该类id
	private String status;// 商品的状态

	public GoodStatus() {
		super();
	}

	public GoodStatus(String status) {
		super();
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
		return "GoodStatus [id=" + id + ", status=" + status + "]";
	}

}
