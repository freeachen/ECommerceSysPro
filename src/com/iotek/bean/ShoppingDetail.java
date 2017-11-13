package com.iotek.bean;

import com.iotek.biz.ManageGoodsController;
import com.iotek.util.NumFormat;

/**
 * 购物车明细项类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ShoppingDetail {
	private int id;// 购物车明细项id
	private int gid;// 商品id
	private int uid;// 用户id
	private int num;// 购买数量
	private double totalPrice;// 购物项总价

	public ShoppingDetail() {
		super();
	}

	/**
	 * 带参构造方法
	 * 
	 * @param id
	 *            购物项ID
	 * @param gid
	 *            商品ID
	 * @param uid
	 *            用户ID
	 * @param num
	 *            数量
	 */
	public ShoppingDetail(int id, int gid, int uid, int num) {
		super();
		this.id = id;
		this.gid = gid;
		this.uid = uid;
		this.num = num;
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

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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

	/**
	 * 根据自定义格式输出
	 */
	public String listInMyStyle() {
		Goods goods = ManageGoodsController.getGood(gid);
		return gid + "\t" + goods.getName() + "\t" + goods.getSalePrice()
				+ "\t" + num + "\t" + getTotalPrice();
	}

	@Override
	public String toString() {
		return "ShoppingDetail [id=" + id + ", gid=" + gid + ", uid=" + uid
				+ ", num=" + num + ", totalPrice=" + totalPrice + "]";
	}

}
