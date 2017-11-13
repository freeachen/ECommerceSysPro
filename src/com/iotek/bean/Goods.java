package com.iotek.bean;

import java.util.List;

import com.iotek.biz.ManageGoodsController;
import com.iotek.db.dao.impl.CategoryDaoImpl;
import com.iotek.db.dao.impl.GoodStatusDaoImpl;
import com.iotek.util.NumFormat;

/**
 * 商品类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class Goods {
	private int id;// 商品id
	private String name;// 商品名称
	private double price;// 商品单价
	private int stock;// 商品库存
	private String describe;// 商品描述信息
	private int categoryId;// 商品类别id
	private int gStatusId;// 商品状态id

	public Goods() {
		super();
	}

	public Goods(int id, String name, double price, int stock, String describe,
			int categoryId, int gStatusId) {
		super();
		this.id = id;
		this.name = name;
		this.price = NumFormat.formatDouble(price);
		this.stock = stock;
		this.describe = describe;
		this.categoryId = categoryId;
		this.gStatusId = gStatusId;
	}

	/**
	 * 返回分类字符串
	 * 
	 * @return 字符串
	 */
	public String getCategory() {
		List<Category> list = new CategoryDaoImpl().selectAll();
		for (Category c : list) {
			if (c.getId() == categoryId) {
				return c.getCategory();
			}
		}
		return null;
	}

	/**
	 * 返回状态字符串
	 * 
	 * @return 字符串
	 */
	public String getStatus() {
		List<GoodStatus> list = new GoodStatusDaoImpl().selectAll();
		for (GoodStatus gs : list) {
			if (gs.getId() == gStatusId) {
				return gs.getStatus();
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 根据折扣系数返回价格
	 * 
	 * @return 打折后价格
	 */
	public double getSalePrice() {
		double discount = ManageGoodsController.checkGoodStatus(this);
		return NumFormat.formatDouble(price * discount);
	}

	/**
	 * 附带浮点型格式化
	 * 
	 * @return 格式化后的浮点型
	 */
	public double getPrice() {
		return NumFormat.formatDouble(price);
	}

	/**
	 * 附带浮点型格式化
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = NumFormat.formatDouble(price);
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getgStatusId() {
		return gStatusId;
	}

	public void setgStatusId(int gStatusId) {
		this.gStatusId = gStatusId;
	}

	@Override
	public String toString() {
		return name + "\t" + getSalePrice() + "\t" + stock + "\t" + describe
				+ "\t" + getCategory() + "\t" + getStatus();
	}

}
