package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.GoodStatus;

/**
 * ��Ʒ״̬�����ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class GoodStatusDaoImpl extends DbDao {
	/**
	 * ������Ʒ״̬
	 * 
	 * @return ��Ʒ״̬�༯��
	 */
	public List<GoodStatus> selectAll() {
		String sql = "select * from goodStatus;";

		return select(sql, null);
	}

	/**
	 * ������Ʒ״̬
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return ��Ʒ״̬����
	 */
	private List<GoodStatus> select(String sql, List<Object> params) {
		List<GoodStatus> list = new ArrayList<GoodStatus>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					GoodStatus gs = null;
					gs = new GoodStatus();
					gs.setId(rs.getInt("id"));
					gs.setStatus(rs.getString("status"));
					list.add(gs);
				}
			} catch (SQLException e) {
				System.out.println("���ݿ��ȡ��Ʒ״̬ʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
