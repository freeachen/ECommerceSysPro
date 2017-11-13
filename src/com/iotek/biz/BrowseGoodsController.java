package com.iotek.biz;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.iotek.bean.Goods;
import com.iotek.db.dao.impl.GoodsDaoImpl;
import com.iotek.util.FilterInputMismatch;

/**
 * 浏览商品的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class BrowseGoodsController {
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
			// 显示所有商品
			listAll(0);
			break;
		case 2:
			// 显示促销商品
			listByStatus();
			break;
		case 3:
			// 分类显示
			listByCategory();
			break;
		case 4:
			// 价格升序
			listAll(1);
			break;
		case 5:
			// 价格降序
			listAll(2);
			break;
		case 9:
			// 返回上级菜单
			return 1;
		case 0:
			// 退出
			return 2;
		default:
			break;
		}
		return 0;
	}

	/**
	 * 显示所有商品
	 * 
	 * @param u
	 *            用户对象
	 * @param i
	 *            指定显示方式
	 */
	public void listAll(int i) {
		List<Goods> list = new GoodsDaoImpl().selectAll();

		if (i != 0) {
			// 用集合的工具类对商品按价格升序排序
			Collections.sort(list, new Comparator<Goods>() {
				@Override
				public int compare(Goods o1, Goods o2) {
					int res = (int) (o1.getSalePrice() - o2.getSalePrice());
					return res == 0 ? o1.getName().compareTo(o2.getName()): res;
				}
			});
			// 当i=2时对升序的集合翻转输出
			if (i == 2) {
				Collections.reverse(list);
			}
		}
		ManageGoodsController.listForUser(list);
		browseAndBuy(list);
	}

	/**
	 * 根据类别显示商品
	 * 
	 * @param u
	 *            用户对象
	 */
	public void listByCategory() {
		System.out.println("请输入类别：");
		System.out.println("【1-服装首饰，2-食品零食，3-耐用品，4-数码产品】");
		int i = FilterInputMismatch.nextInt();

		Goods good = new Goods(0, null, 0, 0, null, i, 0);
		List<Goods> list = new GoodsDaoImpl().selectByCategory(good);
		if (list.size() == 0) {
			System.out.println("暂无该类商品！等待管理员添加~~");
		} else {
			ManageGoodsController.listForUser(list);
			browseAndBuy(list);
		}
	}

	/**
	 * 根据状态显示商品（该处只显示促销商品）
	 * 
	 */
	public void listByStatus() {
		Goods good = new Goods(0, null, 0, 0, null, 0, 3);
		List<Goods> list = new GoodsDaoImpl().selectByStatus(good);
		if (list.size() == 0) {
			System.out.println("暂无促销商品！等待管理员添加~~");
		} else {
			ManageGoodsController.listForUser(list);
			browseAndBuy(list);
		}
	}

	/**
	 * 进入商品明细，查看详细信息并且查看评论和购买
	 */
	public void browseAndBuy(List<Goods> list) {
		if (list.size() != 0) {
			System.out.println("输入序号进入查看详细信息吧~");
			int i = ShoppingCartController.checkIndex(list.size());
			Goods goods = list.get(i - 1);
			ManageGoodsController.listDetail(goods);
			boolean flag = new ShoppingCartController().inertDetail(goods);
			if (flag) {
				System.out.println("成功添加商品到购物车！");
			} else {
				System.out.println("添加商品失败！");
			}
			return;
		}
	}

}
