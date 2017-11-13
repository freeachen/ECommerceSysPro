package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Category;

/**
 * 商品分类数据库操作的实现类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class CategoryDaoImpl extends DbDao {
	/**
	 * 返回所有商品分类
	 * 
	 * @return 商品分类集
	 */
	public List<Category> selectAll() {
		String sql = "select * from category;";

		return select(sql, null);
	}

	/**
	 * 返回商品分类
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数集合
	 * @return 商品分类集合
	 */
	private List<Category> select(String sql, List<Object> params) {
		List<Category> list = new ArrayList<Category>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					Category category = null;
					category = new Category();
					category.setId(rs.getInt("id"));
					category.setCategory(rs.getString("category"));
					list.add(category);
				}
			} catch (SQLException e) {
				System.out.println("数据库读取商品分类失败！");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
