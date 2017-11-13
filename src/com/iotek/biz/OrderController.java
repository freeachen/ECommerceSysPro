package com.iotek.biz;

import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Goods;
import com.iotek.bean.Order;
import com.iotek.bean.OrderDetail;
import com.iotek.bean.ShoppingDetail;
import com.iotek.bean.User;
import com.iotek.db.dao.impl.GoodsDaoImpl;
import com.iotek.db.dao.impl.OrderDaoImpl;
import com.iotek.db.dao.impl.OrderDetailDaoImpl;
import com.iotek.db.dao.impl.ShoppingDetailDaoImpl;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.util.GetDate;
import com.iotek.util.NumFormat;

/**
 * 订单界面的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class OrderController {
	/**
	 * 执行选项的方法
	 * 
	 * @param option
	 *            选项
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int doOption(int option) {
		switch (option) {
		case 1:
			// 显示用户未完成订单项
			listODUnfinished();
			break;
		case 2:
			// 显示用户已完成订单项
			listODFinished();
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
		return 0;
	}

	/**
	 * 显示用户未完成订单项
	 * 
	 * @return 订单项集合
	 */
	public List<OrderDetail> listODUnfinished() {
		List<OrderDetail> od = new ArrayList<OrderDetail>();
		// 返回所有未完成的订单项即状态1到4的订单项
		for (int i = 1; i < 5; i++) {
			List<OrderDetail> odd = getMyODByStatus(i);
			if (odd != null) {
				od.addAll(odd);
			}
		}
		System.out.println("我未完成的订单：");
		new ManageOrderController().listOrderDetail(od);
		// 该界面中显示订单项的总价
		double totalPrice = 0;
		for (OrderDetail orderDetail : od) {
			totalPrice += orderDetail.getTotalPrice();
		}
		System.out
				.println("订单项总价为：" + NumFormat.formatDouble(totalPrice) + "元");
		return od;
	}

	/**
	 * 显示用户完成订单项
	 * 
	 * @return 订单项集合
	 */
	public List<OrderDetail> listODFinished() {
		List<OrderDetail> od = getMyODByStatus(5);
		System.out.println("我已完成的订单：");
		new ManageOrderController().listOrderDetail(od);
		return od;
	}

	/**
	 * 返回当前用户指定状态的订单项
	 * 
	 * @param i
	 *            状态
	 * @return 订单项集合
	 */
	public List<OrderDetail> getMyODByStatus(int i) {
		// 返回用户的订单
		List<Order> o = new OrderDaoImpl().selectByUid();
		List<OrderDetail> od = new ArrayList<OrderDetail>();
		for (Order order : o) {
			// 根据订单号返回对应的所有订单项
			List<OrderDetail> odd = new OrderDetailDaoImpl().selectByOid(order
					.getId());
			for (OrderDetail orderDetail : odd) {
				// 判断这些订单项的状态再添加进od
				int j = orderDetail.getoStatusId();
				if (j == i) {
					od.add(orderDetail);
				}
			}
		}
		return od;
	}

	/**
	 * 生成订单项集合
	 * 
	 * @param o
	 *            订单
	 * @param s
	 *            购物项集合
	 * @return 订单项集合
	 */
	public List<OrderDetail> createOrderDetail(Order o, List<ShoppingDetail> s) {
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		OrderDetail od = null;
		boolean flag = false;
		for (ShoppingDetail sd : s) {
			// 生成订单项的时候会检测商品当时的库存，不够的话无法生成订单项
			if (!ManageOrderController.checkStock(sd.getGid(), sd.getNum())) {
				Goods good = ManageGoodsController.getGood(sd.getGid());
				System.out.println("商品：" + good.getName() + "，无法生成订单项！");
				continue;
			} else {
				// 生成订单项，默认状态待审核
				od = new OrderDetail(sd.getGid(), o.getId(), sd.getNum(), 2);
				flag = new OrderDetailDaoImpl().insert(od);
				if (flag) {
					// 如果生成订单项成功则修改商品库存
					Goods good = ManageGoodsController.getGood(sd.getGid());
					int stock = good.getStock() - sd.getNum();
					good.setStock(stock);
					new GoodsDaoImpl().update(good);
					// 这里还要删除对应的购物项
					new ShoppingDetailDaoImpl().delete(sd);
					list.add(od);
				} else {
					System.out.println("订单项生成失败！");
				}
			}
		}
		return list;
	}

	/**
	 * 生成订单
	 * 
	 * @param u
	 *            用户对象
	 * @param s
	 *            购物项集合
	 */
	public void createOrder(List<ShoppingDetail> s) {
		// 过滤购物车中添加的已下架商品
		List<ShoppingDetail> sc = checkShoppingDetail(s);
		// 获取当前系统时间
		String date = GetDate.getNowTime();
		// 生成初始空订单
		Order order = new Order(MainMenuController.user.getId(), date, 2);

		boolean flag = new OrderDaoImpl().insert(order);
		if (flag) {
			// 订单根据用户ID和时间生成，然后再获取该订单的ID用来生产订单项
			Order newOrder = new OrderDaoImpl().getNewOrder(date);
			List<OrderDetail> od = createOrderDetail(newOrder, sc);
			// 这里一定不能漏掉对大订单总价的计算并更新
			newOrder.setOrderList(od);
			newOrder.culTotalPrice();
			new OrderDaoImpl().updateTotalPrice(newOrder);
			System.out.println("生成订单成功！");
		} else {
			System.out.println("生成订单失败！");
		}
	}

	/**
	 * 付款
	 * 
	 * @return true or false
	 */
	public boolean payMoney() {
		// 返回所有未付款的订单项
		List<OrderDetail> od = getMyODByStatus(3);
		System.out.println("未付款订单项：");
		if (od.size() == 0) {
			System.out.println("无");
			System.out.println("账户可用余额：" + MainMenuController.user.getBalance()
					+ "元");
			return false;
		} else {
			new ManageOrderController().listOrderDetail(od);
			double totalPrice = 0;
			// 当前用户的折扣系数进行一定的格式化
			double d = NumFormat.formatDouble((ManageUserController
					.checkUserLevel() * 10));
			for (OrderDetail odd : od) {
				totalPrice += odd.getTotalPrice();
			}
			System.out.println("合计付款：" + totalPrice);
			System.out.println("用户等级折扣：" + d + "折");
			// 计算实际应付款，要乘上用户的等级折扣
			totalPrice *= ManageUserController.checkUserLevel();
			System.out.println("实际应付款：" + NumFormat.formatDouble(totalPrice));
			System.out.println("账户可用余额：" + MainMenuController.user.getBalance()
					+ "元");
		}

		while (true) {
			System.out.println("确认付款？");
			System.out.println("【1-是，0-否】");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				System.out.println("退出付款！");
				return false;
			} else if (i == 1) {
				if (payNow(od)) {
					return true;
				} else {
					return false;
				}
			} else {
				System.out.println("无此选项！");
				continue;
			}
		}
	}

	/**
	 * 确认付款
	 * 
	 * @param od
	 *            订单项集合
	 * @return true or false
	 */
	private boolean payNow(List<OrderDetail> od) {
		// 查询用户余额够不够下单
		boolean flag = checkBalance(od);
		if (!flag) {
			return false;
		} else {
			// 获取当前用户的余额和经验值
			double balance = MainMenuController.user.getBalance();
			int exp = MainMenuController.user.getExp();
			double totalPrice = 0;
			for (OrderDetail orderDetail : od) {
				orderDetail.setoStatusId(4);
				boolean flag2 = new OrderDetailDaoImpl().update(orderDetail);
				if (flag2) {
					// 订单项状态一旦变动就检查并更新其订单状态
					List<Order> list2 = new OrderDaoImpl()
							.selectById(orderDetail.getOid());
					List<OrderDetail> list3 = new ArrayList<OrderDetail>();
					list3.add(orderDetail);
					int j = new ManageOrderController().checkDetailStatus(
							list3, 4);
					list2.get(0).setoStatusId(j);
					new OrderDaoImpl().updateStatus(list2.get(0));
					// 计算订单项总价的和，经验值等于这些总和
					totalPrice += orderDetail.getTotalPrice();
					exp += (int) (orderDetail.getTotalPrice());
					System.out.println("订单项" + orderDetail.getId() + "付款成功！");
				} else {
					System.out.println("订单项" + orderDetail.getId() + "付款失败！");
				}
			}
			// 根据用户折扣系数计算打折后的价格
			totalPrice *= ManageUserController.checkUserLevel();
			// 用户余额减去折扣后的价格
			balance -= totalPrice;
			MainMenuController.user.setBalance(balance);
			MainMenuController.user.setExp(exp);
			// 经验值一旦变动就计算一下等级
			MainMenuController.user.calcLevel();
			new UserDaoImpl().update(MainMenuController.user);

			// 将用户花的钱转移到管理员账户里去
			User u = new User("admin", "admin", null, null, 0, 0, 0);
			u = new UserDaoImpl().selectByUserNameAndPwd(u);
			balance = u.getBalance() + totalPrice;
			u.setBalance(balance);
			if (u != null) {
				new UserDaoImpl().update(u);
			}
			return true;
		}
	}

	/**
	 * 确认收货
	 * 
	 * @return true or false
	 */
	public boolean receiveGoods() {
		// 返回发货状态的订单项
		List<OrderDetail> od = getMyODByStatus(4);
		if (od.size() == 0) {
			System.out.println("您没有快递哦~，快去购买商品吧！");
			return false;
		} else {
			for (OrderDetail orderDetail : od) {
				try {
					System.out.println("快递派送中。。。");
					Thread.sleep(1500);
					Goods goods = ManageGoodsController.getGood(orderDetail
							.getGid());
					System.out.println("您购买的" + goods.getName() + "已送到，请签收！");
					Thread.sleep(500);
					orderDetail.setoStatusId(5);
					boolean flag = new OrderDetailDaoImpl().update(orderDetail);
					if (flag) {
						// 订单项状态一旦变动就检查并更新其订单状态
						List<Order> list2 = new OrderDaoImpl()
								.selectById(orderDetail.getOid());
						List<OrderDetail> list3 = new ArrayList<OrderDetail>();
						list3.add(orderDetail);
						int j = new ManageOrderController().checkDetailStatus(
								list3, 5);
						list2.get(0).setoStatusId(j);
						new OrderDaoImpl().updateStatus(list2.get(0));
						System.out.println(goods.getName() + "签收成功！");
						// 收货成功就调用新增评论的方法
						new CommentController().insertComment(orderDetail);
						return true;
					} else {
						System.out.println(goods.getName() + "签收失败！");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 检查用户余额
	 * 
	 * @param s
	 *            订单项集合
	 * @return true or false
	 */
	public boolean checkBalance(List<OrderDetail> od) {
		// 这里传进来的是未付款的订单项集合
		double totalPrice = 0;
		for (OrderDetail o : od) {
			totalPrice += o.getTotalPrice();
		}
		// 查看用户余额能否支付折扣价
		totalPrice *= ManageUserController.checkUserLevel();
		double balance = MainMenuController.user.getBalance();
		if (balance < totalPrice) {
			System.out.println("余额不足，快去充值吧！");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 过滤下架商品
	 * 
	 * @param s
	 *            购物项集合
	 * @return 购物项集合（已过滤）
	 */
	public List<ShoppingDetail> checkShoppingDetail(List<ShoppingDetail> s) {
		List<ShoppingDetail> list = new ArrayList<ShoppingDetail>();
		// 遍历购物车，如果是已下架商品就忽略掉
		for (ShoppingDetail sd : s) {
			Goods good = ManageGoodsController.getGood(sd.getGid());
			if (good.getgStatusId() != 1) {
				list.add(sd);
			} else {
				System.out.println(good.getName() + "已下架，无法生成订单！");
			}
		}
		return list;
	}

}
