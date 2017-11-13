package com.iotek.db.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 数据库操作的基类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class DbDao {
	private static Properties prop = null;// 声明配置文件对象

	/**
	 * 读取配置文件，并加载驱动
	 */
	static {
		prop = new Properties();
		try {
			prop.load(new FileReader("ECommerce.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("未找到配置文件！");
		} catch (IOException e) {
			System.out.println("配置文件加载错误！");
		}
		try {
			Class.forName(prop.getProperty("className"));
		} catch (ClassNotFoundException e) {
			System.out.println("未找到驱动！");
		}
	}

	/**
	 * 与数据库建立连接
	 * 
	 * @return Connection对象
	 * @throws SQLException
	 */
	public Connection getConn() throws SQLException {
		return DriverManager.getConnection(prop.getProperty("protocol")
				+ prop.getProperty("dbUrl"));
	}

	/**
	 * 更改数据库操作
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数集合
	 * @return true or false
	 */
	public boolean update(String sql, List<Object> params) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			fillPs(params, ps);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("数据库更新失败！");
			return false;
		} finally {
			closeAll(conn, ps, null);
		}
	}

	/**
	 * 搜索数据库并返回结果集的方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数集合
	 * @param conn
	 *            Connection对象
	 * @param ps
	 *            PreparedStatement对象
	 * @return 结果集
	 */
	public ResultSet select(String sql, List<Object> params, Connection conn, PreparedStatement ps) {
		ResultSet rs = null;
		try {
			conn = getConn();
			ps = conn.prepareStatement(sql);
			fillPs(params, ps);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
		return rs;

	}

	/**
	 * 填充PreparedStatement里面的出现的占位符
	 * 
	 * @param params
	 *            参数集合
	 * @param ps
	 *            PreparedStatement对象
	 * @throws SQLException
	 */
	private void fillPs(List<Object> params, PreparedStatement ps)throws SQLException {
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
		}
	}

	/**
	 * 关闭资源的方法
	 * 
	 * @param conn
	 *            Connection对象
	 * @param ps
	 *            PreparedStatement对象
	 * @param rs
	 *            结果集
	 */
	public void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("关闭资源出错！");
		}
	}
}
