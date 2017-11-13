package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Goods;

/**
 * ��Ʒ�����ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class GoodsDaoImpl extends DbDao {

	/**
	 * ������Ʒ�ķ���
	 * 
	 * @param g
	 *            Goods����
	 * @return true or false
	 */
	public boolean insert(Goods g) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into goods (name,price,stock,"
				+ "describe,categoryId,gStatusId) values(?,?,?,?,?,?);";

		params.add(g.getName());
		params.add(g.getPrice());
		params.add(g.getStock());
		params.add(g.getDescribe());
		params.add(g.getCategoryId());
		params.add(g.getgStatusId());

		return super.update(sql, params);
	}

	/**
	 * ������ƷID������Ʒ��Ϣ�ķ���
	 * 
	 * @param g
	 *            Goods����
	 * @return true or false
	 */
	public boolean update(Goods g) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update goods set name=?,price=?,stock=?,"
				+ "describe=?,categoryId=?,gStatusId=? where id=?;";

		params.add(g.getName());
		params.add(g.getPrice());
		params.add(g.getStock());
		params.add(g.getDescribe());
		params.add(g.getCategoryId());
		params.add(g.getgStatusId());
		params.add(g.getId());

		return super.update(sql, params);
	}

	/**
	 * ����״̬������Ʒ���������¼���Ʒ��
	 * 
	 * @param g
	 *            ��Ʒ
	 * @return ��Ʒ��
	 */
	public List<Goods> selectByStatus(Goods g) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from goods where gStatusId=? and gStatusId!=?;";

		params.add(g.getgStatusId());
		params.add(1);

		return select(sql, params);
	}

	/**
	 * ������𷵻���Ʒ���������¼���Ʒ��
	 * 
	 * @param g
	 *            ��Ʒ
	 * @return ��Ʒ��
	 */
	public List<Goods> selectByCategory(Goods g) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from goods where categoryId=? and gStatusId!=?;";

		params.add(g.getCategoryId());
		params.add(1);

		return select(sql, params);
	}

	/**
	 * ����������Ʒ���������¼���Ʒ��
	 * 
	 * @return ��Ʒ����
	 */
	public List<Goods> selectAll() {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from goods where gStatusId!=?;";

		params.add(1);

		return select(sql, params);
	}

	/**
	 * ����������Ʒ
	 * 
	 * @return ��Ʒ��
	 */
	public List<Goods> queryAll() {
		String sql = "select * from goods;";

		return select(sql, null);
	}

	/**
	 * ������Ʒ
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return ��Ʒ����
	 */
	private List<Goods> select(String sql, List<Object> params) {
		List<Goods> list = new ArrayList<Goods>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					Goods goods = null;
					goods = new Goods();
					goods.setId(rs.getInt("id"));
					goods.setName(rs.getString("name"));
					goods.setPrice(rs.getDouble("price"));
					goods.setStock(rs.getInt("stock"));
					goods.setDescribe(rs.getString("describe"));
					goods.setCategoryId(rs.getInt("categoryId"));
					goods.setgStatusId(rs.getInt("gStatusId"));
					list.add(goods);
				}
			} catch (SQLException e) {
				System.out.println("���ݿ��ȡ��Ʒʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}

}
