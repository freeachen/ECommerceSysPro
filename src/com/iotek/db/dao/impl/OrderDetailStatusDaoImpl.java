package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.OrderDetailStatus;

/**
 * ��������״̬���ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDetailStatusDaoImpl extends DbDao {
	/**
	 * �������ж����򶩵���״̬
	 * 
	 * @return �����򶩵���״̬����
	 */
	public List<OrderDetailStatus> selectAll() {
		String sql = "select * from orderStatus;";

		return select(sql, null);
	}

	/**
	 * ���ض����򶩵���״̬
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return �����򶩵���״̬����
	 */
	private List<OrderDetailStatus> select(String sql, List<Object> params) {
		List<OrderDetailStatus> list = new ArrayList<OrderDetailStatus>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					OrderDetailStatus ods = null;
					ods = new OrderDetailStatus();
					ods.setId(rs.getInt("id"));
					ods.setStatus(rs.getString("status"));
					list.add(ods);
				}
			} catch (SQLException e) {
				System.out.println("���ݿ��ȡ�����򶩵���״̬ʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
