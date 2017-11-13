package com.iotek.bean;

/**
 * 关于商品分类信息的类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class Category {
	private int id;// 该类的id
	private String category;// 表示商品的状态

	public Category() {
		super();
	}

	public Category(String category) {
		super();
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", category=" + category + "]";
	}

}
