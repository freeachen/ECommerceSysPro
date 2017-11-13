package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.GoodStatus;

/**
 * 商品状态类数据库操作的实现类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class GoodStatusDaoImpl extends DbDao {
	/**
	 * 返回商品状态
	 * 
	 * @return 商品状态类集合
	 */
	public List<GoodStatus> selectAll() {
		String sql = "select * from goodStatus;";

		return select(sql, null);
	}

	/**
	 * 返回商品状态
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数集合
	 * @return 商品状态集合
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
				System.out.println("数据库读取商品状态失败！");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
