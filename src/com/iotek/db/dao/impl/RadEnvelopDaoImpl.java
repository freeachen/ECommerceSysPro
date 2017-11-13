package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.RadEnvelope;

/**
 * 操作红包数据库的类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class RadEnvelopDaoImpl extends DbDao {
	/**
	 * 新增红包
	 * 
	 * @param r
	 *            红包
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
	 * 删除红包
	 * 
	 * @param r
	 *            红包
	 * @return true or false
	 */
	public boolean delete(RadEnvelope r) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from radEnvelope where id=?;";

		params.add(r.getId());

		return super.update(sql, params);
	}

	/**
	 * 更改红包
	 * 
	 * @param r
	 *            红包
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
	 * 返回指定红包
	 * 
	 * @return 红包
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
	 * 返回所有红包
	 * 
	 * @return 红包集
	 */
	public List<RadEnvelope> selectAll() {
		String sql = "select * from radEnvelope;";
		return select(sql, null);
	}

	/**
	 * 返回红包集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数集合
	 * @return 评论集
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
				System.out.println("数据库读取评论失败！");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
