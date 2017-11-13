package com.iotek.bean;

/**
 * �ѻ�ú������
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserGotRadEnv {
	private int id;// ����id
	private String userName;// ��ú�����û���
	private String date;// ����
	private int rid;// ���id

	public UserGotRadEnv() {
		super();
	}

	public UserGotRadEnv(String userName, String date, int rid) {
		super();
		this.userName = userName;
		this.date = date;
		this.rid = rid;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return "UserGotRadEnv [id=" + id + ", userName=" + userName + ", date="
				+ date + ", rid=" + rid + "]";
	}

}
