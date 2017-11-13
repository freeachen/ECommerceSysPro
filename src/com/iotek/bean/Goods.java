package com.iotek.bean;

import java.util.List;

import com.iotek.biz.ManageGoodsController;
import com.iotek.db.dao.impl.CategoryDaoImpl;
import com.iotek.db.dao.impl.GoodStatusDaoImpl;
import com.iotek.util.NumFormat;

/**
 * ��Ʒ��
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class Goods {
	private int id;// ��Ʒid
	private String name;// ��Ʒ����
	private double price;// ��Ʒ����
	private int stock;// ��Ʒ���
	private String describe;// ��Ʒ������Ϣ
	private int categoryId;// ��Ʒ���id
	private int gStatusId;// ��Ʒ״̬id

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
	 * ���ط����ַ���
	 * 
	 * @return �ַ���
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
	 * ����״̬�ַ���
	 * 
	 * @return �ַ���
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
	 * �����ۿ�ϵ�����ؼ۸�
	 * 
	 * @return ���ۺ�۸�
	 */
	public double getSalePrice() {
		double discount = ManageGoodsController.checkGoodStatus(this);
		return NumFormat.formatDouble(price * discount);
	}

	/**
	 * ���������͸�ʽ��
	 * 
	 * @return ��ʽ����ĸ�����
	 */
	public double getPrice() {
		return NumFormat.formatDouble(price);
	}

	/**
	 * ���������͸�ʽ��
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
