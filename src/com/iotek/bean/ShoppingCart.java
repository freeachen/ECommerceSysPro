package com.iotek.bean;

import java.util.List;

import com.iotek.util.NumFormat;

/**
 * 购物车类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ShoppingCart {
	private List<ShoppingDetail> sCart = null;// 购物项类的集合容器
	private double totalPrice;// 总价

	public ShoppingCart() {
		super();
	}

	/**
	 * 带参构造方法，自动计算购物车总价
	 * 
	 * @param sCart
	 *            购物项集合
	 */
	public ShoppingCart(List<ShoppingDetail> sCart) {
		super();
		this.sCart = sCart;
		// 计算购物车总价
		if (sCart.size() != 0) {
			for (ShoppingDetail sd : sCart) {
				totalPrice += sd.getTotalPrice();
				totalPrice = NumFormat.formatDouble(totalPrice);
			}
		} else {
			totalPrice = 0;
		}
	}

	public List<ShoppingDetail> getsCart() {
		return sCart;
	}

	public void setsCart(List<ShoppingDetail> sCart) {
		this.sCart = sCart;
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

	@Override
	public String toString() {
		return "ShoppingCart [sCart=" + sCart + ", totalPrice=" + totalPrice
				+ "]";
	}

}
