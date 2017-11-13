package com.iotek.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Comment;
import com.iotek.bean.Goods;
import com.iotek.biz.MainMenuController;

/**
 * �������ݿ������ʵ����
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class CommentDaoImpl extends DbDao {

	/**
	 * ��������
	 * 
	 * @param c
	 *            ���۶���
	 * @return true or false
	 */
	public boolean insert(Comment c) {
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into comment (gid,userName,oid,content,date) values(?,?,?,?,?);";

		params.add(c.getGid());
		params.add(c.getUserName());
		params.add(c.getOid());
		params.add(c.getContent());
		params.add(c.getDate());

		return super.update(sql, params);
	}

	/**
	 * ɾ������
	 * 
	 * @param c
	 *            ���۶���
	 * @return true or false
	 */
	public boolean delete(Comment c) {
		List<Object> params = new ArrayList<Object>();
		String sql = "delete from comment where id=?;";

		params.add(c.getId());

		return super.update(sql, params);
	}

	/**
	 * ��������
	 * 
	 * @param c
	 *            ���۶���
	 * @return true or false
	 */
	public boolean update(Comment c) {
		List<Object> params = new ArrayList<Object>();
		String sql = "update comment set content=?,date=? where id=?;";

		params.add(c.getContent());
		params.add(c.getDate());
		params.add(c.getId());

		return super.update(sql, params);
	}

	/**
	 * ������Ʒid��������
	 * 
	 * @param g
	 *            ��Ʒ
	 * @return ���ۼ�
	 */
	public List<Comment> selectByGid(Goods g) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from comment where gid=?";

		params.add(g.getId());

		return select(sql, params);
	}

	/**
	 * ���ص�ǰ�û�������
	 * 
	 * @param g
	 *            ��Ʒ
	 * @return ���ۼ�
	 */
	public List<Comment> selectByUserName() {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from comment where userName=?;";

		params.add(MainMenuController.user.getUserName());

		return select(sql, params);
	}

	/**
	 * ������������
	 * 
	 * @return ���ۼ�
	 */
	public List<Comment> selectAll() {
		String sql = "select * from comment;";
		return select(sql, null);
	}

	/**
	 * �������ۼ�
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return ���ۼ�
	 */
	public List<Comment> select(String sql, List<Object> params) {
		List<Comment> list = new ArrayList<Comment>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		rs = super.select(sql, params, conn, ps);

		if (rs != null) {
			try {
				while (rs.next()) {
					Comment comment = null;
					comment = new Comment();
					comment.setId(rs.getInt("id"));
					comment.setGid(rs.getInt("gid"));
					comment.setUserName(rs.getString("userName"));
					comment.setOid(rs.getInt("oid"));
					comment.setContent(rs.getString("content"));
					comment.setDate(rs.getString("date"));
					list.add(comment);
				}
			} catch (SQLException e) {
				System.out.println("���ݿ��ȡ����ʧ�ܣ�");
			} finally {
				closeAll(conn, ps, rs);
			}
		}
		return list;
	}
}
