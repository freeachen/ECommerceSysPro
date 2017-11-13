package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.OrderDetail;

/**
 * 订单项数据库操作的实现类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDetailDaoImpl extends DbDao {
	/**
	 * 新建订单项
	 * 
	 * @param od
	 *            订单项
	 * @return true or false
	 */
	public boolean insert(OrderDetail od) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into orderDetail(gid,oid,num,totalPrice,oStatusId) "
				+ "values(?,?,?,?,?);";

		params.add(od.getGid());
		params.add(od.getOid());
		params.add(od.getNum());
		params.add(od.getTotalPrice());
		params.add(od.getoStatusId());

		return super.update(sql, params);
	}

	/**
	 * 删除订单项
	 * 
	 * @param od
	 *            订单项
	 * @return true or false
	 */
	public boolean delete(OrderDetail od) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from shoppingDetail where id=?;";

		params.add(od.getId());

		return super.update(sql, params);
	}

	/**
	 * 更新订单项状态
	 * 
	 * @return true or false
	 */
	public boolean update(OrderDetail od) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update orderdetail set oStatusId=? where id=?;";

		params.add(od.getoStatusId());
		params.add(od.getId());

		return super.update(sql, params);
	}

	/**
	 * 返回所有订单项
	 * 
	 * @return 订单项集合
	 */
	public List<OrderDetail> selectAll() {
		String sql = "select * from orderdetail;";
		return select(sql, null);
	}

	/**
	 * 根据订单号查找其订单项
	 * 
	 * @param oid
	 *            订单号
	 * @return 订单项集合
	 */
	public List<OrderDetail> selectByOid(int oid) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from orderdetail where oid=?;";

		params.add(oid);

		return select(sql, params);
	}

	/**
	 * 根据订单状态返回订单项
	 * 
	 * @param i
	 *            状态id
	 * @return 订单项集合
	 */
	public List<OrderDetail> selectByStatus(int i) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from orderdetail where oStatusId=?;";

		params.add(i);

		return select(sql, params);
	}

	/**
	 * 返回订单项
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数集合
	 * @return 订单项集合
	 */
	private List<OrderDetail> select(String sql, List<Object> params) {
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					OrderDetail oDetail = null;
					oDetail = new OrderDetail();
					oDetail.setId(rs.getInt("id"));
					oDetail.setGid(rs.getInt("gid"));
					oDetail.setOid(rs.getInt("oid"));
					oDetail.setNum(rs.getInt("num"));
					oDetail.setTotalPrice(rs.getDouble("totalPrice"));
					oDetail.setoStatusId(rs.getInt("oStatusId"));
					list.add(oDetail);
				}
			} catch (SQLException e) {
				System.out.println("数据库读取订单项失败！");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}

}
