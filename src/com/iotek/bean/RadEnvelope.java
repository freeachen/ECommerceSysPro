package com.iotek.bean;

/**
 * �����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class RadEnvelope {
	private int id;// ���id
	private String envelopeName;// ���������
	private double sumMoney;// ����ܶ�
	private double beGrab;// �������
	private double balance;// ���

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
	 * ���µ�ʱ��Ҫ���õļ������
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
