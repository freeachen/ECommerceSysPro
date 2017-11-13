package com.iotek.biz;

import java.util.ArrayList;
import java.util.List;

import com.iotek.bean.Goods;
import com.iotek.bean.ShoppingCart;
import com.iotek.bean.ShoppingDetail;
import com.iotek.db.dao.impl.ShoppingDetailDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.view.BrowseGoodsMenu;

/**
 * 购物车界面的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ShoppingCartController {
	/**
	 * 执行选项的方法
	 * 
	 * @param option
	 *            选项
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int doOption(int option) {
		boolean flag = false;
		switch (option) {
		case 1:
			// 显示购物车
			showDetail(checkCart());
			break;
		case 2:
			// 新增商品
			new BrowseGoodsMenu().show();
			break;
		case 3:
			// 修改数量
			flag = updateDetail();
			if (flag) {
				System.out.println("修改成功！");
			} else {
				System.out.println("修改失败！");
			}
			break;
		case 4:
			// 删除购物项
			flag = deleteDetail();
			if (flag) {
				System.out.println("删除成功！");
			} else {
				System.out.println("删除失败！");
			}
			break;
		case 5:
			// 确认下单
			placeOrder();
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
	 * 用户确认下单
	 */
	public void placeOrder() {
		List<ShoppingDetail> list = checkCart();
		if (list.size() == 0) {
			System.out.println("购物车空空，快去添加商品吧！");
			return;
		} else {
			showDetail(list);
			while (true) {
				System.out.println("【1-选择下单，2-全部下单，0-退出】");
				int i = FilterInputMismatch.nextInt();
				switch (i) {
				case 0:
					// 退出下单
					return;
				case 1:
					// 选择下单
					placeOrderSingle(list);
					return;
				case 2:
					// 全部下单
					new OrderController().createOrder(list);
					return;
				default:
					System.out.println("无此选项！");
					continue;
				}
			}
		}
	}

	/**
	 * 选择下单
	 */
	public void placeOrderSingle(List<ShoppingDetail> list) {
		List<ShoppingDetail> list2 = new ArrayList<ShoppingDetail>();
		while (true) {
			System.out.println("【1-继续，0-退出】");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				boolean flag = quitOrSure();
				if (flag) {
					if (list2.size() != 0) {
						// 退出界面中确认才是真的生成订单
						new OrderController().createOrder(list2);
					}
					return;
				} else {
					return;
				}
			} else if (i == 1) {
				System.out.println("请输入要下单的序号：");
				int j = checkIndex(list.size());
				ShoppingDetail sd = list.get(j - 1);
				// 检查商品是否已经重复添加进list2，保证购物项不重复添加
				int g = checkCart(list2, sd.getGid());
				if (g == -1) {
					// list2中无该商品就添加
					list2.add(sd);
					System.out.println("添加成功！");
				} else {
					System.out.println("该商品已下单！");
				}
			} else {
				System.out.println("无此选项！");
				continue;
			}
		}
	}

	/**
	 * 退出还是确认
	 * 
	 * @return true or false 确认 或 放弃
	 */
	private boolean quitOrSure() {
		System.out.println("确认下单还是放弃下单？");
		while (true) {
			System.out.println("【1-确认，0-放弃】");
			int j = FilterInputMismatch.nextInt();
			if (j == 1) {
				return true;
			} else if (j == 0) {
				System.out.println("已放弃下单！");
				return false;
			} else {
				System.out.println("无此选项！");
				continue;
			}
		}
	}

	/**
	 * 查看购物车
	 * 
	 * @return 购物项集合
	 */
	public List<ShoppingDetail> checkCart() {
		List<ShoppingDetail> list = new ShoppingDetailDaoImpl().select();
		return list;
	}

	/**
	 * 更新购物车内容
	 * 
	 * @return true or false
	 */
	public boolean updateDetail() {
		List<ShoppingDetail> list = checkCart();
		showDetail(list);
		if (list.size() == 0) {
			return false;
		}
		System.out.println("输入修改商品的序号:");
		int i = checkIndex(list.size());
		// 返回对应序号的购物项
		ShoppingDetail sd = list.get(i - 1);
		int g = checkCart(list, sd.getGid());

		if (g != -1) {
			int num;
			while (true) {
				System.out.println("输入商品数量：");
				num = FilterInputMismatch.nextInt();
				if (num <= 0) {
					System.out.println("输入商品数量不能为0！");
					continue;
				} else {
					break;
				}
			}
			// 修改数量时也要检测库存
			if (!ManageOrderController.checkStock(sd.getGid(), num)) {
				return false;
			}
			sd.setNum(num);
			return new ShoppingDetailDaoImpl().update(sd);
		} else {
			System.out.println("无该商品！");
			return false;
		}
	}

	/**
	 * 删除购物车内容
	 * 
	 * @return true or false
	 */
	public boolean deleteDetail() {
		List<ShoppingDetail> list = checkCart();
		showDetail(list);
		if (list.size() == 0) {
			return false;
		}
		System.out.println("输入要删除的商品序号:");
		int i = checkIndex(list.size());
		// 返回对应序号的购物项
		ShoppingDetail sd = list.get(i - 1);
		int g = checkCart(list, sd.getGid());

		if (g != -1) {
			return new ShoppingDetailDaoImpl().delete(sd);
		} else {
			System.out.println("无该商品！");
			return false;
		}
	}

	/**
	 * 新增购物项
	 * 
	 * @param 当前商品
	 * 
	 * @return true or false
	 */
	public boolean inertDetail(Goods good) {
		List<ShoppingDetail> list = checkCart();
		ShoppingDetail shoppingDetail = null;
		System.out.println("添加商品到购物车吧！！！");
		while (true) {
			System.out.println("【1-确认添加，0-不喜欢该商品】");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				return false;
			} else if (i == 1) {
				int num;
				while (true) {
					System.out.println("输入商品数量：");
					num = FilterInputMismatch.nextInt();
					if (num <= 0) {
						System.out.println("输入商品数量不能为0！");
						continue;
					} else {
						break;
					}
				}
				if (!ManageOrderController.checkStock(good.getId(), num)) {
					return false;
				}

				int g = checkCart(list, good.getId());

				// 判断购物车里是否已有该商品
				if (list.size() == 0 || g == -1) {
					// 下标为-1的时候表明该商品未出现在购物车中，直接添加
					shoppingDetail = new ShoppingDetail(0, good.getId(),
							MainMenuController.user.getId(), num);
					return new ShoppingDetailDaoImpl().insert(shoppingDetail);
				} else {
					// 如果购物车中已有该物品，则只修改数量
					num += list.get(g).getNum();
					shoppingDetail = new ShoppingDetail(0, good.getId(),
							MainMenuController.user.getId(), num);
					return new ShoppingDetailDaoImpl().update(shoppingDetail);
				}
			} else {
				System.out.println("无此选项！");
				continue;
			}
		}
	}

	/**
	 * 显示购物项
	 * 
	 * @param list
	 *            购物项集合
	 * @param sc
	 *            购物车
	 */
	private void showDetail(List<ShoppingDetail> list) {
		ShoppingCart sc = new ShoppingCart(list);
		System.out.println("序号\t商品ID\t商品名\t商品单价\t数量\t总价");
		int count = 0;
		if (list.size() != 0) {
			for (ShoppingDetail shoppingDetail : list) {
				System.out.println((++count) + "\t"
						+ shoppingDetail.listInMyStyle());
			}
		} else {
			System.out.println("购物车空空，快去添加商品吧！");
		}
		System.out.println("所有商品总价为：" + sc.getTotalPrice() + "元");
	}

	/**
	 * 检查序号是否在集合中
	 * 
	 * @param size
	 *            集合大小
	 * @return 符合即返回
	 */
	public static int checkIndex(int size) {
		int i = 0;
		while (true) {
			i = FilterInputMismatch.nextInt();
			// 判断序号是否在集合中，返回存在的序号
			if ((i - 1) < 0 || i > size) {
				System.out.println("无该项，请重新输入");
				continue;
			} else {
				break;
			}
		}
		return i;
	}

	/**
	 * 检查购物车是否有相同的物品
	 * 
	 * @param list
	 *            购物项集合
	 * @param gid
	 *            商品ID
	 * @return 购物项出现的下标，未出现返回-1
	 */
	private int checkCart(List<ShoppingDetail> list, int gid) {
		// 用于返回商品ID相等的下标，如果商品不在购物项中返回-1
		int g = -1;
		for (ShoppingDetail sDetail : list) {
			if (sDetail.getGid() == gid) {
				g = list.indexOf(sDetail);
				break;
			}
		}
		return g;
	}
}
