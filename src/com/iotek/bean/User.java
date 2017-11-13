package com.iotek.bean;

import com.iotek.util.NumFormat;

/**
 * �û���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class User {
	private int id;// �û�id
	private String userName;// �û���
	private String pwd;// �û�����
	private String address;// �û��ջ���ַ
	private String email;// �û�����
	private int exp;// �û�����ֵ
	private int level;// �û��ȼ�
	private double balance;// �û������

	public User() {
		super();
	}

	public User(String userName, String pwd, String address, String email,
			int exp, int level, double balance) {
		super();
		this.userName = userName;
		this.pwd = pwd;
		this.address = address;
		this.email = email;
		this.exp = exp;
		this.level = level;
		this.balance = NumFormat.formatDouble(balance);
	}

	/**
	 * ����ȼ��ķ�����Lv1 1000��Lv2 5000��Lv3 20000��Lv4 50000��Lv5 100000
	 */
	public void calcLevel() {
		int[] i = { 1000, 5000, 20000, 50000, 100000 };
		level = 5;
		for (int j = i.length; j > 0; j--) {
			if (exp < i[j - 1]) {
				level = j;
			}
		}
	}

	/**
	 * �����û��ȼ��;���ֵ�ַ���
	 * 
	 * @return �ַ���
	 */
	public String showExp() {
		String str = null;
		switch (level) {
		case 1:
			str = "Lv" + level + "	(" + exp + "/1000" + ")";
			break;
		case 2:
			str = "Lv" + level + "	(" + exp + "/5000" + ")";
			break;
		case 3:
			str = "Lv" + level + "	(" + exp + "/20000" + ")";
			break;
		case 4:
			str = "Lv" + level + "	(" + exp + "/50000" + ")";
			break;
		case 5:
			str = "Lv" + level + "	(" + exp + "/100000" + ")";
			break;
		}
		return str;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * ���������͸�ʽ��
	 * 
	 * @return ��ʽ����ĸ�����
	 */
	public double getBalance() {
		return NumFormat.formatDouble(balance);
	}

	/**
	 * ���������͸�ʽ��
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = NumFormat.formatDouble(balance);
	}

	@Override
	public String toString() {
		return id + "\t" + userName + "\t" + pwd + "\t" + address + "\t"
				+ email + "\t" + exp + "\t" + level + "\t" + getBalance();
	}

}
