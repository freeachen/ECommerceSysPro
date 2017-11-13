package com.iotek.bean;

/**
 * ��Ʒ������
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class Comment {
	private int id;// ����id
	private int gid;// ��Ʒid
	private String userName;// �û���
	private int oid;// ������id
	private String content;// ��������
	private String date;// ����ʱ��

	public Comment() {
		super();
	}

	public Comment(int gid, String userName, int oid, String content,
			String date) {
		super();
		this.gid = gid;
		this.userName = userName;
		this.oid = oid;
		this.content = content;
		this.date = date;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", gid=" + gid + ", userName=" + userName
				+ ", oid=" + oid + ", content=" + content + ", date=" + date
				+ "]";
	}

}
