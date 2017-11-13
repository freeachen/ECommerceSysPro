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
 * 订单数据库操作的实现类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDaoImpl extends DbDao {
	/**
	 * 新建订单（初始状态只有用户ID、时间、状态）
	 * 
	 * @param o
	 *            订单
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
	 * 删除订单
	 * 
	 * @param o
	 *            订单
	 * @return true or false
	 */
	public boolean delete(Order o) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from orders where id=?;";

		params.add(o.getId());

		return super.update(sql, params);
	}

	/**
	 * 更新订单状态
	 * 
	 * @param o
	 *            订单
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
	 * 更新订单总价，新建时使用
	 * 
	 * @param o
	 *            订单
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
	 * 返回所有订单
	 * 
	 * @return 订单集合
	 */
	public List<Order> selectAll() {
		String sql = "select * from orders";
		return select(sql, null);
	}

	/**
	 * 返回当前用户订单
	 * 
	 * @return 订单集合
	 */
	public List<Order> selectByUid() {
		String sql = "select * from orders where uid=?";
		List<Object> params = new ArrayList<Object>();

		params.add(MainMenuController.user.getId());

		return select(sql, params);
	}

	/**
	 * 根据状态返回订单
	 * 
	 * @param i
	 *            状态id
	 * @return 订单集合
	 */
	public List<Order> selectByStatus(int i) {
		String sql = "select * from orders where oStatusId=?";
		List<Object> params = new ArrayList<Object>();

		params.add(i);

		return select(sql, params);
	}

	/**
	 * 根据Id返回订单
	 * 
	 * @param i
	 *            订单id
	 * @return 订单集合
	 */
	public List<Order> selectById(int i) {
		String sql = "select * from orders where Id=?";
		List<Object> params = new ArrayList<Object>();

		params.add(i);

		return select(sql, params);
	}

	/**
	 * 新建订单时，获取自动生成的订单
	 * 
	 * @param date
	 *            时间字符串
	 * @return 订单
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
	 * 返回所有订单
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数集合
	 * @return 订单集合
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
				System.out.println("数据库读取订单失败！");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
