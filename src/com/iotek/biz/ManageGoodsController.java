package com.iotek.biz;

import java.util.List;
import java.util.Scanner;

import com.iotek.bean.Goods;
import com.iotek.db.dao.impl.CommentDaoImpl;
import com.iotek.db.dao.impl.GoodsDaoImpl;
import com.iotek.util.FilterInputMismatch;

/**
 * 管理商品的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageGoodsController {
	/**
	 * 对菜单选项操作的方法
	 * 
	 * @param option
	 *            用户输入的选项、
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int doOption(int option) {
		switch (option) {
		case 1:
			// 显示所有商品（包括下架商品）
			listAll();
			break;
		case 2:
			// 新增商品
			insert();
			break;
		case 3:
			// 修改商品库存
			listAll();
			updateStock();
			break;
		case 4:
			// 修改商品状态
			listAll();
			updateStatus();
			break;
		case 5:
			// 修改商品信息
			listAll();
			updateGoodsInfo();
			break;
		case 9:
			// 返回上级菜单
			return 1;
		case 0:
			// 退出
			return 2;
		default:
			System.out.println("输入错误，请重新输入！");
			break;
		}
		return 0;
	}

	/**
	 * 显示所有商品（包括下架商品，仅供管理员使用）
	 */
	public void listAll() {
		List<Goods> list = new GoodsDaoImpl().queryAll();
		listForAdmin(list);
	}

	/**
	 * 显示商品详细信息
	 * 
	 * @param g
	 *            商品对象
	 */
	public static void listDetail(Goods g) {
		System.out.println("商品名：" + g.getName());
		System.out.println("单价：" + g.getSalePrice());
		System.out.println("库存：" + g.getStock());
		System.out.println("描述：" + g.getDescribe());
		System.out.println("类别：" + g.getCategory());
		System.out.println("状态：" + g.getStatus());
		new CommentController()
				.listComment(new CommentDaoImpl().selectByGid(g));
	}

	/**
	 * 显示商品信息（带商品ID，专供管理员使用）
	 * 
	 * @param list
	 *            商品集合
	 * @return 商品集合
	 */
	private static List<Goods> listForAdmin(List<Goods> list) {
		System.out.println("商品ID\t商品名\t单价（元）\t库存\t描述\t类别\t状态");
		for (Goods goods : list) {
			System.out.println(goods.getId() + "\t" + goods);
		}
		return list;
	}

	/**
	 * 显示商品信息（不带商品ID，带序号显示，专供用户使用）
	 * 
	 * @param list
	 *            商品集合
	 * @return 商品集合
	 */
	public static List<Goods> listForUser(List<Goods> list) {
		int count = 0;
		System.out.println("序号\t商品名\t单价（元）\t库存\t描述\t类别\t状态");
		for (Goods goods : list) {
			System.out.println((++count) + "\t" + goods);
		}
		return list;
	}

	/**
	 * 获取指定商品对象
	 * 
	 * @param id
	 *            商品ID
	 * @return Goods对象
	 */
	public static Goods getGood(int id) {
		List<Goods> list = new GoodsDaoImpl().queryAll();
		for (Goods goods : list) {
			if (goods.getId() == id) {
				return goods;
			}
		}
		return null;
	}

	/**
	 * 新增商品
	 */
	public void insert() {
		// 输入商品信息
		Goods good = inputGoodsInfo();
		boolean flag = new GoodsDaoImpl().insert(good);
		if (flag) {
			System.out.println("添加商品成功！");
		} else {
			System.out.println("添加商品失败！");
		}
	}

	/**
	 * 修改商品库存
	 */
	public void updateStock() {
		Goods good = getUpdateGoods();
		if (good != null) {
			System.out.println("请输入商品库存：");
			int stock = FilterInputMismatch.nextInt();
			good.setStock(stock);
			boolean flag = new GoodsDaoImpl().update(good);
			if (flag) {
				System.out.println("修改库存成功！");
			} else {
				System.out.println("修改库存失败！");
			}
		} else {
			System.out.println("该商品不存在！");
			return;
		}
	}

	/**
	 * 修改商品状态的方法
	 * 
	 */
	public void updateStatus() {
		Goods good = getUpdateGoods();
		if (good != null) {
			System.out.println("请输入商品状态：");
			System.out.println("【1-已下架，2-热卖中，3-促销中】");
			int status = FilterInputMismatch.nextInt();
			good.setgStatusId(status);
			boolean flag = new GoodsDaoImpl().update(good);
			if (flag) {
				System.out.println("修改状态成功！");
			} else {
				System.out.println("修改状态失败！");
			}
		} else {
			System.out.println("该商品不存在！");
			return;
		}
	}

	/**
	 * 修改商品详细信息
	 */
	public void updateGoodsInfo() {
		Goods good = getUpdateGoods();
		if (good != null) {
			// 输入商品信息
			Goods inputGoods = inputGoodsInfo();
			inputGoods.setId(good.getId());
			boolean flag = new GoodsDaoImpl().update(inputGoods);
			if (flag) {
				System.out.println("修改状态成功！");
			} else {
				System.out.println("修改状态失败！");
			}
		} else {
			System.out.println("该商品不存在！");
			return;
		}
	}

	/**
	 * 获取要修改的商品
	 * 
	 * @return 商品
	 */
	private Goods getUpdateGoods() {
		System.out.println("请输入修改商品的ID：");
		int id = FilterInputMismatch.nextInt();
		Goods good = ManageGoodsController.getGood(id);
		return good;
	}

	/**
	 * 输入商品信息
	 * 
	 * @return 商品
	 */
	private Goods inputGoodsInfo() {
		Scanner in = new Scanner(System.in);
		String name = null;
		while (true) {
			System.out.println("请输入商品名称：");
			name = in.nextLine();
			// 商品信息不能带空格
			if (new ManageUserController().checkBlank(name)) {
				if (checkGoodsName(name)) {
					break;
				} else {
					continue;
				}
			} else {
				System.out.println("商品名不能带空格！");
				continue;
			}
		}
		double price;
		while (true) {
			System.out.println("请输入商品价格：");
			price = FilterInputMismatch.nextDouble();
			if (price <= 0) {
				System.out.println("商品价格不能为0！");
				continue;
			} else {
				break;
			}
		}
		System.out.println("请输入商品库存：");
		int stock = FilterInputMismatch.nextInt();
		System.out.println("请输入商品描述：");
		String describe = in.nextLine();
		System.out.println("请输入商品类别：");
		System.out.println("【1-服装首饰，2-食品零食，3-耐用品，4-数码产品】");
		int categoryId = FilterInputMismatch.nextInt();
		System.out.println("请输入商品状态：");
		System.out.println("【1-已下架，2-热卖中，3-促销中】");
		int statusId = FilterInputMismatch.nextInt();

		Goods good = new Goods(0, name, price, stock, describe, categoryId,
				statusId);
		return good;
	}

	/**
	 * 检测商品名重名
	 * 
	 * @param name
	 *            商品名
	 * @return true or false
	 */
	private boolean checkGoodsName(String name) {
		List<Goods> list = new GoodsDaoImpl().queryAll();
		for (Goods goods : list) {
			if (goods.getName().equals(name)) {
				System.out.println("输入商品名已存在！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查商品状态并返回折扣系数（促销商品打9折）
	 * 
	 * @param g
	 *            商品
	 * @return 系数值
	 */
	public static double checkGoodStatus(Goods g) {
		// 商品状态3的时候返回一个折扣系数
		if (g.getgStatusId() == 3) {
			return 0.9;
		}
		return 1;
	}
}
