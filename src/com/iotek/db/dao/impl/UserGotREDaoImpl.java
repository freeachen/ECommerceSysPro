package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.UserGotRadEnv;

/**
 * 操作已获得红包的数据库的类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserGotREDaoImpl extends DbDao {
	/**
	 * 新增该信息
	 * 
	 * @param ugr
	 *            信息对象
	 * @return true or false
	 */
	public boolean insert(UserGotRadEnv ugr) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into userGotRadEnv (userName,date,rid) values(?,?,?);";

		params.add(ugr.getUserName());
		params.add(ugr.getDate());
		params.add(ugr.getRid());

		return super.update(sql, params);
	}

	/**
	 * 删除信息
	 * 
	 * @param ugr
	 *            信息对象
	 * @return true or false
	 */
	public boolean delete(UserGotRadEnv ugr) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from userGotRadEnv where id=?;";

		params.add(ugr.getId());

		return super.update(sql, params);
	}

	/**
	 * 返回所有评论
	 * 
	 * @return 评论集
	 */
	public List<UserGotRadEnv> selectByRid(int i) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from userGotRadEnv where rid=?;";

		params.add(i);
		return select(sql, params);
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
	public List<UserGotRadEnv> select(String sql, List<Object> params) {
		List<UserGotRadEnv> list = new ArrayList<UserGotRadEnv>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					UserGotRadEnv ugr = null;
					ugr = new UserGotRadEnv();
					ugr.setId(rs.getInt("id"));
					ugr.setUserName(rs.getString("userName"));
					ugr.setDate(rs.getString("date"));
					ugr.setRid(rs.getInt("rid"));
					list.add(ugr);
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
