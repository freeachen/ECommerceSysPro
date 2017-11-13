package com.iotek.biz;

import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Goods;
import com.iotek.bean.Order;
import com.iotek.bean.OrderDetail;
import com.iotek.db.dao.impl.GoodsDaoImpl;
import com.iotek.db.dao.impl.OrderDaoImpl;
import com.iotek.db.dao.impl.OrderDetailDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.view.ManageOrderInnerMenu;

/**
 * 管理订单界面的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageOrderController {

	/**
	 * 执行选项的方法
	 * 
	 * @param option
	 *            选项
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int doOption(int option) {
		int i = 0;
		switch (option) {
		case 1:
			// 要审核的订单
			i = 0;
			while (true) {
				i = new ManageOrderInnerMenu().show();
				if (i != 0) {
					break;
				}
			}
			break;
		case 2:
			// 显示所有订单
			i = 0;
			listOrder(new OrderDaoImpl().selectAll());
			break;
		case 3:
			// 显示所有订单项
			i = 0;
			listOrderDetail(new OrderDetailDaoImpl().selectAll());
			break;
		case 4:
			// 删除未付款订单项
			i = 0;
			deleteOrderDetail();
			break;
		case 9:
			// 返回上级菜单
			return 1;
		case 0:
			// 退出
			return 2;
		default:
			System.out.println("无此选项，请重新输入！");
			break;
		}
		if (i == 2) {
			return 2;
		} else {
			return 0;
		}
	}

	/**
	 * 删除订单项（只有还未付款的订单项才能删除）
	 * 
	 * @return true or false
	 */
	public boolean deleteOrderDetail() {
		// 返回所有未付款订单项
		List<OrderDetail> list = new OrderDetailDaoImpl().selectByStatus(3);
		System.out.println("【只能删除未付款的订单项】");
		listOrderDetail(list);
		if (list.size() == 0) {
			return false;
		}
		System.out.println("请输入删除订单项序号：");
		int i = ShoppingCartController.checkIndex(list.size());
		OrderDetail od = list.get(i - 1);
		boolean flag = new OrderDetailDaoImpl().delete(od);
		if (flag) {
			// 删除订单后恢复库存
			Goods goods = ManageGoodsController.getGood(od.getGid());
			goods.setStock(goods.getStock() + od.getNum());
			new GoodsDaoImpl().update(goods);
			System.out.println("订单项" + od.getId() + "删除成功！");
			return true;
		} else {
			System.out.println("订单项" + od.getId() + "删除失败！");
			return false;
		}
	}

	/**
	 * 修改订单中所有订单项
	 * 
	 * @return true or false
	 */
	public boolean updateOrder() {
		// 返回所有待审核的订单
		List<Order> list = new OrderDaoImpl().selectByStatus(2);
		if (list.size() == 0) {
			System.out.println("无");
			return false;
		} else {
			listOrder(list);
		}
		System.out.println("请输入修改订单序号：");
		int i = ShoppingCartController.checkIndex(list.size());

		Order o = list.get(i - 1);
		// 根据订单号找到其对应的所有订单项
		List<OrderDetail> list2 = new OrderDetailDaoImpl().selectByOid(o
				.getId());
		for (OrderDetail od : list2) {
			// 设置这些订单项状态为未付款
			od.setoStatusId(3);
			boolean flag = new OrderDetailDaoImpl().update(od);
			if (flag) {
				System.out.println("订单项" + od.getId() + "审核成功！");
			} else {
				System.out.println("订单项" + od.getId() + "审核失败！");
			}
		}

		// 订单项状态一旦变动就检查并更新其订单状态
		int j = checkDetailStatus(list2, 3);
		o.setoStatusId(j);
		return new OrderDaoImpl().updateStatus(o);
	}

	/**
	 * 修改指定订单项
	 * 
	 * @return true or false
	 */
	public boolean updateOrderDetail() {
		// 返回所有待审核的订单项
		List<OrderDetail> list = new OrderDetailDaoImpl().selectByStatus(2);
		if (list.size() == 0) {
			System.out.println("无");
			return false;
		} else {
			listOrderDetail(list);
		}
		System.out.println("请输入修改订单项序号：");
		int i = ShoppingCartController.checkIndex(list.size());

		OrderDetail od = list.get(i - 1);
		// 设置该订单项状态为未付款
		od.setoStatusId(3);
		boolean flag = new OrderDetailDaoImpl().update(od);
		if (flag) {
			// 因为检查状态的方法需要传递集合为参数，所以在此对订单项包装一下
			List<Order> list2 = new OrderDaoImpl().selectById(od.getOid());
			List<OrderDetail> list3 = new ArrayList<OrderDetail>();
			list3.add(od);
			// 订单项状态一旦变动就检查并更新其订单状态
			int j = checkDetailStatus(list3, 3);
			list2.get(0).setoStatusId(j);
			new OrderDaoImpl().updateStatus(list2.get(0));
			System.out.println("订单项" + od.getId() + "审核成功！");
			return true;
		} else {
			System.out.println("订单项" + od.getId() + "审核失败！");
			return false;
		}
	}

	/**
	 * 显示订单
	 * 
	 * @param list
	 *            订单集合
	 * 
	 */
	public void listOrder(List<Order> list) {
		// 带自增序号
		int count = 0;
		System.out.println("序号\t订单ID\t用户ID\t下单时间\t\t\t订单总价\t订单状态");
		if (list.size() != 0) {
			for (Order order : list) {
				System.out.println((++count) + "\t" + order.listInMyStyle());
			}
		} else {
			System.out.println("无");
		}
	}

	/**
	 * 显示订单项
	 * 
	 * @param list
	 *            订单项集合
	 */
	public List<OrderDetail> listOrderDetail(List<OrderDetail> list) {
		// 带自增序号
		int count = 0;
		System.out.println("序号\t订单项ID\t商品ID\t商品名\t商品单价\t订单ID"
				+ "\t购买数量\t订单项总价\t订单项状态");
		if (list.size() != 0) {
			for (OrderDetail orderDetail : list) {
				System.out.println((++count) + "\t"
						+ orderDetail.listInMyStyle());
			}
		} else {
			System.out.println("无");
		}
		return list;
	}

	/**
	 * 检查订单下所有订单项的状态，根据最低的状态更改订单状态
	 * 
	 * @param od
	 *            订单项集合
	 * @param i
	 *            状态
	 * @return 返回更改状态
	 */
	public int checkDetailStatus(List<OrderDetail> od, int i) {
		for (OrderDetail odd : od) {
			if (odd.getoStatusId() < i) {
				i = odd.getoStatusId();
			}
		}
		return i;
	}

	/**
	 * 检验库存的方法
	 * 
	 * @param gid
	 *            商品ID
	 * @param num
	 *            商品数量
	 * @return true or false
	 */
	public static boolean checkStock(int gid, int num) {
		Goods good = ManageGoodsController.getGood(gid);
		if (good.getStock() >= num) {
			return true;
		} else {
			System.out.println("库存不够！");
			return false;
		}
	}

}
