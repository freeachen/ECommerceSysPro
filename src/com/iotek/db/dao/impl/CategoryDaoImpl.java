package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Category;

/**
 * ��Ʒ�������ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class CategoryDaoImpl extends DbDao {
	/**
	 * ����������Ʒ����
	 * 
	 * @return ��Ʒ���༯
	 */
	public List<Category> selectAll() {
		String sql = "select * from category;";

		return select(sql, null);
	}

	/**
	 * ������Ʒ����
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return ��Ʒ���༯��
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
				System.out.println("���ݿ��ȡ��Ʒ����ʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
