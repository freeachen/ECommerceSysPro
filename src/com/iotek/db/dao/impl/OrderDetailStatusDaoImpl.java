package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.OrderDetailStatus;

/**
 * 订单项类状态数据库操作的实现类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDetailStatusDaoImpl extends DbDao {
	/**
	 * 返回所有订单或订单项状态
	 * 
	 * @return 订单或订单项状态集合
	 */
	public List<OrderDetailStatus> selectAll() {
		String sql = "select * from orderStatus;";

		return select(sql, null);
	}

	/**
	 * 返回订单或订单项状态
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数集合
	 * @return 订单或订单项状态集合
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
				System.out.println("数据库读取订单或订单项状态失败！");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
