package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Order;
import com.iotek.biz.MainMenuController;

/**
 * �������ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDaoImpl extends DbDao {
	/**
	 * �½���������ʼ״ֻ̬���û�ID��ʱ�䡢״̬��
	 * 
	 * @param o
	 *            ����
	 * @return true or false
	 */
	public boolean insert(Order o) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into orders(uid,date,oStatusId) values(?,?,?);";

		params.add(o.getUid());
		params.add(o.getDate());
		params.add(o.getoStatusId());

		return super.update(sql, params);
	}

	/**
	 * ɾ������
	 * 
	 * @param o
	 *            ����
	 * @return true or false
	 */
	public boolean delete(Order o) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from orders where id=?;";

		params.add(o.getId());

		return super.update(sql, params);
	}

	/**
	 * ���¶���״̬
	 * 
	 * @param o
	 *            ����
	 * @return true or false
	 */
	public boolean updateStatus(Order o) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update orders set oStatusId=? where id=?;";

		params.add(o.getoStatusId());
		params.add(o.getId());

		return super.update(sql, params);
	}

	/**
	 * ���¶����ܼۣ��½�ʱʹ��
	 * 
	 * @param o
	 *            ����
	 * @return true or false
	 */
	public boolean updateTotalPrice(Order o) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update orders set totalPrice=? where id=?;";

		params.add(o.getTotalPrice());
		params.add(o.getId());

		return super.update(sql, params);
	}

	/**
	 * �������ж���
	 * 
	 * @return ��������
	 */
	public List<Order> selectAll() {
		String sql = "select * from orders";
		return select(sql, null);
	}

	/**
	 * ���ص�ǰ�û�����
	 * 
	 * @return ��������
	 */
	public List<Order> selectByUid() {
		String sql = "select * from orders where uid=?";
		List<Object> params = new ArrayList<Object>();

		params.add(MainMenuController.user.getId());

		return select(sql, params);
	}

	/**
	 * ����״̬���ض���
	 * 
	 * @param i
	 *            ״̬id
	 * @return ��������
	 */
	public List<Order> selectByStatus(int i) {
		String sql = "select * from orders where oStatusId=?";
		List<Object> params = new ArrayList<Object>();

		params.add(i);

		return select(sql, params);
	}

	/**
	 * ����Id���ض���
	 * 
	 * @param i
	 *            ����id
	 * @return ��������
	 */
	public List<Order> selectById(int i) {
		String sql = "select * from orders where Id=?";
		List<Object> params = new ArrayList<Object>();

		params.add(i);

		return select(sql, params);
	}

	/**
	 * �½�����ʱ����ȡ�Զ����ɵĶ���
	 * 
	 * @param date
	 *            ʱ���ַ���
	 * @return ����
	 */
	public Order getNewOrder(String date) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from orders where uid=? and date=?;";

		params.add(MainMenuController.user.getId());
		params.add(date);

		List<Order> list = select(sql, params);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * �������ж���
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return ��������
	 */
	private List<Order> select(String sql, List<Object> params) {
		List<Order> list = new ArrayList<Order>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					Order order = null;
					order = new Order();
					int id = rs.getInt("id");
					order.setId(id);
					order.setUid(rs.getInt("uid"));
					order.setDate(rs.getString("date"));
					order.setTotalPrice(rs.getDouble("totalPrice"));
					order.setoStatusId(rs.getInt("oStatusId"));
					order.setOrderList(new OrderDetailDaoImpl().selectByOid(id));
					list.add(order);
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
