package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.OrderDetail;

/**
 * ���������ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDetailDaoImpl extends DbDao {
	/**
	 * �½�������
	 * 
	 * @param od
	 *            ������
	 * @return true or false
	 */
	public boolean insert(OrderDetail od) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into orderDetail(gid,oid,num,totalPrice,oStatusId) "
				+ "values(?,?,?,?,?);";

		params.add(od.getGid());
		params.add(od.getOid());
		params.add(od.getNum());
		params.add(od.getTotalPrice());
		params.add(od.getoStatusId());

		return super.update(sql, params);
	}

	/**
	 * ɾ��������
	 * 
	 * @param od
	 *            ������
	 * @return true or false
	 */
	public boolean delete(OrderDetail od) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from shoppingDetail where id=?;";

		params.add(od.getId());

		return super.update(sql, params);
	}

	/**
	 * ���¶�����״̬
	 * 
	 * @return true or false
	 */
	public boolean update(OrderDetail od) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update orderdetail set oStatusId=? where id=?;";

		params.add(od.getoStatusId());
		params.add(od.getId());

		return super.update(sql, params);
	}

	/**
	 * �������ж�����
	 * 
	 * @return �������
	 */
	public List<OrderDetail> selectAll() {
		String sql = "select * from orderdetail;";
		return select(sql, null);
	}

	/**
	 * ���ݶ����Ų����䶩����
	 * 
	 * @param oid
	 *            ������
	 * @return �������
	 */
	public List<OrderDetail> selectByOid(int oid) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from orderdetail where oid=?;";

		params.add(oid);

		return select(sql, params);
	}

	/**
	 * ���ݶ���״̬���ض�����
	 * 
	 * @param i
	 *            ״̬id
	 * @return �������
	 */
	public List<OrderDetail> selectByStatus(int i) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from orderdetail where oStatusId=?;";

		params.add(i);

		return select(sql, params);
	}

	/**
	 * ���ض�����
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return �������
	 */
	private List<OrderDetail> select(String sql, List<Object> params) {
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					OrderDetail oDetail = null;
					oDetail = new OrderDetail();
					oDetail.setId(rs.getInt("id"));
					oDetail.setGid(rs.getInt("gid"));
					oDetail.setOid(rs.getInt("oid"));
					oDetail.setNum(rs.getInt("num"));
					oDetail.setTotalPrice(rs.getDouble("totalPrice"));
					oDetail.setoStatusId(rs.getInt("oStatusId"));
					list.add(oDetail);
				}
			} catch (SQLException e) {
				System.out.println("���ݿ��ȡ������ʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}

}
