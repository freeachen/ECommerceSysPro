package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.UserGotRadEnv;

/**
 * �����ѻ�ú�������ݿ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserGotREDaoImpl extends DbDao {
	/**
	 * ��������Ϣ
	 * 
	 * @param ugr
	 *            ��Ϣ����
	 * @return true or false
	 */
	public boolean insert(UserGotRadEnv ugr) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into userGotRadEnv (userName,date,rid) values(?,?,?);";

		params.add(ugr.getUserName());
		params.add(ugr.getDate());
		params.add(ugr.getRid());

		return super.update(sql, params);
	}

	/**
	 * ɾ����Ϣ
	 * 
	 * @param ugr
	 *            ��Ϣ����
	 * @return true or false
	 */
	public boolean delete(UserGotRadEnv ugr) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from userGotRadEnv where id=?;";

		params.add(ugr.getId());

		return super.update(sql, params);
	}

	/**
	 * ������������
	 * 
	 * @return ���ۼ�
	 */
	public List<UserGotRadEnv> selectByRid(int i) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from userGotRadEnv where rid=?;";

		params.add(i);
		return select(sql, params);
	}

	/**
	 * ���غ����
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return ���ۼ�
	 */
	public List<UserGotRadEnv> select(String sql, List<Object> params) {
		List<UserGotRadEnv> list = new ArrayList<UserGotRadEnv>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					UserGotRadEnv ugr = null;
					ugr = new UserGotRadEnv();
					ugr.setId(rs.getInt("id"));
					ugr.setUserName(rs.getString("userName"));
					ugr.setDate(rs.getString("date"));
					ugr.setRid(rs.getInt("rid"));
					list.add(ugr);
				}
			} catch (SQLException e) {
				System.out.println("���ݿ��ȡ����ʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
