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
 * ���ݿ�����Ļ���
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class DbDao {
	private static Properties prop = null;// ���������ļ�����

	/**
	 * ��ȡ�����ļ�������������
	 */
	static {
		prop = new Properties();
		try {
			prop.load(new FileReader("ECommerce.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("δ�ҵ������ļ���");
		} catch (IOException e) {
			System.out.println("�����ļ����ش���");
		}
		try {
			Class.forName(prop.getProperty("className"));
		} catch (ClassNotFoundException e) {
			System.out.println("δ�ҵ�������");
		}
	}

	/**
	 * �����ݿ⽨������
	 * 
	 * @return Connection����
	 * @throws SQLException
	 */
	public Connection getConn() throws SQLException {
		return DriverManager.getConnection(prop.getProperty("protocol")
				+ prop.getProperty("dbUrl"));
	}

	/**
	 * �������ݿ����
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
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
			System.out.println("���ݿ����ʧ�ܣ�");
			return false;
		} finally {
			closeAll(conn, ps, null);
		}
	}

	/**
	 * �������ݿⲢ���ؽ�����ķ���
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @param conn
	 *            Connection����
	 * @param ps
	 *            PreparedStatement����
	 * @return �����
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
	 * ���PreparedStatement����ĳ��ֵ�ռλ��
	 * 
	 * @param params
	 *            ��������
	 * @param ps
	 *            PreparedStatement����
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
	 * �ر���Դ�ķ���
	 * 
	 * @param conn
	 *            Connection����
	 * @param ps
	 *            PreparedStatement����
	 * @param rs
	 *            �����
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
			System.out.println("�ر���Դ����");
		}
	}
}
