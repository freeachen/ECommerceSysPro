package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.ShoppingDetail;
import com.iotek.biz.MainMenuController;

/**
 * ���������ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ShoppingDetailDaoImpl extends DbDao {

	/**
	 * ����������
	 * 
	 * @param sd
	 *            ������
	 * @return true or false
	 */
	public boolean insert(ShoppingDetail sd) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into shoppingDetail(gid,uid,num,totalPrice) "
				+ "values(?,?,?,?);";

		params.add(sd.getGid());
		params.add(sd.getUid());
		params.add(sd.getNum());
		params.add(sd.getTotalPrice());

		return super.update(sql, params);
	}

	/**
	 * ɾ��������
	 * 
	 * @param sd
	 *            ������
	 * @return true or false
	 */
	public boolean delete(ShoppingDetail sd) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from shoppingDetail where id=?;";

		params.add(sd.getId());

		return super.update(sql, params);
	}

	/**
	 * �޸Ĺ����������
	 * 
	 * @param sd
	 *            ������
	 * @return true or false
	 */
	public boolean update(ShoppingDetail sd) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update shoppingDetail set num=?,totalPrice=? where gid=? and uid=?;";

		params.add(sd.getNum());
		params.add(sd.getTotalPrice());
		params.add(sd.getGid());
		params.add(sd.getUid());

		return super.update(sql, params);
	}

	/**
	 * ���ص�½�û����й��������
	 * 
	 * @return �������
	 */
	public List<ShoppingDetail> select() {
		List<Object> params = new ArrayList<Object>();
		List<ShoppingDetail> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from shoppingDetail where uid=?;";

		params.add(MainMenuController.user.getId());

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			list = new ArrayList<ShoppingDetail>();
			try {
				while (rs.next()) {
					ShoppingDetail sDetail = null;
					sDetail = new ShoppingDetail();
					sDetail.setId(rs.getInt("id"));
					sDetail.setGid(rs.getInt("gid"));
					sDetail.setUid(rs.getInt("uid"));
					sDetail.setNum(rs.getInt("num"));
					sDetail.setTotalPrice(rs.getDouble("totalPrice"));
					list.add(sDetail);
				}
			} catch (SQLException e) {
				System.out.println("���ݿ��ȡ������ʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
