package com.iotek.bean;

/**
 * 已获得红包的类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserGotRadEnv {
	private int id;// 自身id
	private String userName;// 获得红包的用户名
	private String date;// 日期
	private int rid;// 红包id

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
