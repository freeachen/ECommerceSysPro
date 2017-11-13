package com.iotek.bean;

import java.util.List;

import com.iotek.biz.ManageGoodsController;
import com.iotek.db.dao.impl.OrderDetailStatusDaoImpl;
import com.iotek.util.NumFormat;

/**
 * 订单明细项类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderDetail {
	private int id;// 订单项id
	private int gid;// 商品id
	private int oid;// 订单id
	private int num;// 购买数量
	private double totalPrice;// 单张订单项的总价
	private int oStatusId;// 订单状态id

	public OrderDetail() {
		super();
	}

	/**
	 * 带参构造方法，并且自动计算订单项总价
	 * 
	 * @param gid
	 *            商品ID
	 * @param oid
	 *            订单ID
	 * @param num
	 *            数量
	 * @param oStatusId
	 *            订单状态
	 */
	public OrderDetail(int gid, int oid, int num, int oStatusId) {
		super();
		this.gid = gid;
		this.oid = oid;
		this.num = num;
		this.oStatusId = oStatusId;
		// 使用有参构造方法创建对象时就计算得到其总价
		Goods goods = ManageGoodsController.getGood(gid);
		totalPrice = goods.getSalePrice() * num;
		totalPrice = NumFormat.formatDouble(totalPrice);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 附带浮点型格式化
	 * 
	 * @return 格式化后的浮点型
	 */
	public double getTotalPrice() {
		return NumFormat.formatDouble(totalPrice);
	}

	/**
	 * 附带浮点型格式化
	 * 
	 * @param totalPrice
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = NumFormat.formatDouble(totalPrice);
	}

	public int getoStatusId() {
		return oStatusId;
	}

	public void setoStatusId(int oStatusId) {
		this.oStatusId = oStatusId;
	}

	/**
	 * 根据自定义格式输出
	 * 
	 * @return 订单项详细信息
	 */
	public String listInMyStyle() {
		List<OrderDetailStatus> list = new OrderDetailStatusDaoImpl()
				.selectAll();
		String str = null;
		// 根据订单状态表返回订单状态的字符串
		for (OrderDetailStatus ods : list) {
			if (ods.getId() == oStatusId) {
				str = ods.getStatus();
			}
		}
		Goods goods = ManageGoodsController.getGood(gid);
		return id + "\t" + goods.getId() + "\t" + goods.getName() + "\t"
				+ goods.getSalePrice() + "\t" + oid + "\t" + num + "\t"
				+ getTotalPrice() + "\t" + str;
	}

	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", gid=" + gid + ", oid=" + oid
				+ ", num=" + num + ", totalPrice=" + totalPrice
				+ ", oStatusId=" + oStatusId + "]";
	}
}
