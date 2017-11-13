package com.iotek.bean;

/**
 * 红包类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class RadEnvelope {
	private int id;// 红包id
	private String envelopeName;// 红包的名字
	private double sumMoney;// 红包总额
	private double beGrab;// 被抢金额
	private double balance;// 余额

	public RadEnvelope() {
		super();
	}

	public RadEnvelope(String envelopeName, double sumMoney, double beGrab,
			double balance) {
		super();
		this.envelopeName = envelopeName;
		this.sumMoney = sumMoney;
		this.beGrab = beGrab;
		this.balance = balance;
	}

	/**
	 * 更新的时候要调用的计算余额
	 */
	public void culBalance() {
		balance = sumMoney - beGrab;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnvelopeName() {
		return envelopeName;
	}

	public void setEnvelopeName(String envelopeName) {
		this.envelopeName = envelopeName;
	}

	public double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public double getBeGrab() {
		return beGrab;
	}

	public void setBeGrab(double beGrab) {
		this.beGrab = beGrab;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "RadEnvelope [id=" + id + ", envelopeName=" + envelopeName
				+ ", sumMoney=" + sumMoney + ", beGrab=" + beGrab
				+ ", balance=" + balance + "]";
	}

}
