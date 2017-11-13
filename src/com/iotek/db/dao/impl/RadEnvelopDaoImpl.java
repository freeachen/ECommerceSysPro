package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.RadEnvelope;

/**
 * ����������ݿ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class RadEnvelopDaoImpl extends DbDao {
	/**
	 * �������
	 * 
	 * @param r
	 *            ���
	 * @return true or false
	 */
	public boolean insert(RadEnvelope r) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into radEnvelope(envelopeName,sumMoney,beGrab,balance) values(?,?,?,?);";

		params.add(r.getEnvelopeName());
		params.add(r.getSumMoney());
		params.add(r.getBeGrab());
		params.add(r.getBalance());

		return super.update(sql, params);
	}

	/**
	 * ɾ�����
	 * 
	 * @param r
	 *            ���
	 * @return true or false
	 */
	public boolean delete(RadEnvelope r) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from radEnvelope where id=?;";

		params.add(r.getId());

		return super.update(sql, params);
	}

	/**
	 * ���ĺ��
	 * 
	 * @param r
	 *            ���
	 * @return true or false
	 */
	public boolean update(RadEnvelope r) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update radEnvelope set envelopeName=?,sumMoney=?,beGrab=?,balance=? where id=?;";

		params.add(r.getEnvelopeName());
		params.add(r.getSumMoney());
		params.add(r.getBeGrab());
		params.add(r.getBalance());
		params.add(r.getId());

		return super.update(sql, params);
	}

	/**
	 * ����ָ�����
	 * 
	 * @return ���
	 */
	public RadEnvelope selectById(int i) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from radEnvelope where id=?;";
		params.add(i);

		List<RadEnvelope> list = select(sql, params);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * �������к��
	 * 
	 * @return �����
	 */
	public List<RadEnvelope> selectAll() {
		String sql = "select * from radEnvelope;";
		return select(sql, null);
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
	public List<RadEnvelope> select(String sql, List<Object> params) {
		List<RadEnvelope> list = new ArrayList<RadEnvelope>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					RadEnvelope re = null;
					re = new RadEnvelope();
					re.setId(rs.getInt("id"));
					re.setEnvelopeName(rs.getString("envelopeName"));
					re.setSumMoney(rs.getDouble("sumMoney"));
					re.setBeGrab(rs.getDouble("beGrab"));
					re.setBalance(rs.getDouble("balance"));
					list.add(re);
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
