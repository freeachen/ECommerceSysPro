package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.User;
import com.iotek.biz.MainMenuController;

/**
 * �û����ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserDaoImpl extends DbDao {
	/**
	 * �����û�
	 * 
	 * @param u
	 *            User����
	 * @return true or false
	 */
	public boolean insert(User u) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into user (userName,pwd,address,"
				+ "email,exp,level,balance) values(?,?,?,?,?,?,?);";

		params.add(u.getUserName());
		params.add(u.getPwd());
		params.add(u.getAddress());
		params.add(u.getEmail());
		params.add(u.getExp());
		params.add(u.getLevel());
		params.add(u.getBalance());

		return super.update(sql, params);
	}

	/**
	 * ɾ����ǰ�û�
	 * 
	 * @return true or false
	 */
	public boolean delete() {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from user where id=?;";

		params.add(MainMenuController.user.getId());

		return super.update(sql, params);
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param u
	 *            User����
	 * @return true or false
	 */
	public boolean update(User u) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update user set pwd=?,address=?,email=?,"
				+ "exp=?,level=?,balance=? where id=?;";

		params.add(u.getPwd());
		params.add(u.getAddress());
		params.add(u.getEmail());
		params.add(u.getExp());
		params.add(u.getLevel());
		params.add(u.getBalance());
		params.add(u.getId());

		return super.update(sql, params);
	}

	/**
	 * �û������û����������ȡ�û�
	 * 
	 * @param u
	 *            User����
	 * @return User����
	 */
	public User selectByUserNameAndPwd(User u) {
		String sql = "select * from user where userName=? and pwd=?;";
		List<Object> params = new ArrayList<Object>();

		params.add(u.getUserName());
		params.add(u.getPwd());

		List<User> list = select(sql, params);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * ����Ա�����û�����ȡ�û�
	 * 
	 * @param u
	 *            User����
	 * @return User����
	 */
	public User selectByUserName(User u) {
		String sql = "select * from user where userName=?;";
		List<Object> params = new ArrayList<Object>();

		params.add(u.getUserName());

		List<User> list = select(sql, params);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * ���������û�����
	 * 
	 * @return �û�����
	 */
	public List<User> selectAll() {
		String sql = "select * from user;";
		return select(sql, null);
	}

	/**
	 * ��ѯ�������û�����
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return �û�����
	 */
	private List<User> select(String sql, List<Object> params) {
		User user = null;
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setUserName(rs.getString("userName"));
					user.setPwd(rs.getString("pwd"));
					user.setAddress(rs.getString("address"));
					user.setEmail(rs.getString("email"));
					user.setExp(rs.getInt("exp"));
					user.setLevel(rs.getInt("level"));
					user.setBalance(rs.getDouble("balance"));
					list.add(user);
				}
			} catch (SQLException e) {
				System.out.println("���ݿ��ȡ�û�ʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}

}
