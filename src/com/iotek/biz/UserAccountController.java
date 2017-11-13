package com.iotek.biz;

import java.util.List;
import java.util.Scanner;

import com.iotek.bean.Order;
import com.iotek.bean.OrderDetail;
import com.iotek.bean.ShoppingDetail;
import com.iotek.bean.User;
import com.iotek.db.dao.impl.OrderDaoImpl;
import com.iotek.db.dao.impl.OrderDetailDaoImpl;
import com.iotek.db.dao.impl.ShoppingDetailDaoImpl;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.util.NumFormat;
import com.iotek.util.RandomGen;

/**
 * 用户账户界面的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class UserAccountController {
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
			// 显示用户的账户
			showMyAccount();
			break;
		case 2:
			// 更新用户信息
			updateMyInfo();
			break;
		case 3:
			// 修改密码
			updateMyPwd();
			break;
		case 4:
			// 注销
			return logout();
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
	 * 查看个人信息
	 */
	public void showMyAccount() {
		// 根据指定格式输出内容
		System.out.println("用户名：" + MainMenuController.user.getUserName());
		System.out.println("等级：" + MainMenuController.user.showExp());
		double d = NumFormat.formatDouble((ManageUserController.checkUserLevel() * 10));
		System.out.println("等级折扣：" + d + "折");
		System.out.println("账户余额：" + NumFormat.formatDouble(MainMenuController.user.getBalance()) + "元");
		System.out.println("收货地址：" + MainMenuController.user.getAddress());
		System.out.println("邮箱：" + MainMenuController.user.getEmail());
	}

	/**
	 * 修改个人信息
	 */
	public void updateMyInfo() {
		// 修改用户的收货地址和邮箱
		User u = new ManageUserController().updateUser(2,MainMenuController.user);
		if (u != null) {
			boolean flag = new UserDaoImpl().update(u);
			if (flag) {
				System.out.println("更新成功！");
			} else {
				System.out.println("更新失败！");
			}
		}
	}

	/**
	 * 修改用户密码
	 */
	public void updateMyPwd() {
		Scanner in = new Scanner(System.in);
		User u = null;
		while (true) {
			// 输入密码验证
			System.out.println("请输入密码：");
			String pwd = in.nextLine();
			if (!pwd.equals(MainMenuController.user.getPwd())) {
				System.out.println("密码错误！");
				continue;
			} else {
				u = MainMenuController.user;
				u = new ManageUserController().updateUser(0, u);
				if (u != null) {
					boolean flag = new UserDaoImpl().update(u);
					if (flag) {
						System.out.println("更新成功！");
					} else {
						System.out.println("更新失败！");
					}
					break;
				}
			}
		}

	}

	/**
	 * 用户注销
	 */
	public int logout() {
		Scanner in = new Scanner(System.in);
		User u = null;
		while (true) {
			// 输入密码验证
			System.out.println("请输入密码：");
			String pwd = in.nextLine();
			if (!pwd.equals(MainMenuController.user.getPwd())) {
				System.out.println("密码错误！");
				continue;
			} else {
				u = MainMenuController.user;
				System.out.println("确认要注销账号？");
				System.out.println("【一旦注销，您的所有信息将不能找回，请慎重操作！】");
				System.out.println("【1-取消，0-确认 】");
				int i = FilterInputMismatch.nextInt();
				if (i == 1) {
					return 0;
				} else if (i == 0) {
					// 验证码验证
					if (RandomGenCheck()) {
						return deleteUserInfo(u);
					} else {
						System.out.println("验证失败！");
						return 0;
					}
				} else {
					System.out.println("无此选项！");
					continue;
				}
			}
		}
	}

	/**
	 * 删除用户的操作
	 * 
	 * @param u
	 *            用户
	 */
	private int deleteUserInfo(User u) {
		try {
			System.out.println("正在把您账户余额" + u.getBalance() + "元返回您的银行卡。。。");
			Thread.sleep(1500);
			System.out.println("正在删除您的订单。。。");
			Thread.sleep(1000);
			System.out.println("正在清空您的购物车。。。");
			Thread.sleep(1000);
			// 返回用户订单
			List<Order> list = new OrderDaoImpl().selectByUid();
			for (Order o : list) {
				// 先删除订单号对应的订单项
				List<OrderDetail> list2 = new OrderDetailDaoImpl().selectByOid(o.getId());
				for (OrderDetail od : list2) {
					new OrderDetailDaoImpl().delete(od);
				}
				new OrderDaoImpl().delete(o);
			}
			// 清空该用户的购物项
			List<ShoppingDetail> list3 = new ShoppingDetailDaoImpl().select();
			for (ShoppingDetail sd : list3) {
				new ShoppingDetailDaoImpl().delete(sd);
			}
			// 删除该用户
			new UserDaoImpl().delete();
			System.out.println("已成功注销您的账户！");
			System.out.println("2秒后您将自动退出系统！");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("无法成功删除！");
			return 0;
		}
		return 2;
	}

	/**
	 * 验证码功能
	 * 
	 * @return true or false
	 */
	public boolean RandomGenCheck() {
		Scanner in = new Scanner(System.in);
		int count = 0;
		while (count < 2) {
			System.out.println("请输入一下4位验证码（可忽略大小写）：");
			String code = RandomGen.codeGen();
			System.out.println("验证码：" + code);
			String str = in.nextLine();
			count++;
			if (str.equalsIgnoreCase(code)) {
				return true;
			} else {
				System.out.println("验证码不匹配！还剩" + (2 - count) + "次机会！");
				continue;
			}
		}
		return false;
	}

	/**
	 * 用户充值
	 * 
	 * @return true or false
	 */
	public boolean recharge() {
		System.out.println("欢迎使用快捷支付！");
		System.out.println("请输入要充值的金额：");
		System.out.println("【0-退出充值】");
		double i = FilterInputMismatch.nextDouble();
		if (i < 0) {
			System.out.println("输入金额错误哦！");
			return false;
		} else if (i == 0) {
			System.out.println("退出充值！");
			return false;
		} else {
			// 充值金额加上用户余额
			i += MainMenuController.user.getBalance();
			MainMenuController.user.setBalance(i);
			boolean flag = new UserDaoImpl().update(MainMenuController.user);
			if (flag) {
				System.out.println("支付成功！快去购买宝贝吧~~");
				return true;
			} else {
				System.out.println("支付失败！呜呜呜~~");
				return false;
			}
		}
	}

	/**
	 * 获取用户的方法
	 * 
	 * @param id
	 *            用户id
	 * @return User对象
	 */
	public static User getUser(int id) {
		List<User> list = new UserDaoImpl().selectAll();
		for (User user : list) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
}
