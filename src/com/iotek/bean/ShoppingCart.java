package com.iotek.bean;

import java.util.List;

import com.iotek.util.NumFormat;

/**
 * ���ﳵ��
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ShoppingCart {
	private List<ShoppingDetail> sCart = null;// ��������ļ�������
	private double totalPrice;// �ܼ�

	public ShoppingCart() {
		super();
	}

	/**
	 * ���ι��췽�����Զ����㹺�ﳵ�ܼ�
	 * 
	 * @param sCart
	 *            �������
	 */
	public ShoppingCart(List<ShoppingDetail> sCart) {
		super();
		this.sCart = sCart;
		// ���㹺�ﳵ�ܼ�
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
	 * ���������͸�ʽ��
	 * 
	 * @return ��ʽ����ĸ�����
	 */
	public double getTotalPrice() {
		return NumFormat.formatDouble(totalPrice);
	}

	/**
	 * ���������͸�ʽ��
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
